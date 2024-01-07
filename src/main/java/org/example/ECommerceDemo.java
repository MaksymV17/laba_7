package com.example.ecommerce;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class ECommerceDemo {
    public static void main(String[] args) {
        ECommercePlatform ecommercePlatform = new ECommercePlatform();
        UserInteractionHandler userInteractionHandler = new UserInteractionHandler(ecommercePlatform);

        User user1 = new User(1, "user1");
        User user2 = new User(2, "user2");
        User user3 = new User(3, "user3");
        User user4 = new User(4, "user4");
        User user5 = new User(5, "user5");
        ecommercePlatform.addUser(user1);
        ecommercePlatform.addUser(user2);
        ecommercePlatform.addUser(user3);
        ecommercePlatform.addUser(user4);
        ecommercePlatform.addUser(user5);


        Product product1 = new Product(1, "Печиво", 6, 50);
        Product product2 = new Product(2, "Цукерки", 12, 30);
        Product product3 = new Product(3, "Тістечка", 25, 40);
        Product product4 = new Product(4, "Карамаль", 8, 100);
        Product product5 = new Product(5, "Пудель", 28, 20);

        ecommercePlatform.addProduct(product1);
        ecommercePlatform.addProduct(product2);
        ecommercePlatform.addProduct(product3);
        ecommercePlatform.addProduct(product4);
        ecommercePlatform.addProduct(product5);

        userInteractionHandler.simulateInteractions();

        System.out.println("Стан електронної комерції після взаємодії:");
        System.out.println(ecommercePlatform);
    }
}

class UserInteractionHandler {
    private final ECommercePlatform ecommercePlatform;
    private final Scanner scanner;

    public UserInteractionHandler(ECommercePlatform ecommercePlatform) {
        this.ecommercePlatform = ecommercePlatform;
        this.scanner = new Scanner(System.in);
    }

    public void simulateInteractions() {
        // Simulate interactions here
        // For example:
        // - Display available products
        // - Add products to the cart
        // - Create orders
        // - Display users, orders, etc.

        displayMainMenu();
    }

    private void displayMainMenu() {
        while (true) {
            System.out.println("Меню");
            System.out.println("1. Показати доступні товари");
            System.out.println("2. Додати товари в кошик");
            System.out.println("3. Створити замовлення");
            System.out.println("4. Показати користувачів");
            System.out.println("5. Показати замовлення");
            System.out.println("6. Вийти");

            int choice = getIntInput("Введіть свій вибір\n: ");
            switch (choice) {
                case 1:
                    displayAvailableProductsMenu();
                    break;
                case 2:
                    addToCartMenu();
                    break;
                case 3:
                    createOrderMenu();
                    break;
                case 4:
                    System.out.println("Користувачі: " + ecommercePlatform.displayUsers());
                    break;
                case 5:
                    System.out.println("Замовлення: " + ecommercePlatform.displayOrders());
                    break;
                case 6:
                    System.out.println("Вихід");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void displayAvailableProductsMenu() {
        System.out.println("Доступні товари");
        System.out.println("1. Показати всі товари");
        System.out.println("2. Показати товари відсортовані за назвою");
        System.out.println("3. Показати товари відсортовані за ціною");
        System.out.println("4. Показати товари відсортовані за залишками на складі");
        System.out.println("5. Показати товари з наявністю на складі");
        System.out.println("6. Повернутися до головного меню");

        int choice = getIntInput("Введіть ваш вибір: ");
        switch (choice) {
            case 1:
                System.out.println("Доступні товари:");
                displayProductsNewLine(ecommercePlatform.getAllProducts());
                break;
            case 2:
                displaySortedProducts(new ProductNameComparator());
                break;
            case 3:
                displaySortedProducts(new ProductPriceComparator());
                break;
            case 4:
                displaySortedProducts(new ProductStockComparator());
                break;
            case 5:
                System.out.println("Доступні товари: " + ecommercePlatform.displayAvailableProducts());
                break;
            case 6:
                return;
            default:
                System.out.println("Невірний вибір. Спробуйте ще раз.");
        }
    }

    private void displayProductsNewLine(Map<Integer, Product> products) {
        for (Product product : products.values()) {
            System.out.println("Ідентифікатор товару:: " + product.getId());
            System.out.println("Назва: " + product.getName());
            System.out.println("Ціна: " + product.getPrice());
            System.out.println("Залишок на складі: " + product.getStock());
            System.out.println("----------------------");
        }
    }

    private void addToCartMenu() {
        System.out.println("Додавання товарів в кошик");
        int userId = getIntInput("\"Введіть ідентифікатор користувача: ");
        User user = ecommercePlatform.getUser(userId);

        if (user != null) {
            System.out.println("Доступні товари:");
            displayProductsNewLine(ecommercePlatform.getAllProducts());

            int productId = getIntInput("Введіть ідентифікатор товару для додавання в кошик: ");
            Product product = ecommercePlatform.getProduct(productId);

            if (product != null) {
                int quantity = getIntInput("\"Введіть кількість: ");
                user.addToCart(product, quantity);
                System.out.println("Товар успішно додано в кошик.\n");
            } else {
                System.out.println("Упсс... Товар не знайдено.");
            }
        } else {
            System.out.println("Користувача не знайдено.");
        }
    }

    private void createOrderMenu() {
        System.out.println("===== Create Order =====");
        int userId = getIntInput("Enter user ID: ");
        User user = ecommercePlatform.getUser(userId);

        if (user != null && !user.getCart().isEmpty()) {
            Order order = ecommercePlatform.createOrder(user.getId());
            if (order != null) {
                System.out.println("Створення замовлення " + order);
            } else {
                System.out.println("Введіть ідентифікатор користувача:");
            }
        } else {
            System.out.println("Користувача не знайдено або кошик порожній.\n");
        }
    }

    private void displaySortedProducts(Comparator<Product> comparator) {
        List<Product> productsList = new ArrayList<>(ecommercePlatform.getAllProducts().values());
        productsList.sort(comparator);

        System.out.println("Sorted Products");
        for (Product product : productsList) {
            System.out.println(product);
        }
     }

    private void displayFilteredProducts(int minStock) {
        List<Product> filteredProducts = ecommercePlatform.getAllProducts().values().stream()
                .filter(product -> product.getStock() >= minStock)
                .collect(Collectors.toList());

        System.out.println("Відфільтровані продукти");
        for (Product product : filteredProducts) {
            System.out.println(product);
        }
     }


    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Неправильні дані. Будь ласка, введіть номер.\n");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    private void displayProducts(Map<Integer, Product> products) {
        for (Product product : products.values()) {
            System.out.println("Індифікатор товару: " + product.getId());
            System.out.println("Назва: " + product.getName());
            System.out.println("Ціна: " + product.getPrice());
            System.out.println("\"Залишок на складі: " + product.getStock());

        }
    }
}

class ProductNameComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getName().compareTo(p2.getName());
    }
}

class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return Double.compare(p1.getPrice(), p2.getPrice());
    }
}

class ProductStockComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return Integer.compare(p1.getStock(), p2.getStock());
    }
}