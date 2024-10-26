package com.example.serverapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.DecimalFormat;

public class CalculatorService extends Service {

    private static final String TAG = "CalculatorService";
    private static final String ChannelID = "CalculatorServiceChannel";

    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
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
        Log.d(TAG, "Service started!");

        Notification notification = new NotificationCompat.Builder(this, ChannelID)
                .setContentTitle("Calculator Service Running")
                .setContentText("Processing calculations in the background")
                .setSmallIcon(R.mipmap.planet_foreground) // Replace with your own icon
                .build();

        startForeground(1, notification);

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
            // Explicit intent to send the result
            resultIntent.setAction("com.example.clientapp.RESULT");
            resultIntent.putExtra("result", result);

            sendBroadcast(resultIntent);
        }

        // In order to keep the service alive and receive more requests
        return START_STICKY;
    }

    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                ChannelID,
                "Calculator Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(serviceChannel);
        }
    }

}