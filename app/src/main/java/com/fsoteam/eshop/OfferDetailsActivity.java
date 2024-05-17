package com.fsoteam.eshop;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.CustomDate;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class OfferDetailsActivity extends AppCompatActivity {

    private ImageView banner;
    private TextView title, description, startDate, endDate, countdownTimer, discount, badge;
    private RecyclerView offersRecyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;
    private DatabaseReference offersRef;
    private CustomDate customDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        customDate = new CustomDate();

        banner = findViewById(R.id.banner);
        title = findViewById(R.id.title);
        badge= findViewById(R.id.badge);
        discount = findViewById(R.id.discount);
        description = findViewById(R.id.description);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        countdownTimer = findViewById(R.id.countdownTimer);
        offersRecyclerView = findViewById(R.id.offersRecyclerView);

        String offerId = getIntent().getStringExtra("OfferId");

        offersRef = FirebaseDatabase.getInstance().getReference(DbCollections.OFFERS).child(offerId);

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Offer offer = dataSnapshot.getValue(Offer.class);

                if (offer != null) {

                    title.setText(offer.getOfferTitle());
                    description.setText(offer.getOfferDescription());
                    badge.setText(offer.getOfferBadge());
                    discount.setText(offer.getOfferDiscount() + "% OFF");
                    startDate.setText(customDate.getFormattedDateTime(offer.getOfferStartDate()));
                    endDate.setText(customDate.getFormattedDateTime(offer.getOfferEndDate()));
                    Glide.with(OfferDetailsActivity.this).load(offer.getOfferImage()).into(banner);
                    long timeInMillis = offer.getOfferEndDate() - System.currentTimeMillis();
                    if(timeInMillis > 0)
                        startCountdown(countdownTimer, timeInMillis);

                    // For the RecyclerView, you need to create a new adapter and set it
                    productList = (ArrayList<Product>) offer.getOfferItems();
                    productAdapter = new ProductAdapter(productList, OfferDetailsActivity.this);
                    offersRecyclerView.setLayoutManager(new GridLayoutManager(OfferDetailsActivity.this, 2));
                    offersRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void startCountdown(TextView countdownTimer, long timeInMillis) {
        new CountDownTimer(timeInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000 % 60;
                long minutes = millisUntilFinished / (1000 * 60) % 60;
                long hours = millisUntilFinished / (1000 * 60 * 60) % 24;
                long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                String timeLeftFormatted = String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
                countdownTimer.setText(timeLeftFormatted);
            }

            public void onFinish() {
                countdownTimer.setText("00:00:00:00");
            }
        }.start();
    }
}