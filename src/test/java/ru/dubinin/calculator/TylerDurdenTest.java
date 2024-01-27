package ru.dubinin.calculator;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TylerDurdenTest {

    private static final List<String> dataSimple = new ArrayList<>();
    private static final List<String> dataBigTests = new ArrayList<>();

    private static final String currentPath = "src/test/java/ru/dubinin/calculator/";


    @BeforeAll
    public static void setup() throws IOException {
        TestHelper.setup(currentPath + "testdata/Tyler/Simple", dataSimple);
        TestHelper.setup(currentPath + "testdata/Tyler/BigTests", dataBigTests);
        System.out.println("Test from Tyler Durden");
    }

    @TestFactory
    List<DynamicTest> dynamicTestsSimple() {
        return TestHelper.dynamicTestsEquals(dataSimple, "Равны");
    }

    @TestFactory
    List<DynamicTest> dynamicBigTests() {
        return TestHelper.dynamicTestsEquals(dataBigTests, "Равны");
    }
}
