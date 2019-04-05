package com.wind.tiki;

import java.util.Scanner;

public class Assignment {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfCell = scanner.nextInt();
        scanner.nextLine();

        SimpleExcel excelCalculator = new SimpleExcel();
        String cellName = null;

        for (int i = 0; i < numberOfCell * 2; i++) {
            String input = scanner.nextLine();
            if ((i % 2) > 0) {
                excelCalculator.addCell(cellName, input);
            } else
                cellName = input;
        }

        if (!excelCalculator.validateCircular()) {
            excelCalculator.computeFormula();
            excelCalculator.printResult();
        }

    }
}
