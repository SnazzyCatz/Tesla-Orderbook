package com.sg.orderbook.dao;

import com.sg.orderbook.dto.Order;

import java.util.Comparator;

public class CustomOrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
