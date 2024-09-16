package com.broker.Service;

import com.broker.entity.Asset;
import com.broker.entity.Order;
import com.broker.entity.OrderSide;
import com.broker.entity.OrderStatus;
import com.broker.repository.AssetRepository;
import com.broker.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    public Order createOrder(Order order) throws Exception {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
            .orElseThrow(() -> new Exception("TRY Asset not found"));

        if (order.getSide() == OrderSide.BUY && asset.getUsableSize() < order.getPrice() * order.getSize()) {
            throw new Exception("Not enough TRY to place order");
        }

        asset.setUsableSize(asset.getUsableSize() - order.getPrice() * order.getSize());
        assetRepository.save(asset);

        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> listOrders(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    public void cancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new Exception("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new Exception("Only pending orders can be canceled");
        }

        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
            .orElseThrow(() -> new Exception("TRY Asset not found"));

        asset.setUsableSize(asset.getUsableSize() + order.getPrice() * order.getSize());
        assetRepository.save(asset);

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    public void matchOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be matched.");
        }

        // Update asset sizes
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
            .orElseThrow(() -> new RuntimeException("TRY asset not found"));
        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
            .orElse(new Asset(order.getCustomerId(), order.getAssetName(), 0.0, 0.0));

        if (order.getSide() == OrderSide.BUY) {
            asset.setSize(asset.getSize() + order.getSize());
            asset.setUsableSize(asset.getUsableSize() + order.getSize());
        } else if (order.getSide() == OrderSide.SELL) {
            if (asset.getUsableSize() < order.getSize()) {
                throw new IllegalStateException("Not enough asset to sell");
            }
            asset.setSize(asset.getSize() - order.getSize());
            asset.setUsableSize(asset.getUsableSize() - order.getSize());
        }

        order.setStatus(OrderStatus.MATCHED);
        orderRepository.save(order);
        assetRepository.save(asset);
    }
}
