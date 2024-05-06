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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.ProductDetailsActivity;
import java.util.ArrayList;
import java.util.List;

public class SaleProductAdapter extends RecyclerView.Adapter<SaleProductAdapter.ViewHolder> {

    private ArrayList<Product> saleProductList;
    private Context ctx;

    public SaleProductAdapter(ArrayList<Product> saleProductList, Context ctx) {
        this.saleProductList = saleProductList;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = saleProductList.get(position);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    goDetailsPage(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return saleProductList.size();
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
        Product product = saleProductList.get(position);
        Intent intent = new Intent(ctx, ProductDetailsActivity.class);
        intent.putExtra("ProductID", product.getProductId());
        intent.putExtra("ProductFrom", "New");
        ctx.startActivity(intent);
    }
}