package com.example.calculatorjava;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RedTest {

    static class Calculator {
        private String input = "0";
        private boolean resetInputOnNextDigit = false;

        public void digit(int d) {
            if (resetInputOnNextDigit) {
                input = "0";
                resetInputOnNextDigit = false;
            }
            input = input.equals("0") ? String.valueOf(d) : input + d;
        }

        public void equalsPressed() { }

        public String getDisplay() {
            return input;
        }
        // метод add() пока не написан
    }

    @Test
    public void add_twoPlusThree_displaysFive() {
        Calculator c = new Calculator();
        c.digit(2);
        //c.add();            // ОШИБКА: cannot find symbol add()
        c.digit(3);
        c.equalsPressed();
        assertEquals("5", c.getDisplay());
    }
}