package com.fsoteam.eshop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HelpActivity extends AppCompatActivity {

    private ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_extended_help);

        back_button = findViewById(R.id.fragment_settings_extended_help_back_button);
        back_button.setOnClickListener(v -> onBackPressed());
    }
}