package com.broker.Service;

import com.broker.entity.Asset;
import com.broker.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> listAssets(Long customerId) {
        return assetRepository.findAll().stream()
            .filter(asset -> asset.getCustomerId().equals(customerId))
            .collect(Collectors.toList());
    }
}
