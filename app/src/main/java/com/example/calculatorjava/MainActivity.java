package com.example.calculatorjava;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int[] DIGIT_IDS = {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
    };

    private final CalculatorEngine engine = new CalculatorEngine();
    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = findViewById(R.id.tvDisplay);

        for (int i = 0; i < DIGIT_IDS.length; i++) {
            final int digit = i;
            click(DIGIT_IDS[i], () -> engine.inputDigit(digit));
        }
        click(R.id.btnAdd, engine::add);
        click(R.id.btnEquals, engine::calculate);

        tvDisplay.setText(engine.getDisplay());
    }

    /** Выполняет действие над движком и обновляет экран. */
    private void click(int buttonId, Runnable action) {
        findViewById(buttonId).setOnClickListener(v -> {
            action.run();
            tvDisplay.setText(engine.getDisplay());
        });
    }
}