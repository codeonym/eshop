package com.fsoteam.eshop.utils;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.graphics.pdf.PdfDocument.Page;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.fsoteam.eshop.model.Order;
import com.fsoteam.eshop.model.OrderItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class PdfDocumentGenerator {

    private static final int PAGE_WIDTH = 595;
    private static final int PAGE_HEIGHT = 842;
    private static final int MARGIN = 20;

    public static void createPdfDocument(Context context, Order order, Bitmap logo, String fileName) throws IOException {
        PdfDocument document = new PdfDocument();
        PageInfo pageInfo = new PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create();
        Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(10);

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(14);
        titlePaint.setFakeBoldText(true);

        Paint pricePaint = new Paint();
        pricePaint.setColor(Color.RED);
        pricePaint.setTextSize(16);
        pricePaint.setFakeBoldText(true);

        Paint itemPaint = new Paint();
        itemPaint.setColor(Color.GRAY);
        itemPaint.setTextSize(8);




        int y = MARGIN;

        int logoWidth = 50; // specify the width
        int logoHeight = 50; // specify the height
        Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, false);

        // Draw the logo
        int logoMargin = (PAGE_WIDTH - scaledLogo.getWidth()) / 2;
        canvas.drawBitmap(scaledLogo, logoMargin , y, null);
        y += scaledLogo.getHeight() + MARGIN;

        // Generate a barcode from the order ID
        Bitmap barcodeBitmap = null;
        int barcodeBitmapWidth = 80;
        int barcodeBitmapHeight = 80;
        try {
            barcodeBitmap = encodeAsBitmap(order.getOrderId(), BarcodeFormat.QR_CODE, barcodeBitmapWidth, barcodeBitmapHeight);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // Draw the barcode onto the PDF
        if (barcodeBitmap != null) {
            int margin = (PAGE_WIDTH - barcodeBitmap.getWidth()) / 2;
            canvas.drawBitmap(barcodeBitmap, margin, y, null);
            y += barcodeBitmap.getHeight() + MARGIN;
        }
        String placeholder = "";
        for (int i = 0; i < 100; i++) {
            placeholder += "-";
        }

        canvas.drawText(placeholder, MARGIN, y, paint);
        y += paint.getTextSize() + MARGIN;

        // Draw the order details
        canvas.drawText("Order Details: ", MARGIN, y, titlePaint);
        y += paint.getTextSize() + MARGIN;
        // Draw the order details
        canvas.drawText(order.getOrderId(), MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy, h:mm a", Locale.getDefault());
        String orderDate = sdf.format(order.getOrderDate());
        canvas.drawText(orderDate, MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText("Payment Method: Delivery", MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(placeholder, MARGIN, y, paint);
        y += paint.getTextSize() + MARGIN;

        // Draw the shipment details
        canvas.drawText("Shipment details:", MARGIN, y, titlePaint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(order.getShipmentDetails().getReceiverName(), MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(order.getShipmentDetails().getReceiverEmail(), MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(order.getShipmentDetails().getReceiverPhone(), MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(order.getShipmentDetails().getReceiverAddress(), MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(order.getShipmentDetails().getReceiverCountry() + " | "+ order.getShipmentDetails().getReceiverCity() + " | " + order.getShipmentDetails().getReceiverZipCode(), MARGIN + 100, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText(placeholder, MARGIN, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText("Ordered items: ", MARGIN, y, titlePaint);
        y += paint.getTextSize() + MARGIN;

        String currency = "";
        int i = 1;
        // Draw the order items
        for (OrderItem item : order.getOrderProducts()) {
            currency = item.getProduct().getProductCurrency();
            canvas.drawText(i + " -" + item.getProduct().getProductName() + " x" + item.getQuantity() + " \t " + item.getProduct().getProductPrice() + item.getProduct().getProductCurrency(), MARGIN + 50, y, itemPaint);
            y += paint.getTextSize() + MARGIN;
            i++;
        }

        canvas.drawText(placeholder, MARGIN, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText("Order Summary: ", MARGIN, y, titlePaint);
        y += paint.getTextSize() + MARGIN;

        // Draw the total order items and total amount
        canvas.drawText("Total Items: " + order.getOrderProducts().size(), MARGIN, y, paint);
        y += paint.getTextSize() + MARGIN;

        canvas.drawText("Total Amount: " + order.getOrderTotalAmount() + currency, MARGIN * 2, y, pricePaint);

        document.finishPage(page);

        // Create a ContentValues object and set the necessary attributes for the file
        ContentValues values = new ContentValues();
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        // Use the ContentResolver to create a new file in the MediaStore
        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);

        // Get an OutputStream from the Uri and write your PDF document to it
        if (uri != null) {
            OutputStream outputStream = resolver.openOutputStream(uri);
            if (outputStream != null) {
                document.writeTo(outputStream);
                document.close();
                outputStream.close();
            }
        }
    }
    private static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }
}