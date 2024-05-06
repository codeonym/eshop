package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Product;

import java.util.ArrayList;

public class VisualSearchResultAdapter extends RecyclerView.Adapter<VisualSearchResultAdapter.VisualViewHolder> {

    private ArrayList<Product> productList;
    private Context ctx;

    public VisualSearchResultAdapter(ArrayList<Product> productList, Context ctx) {
        this.productList = productList;
        this.ctx = ctx;
    }

    @Override
    public VisualViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.predicted_result_single, parent, false);
        return new VisualViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(VisualViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productBrandName_singleProduct.setText(product.getProductBrand());
        holder.productName_singleProduct.setText(product.getProductName());
        holder.productPrice_singleProduct.setText("$" + product.getProductPrice());
        holder.productRating_singleProduct.setRating(product.getProductRating());

        Glide.with(ctx)
                .load(product.getProductImage())
                .placeholder(R.drawable.bn)
                .into(holder.productImage_singleProduct);

        if (product.isProductHave()) {
            holder.discountTv_singleProduct.setText(product.getProductDisCount());
            holder.discount_singleProduct.setVisibility(View.VISIBLE);
        }

        if (!product.isProductHave()) {
            holder.discount_singleProduct.setVisibility(View.VISIBLE);
            holder.discountTv_singleProduct.setText("New");
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class VisualViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage_singleProduct;
        ImageView productAddToFav_singleProduct;
        RatingBar productRating_singleProduct;
        TextView productBrandName_singleProduct;
        TextView discountTv_singleProduct;
        TextView productName_singleProduct;
        TextView productPrice_singleProduct;
        LinearLayout discount_singleProduct;

        public VisualViewHolder(View itemView) {
            super(itemView);
            productImage_singleProduct = itemView.findViewById(R.id.productImage_predictProduct);
            productAddToFav_singleProduct = itemView.findViewById(R.id.productAddToFav_predictProduct);
            productRating_singleProduct = itemView.findViewById(R.id.productRating_predictProduct);
            productBrandName_singleProduct = itemView.findViewById(R.id.productBrandName_predictProduct);
            discountTv_singleProduct = itemView.findViewById(R.id.discountTv_predictProduct);
            productName_singleProduct = itemView.findViewById(R.id.productName_predictProduct);
            productPrice_singleProduct = itemView.findViewById(R.id.productPrice_predictProduct);
            discount_singleProduct = itemView.findViewById(R.id.discount_predictProduct);
        }
    }
}