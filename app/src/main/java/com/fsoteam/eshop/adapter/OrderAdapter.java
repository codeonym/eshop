package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.OrderDetailsActivity;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Order;
import com.fsoteam.eshop.utils.CustomDate;
import com.fsoteam.eshop.utils.Helpers;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {
    private Context ctx;
    private ArrayList<Order> orders;

    public OrderAdapter(Context ctx, ArrayList<Order> orders) {
        this.ctx = ctx;
        this.orders = orders;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_single, parent, false);
        return new OrderAdapter.viewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        int pos = holder.getAdapterPosition();
        if(pos == RecyclerView.NO_POSITION) {
            return;
        }

        CustomDate customDate = new CustomDate();
        Order order = orders.get(pos);
        holder.orderId.setText(order.getOrderId());
        holder.orderDate.setText(customDate.getFormattedDateTime(order.getOrderDate()));
        holder.orderStatus.setText(order.getOrderStatusString());
        Helpers.setStatusTextView(ctx, holder.orderStatus, order.getOrderStatus().toString());

        holder.orderTotalAmount.setText(String.valueOf(order.getOrderTotalAmount()) + "DH");
        holder.orderQuantity.setText(order.getOrderProducts().size() +" items");

        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, OrderDetailsActivity.class);
            intent.putExtra("OrderID", order.getOrderId());
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView orderId;
        private TextView orderDate;
        private TextView orderStatus;
        private TextView orderTotalAmount;
        private TextView orderQuantity;
        private Button detailsButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.single_orderTitle);
            orderDate = itemView.findViewById(R.id.single_orderDate);
            orderStatus = itemView.findViewById(R.id.single_orderStatus);
            orderTotalAmount = itemView.findViewById(R.id.single_orderTotalPrice);
            orderQuantity = itemView.findViewById(R.id.single_orderQuantity);
            detailsButton = itemView.findViewById(R.id.single_orderDetailsBtn);
        }
    }
}
