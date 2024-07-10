package ru.clevertec.check;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CsvWriter {

    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy;HH:mm:ss";
    private static final String HEADER_DATE_TIME = "Date;Time";
    private static final String HEADER_PRODUCTS = "QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL";
    private static final String HEADER_DISCOUNT_CARD = "DISCOUNT CARD;DISCOUNT PERCENTAGE";
    private static final String HEADER_TOTAL = "TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT";
    private static final String ERROR_NOT_ENOUGH_MONEY = "ERROR\nNOT ENOUGH MONEY";
    private static final String FILE_PATH = "src/result.csv";

    public void writeFile(SalesReceipt salesReceipt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            boolean hasCard = salesReceipt.getCard() != null;
            int card = hasCard ? salesReceipt.getCard() : -1;
            int discountOnCard = new DiscountChecker().checkCardDiscount(card);

            double[] totals = calculateTotals(salesReceipt);
            double totalSales = totals[0];
            double totalDiscount = totals[1];
            double totalSalesWithDiscount = totals[2];
            double balance = salesReceipt.getBalance();

            if (totalSalesWithDiscount > balance){
                writer.write(ERROR_NOT_ENOUGH_MONEY);
                writer.newLine();
                System.out.println("NOT ENOUGH MONEY");
            }else{
                writeDateTime(writer);
                writeProducts(salesReceipt, writer, card);

                if (hasCard) {
                    writeCard(writer, discountOnCard, card);
                }

                writeTotals(writer, totalSales, totalDiscount);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Метод для расчета итогов (сумма, скидка, сумма со скидкой)
    private double[] calculateTotals(SalesReceipt salesReceipt) throws IOException {
        double totalDiscount = 0;
        double totalSales = 0;

        for (Map.Entry<Product, Integer> entry : salesReceipt.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            int discount = new DiscountChecker().checkDiscount(salesReceipt.getCard() != null ? salesReceipt.getCard() : -1,
                    product,
                    quantity);
            double productPrice = product.getPrice();
            double totalPrice = productPrice * quantity;
            double discountAmount = productPrice * discount / 100 * quantity;

            totalSales += totalPrice;
            totalDiscount += discountAmount;
        }
        return new double[]{totalSales, totalDiscount, (totalSales - totalDiscount)};
    }
    // Метод для записи итоговых сумм
    private static void writeTotals(BufferedWriter writer, double totalSales, double totalDiscount) throws IOException {
        writer.write(HEADER_TOTAL);
        writer.newLine();
        writer.write(String.format("%.2f$;%.2f$;%.2f$",
                totalSales,
                totalDiscount,
                (totalSales - totalDiscount)));
        writer.newLine();
    }
    // Метод для записи информации о скидочной карте
    private static void writeCard(BufferedWriter writer, int discountOnCard, int card ) throws IOException {
        writer.write(HEADER_DISCOUNT_CARD);
        writer.newLine();
        writer.write(String.format("%d;%d%%;",
                card,
                discountOnCard));
        writer.newLine();
        writer.newLine();
    }
    // Метод для записи информации о продуктах
    private void writeProducts(SalesReceipt salesReceipt, BufferedWriter writer, int card) throws IOException {

        writer.write(HEADER_PRODUCTS);
        writer.newLine();

        for (Map.Entry<Product, Integer> entry : salesReceipt.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            int discount = new DiscountChecker().checkDiscount(card, product, quantity);
            double productPrice = product.getPrice();
            double totalPrice = productPrice * quantity;
            double discountAmount = productPrice * discount / 100 * quantity;

            writer.write(String.format("%d;%s;%.2f$;%.2f$;%.2f$",
                    quantity,
                    product.getDescription(),
                    productPrice,
                    discountAmount,
                    totalPrice));
            writer.newLine();
        }
        writer.newLine();
    }
    // Метод для записи текущей даты и времени
    private void writeDateTime(BufferedWriter writer) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

        writer.write(HEADER_DATE_TIME);
        writer.newLine();
        writer.write(now.format(dateTimeFormatter));
        writer.newLine();
        writer.newLine();
    }
}
