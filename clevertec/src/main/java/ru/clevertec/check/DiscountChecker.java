package ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Читает файл с картами и возвращает сколько процентов скидка с этой картой, если карты нет, то возвращает 0
public class DiscountChecker {
    private static final String DISCOUNT_CARDS_FILE_PATH = "src/main/resources/discountCards.csv";

    public int checkDiscount(int card, Product product, int quantity) throws IOException {
        int discount = checkCardDiscount(card);

        if (product.isWholesaleProduct() && quantity >= 5) {
            return 10;
        }

        return discount;
    }

    public int checkCardDiscount(int card) throws IOException {
        if (card == -1) {
            return 0;
        }

        int cardDiscount = 2;

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(DISCOUNT_CARDS_FILE_PATH));
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(";");
            if (card == Integer.parseInt(values[0])) {
                cardDiscount = Integer.parseInt(values[1]);
                break;
            }
        }


        return cardDiscount;
    }
}

