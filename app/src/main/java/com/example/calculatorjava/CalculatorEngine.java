package com.example.calculatorjava;

import java.math.BigDecimal;

/**
 * Модель калькулятора, умеющего только складывать. Не зависит от Android:
 * вся логика вычислений находится здесь, поэтому её можно проверять
 * быстрыми unit-тестами.
 */
public class CalculatorEngine {

    private static final String ZERO = "0";
    private static final String MINUS = "-";
    private static final String DOT = ".";
    private static final String PLUS_SYMBOL = "+";
    private static final int MAX_INPUT_LENGTH = 15;

    private String input = ZERO;              // число, которое видит пользователь
    private String lastExpression = "";       // история после «=», например «2 + 3 =»
    private BigDecimal accumulator;           // накопленное значение (левый операнд)
    private boolean pendingAdd;               // ожидается второе слагаемое
    private boolean resetInputOnNextDigit;    // следующая цифра начинает новое число

    /** Ввод цифры 0..9. */
    public void inputDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("digit must be in 0..9: " + digit);
        }
        prepareForInput();
        if (digitCount() >= MAX_INPUT_LENGTH) {
            return;
        }
        input = input.equals(ZERO) ? String.valueOf(digit) : input + digit;
    }

    /** Ввод десятичной точки (в числе допускается только одна). */
    public void inputDot() {
        prepareForInput();
        if (!input.contains(DOT)) {
            input += DOT;
        }
    }

    /** Нажатие «+». Если сложение уже ожидает второго числа, оно сначала вычисляется. */
    public void add() {
        if (!pendingAdd) {
            accumulator = parseInput();
        } else if (!resetInputOnNextDigit) {
            evaluatePending();
        }
        pendingAdd = true;
        resetInputOnNextDigit = true;
    }

    /** Нажатие «=». */
    public void calculate() {
        if (!pendingAdd) {
            return;
        }
        lastExpression = format(accumulator) + " " + PLUS_SYMBOL
                + " " + format(parseInput()) + " =";
        evaluatePending();
        pendingAdd = false;
        resetInputOnNextDigit = true;
    }

    /** Полный сброс (кнопка C). */
    public void clear() {
        input = ZERO;
        lastExpression = "";
        accumulator = null;
        pendingAdd = false;
        resetInputOnNextDigit = false;
    }

    /** Удаление последнего символа (кнопка ⌫). */
    public void backspace() {
        if (resetInputOnNextDigit) {
            return;
        }
        boolean lastCharIsOnlyDigit = input.length() == 1
                || (input.length() == 2 && input.startsWith(MINUS));
        input = lastCharIsOnlyDigit ? ZERO : input.substring(0, input.length() - 1);
    }

    /** Смена знака (кнопка ±). */
    public void toggleSign() {
        if (input.equals(ZERO)) {
            return;
        }
        input = input.startsWith(MINUS) ? input.substring(1) : MINUS + input;
    }

    public String getDisplay() {
        return input;
    }

    public String getExpression() {
        if (pendingAdd) {
            return format(accumulator) + " " + PLUS_SYMBOL;
        }
        return lastExpression;
    }

    // ---------- внутренняя логика ----------

    /** Подготовка к вводу: начало нового числа, если это нужно. */
    private void prepareForInput() {
        if (resetInputOnNextDigit) {
            input = ZERO;
            resetInputOnNextDigit = false;
            if (!pendingAdd) {
                lastExpression = "";
            }
        }
    }

    /** Складывает accumulator и input, результат кладёт в input. */
    private void evaluatePending() {
        accumulator = accumulator.add(parseInput());
        input = format(accumulator);
    }

    private BigDecimal parseInput() {
        String text = input.endsWith(DOT) ? input.substring(0, input.length() - 1) : input;
        return new BigDecimal(text);
    }

    private int digitCount() {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    private static String format(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }
}