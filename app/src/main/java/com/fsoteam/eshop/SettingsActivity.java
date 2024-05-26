package com.fsoteam.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.appcheck.BuildConfig;

public class SettingsActivity extends AppCompatActivity {

    ImageView fragment_settings_back_button;
    LinearLayout fragment_settings_language_layout, fragment_settings_appearance_layout, fragment_settings_about_layout, fragment_settings_help_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fragment_settings_back_button = findViewById(R.id.fragment_settings_back_button);
        fragment_settings_language_layout = findViewById(R.id.fragment_settings_language_settings);
        fragment_settings_appearance_layout = findViewById(R.id.fragment_settings_appearance_settings);
        fragment_settings_about_layout = findViewById(R.id.fragment_settings_about_settings);
        fragment_settings_help_layout = findViewById(R.id.fragment_settings_help_settings);
        TextView version = findViewById(R.id.activity_settings_version);

        version.setText(R.string.app_name + " " + BuildConfig.VERSION_NAME);
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
}