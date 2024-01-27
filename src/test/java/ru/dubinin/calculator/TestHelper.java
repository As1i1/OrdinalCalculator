package ru.dubinin.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestHelper {
    static void setup(String path, List<String> dataTests) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path));
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                dataTests.add(scanner.nextLine());
            }
        }
    }

    static List<DynamicTest> dynamicTestsEquals(List<String> dataTests, String result) {
        List<DynamicTest> tests = new ArrayList<>();
        for (int i = 0; i < dataTests.size(); ++i) {
            int finalI = i;
            tests.add(DynamicTest.dynamicTest("Equals test " + (i + 1), () ->
                    Assertions.assertEquals(Main.comparing(dataTests.get(finalI)), result, dataTests.get(finalI))));
        }
        return tests;
    }
}
