package com.fsoteam.eshop;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.adapter.ProductImagesAdapter;
import com.fsoteam.eshop.fragment.BagFragment;
import com.fsoteam.eshop.model.Cart;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.model.Wishlist;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    RecyclerView productImagesRecyclerView;
    RecyclerView RecomRecView_ProductDetailsPage;
    private Product product;
    ProductAdapter productAdapter;
    private ProductImagesAdapter productImagesAdapter;
    private ImageView likeButton_ProductDetailsPage;
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
    private ArrayList<OrderItem> cartItems;
    private Button addToCart_ProductDetailsPage;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productsRef = database.getReference(DbCollections.PRODUCTS);
    private String userId = FirebaseAuth.getInstance().getUid();
    private DatabaseReference userRef = database.getReference(DbCollections.USERS).child(userId);
    private Wishlist userWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        recomProducts = new ArrayList<>();
        userWishlist = new Wishlist();

        likeButton_ProductDetailsPage = findViewById(R.id.productDetailsPageLikeBtn);
        productImagesRecyclerView = findViewById(R.id.productDetailsImagesRecyclerView);
        productImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecomRecView_ProductDetailsPage = findViewById(R.id.RecomRecView_ProductDetailsPage);
        RecomRecView_ProductDetailsPage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        pid = getIntent().getStringExtra("productID");

        Log.d("ProductDetailsActivity", "Product ID: " + pid);
        if (pid != null) {
            getUserWishlist();
            likeButton_ProductDetailsPage.setOnClickListener(v -> {
                // Like the product
                if(product.isProductLiked()){

                    product.setProductLiked(false);
                    int likesCount = product.getLikesCount();
                    likesCount--;
                    product.setLikesCount(likesCount);
                    productsRef.child(product.getProductId()).setValue(product);
                    totalLikes_productDetails.setText(String.valueOf(likesCount));
                    likeButton_ProductDetailsPage.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
                    ArrayList<Product> tmpList= new ArrayList<>();
                    tmpList.addAll(userWishlist.getWishlistProducts());

                    for(Product tmp: userWishlist.getWishlistProducts()){
                        if(tmp.getProductId() != null && tmp.getProductId().equals(product.getProductId())){
                            tmpList.remove(tmp);
                            break;
                        }
                    }
                    userWishlist.setWishlistProducts(tmpList);
                    userRef.child("userWishlist").setValue(userWishlist);

                }else {
                    product.setProductLiked(true);
                    int likesCount = product.getLikesCount();
                    likesCount++;
                    product.setLikesCount(likesCount);
                    productsRef.child(product.getProductId()).setValue(product);
                    totalLikes_productDetails.setText(String.valueOf(likesCount));
                    likeButton_ProductDetailsPage.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_added));
                    List<Product> tmp = userWishlist.getWishlistProducts();
                    tmp.add(product);
                    userWishlist.setWishlistProducts(tmp);
                    userRef.child("userWishlist").setValue(userWishlist);
                }
            });
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

    private void getUserWishlist(){
        userRef.child("userWishlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Wishlist wishlist = dataSnapshot.getValue(Wishlist.class);
                userWishlist = wishlist;
                fetchProductData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load user wishlist.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addToBag() {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setItemId(product.getProductId());
        for(OrderItem tmp: cartItems){
            if(tmp.getProduct().getProductId().equals(pid)){
                Toast.makeText(ProductDetailsActivity.this, "Product is already in cart", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        userRef.child("userCart").child("cartItems").child(orderItem.getItemId()).setValue(orderItem);
        addToCart_ProductDetailsPage.setText("Product added to cart");
        Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
    }
    private void fetchUserCartData() {
        userRef.child("userCart").child("cartItems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItems = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderItem orderItem = snapshot.getValue(OrderItem.class);
                    cartItems.add(orderItem);
                    if(orderItem.getProduct().getProductId().equals(pid)){
                        addToCart_ProductDetailsPage.setText("Product is in cart");
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
                productPriceRaw_ProductDetailsPage.setText(product.getProductPriceRaw() + product.getProductCurrency());
                productBrand_ProductDetailsPage.setText(product.getProductBrand());
                productDes_ProductDetailsPage.setText(product.getProductDes());
                RatingProductDetails.setText(String.valueOf(product.getProductRating()));
                productRating_singleProduct.setRating(product.getProductRating());
                for(Product tmp: userWishlist.getWishlistProducts()){
                    if(tmp.getProductId().equals(product.getProductId())){
                        likeButton_ProductDetailsPage.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_added));
                        product.setProductLiked(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductDetailsActivity.this, "Failed to load product.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getRecomProducts() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recomProducts.clear();
                int counter = 1;
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    if(counter > 10) break;
                    Product p = productSnapshot.getValue(Product.class);
                    if (p != null && product != null && !p.getProductId().equals(product.getProductId()) && p.getProductBrand().equals(product.getProductBrand())) {
                        recomProducts.add(p);
                        counter ++;
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