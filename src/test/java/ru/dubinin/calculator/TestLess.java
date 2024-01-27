package ru.dubinin.calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestLess {
    private static final List<String> dataTests = new ArrayList<>();

    private static final String currentPath = "src/test/java/ru/dubinin/calculator/";


    @BeforeAll
    public static void setup() throws IOException {
        TestHelper.setup(currentPath + "testdata/Less/tests", dataTests);
        System.out.println("Test Less");
    }

    @TestFactory
    List<DynamicTest> dynamicTestsGreater() {
        return TestHelper.dynamicTestsEquals(dataTests, "Меньше");
    }
}
