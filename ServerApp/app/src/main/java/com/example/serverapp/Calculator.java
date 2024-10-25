package com.example.serverapp;

public class Calculator {

    public static float calculateResult(String operator, int num1, int num2) {

        switch (operator) {
            case "+":
                return num1 + num2;

            case  "-":
                return num1 - num2;

            case "*":
                return num1 * num2;

            case "/":

                if (num2 == 0) throw new ArithmeticException("Cannot divide by zero!");
                return (float) num1 / num2;
        }

        throw new ArithmeticException("Invalid or unknown operator!");
    }
}
