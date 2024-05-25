package com.fsoteam.eshop;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fsoteam.eshop.adapter.OrderItemAdapter;
import com.fsoteam.eshop.model.OrderItem;
import com.fsoteam.eshop.utils.CustomDate;
import com.fsoteam.eshop.utils.Helpers;
import com.fsoteam.eshop.utils.OrderStatus;
import com.fsoteam.eshop.utils.PdfDocumentGenerator;
import com.fsoteam.eshop.viewmodel.OrderDetailsViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private static final String TAG = "OrderDetailsActivity";
    private static final int REQUEST_WRITE_STORAGE = 112;
    private TextView orderIdTv, orderDateTv, orderStatusTv, orderTotalAmountTv, orderQuantityTv, orderParagraphTv, orderRecipientNameTv, orderRecipientEmailTv, orderRecipientPhoneTv, orderRecipientAddressTv, orderRecipientCountryTv, orderRecipientCityTv, orderRecipientZipCodeTv;
    private Button confirmReceivedBtnBtn, printInvoiceBtn, cancelOrderBtn, requestRefundBtn;
    private OrderItemAdapter orderItemAdapter;
    private RecyclerView orderItemsRecView;
    private String orderId;
    private CustomDate customDate = new CustomDate();
    private OrderDetailsViewModel orderDetailsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        orderIdTv = findViewById(R.id.orderDetails_orderId);
        orderDateTv = findViewById(R.id.orderDetails_orderDate);
        orderStatusTv = findViewById(R.id.orderDetails_orderStatus);
        orderTotalAmountTv = findViewById(R.id.orderDetails_orderTotalAmount);
        orderQuantityTv = findViewById(R.id.orderDetails_orderQuantity);
        orderParagraphTv = findViewById(R.id.orderDetails_orderParagraph);
        orderRecipientNameTv = findViewById(R.id.orderDetails_orderRecipientName);
        orderRecipientEmailTv = findViewById(R.id.orderDetails_orderRecipientEmail);
        orderRecipientPhoneTv = findViewById(R.id.orderDetails_orderRecipientPhone);
        orderRecipientAddressTv = findViewById(R.id.orderDetails_orderRecipientAddress);
        orderRecipientCountryTv = findViewById(R.id.orderDetails_orderRecipientCountry);
        orderRecipientCityTv = findViewById(R.id.orderDetails_orderRecipientCity);
        orderRecipientZipCodeTv = findViewById(R.id.orderDetails_orderRecipientZipCode);
        confirmReceivedBtnBtn = findViewById(R.id.confirmReceivedBtn);
        confirmReceivedBtnBtn.setVisibility(View.GONE);
        requestRefundBtn = findViewById(R.id.requestRefundBtn);
        requestRefundBtn.setVisibility(View.GONE);
        cancelOrderBtn = findViewById(R.id.cancelOrderBtn);
        cancelOrderBtn.setVisibility(View.GONE);
        printInvoiceBtn = findViewById(R.id.printInvoiceBtn);
        orderItemsRecView = findViewById(R.id.orderDetails_orderItems);
        orderItemsRecView.setLayoutManager(new LinearLayoutManager(this));

        orderId = getIntent().getStringExtra("OrderID");
        orderDetailsViewModel = new ViewModelProvider(this).get(OrderDetailsViewModel.class);
        orderDetailsViewModel.getOrderLiveData().observe(this, order -> {
            if (order != null) {
                orderIdTv.setText(order.getOrderId());
                orderDateTv.setText(customDate.getFormattedDateTime(order.getOrderDate(), "MMM dd, yyyy, h:mm a"));
                orderStatusTv.setText(order.getOrderStatusString());
                Helpers.setStatusTextView(this, orderStatusTv, order.getOrderStatus().toString());
                orderTotalAmountTv.setText(String.valueOf(order.getOrderTotalAmount()) + order.getOrderProducts().get(0).getProduct().getProductCurrency());
                orderQuantityTv.setText(String.valueOf(order.getOrderProducts().size()));
                orderRecipientNameTv.setText(order.getShipmentDetails().getReceiverName());
                orderRecipientEmailTv.setText(order.getShipmentDetails().getReceiverEmail());
                orderRecipientPhoneTv.setText(order.getShipmentDetails().getReceiverPhone());
                orderRecipientAddressTv.setText(order.getShipmentDetails().getReceiverAddress());
                orderRecipientCountryTv.setText(order.getShipmentDetails().getReceiverAddress());
                orderRecipientCityTv.setText(order.getShipmentDetails().getReceiverCity());
                setOrderParagraphTv(order.getOrderStatus());
                orderRecipientZipCodeTv.setText(order.getShipmentDetails().getReceiverZipCode());
                orderItemAdapter = new OrderItemAdapter(this, (ArrayList<OrderItem>) order.getOrderProducts());
                orderItemsRecView.setAdapter(orderItemAdapter);

                printInvoiceBtn.setOnClickListener(v -> {
                    Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    String fileName = System.currentTimeMillis() + "-invoice.pdf";
                    try {
                        PdfDocumentGenerator.createPdfDocument(this, order, logo, fileName);

                        Toast.makeText(this, "PDF Saved  as " + fileName, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(this, "Error generating PDF", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                });

                if(order.getOrderStatus() == OrderStatus.DELIVERED){
                    confirmReceivedBtnBtn.setVisibility(View.VISIBLE);
                    confirmReceivedBtnBtn.setOnClickListener(v -> {
                        orderDetailsViewModel.confirmReceived(order.getOrderId());
                        order.setOrderStatus(OrderStatus.CONFIRMED);
                    });

                }
                if(order.getOrderStatus() == OrderStatus.CONFIRMED){
                    requestRefundBtn.setVisibility(View.VISIBLE);
                    requestRefundBtn.setOnClickListener(v -> {
                        orderDetailsViewModel.returnOrder(order.getOrderId());
                        order.setOrderStatus(OrderStatus.REFUND_REQUESTED);
                    });
                }
                if(order.getOrderStatus() == OrderStatus.PENDING){
                    cancelOrderBtn.setVisibility(View.VISIBLE);
                    cancelOrderBtn.setOnClickListener(v -> {
                        orderDetailsViewModel.cancelOrder(order.getOrderId());
                        order.setOrderStatus(OrderStatus.CANCELLED);
                    });
                }
            }
        });

        orderDetailsViewModel.loadOrder(orderId);
    }

    public void setOrderParagraphTv(OrderStatus orderStatus) {
        switch (orderStatus) {
            case PENDING:
                orderParagraphTv.setText(R.string.status_pending);
                break;
            case PROCESSING:
                orderParagraphTv.setText(R.string.status_processing);
                break;
            case PACKAGED:
                orderParagraphTv.setText(R.string.status_packaged);
                break;
            case SHIPPED:
                orderParagraphTv.setText(R.string.status_shipped);
                break;
            case ON_HOLD:
                orderParagraphTv.setText(R.string.status_on_hold);
                break;
            case DELIVERED:
                orderParagraphTv.setText(R.string.status_delivered);
                break;
            case CONFIRMED:
                orderParagraphTv.setText(R.string.status_confirmed);
                break;
            case COMPLETED:
                orderParagraphTv.setText(R.string.status_completed);
                break;
            case RETURNED:
                orderParagraphTv.setText(R.string.status_returned);
                break;
            case REFUND_REQUESTED:
                orderParagraphTv.setText(R.string.status_refund_requested);
                break;
            case REFUNDED:
                orderParagraphTv.setText(R.string.status_refunded);
                break;
            case CANCELLED:
                orderParagraphTv.setText(R.string.status_cancelled);
                break;
            case REJECTED:
                orderParagraphTv.setText(R.string.status_rejected);
                break;
        }
    }
}