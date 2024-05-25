package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.ProductDetailsActivity;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.viewmodel.FavViewModel;
import java.util.List;

public class FavProductAdapter extends RecyclerView.Adapter<FavProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> favProductList;
    private FavViewModel favViewModel;

    public FavProductAdapter(Context context, List<Product> favProductList, FavViewModel favViewModel) {
        this.context = context;
        this.favProductList = favProductList;
        this.favViewModel = favViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_product_item_single, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = favProductList.get(holder.getAdapterPosition());

        Log.d("Product", product.toString());

        holder.productBrandName_favProduct.setText(product.getProductBrand());
        holder.productName_favProduct.setText(product.getProductName());
        holder.productPrice_favProduct.setText(String.valueOf(product.getProductPrice()));
        holder.productDiscount_favProduct.setText(product.getProductDisCount());
        holder.productRating_favProduct.setRating(product.getProductRating());

        if(product.getProductDisCount().isEmpty() || product.getProductDisCount().equals("0")) {
            holder.favProductDiscountLayout.setVisibility(View.GONE);
        } else {
            holder.favProductDiscountLayout.setVisibility(View.VISIBLE);
            holder.productDiscount_favProduct.setText("-" + product.getProductDisCount() + "%");
        }
        Glide.with(context)
                .load(product.getProductThumbnail())
                .placeholder(R.drawable.no_product)
                .into(holder.productImage_favProduct);

        holder.productImage_favProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the product details activity
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("ProductID", product.getProductId());
                context.startActivity(intent);
            }
        });
        holder.productAddToFav_favProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current position
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == RecyclerView.NO_POSITION) {
                    // The position is invalid
                    return;
                }

                Product currentProduct = favProductList.get(currentPosition);
                favViewModel.removeProductFromWishlist(currentProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productAddToFav_favProduct;
        ImageView productImage_favProduct;
        TextView productBrandName_favProduct;
        TextView productName_favProduct;
        TextView productPrice_favProduct;
        TextView productDiscount_favProduct;
        LinearLayout favProductDiscountLayout;
        RatingBar productRating_favProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productAddToFav_favProduct = itemView.findViewById(R.id.productAddToFav_favProduct);
            productImage_favProduct = itemView.findViewById(R.id.productImage_favProduct);
            productBrandName_favProduct = itemView.findViewById(R.id.productBrandName_favProduct);
            productName_favProduct = itemView.findViewById(R.id.productName_favProduct);
            productPrice_favProduct = itemView.findViewById(R.id.productPrice_favProduct);
            productDiscount_favProduct = itemView.findViewById(R.id.discountTv_favProduct);
            favProductDiscountLayout = itemView.findViewById(R.id.discountLayout_favProduct);
            productRating_favProduct = itemView.findViewById(R.id.productRating_favProduct);

        }
    }
}
