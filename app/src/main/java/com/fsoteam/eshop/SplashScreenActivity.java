package com.fsoteam.eshop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.fsoteam.eshop.utils.Helpers;

public class SplashScreenActivity extends Activity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Retrieve the saved language and apply it
        SharedPreferences preferences = getSharedPreferences("language_settings", MODE_PRIVATE);
        String lang = preferences.getString("selected_language", "default");
        Helpers.setLocale(getBaseContext(), lang);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}