package testing.demo.computation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class CalculatorTest {

    private Calculator calculator;
    private static int testNumber = 0;

    // Replace YOUR_USERNAME with your username
    private static final String USERNAME =
            "Carmella Gullatt";

    @BeforeAll
    static void beforeAll() {
        System.out.println(
            "[Before All] Calculator Test suite starting ... by "
            + USERNAME);
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        calculator = new Calculator();
        testNumber++;

        System.out.println(
            "[Before Each] Starting Test #" + testNumber
            + ": " + testInfo.getDisplayName());
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println(
            "[After Each] Finished Test #" + testNumber
            + ": " + testInfo.getDisplayName());
    }

    @AfterAll
    static void afterAll() {
        System.out.println(
            "[After All] completed " + testNumber
            + " test invocations by " + USERNAME);
    }

    // Test 1: Addition using @MethodSource

    static Stream<Arguments> additionData() {
        return Stream.of(
            Arguments.of(100, 2, 102),
            Arguments.of(100, -2, 98),
            Arguments.of(-100, 2, -98),
            Arguments.of(-100, -2, -102)
        );
    }

    @ParameterizedTest(
        name = "{0} + {1} = {2}")
    @MethodSource("additionData")
    void testAdd(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    // Test 2: Subtraction using @CsvSource

    @ParameterizedTest(
        name = "{0} - {1} = {2}")
    @CsvSource({
        "100, 2, 98",
        "100, -2, 102",
        "-100, 2, -102",
        "-100, -2, -98"
    })
    void testSubtract(int a, int b, int expected) {
        assertEquals(
            expected, calculator.subtract(a, b));
    }

    // Test 3: Multiplication using @CsvFileSource

    @ParameterizedTest(
        name = "{0} * {1} = {2}")
    @CsvFileSource(
        resources = "/multiplication.csv")
    void testMultiple(int a, int b, int expected) {
        assertEquals(
            expected, calculator.multiple(a, b));
    }

    // Test 4: Negative test for division by zero

    @Test
    void divide_byZero() {
        IllegalArgumentException exception =
            assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(100, 0)
            );

        assertEquals(
            "Denominator value cannot be zero.",
            exception.getMessage());
    }  
    // Test 5: Division with a nonzero denominator
    @Test
    void divide_byNonZero() {
    	assertEquals(5, calculator.divide(10,  2));
    }
}
