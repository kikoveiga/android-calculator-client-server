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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    
    Spinner spinner;
    Button button;
    TextView resultView;
    EditText number1;
    EditText number2;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.operationSpinner);
        button = findViewById(R.id.resultButton);
        resultView = findViewById(R.id.result);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operations_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        BroadcastReceiver sendResultReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent != null && Objects.equals(intent.getAction(), "SendResult")) {
                    float result = intent.getFloatExtra("result", 0);
                    resultView.setText(String.valueOf(result));
                }

            }
        };

    }

    public void calculateResult(View view) {

        boolean emptyNumber = false;

        if (number1.getText().toString().isEmpty()) {
            number1.setError("Please choose a number!");
            emptyNumber = true;
        }

        if (number2.getText().toString().isEmpty()) {
            number2.setError("Please choose a number!");
            emptyNumber = true;
        }

        if (emptyNumber) return;

        String operation = spinner.getSelectedItem().toString();

        int num1 = Integer.parseInt(number1.getText().toString());
        int num2 = Integer.parseInt(number2.getText().toString());

        intent = new Intent();
        intent.setComponent(new ComponentName(
            "com.example.serverapp",
                "com.example.serverapp.CalculatorService"
        ));

        intent.putExtra("operation", operation);
        intent.putExtra("number1", num1);
        intent.putExtra("number2", num2);

        startService(intent);
    }

}
