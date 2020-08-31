package com.example.textdetectionapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class LCOTextRecognization extends Application {

    public static final String RESULT_TEXT="Result_text";


    @Override
    public void onCreate() {

        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
