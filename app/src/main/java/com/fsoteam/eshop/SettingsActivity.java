package com.fsoteam.eshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    ImageView fragment_settings_back_button;
    LinearLayout fragment_settings_language_layout, fragment_settings_appearance_layout, fragment_settings_about_layout, fragment_settings_help_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fragment_settings_back_button = findViewById(R.id.fragment_settings_back_button);
        fragment_settings_language_layout = findViewById(R.id.fragment_settings_language_settings);
        fragment_settings_appearance_layout = findViewById(R.id.fragment_settings_appearance_settings);
        fragment_settings_about_layout = findViewById(R.id.fragment_settings_about_settings);
        fragment_settings_help_layout = findViewById(R.id.fragment_settings_help_settings);
        TextView version = findViewById(R.id.activity_settings_version);

        version.setText(getString(R.string.app_name) + " " + getString(R.string.app_version) + "-"  + getAppVersion());
        fragment_settings_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fragment_settings_language_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LanguageSettingsActivity.class);
                startActivity(intent);
            }
        });

        fragment_settings_appearance_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AppearanceSettingsActivity.class);
                startActivity(intent);
            }
        });

        fragment_settings_about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        fragment_settings_help_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getAppVersion() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}