package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.adapter.FavProductAdapter;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.model.User;
import com.fsoteam.eshop.model.Wishlist;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FavFragment extends Fragment {

    private RecyclerView favRecyclerView;
    private LottieAnimationView animationView;
    private FavProductAdapter favProductAdapter;
    private Wishlist userWishlist;
    private DatabaseReference userRef;
    private String currentUserId = FirebaseAuth.getInstance().getUid(); // get the current user's ID

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        favRecyclerView = view.findViewById(R.id.favRecyclerView);
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        animationView = view.findViewById(R.id.animationViewFavPage);

        userRef = FirebaseDatabase.getInstance().getReference(DbCollections.USERS);

        loadFavProducts();

        return view;
    }

    private void loadFavProducts() {
        userRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                userWishlist = currentUser.getUserWishlist();

                if(userWishlist.getWishlistProducts().isEmpty()) {
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                    favRecyclerView.setVisibility(View.GONE);
                } else {
                    animationView.setVisibility(View.GONE);
                    favRecyclerView.setVisibility(View.VISIBLE);

                    List<Product> favProductList = userWishlist.getWishlistProducts();
                    favProductAdapter = new FavProductAdapter(getContext(), favProductList);
                    favRecyclerView.setAdapter(favProductAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load wishlist.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}