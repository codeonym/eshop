package com.fsoteam.eshop.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.adapter.CoverOfferAdapter;
import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.adapter.SaleProductAdapter;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView coverRecView;
    private RecyclerView newRecView;
    private RecyclerView saleRecView;
    private ArrayList<Offer> coverOffer;
    private ArrayList<Product> newProduct;
    private ArrayList<Product> saleProduct;

    private CoverOfferAdapter coverOfferAdapter;
    private ProductAdapter newProductAdapter;
    private SaleProductAdapter saleProductAdapter;

    private LottieAnimationView animationView;

    private LinearLayout newLayout;
    private LinearLayout saleLayout;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference offersRef = db.getReference(DbCollections.OFFERS);
    private DatabaseReference productsRef = db.getReference(DbCollections.PRODUCTS);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        coverOffer = new ArrayList<>();
        newProduct = new ArrayList<>();
        saleProduct = new ArrayList<>();
        // populateDatabaseFromJson();
        coverRecView = view.findViewById(R.id.coverRecView);
        newRecView = view.findViewById(R.id.newRecView);
        saleRecView = view.findViewById(R.id.saleRecView);
        newLayout = view.findViewById(R.id.newLayout);
        saleLayout = view.findViewById(R.id.saleLayout);
        animationView = view.findViewById(R.id.animationView);

        hideLayout();
        setCoverData();
        setNewProductData();
        setSaleProductData();

        coverRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        coverRecView.setHasFixedSize(true);
        coverOfferAdapter = new CoverOfferAdapter(getActivity(), coverOffer);
        coverRecView.setAdapter(coverOfferAdapter);

        newRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecView.setHasFixedSize(true);
        newProductAdapter = new ProductAdapter(newProduct, getActivity());
        newRecView.setAdapter(newProductAdapter);

        saleRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        saleRecView.setHasFixedSize(true);
        saleProductAdapter = new SaleProductAdapter(saleProduct, getActivity());
        saleRecView.setAdapter(saleProductAdapter);
        showLayout();


        return view;
    }

    private void hideLayout() {
        animationView.playAnimation();
        animationView.loop(true);
        coverRecView.setVisibility(View.GONE);
        newLayout.setVisibility(View.GONE);
        saleLayout.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
    }

    private void showLayout() {
        animationView.pauseAnimation();
        animationView.setVisibility(View.GONE);
        coverRecView.setVisibility(View.VISIBLE);
        newLayout.setVisibility(View.VISIBLE);
        saleLayout.setVisibility(View.VISIBLE);
    }

    private void setCoverData() {
        Log.d("HomeFragment", "setCoverData: Method called");

        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("HomeFragment", "onDataChange: Method called");
                for (DataSnapshot offerSnapshot: dataSnapshot.getChildren()) {
                    Offer offer = offerSnapshot.getValue(Offer.class);
                    Log.d("HomeFragment", "onDataChange: Offer data - " + offer.getOfferTitle());
                    coverOffer.add(offer);
                }
                coverOfferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("HomeFragment", "onCancelled: Error getting documents: ", databaseError.toException());
            }
        });

        Log.d("HomeFragment", "setCoverData: Method end");
    }

    private void setNewProductData() {
        productsRef.orderByChild("productAddDate").limitToFirst(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            newProduct.add(product);
                        }
                        newProductAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HomeFragment", "Error getting documents: ", databaseError.toException());
                    }
                });
    }
    private void setSaleProductData() {
        productsRef.orderByChild("productRating").limitToFirst(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            saleProduct.add(product);
                        }
                        saleProductAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HomeFragment", "Error getting documents: ", databaseError.toException());
                    }
                });
    }
    public void populateDatabaseFromJson() {

        // this function populates the database with some fake data
        try {
            // Get the JSON data from the file
            InputStream is = getResources().openRawResource(R.raw.populatedb_raw);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(json);
            JSONArray categoriesArray = jsonObject.getJSONArray("categories");
            JSONArray offersArray = jsonObject.getJSONArray("offers");
            JSONArray productsArray = jsonObject.getJSONArray("products");

            // Get a reference to the Firebase database
            FirebaseDatabase db = FirebaseDatabase.getInstance();

            // Populate the categories collection
            DatabaseReference categoriesRef = db.getReference(DbCollections.CATEGORIES);
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject categoryObject = categoriesArray.getJSONObject(i);
                Category category = new Category();
                category.setCategoryId(categoryObject.getString("categoryId"));
                category.setName(categoryObject.getString("name"));
                category.setImage(categoryObject.getString("image"));
                category.setCategoryDescription(categoryObject.getString("categoryDescription"));
                categoriesRef.child(category.getCategoryId()).setValue(category);
            }

            // Populate the offers collection
            DatabaseReference offersRef = db.getReference(DbCollections.OFFERS);
            DateTimeFormatter formatter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            }
            for (int i = 0; i < offersArray.length(); i++) {
                JSONObject offerObject = offersArray.getJSONObject(i);
                Offer offer = new Offer();
                offer.setOfferId(offerObject.getString("offerId"));
                offer.setOfferTitle(offerObject.getString("offerTitle"));
                offer.setOfferDescription(offerObject.getString("offerDescription"));
                offer.setOfferImage(offerObject.getString("offerImage"));
                offer.setOfferStartDate(Long.parseLong(offerObject.getString("offerStartDate")));
                offer.setOfferEndDate(Long.parseLong(offerObject.getString("offerEndDate")));
                offersRef.child(offer.getOfferId()).setValue(offer);
            }

            // Populate the products collection
            DatabaseReference productsRef = db.getReference(DbCollections.PRODUCTS);
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObject = productsArray.getJSONObject(i);
                Product product = new Product();
                product.setProductName(productObject.getString("productName"));
                product.setProductId(productObject.getString("productId"));
                product.setProductPrice((float) productObject.getDouble("productPrice"));
                product.setProductDes(productObject.getString("productDes"));
                product.setProductRating((float) productObject.getDouble("productRating"));
                product.setProductDisCount(productObject.getString("productDisCount"));
                product.setProductHave(productObject.getBoolean("productHave"));
                product.setProductBrand(productObject.getString("productBrand"));
                product.setProductImage(productObject.getString("productImage"));
                Category cat = new Category();
                cat.setCategoryId(productObject.getString("productCategoryId"));
                product.setProductCategory(cat);
                product.setProductNote(productObject.getString("productNote"));
                product.setProductQuantity(productObject.getInt("productQuantity"));
                product.setProductSales(productObject.getInt("productSales"));
                productsRef.child(product.getProductId()).setValue(product);
            }

        } catch (Exception e) {
            Log.e("HomeFragment", "Error populating database", e);
        }
    }
}