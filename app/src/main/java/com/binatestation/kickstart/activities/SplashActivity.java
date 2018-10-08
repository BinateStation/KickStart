package com.binatestation.kickstart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.binatestation.kickstart.utils.Session;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogin();
    }

    private void checkLogin() {
        if (Session.getUserId(this) > 0) {
            navigateToHome();
        } else {
            navigateToLogin();
        }
    }


    /**
     * navigate to Login
     */
    private void navigateToLogin() {
        Log.d(TAG, "navigateToLogin() called");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    /**
     * navigate to home
     * If you have some thing to download for app use do that before calling this method.
     */
    private void navigateToHome() {
        Log.d(TAG, "navigateToHome() called");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
