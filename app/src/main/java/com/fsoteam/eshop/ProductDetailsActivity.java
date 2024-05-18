package com.fsoteam.eshop;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.adapter.ProductImagesAdapter;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.viewmodel.ProductDetailsViewModel;
import com.fsoteam.eshop.viewmodel.ProductViewModel;
import java.util.ArrayList;

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

    private ProductDetailsViewModel productDetailsViewModel;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        likeButton_ProductDetailsPage = findViewById(R.id.productDetailsPageLikeBtn);
        productImagesRecyclerView = findViewById(R.id.productDetailsImagesRecyclerView);
        productImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecomRecView_ProductDetailsPage = findViewById(R.id.RecomRecView_ProductDetailsPage);
        RecomRecView_ProductDetailsPage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        pid = getIntent().getStringExtra("ProductID");

        Log.d("ProductDetailsActivity", "Product ID: " + pid);
        if (pid != null) {
            productDetailsViewModel.fetchUserWishlist();
            likeButton_ProductDetailsPage.setOnClickListener(v -> {
                // Like the product
                if(product.isProductLiked()){

                    product.setProductLiked(false);
                    int likesCount = product.getLikesCount();
                    likesCount--;
                    product.setLikesCount(likesCount);
                    totalLikes_productDetails.setText(String.valueOf(likesCount));
                    likeButton_ProductDetailsPage.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
                    productDetailsViewModel.removeFromWishlist(product);

                }else {
                    product.setProductLiked(true);
                    int likesCount = product.getLikesCount();
                    likesCount++;
                    product.setLikesCount(likesCount);
                    totalLikes_productDetails.setText(String.valueOf(likesCount));
                    likeButton_ProductDetailsPage.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_added));
                    productDetailsViewModel.addToWishlist(product);
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
        productDetailsViewModel.fetchUserCartData();
        addToCart_ProductDetailsPage.setOnClickListener(v -> {
            // Add the product to the cart
            productDetailsViewModel.addToBag(product);
            addToCart_ProductDetailsPage.setText("Product added to cart");
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
        });

        productDetailsViewModel.getProduct().observe(this, product -> {
            // Update the UI with the product data
            this.product = product;
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

            productDetailsViewModel.getUserWishlist().observe(this, wishlist -> {
                if (wishlist != null && !wishlist.getWishlistProducts().isEmpty() && wishlist.getWishlistProducts().containsKey(product.getProductId())) {

                    // Update the UI with the wishlist data
                    likeButton_ProductDetailsPage.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_added));
                    product.setProductLiked(true);
                }
            });

            productDetailsViewModel.getCartItems().observe(this, cartItems -> {
                // Update the UI with the cart items data
                this.cartItems = new ArrayList<>(cartItems);
                for(OrderItem tmp: cartItems){
                    if(tmp.getProduct().getProductId().equals(pid)){
                        addToCart_ProductDetailsPage.setText("Product is in cart");
                    }
                }
            });

            // Fetch the recommended products after the product data has been fetched and processed
            productDetailsViewModel.fetchRecomProducts();
            productDetailsViewModel.getRecomProductsLiveData().observe(this, recomProducts -> {
                // Update the UI with the recommended products data
                this.recomProducts = new ArrayList<>(recomProducts);
                productAdapter = new ProductAdapter( (ArrayList<Product>) recomProducts, ProductDetailsActivity.this, productViewModel, this);
                RecomRecView_ProductDetailsPage.setAdapter(productAdapter);
            });
        });

        productDetailsViewModel.fetchProductData(pid);
    }
}