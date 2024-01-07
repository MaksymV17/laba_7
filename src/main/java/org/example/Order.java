package com.example.ecommerce;

import java.util.List;

public class Order {
    private Integer id;
    private Integer userId;
    private List<OrderItem> orderItems;

    public Order(Integer id, Integer userId, List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Integer getUserId() {
        return userId;
    }

    public double calculateTotalPrice() {
        return orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getProduct().getPrice() * orderItem.getQuantity())
                .sum();
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderItems=" + orderItems +
                ", totalPrice=" + calculateTotalPrice() +
                '}';
    }
}