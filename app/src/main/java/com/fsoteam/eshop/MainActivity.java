package com.fsoteam.eshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import com.fsoteam.eshop.fragment.*;
import com.fsoteam.eshop.utils.Helpers;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        int nightMode = sharedPreferences.getInt("nightMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); // Default mode is follow system settings
        AppCompatDelegate.setDefaultNightMode(nightMode);
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        SharedPreferences preferences = getSharedPreferences("language_settings", MODE_PRIVATE);
        String lang = preferences.getString("selected_language", "default");
        Helpers.setLocale(getBaseContext(), lang);

        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.homeMenu) {
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, fragment, fragment.getClass().getSimpleName())
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.shopMenu) {
            ShopFragment fragment = new ShopFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, fragment, fragment.getClass().getSimpleName())
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.bagMenu) {
            BagFragment fragment = new BagFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, fragment, fragment.getClass().getSimpleName())
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.favMenu) {
            FavFragment fragment = new FavFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, fragment, fragment.getClass().getSimpleName())
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.profileMenu) {
            ProfileFragment fragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, fragment, fragment.getClass().getSimpleName())
                    .commit();
            return true;
        }
        return false;
    }
}