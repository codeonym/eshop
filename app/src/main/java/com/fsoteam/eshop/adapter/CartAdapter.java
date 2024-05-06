package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context ctx;
    private List<Product> cartList;

    public CartAdapter(Context ctx) {
        this.ctx = ctx;
        this.cartList = new ArrayList<>();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cartView = LayoutInflater.from(ctx).inflate(R.layout.cart_item_single, parent, false);
        return new CartViewHolder(cartView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Product cartItem = cartList.get(position);

        holder.cartName.setText(cartItem.getProductName());
        holder.cartPrice.setText("$" + cartItem.getProductPrice());
        // Assuming you have a getQuantity method in your Product model
        holder.quantityTvCart.setText(String.valueOf(cartItem.getProductQuantity()));

        Glide.with(ctx)
                .load(cartItem.getProductImage())
                .into(holder.cartImage);

        holder.cartMore.setOnClickListener(v -> {
            // Handle item delete click
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void updateList(List<Product> newList) {
        cartList.clear();
        cartList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        ImageView cartMore;
        TextView cartName;
        TextView cartPrice;
        TextView quantityTvCart;

        public CartViewHolder(View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartMore = itemView.findViewById(R.id.cartMore);
            cartName = itemView.findViewById(R.id.cartName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            quantityTvCart = itemView.findViewById(R.id.quantityTvCart);
        }
    }
}