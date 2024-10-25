package com.example.serverapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CalculatorService extends Service {

    private static final String TAG = "CalculatorService";

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created!");
    }

    // This is not a bound service
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {

        if (intent != null) {
            Log.d(TAG, "Intent received!");

            String operation = intent.getStringExtra("operation");
            int number1 = intent.getIntExtra("number1", 0);
            int number2 = intent.getIntExtra("number2", 0);

            assert operation != null;
            float result = calculateResult(operation, number1, number2);

            Intent sendResultIntent = new Intent();
            sendResultIntent.setAction("com.example.serverapp.RESULT");
            sendResultIntent.putExtra("result", result);

            sendBroadcast(sendResultIntent);

        }

        return START_STICKY;
    }
    private float calculateResult(String operation, int num1, int num2) {

        switch (operation) {
            case "+":
                return num1 + num2;

            case  "-":
                return num1 - num2;

            case "*":
                return num1 * num2;

            case "/":
                return (float) num1 / num2;
        }

        return 0;
    }

}