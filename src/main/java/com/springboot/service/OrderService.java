package com.springboot.service;


import com.springboot.domain.Order;

public interface OrderService {
    void confirmOrder(String bookId, long quantity);
    Long saveOrder(Order order);
}
