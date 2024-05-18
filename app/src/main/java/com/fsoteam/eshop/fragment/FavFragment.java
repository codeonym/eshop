package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fsoteam.eshop.viewmodel.FavViewModel;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.adapter.FavProductAdapter;

public class FavFragment extends Fragment {

    private RecyclerView favRecyclerView;
    private LottieAnimationView animationView;
    private FavProductAdapter favProductAdapter;

    private FavViewModel favViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        favRecyclerView = view.findViewById(R.id.favRecyclerView);
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        animationView = view.findViewById(R.id.animationViewFavPage);

        favViewModel = new ViewModelProvider(this).get(FavViewModel.class);
        favViewModel.getFavProductsLiveData().observe(getViewLifecycleOwner(), favProductList -> {
            if(favProductList.isEmpty()) {
                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();
                favRecyclerView.setVisibility(View.GONE);
            } else {
                animationView.setVisibility(View.GONE);
                favRecyclerView.setVisibility(View.VISIBLE);

                favProductAdapter = new FavProductAdapter(getContext(), favProductList, favViewModel);
                favRecyclerView.setAdapter(favProductAdapter);
            }
        });

        favViewModel.loadFavProducts();

        return view;
    }
}