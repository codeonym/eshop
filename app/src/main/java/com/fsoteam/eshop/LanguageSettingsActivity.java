package com.fsoteam.eshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.Locale;

public class LanguageSettingsActivity extends AppCompatActivity {

    private MaterialRadioButton systemDefault, english, arabic, french, spanish;
    ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);

        // Retrieve the saved language and apply it
        SharedPreferences preferences = getSharedPreferences("language_settings", MODE_PRIVATE);
        String lang = preferences.getString("selected_language", "default"); // default value is "default"

        setContentView(R.layout.activity_settings_extended_language);

        systemDefault = findViewById(R.id.language_setting_system_default);
        english = findViewById(R.id.language_setting_english);
        french = findViewById(R.id.language_setting_french);
        spanish = findViewById(R.id.language_setting_spanish);
        arabic = findViewById(R.id.language_setting_arabic);
        back_button = findViewById(R.id.fragment_settings_extended_language_back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        switch (lang) {
            case "default":
                uncheckedAllRadioButtons();
                systemDefault.setChecked(true);
                break;
            case "en":
                uncheckedAllRadioButtons();
                english.setChecked(true);
                break;
            case "fr":
                uncheckedAllRadioButtons();
                french.setChecked(true);
                break;
            case "es":
                uncheckedAllRadioButtons();
                spanish.setChecked(true);
                break;
            case "ar":
                uncheckedAllRadioButtons();
                arabic.setChecked(true);
                break;
        }

        systemDefault.setOnClickListener(v -> setLocale("default")); // Use default system language
        english.setOnClickListener(v -> {
            uncheckedAllRadioButtons();
            setLocale("en");
        }); // English
        french.setOnClickListener(v -> {
            uncheckedAllRadioButtons();
            setLocale("fr");
        }); // French
        spanish.setOnClickListener(v -> {
            uncheckedAllRadioButtons();
            setLocale("es");
        }); // Spanish
        arabic.setOnClickListener(v -> {
            uncheckedAllRadioButtons();
            setLocale("ar");
        }); // Arabic
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        // Save the selected language in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("language_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selected_language", lang);
        editor.apply();

        // Start MainActivity as a new task
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void uncheckedAllRadioButtons() {
        systemDefault.setChecked(false);
        english.setChecked(false);
        french.setChecked(false);
        spanish.setChecked(false);
        arabic.setChecked(false);
    }
}