package com.fsoteam.eshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.fsoteam.eshop.fragment.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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