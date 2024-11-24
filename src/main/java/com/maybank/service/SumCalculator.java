package com.maybank.service;
import java.util.*;
class Row {
    private String coCode;
    private String currencyCode;
    private String unitNo;
    private String drCrInd;
    private double postAmt;

    public Row(String coCode, String currencyCode, String unitNo, String drCrInd, double postAmt) {
        this.coCode = coCode;
        this.currencyCode = currencyCode;
        this.unitNo = unitNo;
        this.drCrInd = drCrInd;
        this.postAmt = postAmt;
    }

    public String getCoCode() {
        return coCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public String getDrCrInd() {
        return drCrInd;
    }

    public double getPostAmt() {
        return postAmt;
    }
}

public class SumCalculator {

    public static void main(String[] args) {
        // Sample data
        List<Row> data = Arrays.asList(
                new Row("003", "AUD", "190", "C", 120),
                new Row("003", "AUD", "190", "D", 80),
                new Row("003", "AUD", "190", "C", 180),
                new Row("003", "AUD", "190", "D", 120),

                new Row("003", "AUD", "300", "C", 47910.82),
                new Row("003", "AUD", "300", "D", 47910.82),
                new Row("003", "CAD", "300", "C", 430.03),
                new Row("003", "CAD", "300", "D", 430.03)
        );

        // Group and calculate combined sums for C and D
        calculateCombinedSums(data);
    }

    private static void calculateCombinedSums(List<Row> data) {
        // Map to group by CO_CODE -> CURCY_3_CODE -> UNIT_NO
        Map<String, Map<String, Map<String, Double>>> result = new HashMap<>();

        // Process each row in the data
        for (Row row : data) {
            String coCode = row.getCoCode();
            String currencyCode = row.getCurrencyCode();
            String unitNo = row.getUnitNo();
            double postAmt = row.getPostAmt();

            // Group and aggregate
            result.putIfAbsent(coCode, new HashMap<>());
            Map<String, Map<String, Double>> currencyMap = result.get(coCode);
            currencyMap.putIfAbsent(currencyCode, new HashMap<>());
            Map<String, Double> unitMap = currencyMap.get(currencyCode);
            unitMap.put(unitNo, unitMap.getOrDefault(unitNo, 0.0) + postAmt);

        }

        // Print the results
        // Print the results
        for (Map.Entry<String, Map<String, Map<String, Double>>> coCodeEntry : result.entrySet()) {
            String coCode = coCodeEntry.getKey();
            System.out.println("CO_CODE: " + coCode);

            for (Map.Entry<String, Map<String, Double>> currencyEntry : coCodeEntry.getValue().entrySet()) {
                String currency = currencyEntry.getKey();
                System.out.println("  CURCY_3_CODE: " + currency);

                for (Map.Entry<String, Double> unitEntry : currencyEntry.getValue().entrySet()) {
                    Map<String, Double> drCrMap = currencyEntry.getValue();
                    double creditSum = drCrMap.getOrDefault("C", 0.0);
                    double debitSum = drCrMap.getOrDefault("D", 0.0);
                    System.out.println("    UNIT_NO: " + unitEntry.getKey());
                    System.out.println("      Credit Sum (C): " + creditSum);
                    System.out.println("      Debit Sum (D): " + debitSum);
                    // Calculate the difference
                    double difference = creditSum == debitSum ? 0 : Math.abs(creditSum - debitSum);

                    System.out.println("    UNIT_NO: " + unitEntry.getKey());
                    System.out.println("      Credit Sum (C): " + creditSum);
                    System.out.println("      Debit Sum (D): " + debitSum);
                    System.out.println("      Difference (C - D): " + difference);
                }
            }
        }
    }
}