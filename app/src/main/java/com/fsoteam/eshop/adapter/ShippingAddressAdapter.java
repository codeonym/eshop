package com.fsoteam.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.viewmodel.ShippingAddressViewModel;
import java.util.List;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ShippingAddressViewHolder> {

    private List<ShipmentDetails> shipmentDetailsList;
    private Context ctx;
    private ShippingAddressViewModel shipmentDetailsViewModel;

    public ShippingAddressAdapter(List<ShipmentDetails> shipmentDetailsList, Context ctx, ShippingAddressViewModel shipmentDetailsViewModel) {
        this.shipmentDetailsList = shipmentDetailsList;
        this.ctx = ctx;
        this.shipmentDetailsViewModel = shipmentDetailsViewModel;
    }

    @NonNull
    @Override
    public ShippingAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_address_single_item, parent, false);
        return new ShippingAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingAddressViewHolder holder, int position) {
        ShipmentDetails shipmentDetails = shipmentDetailsList.get(holder.getAdapterPosition());

        // Set the data to the views
        holder.shipmentTitle.setText(shipmentDetails.getShipmentTitle());
        holder.receiverNameTv.setText(shipmentDetails.getReceiverName());
        holder.receiverEmailTv.setText(shipmentDetails.getReceiverEmail());
        holder.receiverPhoneTv.setText(shipmentDetails.getReceiverPhone());
        holder.receiverCountryTv.setText(shipmentDetails.getReceiverCountry());
        holder.receiverCityTv.setText(shipmentDetails.getReceiverCity());
        holder.receiverZipTv.setText(shipmentDetails.getReceiverZipCode());
        holder.receiverAddressTv.setText(shipmentDetails.getReceiverAddress());

        // Set a click listener to the cartRemove ImageView
        holder.cartRemove.setOnClickListener(v -> {

            // Remove the item from the list
            shipmentDetailsList.remove(holder.getAdapterPosition());

            shipmentDetailsViewModel.removeShipmentDetails(shipmentDetails.getShipmentId());
        });
    }

    @Override
    public int getItemCount() {
        return shipmentDetailsList.size();
    }

    static class ShippingAddressViewHolder extends RecyclerView.ViewHolder {

        TextView shipmentTitle, receiverNameTv, receiverEmailTv, receiverPhoneTv, receiverCountryTv, receiverCityTv, receiverZipTv, receiverAddressTv;
        ImageView cartRemove;

        public ShippingAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            shipmentTitle = itemView.findViewById(R.id.shippingAddress_shipmentTitleTv);
            receiverNameTv = itemView.findViewById(R.id.shippingAddress_receiverNameTv);
            receiverEmailTv = itemView.findViewById(R.id.shippingAddress_receiverEmailTv);
            receiverPhoneTv = itemView.findViewById(R.id.shippingAddress_receiverPhoneTv);
            receiverCountryTv = itemView.findViewById(R.id.shippingAddress_receiverCountryTv);
            receiverCityTv = itemView.findViewById(R.id.shippingAddress_receiverCityTv);
            receiverZipTv = itemView.findViewById(R.id.shippingAddress_receiverZipTv);
            receiverAddressTv = itemView.findViewById(R.id.shippingAddress_receiverAddressTv);
            cartRemove = itemView.findViewById(R.id.cartRemove);
        }
    }
}