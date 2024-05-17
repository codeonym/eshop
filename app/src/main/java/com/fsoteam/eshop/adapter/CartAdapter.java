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
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context ctx;
    private List<OrderItem> cartItemsList;

    private String userId = FirebaseAuth.getInstance().getUid();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference cartRef = db.getReference(DbCollections.USERS).child(userId).child("userCart").child("cartItems");

    public CartAdapter(Context ctx, List<OrderItem> cartItemsList) {
        this.ctx = ctx;
        this.cartItemsList = cartItemsList;
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
        holder.cartPrice.setText(cartItem.getProduct().getProductPrice() + cartItem.getProduct().getProductCurrency());
        holder.cartItemPLus.setOnClickListener(v -> {

            Product product = cartItem.getProduct();
            float availableQuantity = product.getProductQuantity() - product.getProductSales();
            if(cartItem.getQuantity() < product.getProductMaxPurchasePerUser() && availableQuantity > 0){
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                holder.quantityTvCart.setText(String.valueOf(cartItem.getQuantity()));
                updateCartItem(holder, cartItem);
            }
        });
        holder.cartItemMinus.setOnClickListener(v -> {

            if(cartItem.getQuantity() > 1){
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                holder.quantityTvCart.setText(String.valueOf(cartItem.getQuantity()));
                holder.cartPrice.setText(cartItem.getProduct().getProductPrice() * cartItem.getQuantity() + "DH");
                updateCartItem(holder, cartItem);
            }
        });

        holder.quantityTvCart.setText(String.valueOf(cartItem.getQuantity()));

        Glide.with(ctx)
                .load(cartItem.getProduct().getProductImage())
                .into(holder.cartImage);

        holder.cartRemove.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if(pos != -1){
                OrderItem removedItem = cartItemsList.remove(pos);
                cartRef.child(removedItem.getItemId()).removeValue();
                notifyItemRemoved(pos);
            }
        });
    }

    private void updateCartItem(CartViewHolder holder, OrderItem cartItem){

        holder.cartPrice.setText(cartItem.getProduct().getProductPrice() * cartItem.getQuantity() + "DH");
        cartRef.child(cartItem.getItemId()).setValue(cartItem);
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