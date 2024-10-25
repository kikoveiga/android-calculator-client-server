package com.example.serverapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.DecimalFormat;

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

            String operator = intent.getStringExtra("operator");
            int number1 = intent.getIntExtra("number1", 0);
            int number2 = intent.getIntExtra("number2", 0);

            assert operator != null;
            float result = Calculator.calculateResult(operator, number1, number2);

            DecimalFormat df = new DecimalFormat("#.##");

            Log.d(TAG, number1 + " " + operator + " " + number2 + " = " + df.format(result));

            Intent resultIntent = new Intent();
            resultIntent.setAction("com.example.clientapp.RESULT");
            resultIntent.putExtra("result", result);

            sendBroadcast(resultIntent);
        }

        return START_STICKY;
    }

}