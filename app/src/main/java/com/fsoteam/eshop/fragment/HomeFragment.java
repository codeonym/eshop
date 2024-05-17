package com.fsoteam.eshop.fragment;

import static com.fsoteam.eshop.utils.CustomUtils.populateDB;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.fsoteam.eshop.LoginActivity;
import com.fsoteam.eshop.SplashScreenActivity;
import com.fsoteam.eshop.adapter.CoverOfferAdapter;
import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.ProductImage;
import com.fsoteam.eshop.utils.CustomUtils;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private RecyclerView coverRecView;
    private RecyclerView newRecView;
    private RecyclerView saleRecView;
    private ArrayList<Offer> coverOffer;
    private ArrayList<Product> newProduct;
    private ArrayList<Product> saleProduct;

    private CoverOfferAdapter coverOfferAdapter;
    private ProductAdapter newProductAdapter;
    private ProductAdapter saleProductAdapter;

    private LottieAnimationView animationView;

    private LinearLayout newLayout;
    private LinearLayout saleLayout;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference offersRef = db.getReference(DbCollections.OFFERS);
    private DatabaseReference productsRef = db.getReference(DbCollections.PRODUCTS);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //populateDB(getActivity());

        coverOffer = new ArrayList<>();
        newProduct = new ArrayList<>();
        saleProduct = new ArrayList<>();

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
        showLayout();

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
        saleProductAdapter = new ProductAdapter(saleProduct, getActivity());
        saleRecView.setAdapter(saleProductAdapter);
        View newProductsTv = view.findViewById(R.id.product_GroupViewAll);
        View saleProductsTv = view.findViewById(R.id.saleProduct_ViewAll);
        CustomUtils.setProductsFragmentFilter(getActivity(), newProductsTv, null, true, false);
        CustomUtils.setProductsFragmentFilter(getActivity(), saleProductsTv, null, false, true);
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
        productsRef.orderByChild("productAddDate").limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            newProduct.add(product);
                        }

                        Collections.reverse(newProduct);
                        newProductAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HomeFragment", "Error getting documents: ", databaseError.toException());
                    }
                });
    }
    private void setSaleProductData() {
        productsRef.orderByChild("likesCount").limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            if(product != null)
                                saleProduct.add(product);
                            System.out.println(product.toString());
                        }

                        Collections.reverse(saleProduct);

                        saleProductAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("HomeFragment", "Error getting documents: ", databaseError.toException());
                    }
                });
    }
}