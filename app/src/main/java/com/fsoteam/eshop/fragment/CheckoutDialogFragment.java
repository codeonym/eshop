package com.fsoteam.eshop.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

import com.fsoteam.eshop.R;
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
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        TextView totalPrice = dialogView.findViewById(R.id.totalPrice);
        LinearLayout orderedItems = dialogView.findViewById(R.id.orderedItems);
        Spinner shippingAddress = dialogView.findViewById(R.id.shippingAddress);
        Button dialogPositiveBtn = dialogView.findViewById(R.id.dialogPositiveBtn);
        Button dialogNegativeBtn = dialogView.findViewById(R.id.dialogNegativeBtn);

        dialogTitle.setText("Checkout Confirmation");
        dialogMessage.setText("Please select your shipping address");
        totalPrice.setText("Total Price: " + sum);

        // Add ordered items to the orderedItems LinearLayout
        for (OrderItem item : cartItems) {
            TextView itemTextView = new TextView(ctx);
            itemTextView.setText(item.getProduct().getProductName() + " x" + item.getQuantity() + " - " + item.getProduct().getProductPrice() + item.getProduct().getProductCurrency());
            orderedItems.addView(itemTextView);
        }

        // Populate the shippingAddress Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, shippingAddressesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingAddress.setAdapter(adapter);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        dialogPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the selected shipping address
                String selectedAddress = (String) shippingAddress.getSelectedItem();
                int selectedAddressIndex = shippingAddresses.indexOf(selectedAddress);
                checkoutViewModel.submitOrder(cartItems, sum, shippingAddresses, selectedAddressIndex).addOnCompleteListener(task -> {
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