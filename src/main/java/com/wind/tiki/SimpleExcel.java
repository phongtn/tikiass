package com.wind.tiki;

import java.util.*;

public class SimpleExcel {

    private Map<String, Double> cellData;

    private Map<String, String> postfixNotation;

    public SimpleExcel() {
        this.cellData = new TreeMap<>();
        this.postfixNotation = new HashMap<>();
    }

    public void addCell(String name, String value) {
        boolean isNumber = this.isNumber(value);
        if (isNumber) {
            cellData.put(name, Double.parseDouble(value));
        } else
            postfixNotation.put(name, value);
    }

    private boolean isNumber(String input) {
        return input.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperator(String next) {
        return (next.equals("+") ||
                next.equals("-") ||
                next.equals("*") ||
                next.equals("/"));
    }

    private Double calculatePostfixNotation(String input) {
        Scanner scan = new Scanner(input.trim());
        Stack<Double> stack = new Stack<>();
        while (scan.hasNext()) {
            String next = scan.next().trim();

            if (this.isOperator(next)) {
                if (stack.size() > 1) {
                    switch (next) {
                        case "+":
                            stack.push(stack.pop() + stack.pop());
                            break;
                        case "-":
                            stack.push(-stack.pop() + stack.pop());
                            break;
                        case "*":
                            stack.push(stack.pop() * stack.pop());
                            break;
                        case "/":
                            double first = stack.pop();
                            double second = stack.pop();

                            if (first == 0) {
                                System.out.println("Divide by zero.");
                            } else {
                                stack.push(second / first);
                            }
                            break;
                    }
                } else {
                    System.out.println("Missing number values.");
                }
            } else {
                Double cellValue = this.isNumber(next) ? Double.parseDouble(next) : cellData.get(next);
                stack.push(cellValue);
            }
        }
        return stack.pop();
    }


    /**
     * O(n^2)
     */
    public boolean validateCircular() {
        Set<String> key = postfixNotation.keySet();
        for (String k : key) {
            String v = postfixNotation.get(k);

            for (Map.Entry<String, String> entry : postfixNotation.entrySet()) {
                String k2 = entry.getKey();
                String v2 = entry.getValue();
                if (!k.equals(k2)) {
                    if (v2.contains(k) && v.contains(k2)) {
                        System.out.println("Circular dependency between " + k + " and " + k2 + " detected ");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void computeFormula() {
        postfixNotation.forEach((name, formula) -> {
            Double val = this.calculatePostfixNotation(formula);
            cellData.put(name, val);
        });
    }

    public void printResult() {
        cellData.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v);
        });
    }
}
