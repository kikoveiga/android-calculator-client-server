package com.example.clientapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    
    Spinner spinner;
    Button button;
    TextView resultView;
    TextInputLayout number1;
    TextInputLayout number2;

    Intent intent;

    IntentFilter resultFilter;

    BroadcastReceiver resultReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null && Objects.equals(intent.getAction(), "com.example.clientapp.RESULT")) {
                float result = intent.getFloatExtra("result", 0);
                DecimalFormat df = new DecimalFormat("#.##");

                resultView.setText(df.format(result));
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.operatorsSpinner);
        button = findViewById(R.id.resultButton);
        resultView = findViewById(R.id.result);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operators_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        resultFilter = new IntentFilter("com.example.clientapp.RESULT");
        // Requires sdk 33
        registerReceiver(resultReceiver, resultFilter, Context.RECEIVER_EXPORTED);

    }

    public void calculateResult(View view) {

        boolean emptyNumber = false;

        String num1String = Objects.requireNonNull(number1.getEditText()).getText().toString();
        String num2String = Objects.requireNonNull(number2.getEditText()).getText().toString();

        if (num1String.isEmpty()) {
            number1.setError("Number cannot be empty!");
            emptyNumber = true;
        } else number1.setError(null);

        if (num2String.isEmpty()) {
            number2.setError("Number cannot be empty!");
            emptyNumber = true;
        } else number2.setError(null);

        if (emptyNumber) return;

        String operator = spinner.getSelectedItem().toString();

        int num1 = Integer.parseInt(num1String);
        int num2 = Integer.parseInt(num2String);

        if (Objects.equals(operator, "/") && num2 == 0) {
            number2.setError("Cannot divide by 0!");
            return;
        }

        intent = new Intent();
        intent.setComponent(new ComponentName(
            "com.example.serverapp",
                "com.example.serverapp.CalculatorService"
        ));

        intent.putExtra("operator", operator);
        intent.putExtra("number1", num1);
        intent.putExtra("number2", num2);

        startForegroundService(intent);
    }

}
