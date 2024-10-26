# android-calculator-client-server
Simple Android app in Java that implements a Client-Server architecture by sending arithmetic calculation requests using Intents and Services.


## Run

To run this project, you need to install both apps in your device. If it's a physical Android device, install the APKs. If you're on Android Studio, run the **app** or **MainAcitivty** configuration of both apps in your AVD, guaranteeing they are installed.
Next, make sure both apps are closed and run the **Client App**. The **Client App** will be able to "start" the **Server App** using a <code>ForegroundService</code>.

## Implementation

- Client App
    - TextInputLayout for the input fields
    - Spinner for the operation selection
    - Explicit Intent to start the Server App
    - Broadcast Receiver to receive the result
    - Input validation (only integers, no empty field, no division by zero)


- Server App
    - Foreground Service <code>CalculatorService</code>
    - Implicit Intent to send the result back
    - Logs to show the requests and results
    - Unit tests for the Calculator class

## Research

In the beginning, I tried to use a normal (Started) Service on the Server side, and i couldn't get it to work as this kind of error kept appearing:
    
    ```plaintext
    Unable to start service Intent { } U=0: not found
    ```

I finally came across this [link](https://stackoverflow.com/questions/67648647/android-11-starting-a-service-of-another-app), where it explained that starting from Android 11, this was necessary in the Client App's manifest:

    ```xml
    <queries>
        <package android:name="com.example.serverapp" />
    </queries>
    ```

After this problem was fixed, I finally got the Apps to communicate between them. The remaining problem was that I had to start the Server App manually before starting the Client App. Using a <code>ForegroundService</code> is the ideal solution, as now the Client App can start the Server App automatically.

Also, it was essential to [use an Explicit Intent](https://stackoverflow.com/questions/27842430/service-intent-must-be-explicit-intent) rather than Implicit with a filtered action when starting the Service, as this is insecure and kept throwing this error:
    
        ```plaintext
        Service Intent must be explicit: Intent { }
        ```

To sent the result from the Server back to the Client, a simple <code>sendBroadcast</code> with an Implicit Intent this time was used. The Client app had a <code>BroadcastReceiver</code> listening for this Intent.

## Useful Links

- [Android services](https://developer.android.com/develop/background-work/services)
- [Android Intents](https://developer.android.com/reference/android/content/Intent)

