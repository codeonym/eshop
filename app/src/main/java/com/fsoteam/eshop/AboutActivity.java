package com.fsoteam.eshop;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    ImageView back_button;
    TextView versionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        setTheme(themeStyle);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_extended_about);

        back_button = findViewById(R.id.fragment_settings_extended_about_back_button);
        versionTv = findViewById(R.id.activity_settings_extended_about_version);

        back_button.setOnClickListener(v -> onBackPressed());
        String app_version = getAppVersion();

        versionTv.setText(getString(R.string.app_version) + "-" + app_version);

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