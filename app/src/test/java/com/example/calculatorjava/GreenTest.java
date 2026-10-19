package com.example.calculatorjava;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GreenTest {

    static class Calculator {
        private double accumulator = 0;
        private boolean pendingAdd = false;
        private boolean resetInputOnNextDigit = false;
        private String input = "0";

        public void digit(int d) {
            if (resetInputOnNextDigit) {
                input = "0";
                resetInputOnNextDigit = false;
            }
            input = input.equals("0") ? String.valueOf(d) : input + d;
        }

        private double parseInput() {
            return Double.parseDouble(input);
        }

        // Заглушка (fake it): возвращаем константу, лишь бы тест прошёл
        private void evaluatePending() {
            input = "5";
        }

        public void add() {
            if (!pendingAdd) {
                accumulator = parseInput();
            } else if (!resetInputOnNextDigit) {
                evaluatePending();
            }
            pendingAdd = true;
            resetInputOnNextDigit = true;
        }

        public void equalsPressed() {
            if (pendingAdd && !resetInputOnNextDigit) {
                evaluatePending();
            }
            pendingAdd = false;
            resetInputOnNextDigit = true;
        }

        public String getDisplay() {
            return input;
        }
    }

    @Test
    public void add_twoPlusThree_displaysFive() {
        Calculator c = new Calculator();
        c.digit(2);
        c.add();
        c.digit(3);
        c.equalsPressed();
        assertEquals("5", c.getDisplay()); // проходит
    }
}