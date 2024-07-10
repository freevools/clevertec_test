package ru.clevertec.check;

import java.util.Map;

public class SalesReceipt {
    private final double balance;
    private final Integer card;
    private final Map<Product, Integer> products;
    private final double totalAmount;

    private SalesReceipt(Builder builder) {
        this.balance = builder.balance;
        this.card = builder.card;
        this.products = builder.products;
        this.totalAmount = calculateTotalAmount();
    }

    public double getBalance() {
        return balance;
    }

    public Integer getCard() {
        return card;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private double calculateTotalAmount() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }

    public static class Builder {
        private double balance;
        private Integer card;
        private Map<Product, Integer> products;

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder setCard(Integer card) {
            this.card = card;
            return this;
        }

        public Builder setProducts(Map<Product, Integer> products) {
            this.products = products;
            return this;
        }

        public SalesReceipt build() {
            return new SalesReceipt(this);
        }
    }
}
