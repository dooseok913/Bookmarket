package com.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue
    private Long orderId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "shipping_id")
    private Shipping shipping;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_item_id")
    private Map<String, OrderItem> orderItems = new HashMap<>();

    private BigDecimal grandTotal;

    @OneToOne(mappedBy =  "order", cascade = CascadeType.ALL)
    private Payment payment;



}
