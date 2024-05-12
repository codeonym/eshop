package com.fsoteam.eshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fsoteam.eshop.adapter.CategoryAdapter;
import com.fsoteam.eshop.adapter.CoverOfferAdapter;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.utils.CustomUtils;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private ArrayList<Category> cateList;
    private ArrayList<Offer> coverOffer;
    private CategoryAdapter categoryAdapter;
    private CoverOfferAdapter coverOfferAdapter;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference offersRef = db.getReference(DbCollections.OFFERS);
    private DatabaseReference categoriesRef = db.getReference(DbCollections.CATEGORIES);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView coverRecView_shopFrag = view.findViewById(R.id.coverRecView_shopFrag);
        RecyclerView categoriesRecView = view.findViewById(R.id.categoriesRecView);

        cateList = new ArrayList<>();
        coverOffer = new ArrayList<>();

        setCoverData();
        setCategoryData();

        coverRecView_shopFrag.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        coverRecView_shopFrag.setHasFixedSize(true);
        coverOfferAdapter = new CoverOfferAdapter(getActivity(), coverOffer);
        coverRecView_shopFrag.setAdapter(coverOfferAdapter);

        categoriesRecView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        categoriesRecView.setHasFixedSize(true);
        categoryAdapter = new CategoryAdapter(getActivity(), cateList);
        categoriesRecView.setAdapter(categoryAdapter);

        TextView allProductsTv = view.findViewById(R.id.shop_fragment_all_products);

        CustomUtils.setProductsFragmentFilter(getActivity(), allProductsTv, null, false, false);
        return view;
    }

    private void setCategoryData() {
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    cateList.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ShopFragment", "Error getting documents: ", databaseError.toException());
            }
        });
    }

    private String getJsonData(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private void setCoverData() {
        offersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot offerSnapshot: dataSnapshot.getChildren()) {
                    Offer offer = offerSnapshot.getValue(Offer.class);
                    coverOffer.add(offer);
                }
                coverOfferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("HomeFragment", "Error getting documents: ", databaseError.toException());
            }
        });
    }
}