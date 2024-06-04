package com.fsoteam.eshop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.adapter.ThemeAdapter;
import com.fsoteam.eshop.model.ThemeModel;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class AppearanceSettingsActivity extends AppCompatActivity {
    RecyclerView themeRecyclerView;
    ImageView back_button;
    MaterialButtonToggleGroup toggleMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        int nightMode = sharedPreferences.getInt("nightMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); // Default mode is follow system settings
        AppCompatDelegate.setDefaultNightMode(nightMode);
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_extended_appearance);

        back_button = findViewById(R.id.fragment_settings_extended_appearance_back_button);
        themeRecyclerView = findViewById(R.id.theme_recycler_view);
        toggleMode = findViewById(R.id.theme_switcher_mode_toggle);

        // Back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Check the button representing the night mode
        int checkedButtonId;

        switch (nightMode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                checkedButtonId = R.id.theme_switcher_mode_light;
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                checkedButtonId = R.id.theme_switcher_mode_dark;
                break;
            default:
                checkedButtonId = R.id.theme_switcher_mode_default;
                break;
        }

        toggleMode.check(checkedButtonId);


        // Retrieve the saved night mode from SharedPreferences and apply it
        toggleMode.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    int mode;
                    if(checkedId == R.id.theme_switcher_mode_default){
                        mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                    }else if(checkedId == R.id.theme_switcher_mode_light){
                        mode = AppCompatDelegate.MODE_NIGHT_NO;
                    }else{
                        mode = AppCompatDelegate.MODE_NIGHT_YES;
                    }
                    AppCompatDelegate.setDefaultNightMode(mode);

                    // Save the selected mode in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("nightMode", mode);
                    editor.apply();
                }
            }
        });

        // Set the RecyclerView for the themes
        themeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<ThemeModel> themeList = new ArrayList<>();
        themeList.add(new ThemeModel(getString(R.string.theme_default), R.style.Theme_Eshop));
        themeList.add(new ThemeModel(getString(R.string.theme_lavender), R.style.Theme_Eshop_Lavender));
        themeList.add(new ThemeModel(getString(R.string.theme_modern_minimalist), R.style.Theme_Eshop_ModernMinimalist));
        themeList.add(new ThemeModel(getString(R.string.theme_nature_inspired), R.style.Theme_Eshop_NatureInspired));
        themeList.add(new ThemeModel(getString(R.string.theme_luxury_chic), R.style.Theme_Eshop_LuxuryChic));
        themeList.add(new ThemeModel(getString(R.string.theme_vibrant_playful), R.style.Theme_Eshop_VibrantPlayful));

        for(ThemeModel theme: themeList) {
            if(theme.getThemeStyle() == themeStyle){
                theme.setSelected(true);
                break;
            }
        }

        // Set the adapter for the RecyclerView
        ThemeAdapter themeAdapter = new ThemeAdapter(themeList, this);
        themeRecyclerView.setAdapter(themeAdapter);
    }
}