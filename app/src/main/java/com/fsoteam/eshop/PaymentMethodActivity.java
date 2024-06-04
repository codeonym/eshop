package com.fsoteam.eshop;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);
    }
}