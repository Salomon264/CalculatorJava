package com.example.calculatorjava;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CalculatorEngineTest {

    private CalculatorEngine engine;

    @Before
    public void setUp() {
        engine = new CalculatorEngine();
    }

    @Test
    public void add_twoIntegers() {
        engine.inputDigit(2);
        engine.add();
        engine.inputDigit(3);
        engine.calculate();

        assertEquals("5", engine.getDisplay());
        assertEquals("2 + 3 =", engine.getExpression());
    }

    @Test
    public void add_multiDigitNumbers() {
        engine.inputDigit(1);
        engine.inputDigit(2);
        engine.inputDigit(0);
        engine.add();
        engine.inputDigit(8);
        engine.inputDigit(0);
        engine.calculate();

        assertEquals("200", engine.getDisplay());
    }

    @Test
    public void add_decimalsHaveNoFloatingPointError() {
        engine.inputDigit(0);
        engine.inputDot();
        engine.inputDigit(1);
        engine.add();
        engine.inputDigit(0);
        engine.inputDot();
        engine.inputDigit(2);
        engine.calculate();

        assertEquals("0.3", engine.getDisplay());
    }

    @Test
    public void add_negativeNumber() {
        engine.inputDigit(5);
        engine.toggleSign();          // -5
        engine.add();
        engine.inputDigit(3);
        engine.calculate();

        assertEquals("-2", engine.getDisplay());
    }

    @Test
    public void add_resultCanBeZero() {
        engine.inputDigit(4);
        engine.toggleSign();          // -4
        engine.add();
        engine.inputDigit(4);
        engine.calculate();

        assertEquals("0", engine.getDisplay());
    }

    @Test
    public void add_chainShowsIntermediateResult() {
        engine.inputDigit(1);
        engine.add();
        engine.inputDigit(2);
        engine.add();                 // 1 + 2 вычисляется при втором «+»

        assertEquals("3", engine.getDisplay());
        assertEquals("3 +", engine.getExpression());

        engine.inputDigit(3);
        engine.calculate();

        assertEquals("6", engine.getDisplay());
        assertEquals("3 + 3 =", engine.getExpression());
    }

    @Test
    public void add_canContinueFromPreviousResult() {
        engine.inputDigit(2);
        engine.add();
        engine.inputDigit(3);
        engine.calculate();           // 5

        engine.add();
        engine.inputDigit(4);
        engine.calculate();

        assertEquals("9", engine.getDisplay());
        assertEquals("5 + 4 =", engine.getExpression());
    }

    @Test
    public void add_expressionShownWhileWaitingForSecondOperand() {
        engine.inputDigit(7);
        engine.add();

        assertEquals("7 +", engine.getExpression());
        assertEquals("7", engine.getDisplay());
    }

    @Test
    public void calculate_withoutOperationDoesNothing() {
        engine.inputDigit(9);
        engine.calculate();

        assertEquals("9", engine.getDisplay());
        assertEquals("", engine.getExpression());
    }

    @Test
    public void clear_resetsStateAfterAddition() {
        engine.inputDigit(2);
        engine.add();
        engine.inputDigit(3);
        engine.calculate();
        engine.clear();

        assertEquals("0", engine.getDisplay());
        assertEquals("", engine.getExpression());
    }
}