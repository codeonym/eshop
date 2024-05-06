package com.fsoteam.eshop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.fsoteam.eshop.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsActivity extends AppCompatActivity {

    private String pPid;
    private DatabaseReference mDatabase;
    private ImageView productImage_ProductDetailsPage;
    private TextView productName_ProductDetailsPage;
    private TextView productPrice_ProductDetailsPage;
    private TextView productBrand_ProductDetailsPage;
    private TextView productDes_ProductDetailsPage;
    private TextView RatingProductDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        pPid = getIntent().getStringExtra("ProductID");

        productImage_ProductDetailsPage = findViewById(R.id.productImage_ProductDetailsPage);
        productName_ProductDetailsPage = findViewById(R.id.productName_ProductDetailsPage);
        productPrice_ProductDetailsPage = findViewById(R.id.productPrice_ProductDetailsPage);
        productBrand_ProductDetailsPage = findViewById(R.id.productBrand_ProductDetailsPage);
        productDes_ProductDetailsPage = findViewById(R.id.productDes_ProductDetailsPage);
        RatingProductDetails = findViewById(R.id.RatingProductDetails);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fetchProductData();
    }

    private void fetchProductData() {
        mDatabase.child("products").child(pPid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                if (product != null) {
                    Glide.with(getApplicationContext())
                            .load(product.getProductImage())
                            .into(productImage_ProductDetailsPage);

                    productName_ProductDetailsPage.setText(product.getProductName());
                    productPrice_ProductDetailsPage.setText("$" + product.getProductPrice());
                    productBrand_ProductDetailsPage.setText(product.getProductBrand());
                    productDes_ProductDetailsPage.setText(product.getProductDes());
                    RatingProductDetails.setText(product.getProductRating() + " Rating on this Product.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load product.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}