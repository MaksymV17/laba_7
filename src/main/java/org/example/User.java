package com.example.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final Integer id;
    private final String username;
    private final Map<Product, Integer> cart;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        this.cart = new HashMap<>();
    }

    public Integer getId() {
        return id;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void addToCart(Product product, int quantity) {
        cart.put(product, quantity);
    }

    public void clearCart() {
        cart.clear();
    }

    private String displayCart() {
        StringBuilder result = new StringBuilder("cart={");

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            result.append(entry.getKey().getName()).append("=").append(entry.getValue()).append(", ");
        }

        if (result.length() > "cart={".length()) {
            result.setLength(result.length() - 2);
        }

        result.append("}");

        return result.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", " + displayCart() +
                '}';
    }
}