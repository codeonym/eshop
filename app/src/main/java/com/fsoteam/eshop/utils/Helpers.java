package com.fsoteam.eshop.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.fsoteam.eshop.R;

public class Helpers {

    public static String getCurrencySymbol(String currency) {
        switch (currency) {
            case "USD":
                return "$";
            case "EUR":
                return "€";
            case "GBP":
                return "£";
            case "JPY":
                return "¥";
            case "RUB":
                return "₽";
            case "INR":
                return "₹";
            case "CNY":
                return "¥";
            case "AED":
                return "د.إ";
            case "AUD":
                return "$";
            case "CAD":
                return "$";
            case "CHF":
                return "CHF";
            case "HKD":
                return "HK$";
            case "SGD":
                return "S$";
            case "SEK":
                return "kr";
            case "ZAR":
                return "R";
            case "BRL":
                return "R$";
            case "TRY":
                return "₺";
            case "KRW":
                return "₩";
            case "IDR":
                return "Rp";
            case "THB":
                return "฿";
            case "MYR":
                return "RM";
            case "NGN":
                return "₦";
            case "MXN":
                return "$";
            case "ILS":
                return "₪";
            case "DKK":
                return "kr";
            case "NOK":
                return "kr";
            case "CZK":
                return "Kč";
            case "HUF":
                return "Ft";
            case "NZD":
                return "$";
            case "PLN":
                return "zł";
            case "PHP":
                return "₱";
            case "SAR":
                return "﷼";
            case "VND":
                return "₫";
            case "RON":
                return "lei";
            case "ARS":
                return "$";
            case "COP":
                return "$";
            case "CLP":
                return "$";
            case "PEN":
                return "S/";
            case "EGP":
                return "E£";
            case "UAH":
                return "₴";
            case "BDT":
                return "৳";
            case "IQD":
                return "ع.د";
            case "QAR":
                return "﷼";
        }
        return currency;
    }

    public static void setStatusTextView(Context ctx, TextView textView, String status) {
        switch (status) {
            case "PENDING":
                textView.setText("Pending");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.pending));
                break;
            case "PROCESSING":
                textView.setText("Processing");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.processing));
                break;
            case "SHIPPED":
                textView.setText("Shipped");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.shipped));
                break;
            case "ON_HOLD":
                textView.setText("On Hold");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.onHold));
                break;
            case "DELIVERED":
                textView.setText("Delivered");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.delivered));
                break;
            case "CONFIRMED":
                textView.setText("Confirmed");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.confirmed));
                break;
            case "RETURNED":
                textView.setText("Returned");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.returned));
                break;
            case "CANCELLED":
                textView.setText("Cancelled");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.cancelled));
                break;
            case "REFUNDED":
                textView.setText("Refunded");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.refunded));
                break;
            case "COMPLETED":
                textView.setText("Completed");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.completed));
                break;
            case "PACKAGED":
                textView.setText("Packaged");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.packaging));
                break;
            case "REJECTED":
                textView.setText("Rejected");
                textView.setTextColor(ContextCompat.getColor(ctx, R.color.rejected));
                break;
        }
    }

}
