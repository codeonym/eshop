package com.fsoteam.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fsoteam.eshop.adapter.ShippingAddressAdapter;
import com.fsoteam.eshop.model.ShipmentDetails;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddShippingAddressActivity extends AppCompatActivity {

    private EditText shipmentTitleEt, receiverNameEt, receiverEmailEt, receiverPhoneEt ,receiverCountryEt, receiverCityEt, receiverZipCodeEt, receiverAddressEt;
    private TextView shipmentTitleError, receiverNameError, receiverEmailError, receiverPhoneError, receiverCountryError, receiverCityError, receiverZipCodeError, receiverAddressError;
    private Button addShippingAddressBtn;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String userUid = FirebaseAuth.getInstance().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_address);
        shipmentTitleEt = findViewById(R.id.shipmentTitleEt);
        receiverNameEt = findViewById(R.id.receiverNameEt);
        receiverEmailEt = findViewById(R.id.receiverEmailEt);
        receiverPhoneEt = findViewById(R.id.receiverPhoneEt);
        receiverCountryEt = findViewById(R.id.receiverCountryEt);
        receiverCityEt = findViewById(R.id.receiverCityEt);
        receiverZipCodeEt = findViewById(R.id.receiverZipCodeEt);
        receiverAddressEt = findViewById(R.id.receiverAddressEt);

        shipmentTitleError = findViewById(R.id.shipmentTitleError);
        receiverNameError = findViewById(R.id.receiverNameError);
        receiverEmailError = findViewById(R.id.receiverEmailError);
        receiverPhoneError = findViewById(R.id.receiverPhoneError);
        receiverCountryError = findViewById(R.id.receiverCountryError);
        receiverCityError = findViewById(R.id.receiverCityError);
        receiverZipCodeError = findViewById(R.id.receiverZipCodeError);
        receiverAddressError = findViewById(R.id.receiverAddressError);

        textAutoCheck(shipmentTitleEt, shipmentTitleError);
        textAutoCheck(receiverNameEt, receiverNameError);
        textAutoCheck(receiverEmailEt, receiverEmailError);
        textAutoCheck(receiverPhoneEt, receiverPhoneError);
        textAutoCheck(receiverCountryEt, receiverCountryError);
        textAutoCheck(receiverCityEt, receiverCityError);
        textAutoCheck(receiverZipCodeEt, receiverZipCodeError);
        textAutoCheck(receiverAddressEt, receiverAddressError);


        addShippingAddressBtn = findViewById(R.id.addShippingAddressBtn);

        addShippingAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });
    }
    private void textAutoCheck(EditText et, TextView tvError) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                et.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkEditTextValidation(et)) {
                    et.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                    tvError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et.getText().toString().isEmpty()) {
                    et.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Can't be Empty");
                } else {
                    et.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.ic_check), null);
                    tvError.setVisibility(View.GONE);
                }
            }
        });
    }

    private void checkInput() {
        if (!checkEditTextValidation(shipmentTitleEt)) {
            shipmentTitleError.setVisibility(View.VISIBLE);
            shipmentTitleError.setText("Can't be Empty");
            return;
        }
        if (!checkEditTextValidation(receiverNameEt)) {
            receiverNameError.setVisibility(View.VISIBLE);
            receiverNameError.setText("Can't be Empty");
            return;
        }
        if (!checkEditTextValidation(receiverEmailEt)) {
            receiverEmailError.setVisibility(View.VISIBLE);
            receiverEmailError.setText("Invalid Email");
            return;
        }
        if (!checkEditTextValidation(receiverPhoneEt)) {
            receiverPhoneError.setVisibility(View.VISIBLE);
            receiverPhoneError.setText("Invalid Phone Number");
            return;
        }
        if (!checkEditTextValidation(receiverCountryEt)) {
            receiverCountryError.setVisibility(View.VISIBLE);
            receiverCountryError.setText("Can't be Empty");
            return;
        }
        if (!checkEditTextValidation(receiverCityEt)) {
            receiverCityError.setVisibility(View.VISIBLE);
            receiverCityError.setText("Can't be Empty");
            return;
        }
        if (!checkEditTextValidation(receiverZipCodeEt)) {
            receiverZipCodeError.setVisibility(View.VISIBLE);
            receiverZipCodeError.setText("Invalid Zip Code");
            return;
        }
        if (!checkEditTextValidation(receiverAddressEt)) {
            receiverAddressError.setVisibility(View.VISIBLE);
            receiverAddressError.setText("Can't be Empty");
            return;
        }

        // If all the input fields are valid, create a new ShipmentDetails object and add it to the Firebase Database
        ShipmentDetails shipmentDetails = new ShipmentDetails();
        shipmentDetails.setShipmentTitle(shipmentTitleEt.getText().toString());
        shipmentDetails.setReceiverName(receiverNameEt.getText().toString());
        shipmentDetails.setReceiverEmail(receiverEmailEt.getText().toString());
        shipmentDetails.setReceiverPhone(receiverPhoneEt.getText().toString());
        shipmentDetails.setReceiverCountry(receiverCountryEt.getText().toString());
        shipmentDetails.setReceiverCity(receiverCityEt.getText().toString());
        shipmentDetails.setReceiverZipCode(receiverZipCodeEt.getText().toString());
        shipmentDetails.setReceiverAddress(receiverAddressEt.getText().toString());

        databaseReference.child(DbCollections.USERS).child(userUid).child("userShipmentAddress").child(shipmentDetails.getShipmentId()).setValue(shipmentDetails);

        Toast.makeText(this, "Shipment Address Added Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean checkEditTextValidation(EditText et) {
        int id = et.getId();

        if (id == R.id.shipmentTitleEt) {
            return checkShipmentTitle(et);
        } else if (id == R.id.receiverNameEt) {
            return checkReceiverName(et);
        } else if (id == R.id.receiverEmailEt) {
            return checkReceiverEmail(et);
        } else if (id == R.id.receiverPhoneEt) {
            return checkReceiverPhone(et);
        } else if (id == R.id.receiverCountryEt) {
            return checkReceiverCountry(et);
        } else if (id == R.id.receiverCityEt) {
            return checkReceiverCity(et);
        } else if (id == R.id.receiverZipCodeEt) {
            return checkReceiverZipCode(et);
        } else if (id == R.id.receiverAddressEt) {
            return checkReceiverAddress(et);
        } else {
            return false;
        }
    }

    private boolean checkShipmentTitle(EditText et) {
        String title = et.getText().toString().trim();
        return !title.isEmpty();
    }

    private boolean checkReceiverName(EditText et) {
        String name = et.getText().toString().trim();
        return !(name.isEmpty() || name.length() < 3 || !name.matches("^[a-zA-Z\\s]+$"));
    }

    private boolean checkReceiverEmail(EditText et) {
        String email = et.getText().toString().trim();
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkReceiverPhone(EditText et) {
        String phone = et.getText().toString().trim();
        return Patterns.PHONE.matcher(phone).matches();
    }

    private boolean checkReceiverCountry(EditText et) {
        String country = et.getText().toString().trim();
        return !country.isEmpty();
    }

    private boolean checkReceiverCity(EditText et) {
        String city = et.getText().toString().trim();
        return !(city.isEmpty() || city.length() < 3 || !city.matches("^[a-zA-Z\\s]+$"));
    }

    private boolean checkReceiverZipCode(EditText et) {
        String zipCode = et.getText().toString().trim();
        return !(zipCode.isEmpty() || !zipCode.matches("\\d+"));
    }

    private boolean checkReceiverAddress(EditText et) {
        String address = et.getText().toString().trim();
        return !(address.isEmpty() || address.length() < 10);
    }

}