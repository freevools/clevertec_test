package ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductFactory {

    private static final String CSV_FILE_PATH = "src/main/resources/products.csv";

    public Product createProduct(int productId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                int id = Integer.parseInt(values[0]);
                if (id == productId) {
                    String description = values[1];
                    double price = Double.parseDouble(values[2]);
                    int quantityInStock = Integer.parseInt(values[3]);
                    boolean isWholesale = values[4].equals("+");

                    return new Product(id, description, price, quantityInStock, isWholesale);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
