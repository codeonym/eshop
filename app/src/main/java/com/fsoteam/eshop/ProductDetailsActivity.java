package com.fsoteam.eshop;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.adapter.ProductImagesAdapter;
import com.fsoteam.eshop.model.Cart;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    RecyclerView productImagesRecyclerView;
    RecyclerView RecomRecView_ProductDetailsPage;
    private Product product;
    ProductAdapter productAdapter;
    private ProductImagesAdapter productImagesAdapter;
    private TextView productName_ProductDetailsPage;
    private TextView productPrice_ProductDetailsPage;
    private TextView productBrand_ProductDetailsPage;
    private TextView productDes_ProductDetailsPage;
    private TextView totalLikes_productDetails;
    private RatingBar productRating_singleProduct;
    TextView productPriceRaw_ProductDetailsPage;
    private TextView RatingProductDetails;
    private String pid;
    private ArrayList<Product> recomProducts;
    private Button addToCart_ProductDetailsPage;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productsRef = database.getReference(DbCollections.PRODUCTS);
    private DatabaseReference usersRef = database.getReference(DbCollections.USERS);
    private String userId = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        recomProducts = new ArrayList<>();

        productImagesRecyclerView = findViewById(R.id.productDetailsImagesRecyclerView);
        productImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecomRecView_ProductDetailsPage = findViewById(R.id.RecomRecView_ProductDetailsPage);
        RecomRecView_ProductDetailsPage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        pid = getIntent().getStringExtra("productID");

        Log.d("ProductDetailsActivity", "Product ID: " + pid);
        if (pid != null) {
            fetchProductData();
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        productName_ProductDetailsPage = findViewById(R.id.productName_ProductDetailsPage);
        totalLikes_productDetails = findViewById(R.id.totalLikes_productDetails);
        productPrice_ProductDetailsPage = findViewById(R.id.productPrice_ProductDetailsPage);
        productBrand_ProductDetailsPage = findViewById(R.id.productBrand_ProductDetailsPage);
        productDes_ProductDetailsPage = findViewById(R.id.productDes_ProductDetailsPage);
        RatingProductDetails = findViewById(R.id.RatingProductDetails);
        productRating_singleProduct = findViewById(R.id.productRating_singleProduct);
        productPriceRaw_ProductDetailsPage = findViewById(R.id.productPriceRaw_ProductDetailsPage);
        productPriceRaw_ProductDetailsPage.setPaintFlags(productPriceRaw_ProductDetailsPage.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        addToCart_ProductDetailsPage = findViewById(R.id.addToCart_ProductDetailsPage);
        fetchUserCartData();
        addToCart_ProductDetailsPage.setOnClickListener(v -> {
            // Add the product to the cart
            addToBag();

        });
    }

    private void addToBag() {
        usersRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the user's cart
                Log.d("ProductDetailsActivity", "Event listener triggered!");
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser != null && currentUser.getUserCart() != null) {

                    Log.d("ProductDetailsActivity", "User's cart found!");
                    ArrayList<OrderItem> tempCartItems = new ArrayList<>();
                    for(OrderItem item : currentUser.getUserCart().getCartItems()) {
                        if (item.getProduct().getProductId() != null && item.getProduct().getProductId().equals(orderItem.getProduct().getProductId())) {
                            return;
                        }
                        tempCartItems.add(item);
                    }

                    // Add the product to the cart
                    tempCartItems.add(orderItem);
                    currentUser.getUserCart().setCartItems(tempCartItems);
                    Log.d("ProductDetailsActivity", "bag was set, Product added to cart!");
                    // Update the user's cart
                    usersRef.child(userId).setValue(currentUser);
                    addToCart_ProductDetailsPage.setText("Product is in cart");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchUserCartData() {
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser != null && currentUser.getUserCart() != null) {
                    for(OrderItem item : currentUser.getUserCart().getCartItems()) {
                        if (item.getProduct().getProductId().equals(pid)) {
                            addToCart_ProductDetailsPage.setText("Product is in cart");
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load user cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProductData() {
        productsRef.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product p = dataSnapshot.getValue(Product.class);
                Log.d("ProductDetailsActivity", "Product: " + p.getProductName());
                product = p;
                getRecomProducts();
                // Initialize the ProductImagesAdapter and set it to the RecyclerView
                productImagesAdapter = new ProductImagesAdapter(product.getProductImages());
                productImagesRecyclerView.setAdapter(productImagesAdapter);

                // Set the product details
                totalLikes_productDetails.setText(String.valueOf(product.getLikesCount()));
                productName_ProductDetailsPage.setText(product.getProductName());
                productPrice_ProductDetailsPage.setText(product.getProductPrice() + product.getProductCurrency());
                productPriceRaw_ProductDetailsPage.setText("" + product.getProductPriceRaw());
                productBrand_ProductDetailsPage.setText(product.getProductBrand());
                productDes_ProductDetailsPage.setText(product.getProductDes());
                RatingProductDetails.setText(String.valueOf(product.getProductRating()));
                productRating_singleProduct.setRating(product.getProductRating());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load product.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getRecomProducts() {
        productsRef.limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recomProducts.clear();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product p = productSnapshot.getValue(Product.class);
                    if (p != null && p.getProductSubCategory().equals(product.getProductSubCategory())) {
                        recomProducts.add(p);
                    }
                }

                productAdapter = new ProductAdapter(recomProducts, ProductDetailsActivity.this);
                RecomRecView_ProductDetailsPage.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load products.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}