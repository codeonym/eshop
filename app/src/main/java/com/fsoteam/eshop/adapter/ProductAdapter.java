package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.ProductDetailsActivity;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product> productList;
    private Context ctx;
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(DbCollections.USERS);
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private User currentUser;

    public ProductAdapter(ArrayList<Product> productList, Context ctx) {
        this.productList = productList;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(holder.getAdapterPosition());
        holder.productBrandName_singleProduct.setText(product.getProductBrand());
        holder.productName_singleProduct.setText(product.getProductName());
        holder.productPrice_singleProduct.setText(product.getProductPrice() + product.getProductCurrency());
        holder.productRating_singleProduct.setRating(product.getProductRating());

        Glide.with(ctx)
                .load(product.getProductThumbnail())
                .placeholder(R.drawable.no_product)
                .into(holder.productImage_singleProduct);

        if (product.isProductHave()) {
            holder.discountTv_singleProduct.setText(product.getProductDisCount());
            holder.discount_singleProduct.setVisibility(View.VISIBLE);
        }

        if (!product.isProductHave()) {
            holder.discount_singleProduct.setVisibility(View.VISIBLE);
            holder.discountTv_singleProduct.setText("New");
        }

        holder.productImage_singleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    goDetailsPage(position);
                }
            }
        });

        userRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                currentUser = user;
                if (currentUser.getUserWishlist().containsProduct(product)) {
                    product.setProductLiked(true);
                    holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_fav_added);
                } else {
                    product.setProductLiked(false);
                    holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_fav);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ctx, "Failed to load user.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the OnClickListener for the add to wishlist button
        holder.productAddToFav_singleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.isProductLiked()) {
                    // The product is in the wishlist, remove it
                    currentUser.getUserWishlist().removeProductById(product.getProductId());
                    product.setProductLiked(false);
                    holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_fav);
                } else {
                    // The product is not in the wishlist, add it
                    currentUser.getUserWishlist().addProduct(product);
                    product.setProductLiked(true);
                    holder.productAddToFav_singleProduct.setImageResource(R.drawable.ic_fav_added);
                }

                // Update the user in the database
                userRef.child(currentUserId).child("userWishlist").setValue(currentUser.getUserWishlist());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage_singleProduct;
        ImageView productAddToFav_singleProduct;
        RatingBar productRating_singleProduct;
        TextView productBrandName_singleProduct;
        TextView discountTv_singleProduct;
        TextView productName_singleProduct;
        TextView productPrice_singleProduct;
        LinearLayout discount_singleProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage_singleProduct = itemView.findViewById(R.id.productImage_singleProduct);
            productAddToFav_singleProduct = itemView.findViewById(R.id.productAddToFav_singleProduct);
            productRating_singleProduct = itemView.findViewById(R.id.productRating_singleProduct);
            productBrandName_singleProduct = itemView.findViewById(R.id.productBrandName_singleProduct);
            discountTv_singleProduct = itemView.findViewById(R.id.discountTv_singleProduct);
            productName_singleProduct = itemView.findViewById(R.id.productName_singleProduct);
            productPrice_singleProduct = itemView.findViewById(R.id.productPrice_singleProduct);
            discount_singleProduct = itemView.findViewById(R.id.discount_singleProduct);
        }
    }

    private void goDetailsPage(int position) {
        Product product = productList.get(position);
        Intent intent = new Intent(ctx, ProductDetailsActivity.class);
        intent.putExtra("productID", product.getProductId());
        intent.putExtra("ProductFrom", "New");
        ctx.startActivity(intent);
    }
    public void filterList(ArrayList<Product> filteredList) {
        productList = filteredList;
    }
}
