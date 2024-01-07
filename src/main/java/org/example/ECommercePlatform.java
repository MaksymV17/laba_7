package com.example.ecommerce;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class ECommercePlatform {
    private Map<Integer, User> users;
    private Map<Integer, Product> products;
    private Map<Integer, Order> orders;

    public ECommercePlatform() {
        this.users = new HashMap<>();
        this.products = new HashMap<>();
        this.orders = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Order createOrder(Integer userId) {
        User user = users.get(userId);
        if (user != null && !user.getCart().isEmpty()) {
            List<OrderItem> orderItems = user.getCart().entrySet().stream()
                    .map(entry -> new OrderItem(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            Order order = new Order(generateOrderId(), userId, orderItems);
            orders.put(order.getId(), order);

            for (OrderItem orderItem : orderItems) {
                Product product = orderItem.getProduct();
                int quantity = orderItem.getQuantity();
                int currentStock = product.getStock();

                if (currentStock >= quantity) {
                    product.setStock(currentStock - quantity);
                } else {
                    product.setStock(0);
                }
            }

            user.clearCart();
            return order;
        }
        return null;
    }



    public User getUser(Integer userId) {
        return users.get(userId);
    }

    public Product getProduct(Integer productId) {
        return products.get(productId);
    }

    public Order getOrder(Integer orderId) {
        return orders.get(orderId);
    }

    private Integer generateOrderId() {
        return orders.size() + 1;
    }

    public String displayAvailableProducts() {
        return displayProducts(getAllProducts());
    }

    public String displayUsers() {
        StringBuilder result = new StringBuilder("===== Users =====\n");

        for (User user : users.values()) {
            result.append(user).append("\n");
        }

        result.append("=================\n");
        return result.toString();
    }

    public String displayOrders() {
        StringBuilder result = new StringBuilder("===== Orders =====\n");

        for (Order order : orders.values()) {
            result.append(order).append("\n");
        }

        result.append("=================\n");
        return result.toString();
    }

    @Override
    public String toString() {
        return "ECommercePlatform{" +
                "users=" + users +
                ", products=" + products +
                ", orders=" + orders +
                '}';
    }

    public Map<Integer, Product> getAllProducts() {
        return new HashMap<>(products);
    }

    public String displayProducts(Map<Integer, Product> products) {
        StringBuilder result = new StringBuilder();

        for (Product product : products.values()) {
            result.append("Product ID: ").append(product.getId()).append("\n");
            result.append("Name: ").append(product.getName()).append("\n");
            result.append("Price: $").append(product.getPrice()).append("\n");
            result.append("Stock: ").append(product.getStock()).append("\n");
            result.append("----------------------\n");
        }

        return result.toString();
    }
}