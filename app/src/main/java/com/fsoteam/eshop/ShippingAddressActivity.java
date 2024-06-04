package com.fsoteam.eshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.adapter.ShippingAddressAdapter;
import com.fsoteam.eshop.viewmodel.ShippingAddressViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShippingAddressActivity extends AppCompatActivity {

    private RecyclerView cartRecView;
    private TextView myAddressTitle_ShippingPage;
    private FloatingActionButton addAddressButton;
    private LottieAnimationView animationView;
    private LinearLayout emptyAddressMsgLayout;

    private ShippingAddressViewModel shippingAddressViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Retrieve the saved theme from SharedPreferences and apply it
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePref", MODE_PRIVATE);
        int themeStyle = sharedPreferences.getInt("themeStyle", R.style.Theme_Eshop); // Default theme is Theme.Eshop
        setTheme(themeStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        cartRecView = findViewById(R.id.cartRecView);
        addAddressButton = findViewById(R.id.addAddress_ShippingPage);
        myAddressTitle_ShippingPage = findViewById(R.id.myAddressTitle_ShippingPage);
        emptyAddressMsgLayout = findViewById(R.id.emptyAddressLayout);
        animationView = findViewById(R.id.animationViewAddressPage);

        animationView.playAnimation();
        animationView.loop(true);
        myAddressTitle_ShippingPage.setVisibility(View.GONE);
        emptyAddressMsgLayout.setVisibility(View.VISIBLE);

        // Set the layout manager to the RecyclerView
        cartRecView.setLayoutManager(new LinearLayoutManager(this));

        shippingAddressViewModel = new ViewModelProvider(this).get(ShippingAddressViewModel.class);
        shippingAddressViewModel.getShipmentDetailsLiveData().observe(this, shipmentDetailsList -> {
            ShippingAddressAdapter adapter = new ShippingAddressAdapter(shipmentDetailsList, ShippingAddressActivity.this, shippingAddressViewModel);
            cartRecView.setAdapter(adapter);

            if (shipmentDetailsList.isEmpty()) {
                animationView.playAnimation();
                animationView.loop(true);
                myAddressTitle_ShippingPage.setVisibility(View.GONE);
                emptyAddressMsgLayout.setVisibility(View.VISIBLE);
            } else {
                emptyAddressMsgLayout.setVisibility(View.GONE);
                myAddressTitle_ShippingPage.setVisibility(View.VISIBLE);
                animationView.pauseAnimation();
            }
        });

        shippingAddressViewModel.loadShipmentDetails();

        addAddressButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShippingAddressActivity.this, AddShippingAddressActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShippingAddressActivity.this, MainActivity.class);
        intent.putExtra("fragment", "profile");
        startActivity(intent);
        finish();
    }
}