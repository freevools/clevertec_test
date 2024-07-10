package ru.clevertec.check;

public class CheckRunner {
    private static final String ERROR_BAD_REQUEST = "ERROR\nBAD REQUEST";
    private static final String INTERNAL_SERVER_ERROR = "ERROR\nINTERNAL SERVER ERROR";
    public static void main(String[] args) {
        try {
            CsvWriter writer = new CsvWriter();
            SalesReceipt salesReceipt = null;
            try {
                salesReceipt = ParameterParser.parse(args);
            } catch (Exception e) {
                ParameterParser.writeError(ERROR_BAD_REQUEST);
                System.out.println("BAD REQUEST");
            }
            if (salesReceipt != null) {
                writer.writeFile(salesReceipt);
            }
        }catch (Exception e){
            ParameterParser.writeError(INTERNAL_SERVER_ERROR);
            System.out.println("INTERNAL SERVER ERROR");
        }
    }
}
