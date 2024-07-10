package ru.clevertec.check;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParameterParser {
    private static final String FILE_PATH = "src/result.csv";
    // Метод для парсинга аргументов и создания объекта SalesReceipt
    public static SalesReceipt parse(String[] args) {
        boolean hasCard = false;
        double balance = 0;
        int card = 0;
        Product product;
        ProductFactory productFactory = new ProductFactory();
        Map<Product, Integer> productMap = new HashMap<>();


        for (String arg : args) {
            if (arg.startsWith("balanceDebitCard=")) {
                balance = Double.parseDouble(arg.split("=")[1]);

            } else if (arg.startsWith("discountCard=")) {
                card = Integer.parseInt(arg.split("=")[1]);
                hasCard = true;
            } else {
                String[] itemData = arg.split("-");
                int productId = Integer.parseInt(itemData[0]);
                int quantity = Integer.parseInt(itemData[1]);
                product = productFactory.createProduct(productId);

                if (product != null) {
                    productMap.put(product, productMap.getOrDefault(product, 0) + quantity);
                }
            }
        }


        SalesReceipt.Builder builder = new SalesReceipt.Builder();
        builder.setBalance(balance);
        builder.setProducts(productMap);

        if (hasCard) {
            builder.setCard(card);
        }

        return builder.build();
    }
    // Метод для записи ошибки в файл
    public static void writeError(String errorMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(errorMessage);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write error to file", e);
        }
    }
}
