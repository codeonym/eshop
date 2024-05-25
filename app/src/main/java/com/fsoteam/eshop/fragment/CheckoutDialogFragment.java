package com.fsoteam.eshop.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.R;
import com.fsoteam.eshop.adapter.OrderItemAdapter;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.viewmodel.CheckoutViewModel;
import java.util.ArrayList;

public class CheckoutDialogFragment extends DialogFragment {

    private ArrayList<OrderItem> cartItems;
    private float sum;
    private ArrayList<ShipmentDetails> shippingAddresses;
    private ArrayList<String> shippingAddressesNames;
    private Context ctx;
    private RecyclerView orderItemsRecView;
    private OrderItemAdapter orderItemAdapter;

    private CheckoutViewModel checkoutViewModel;

    public CheckoutDialogFragment(Context ctx, ArrayList<OrderItem> cartItems, float sum, ArrayList<ShipmentDetails> shippingAddresses) {
        this.cartItems = cartItems;
        this.ctx = ctx;
        this.sum = sum;
        this.shippingAddresses = shippingAddresses;
        this.shippingAddressesNames = new ArrayList<>();
        for (ShipmentDetails address : shippingAddresses) {
            shippingAddressesNames.add(address.getShipmentTitle());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_checkout, null);

        // Find the dialog elements and set their values
        TextView totalPrice = dialogView.findViewById(R.id.totalPrice);
        Spinner shippingAddress = dialogView.findViewById(R.id.shippingAddress);
        Button dialogPositiveBtn = dialogView.findViewById(R.id.dialogPositiveBtn);
        Button dialogNegativeBtn = dialogView.findViewById(R.id.dialogNegativeBtn);
        orderItemsRecView = dialogView.findViewById(R.id.orderedItemsRecycler);
        orderItemsRecView.setLayoutManager(new LinearLayoutManager(ctx));
        orderItemAdapter = new OrderItemAdapter(ctx, cartItems);
        orderItemsRecView.setAdapter(orderItemAdapter);

        totalPrice.setText(sum + "");

        // Populate the shippingAddress Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, shippingAddressesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingAddress.setAdapter(adapter);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        dialogPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the selected shipping address
                String selectedAddress = (String) shippingAddress.getSelectedItem();
                int selectedAddressIndex = -1;

                for(int i = 0; i < shippingAddresses.size(); i++) {
                    if(shippingAddresses.get(i).getShipmentTitle().equals(selectedAddress)) {
                        selectedAddressIndex = i;
                        break;
                    }
                }

                ShipmentDetails shipmentDetails = shippingAddresses.get(selectedAddressIndex);
                if(shipmentDetails == null || selectedAddressIndex == -1) {
                    Toast.makeText(ctx, "Please select a shipping address", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkoutViewModel.submitOrder(cartItems, shipmentDetails, sum).addOnCompleteListener(task -> {

                    // Dismiss the dialog
                    Toast.makeText(ctx, "Order is being processed", Toast.LENGTH_SHORT).show();
                    dismiss();
                });
            }
        });
        dialogNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the user cancelling the checkout
                dismiss();
            }
        });

        // Create a new AlertDialog.Builder and set the view
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(dialogView);

        return builder.create();
    }

}