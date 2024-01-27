package ru.dubinin.calculator;

import ru.dubinin.calculator.KNF.KNFWrapper;
import ru.dubinin.calculator.expression.Expression;
import ru.dubinin.calculator.parser.ExpressionParser;

import java.util.Scanner;

public class Main {
    private static final ExpressionParser parser = new ExpressionParser();

    public static String comparing(String data) {
        String[] strings = data.split("=");
        Expression e1 = parser.parse(strings[0]);
        Expression e2 = parser.parse(strings[1]);
        KNFWrapper k1 = e1.toKNF();
        KNFWrapper k2 = e2.toKNF();
        int resultComparing = k1.compare(k2);
        if (resultComparing == 0) {
            return "Равны";
        } else {
            return resultComparing < 0 ? "Меньше" : "Больше";
        }
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println(comparing(in.nextLine()));
        }
    }
}