package com.example.serverapp;

import static com.example.serverapp.Calculator.calculateResult;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorTest {

    @Test
    public void testSum() {
        assertEquals(10, calculateResult("+", 5, 5), 0);
    }

    @Test
    public void testSubtraction() {
        assertEquals(-3, calculateResult("-", 2, 5), 0);
    }

    @Test
    public void testMultiplication() {
        assertEquals(35, calculateResult("*", 7, 5), 0);
    }

    // Here we see that 1/10 is not finitely represented in floating point notation, need to add a small delta
    @Test
    public void testDivision() {
        assertEquals(0.1, calculateResult("/", 1, 10), 0.001);
    }

    @Test
    public void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculateResult("/", 1, 0));
    }
}