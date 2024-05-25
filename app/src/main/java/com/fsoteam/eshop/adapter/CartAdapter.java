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
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.viewmodel.BagViewModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context ctx;
    private List<OrderItem> cartItemsList;
    private BagViewModel bagViewModel;

    public CartAdapter(Context ctx, List<OrderItem> cartItemsList, BagViewModel bagViewModel) {
        this.ctx = ctx;
        this.cartItemsList = cartItemsList;
        this.bagViewModel = bagViewModel;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cartView = LayoutInflater.from(ctx).inflate(R.layout.cart_item_single, parent, false);
        return new CartViewHolder(cartView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        OrderItem cartItem = cartItemsList.get(holder.getAdapterPosition());

        holder.cartName.setText(cartItem.getProduct().getProductName());
        holder.cartPrice.setText((cartItem.getProduct().getProductPrice() * cartItem.getQuantity()) + cartItem.getProduct().getProductCurrency());
        holder.cartItemPLus.setOnClickListener(v -> {
            bagViewModel.increaseQuantity(cartItem);
            holder.quantityTvCart.setText(String.valueOf(cartItem.getQuantity()));
            holder.cartPrice.setText((cartItem.getProduct().getProductPrice() * cartItem.getQuantity()) + cartItem.getProduct().getProductCurrency());
        });
        holder.cartItemMinus.setOnClickListener(v -> {
            bagViewModel.decreaseQuantity(cartItem);
            holder.quantityTvCart.setText(String.valueOf(cartItem.getQuantity()));
            holder.cartPrice.setText((cartItem.getProduct().getProductPrice() * cartItem.getQuantity()) + cartItem.getProduct().getProductCurrency());
        });

        holder.quantityTvCart.setText(String.valueOf(cartItem.getQuantity()));

        Glide.with(ctx)
                .load(cartItem.getProduct().getProductImage())
                .into(holder.cartImage);

        holder.cartRemove.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if(pos != -1){
                OrderItem removedItem = cartItemsList.remove(pos);
                bagViewModel.removeCartItem(removedItem);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        ImageView cartRemove;
        TextView cartName;
        TextView cartPrice;
        TextView quantityTvCart;
        ImageView cartItemPLus;
        ImageView cartItemMinus;

        public CartViewHolder(View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartRemove = itemView.findViewById(R.id.cartRemove);
            cartName = itemView.findViewById(R.id.cartName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            quantityTvCart = itemView.findViewById(R.id.quantityTvCart);
            cartItemPLus = itemView.findViewById(R.id.cartItemPLus);
            cartItemMinus = itemView.findViewById(R.id.cartItemMinus);
        }
    }
}