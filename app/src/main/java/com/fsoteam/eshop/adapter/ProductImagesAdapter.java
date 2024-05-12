package com.fsoteam.eshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.ProductImage;

import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ViewHolder> {

    private List<ProductImage> productImages;

    public ProductImagesAdapter(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductImage productImage = productImages.get(position);
        Glide.with(holder.itemView.getContext()).load(productImage.getImage()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productDetailsImage);
        }
    }
}