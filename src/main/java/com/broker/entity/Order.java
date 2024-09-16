package com.broker.entity;


import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    private double size;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime createDate;
}

