package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.viewHolder> {

    private Context ctx;
    private ArrayList<OrderItem> orderItems;


    public OrderItemAdapter(Context ctx, ArrayList<OrderItem> orderItems) {
        Log.d("OrderItemAdapter", "OrderItemAdapter: " + orderItems.size());
        this.ctx = ctx;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_card, parent, false);
        return new OrderItemAdapter.viewHolder(orderItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Log.d("OrderItemAdapter", "onBindViewHolder called for position: " + position);
        if(pos == RecyclerView.NO_POSITION) {
            return;
        }
        OrderItem orderItem = orderItems.get(pos);
        holder.productName.setText(orderItem.getProduct().getProductName());
        holder.productPrice.setText(String.valueOf(orderItem.getProduct().getProductPrice()));
        holder.productQuantity.setText(String.valueOf(orderItem.getQuantity()) );
        holder.productBrand.setText(orderItem.getProduct().getProductBrand());
        Glide.with(ctx).load(orderItem.getProduct().getProductThumbnail()).into(holder.productThumbnail);

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productPrice, productQuantity, productBrand;
        ImageView productThumbnail;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            productThumbnail = itemView.findViewById(R.id.orderItem_itemThumbnail);
            productName = itemView.findViewById(R.id.orderItem_itemName);
            productPrice = itemView.findViewById(R.id.orderItem_itemPrice);
            productQuantity = itemView.findViewById(R.id.orderItem_itemQuantity);
            productBrand = itemView.findViewById(R.id.orderItem_itemBrand);
        }
    }
}
