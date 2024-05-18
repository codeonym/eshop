package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.adapter.CoverOfferAdapter;
import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.CustomUtils;
import com.fsoteam.eshop.viewmodel.HomeViewModel;
import com.fsoteam.eshop.viewmodel.ProductViewModel;

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
    private ProductAdapter saleProductAdapter;
    private ProductViewModel productViewModel;

    private LottieAnimationView animationView;

    private LinearLayout newLayout;
    private LinearLayout saleLayout;

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        homeViewModel.getCoverOffersLiveData().observe(getViewLifecycleOwner(), offers -> {
            coverOffer.clear();
            coverOffer.addAll(offers);
            coverOfferAdapter.notifyDataSetChanged();
            showLayout();
        });
        homeViewModel.getNewProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            newProduct.clear();
            newProduct.addAll(products);
            newProductAdapter.notifyDataSetChanged();
        });
        homeViewModel.getSaleProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            saleProduct.clear();
            saleProduct.addAll(products);
            saleProductAdapter.notifyDataSetChanged();
        });

        homeViewModel.loadCoverOffers();
        homeViewModel.loadNewProducts();
        homeViewModel.loadSaleProducts();

        coverRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        coverRecView.setHasFixedSize(true);
        coverOfferAdapter = new CoverOfferAdapter(getActivity(), coverOffer);
        coverRecView.setAdapter(coverOfferAdapter);

        newRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecView.setHasFixedSize(true);
        newProductAdapter = new ProductAdapter(newProduct, getActivity(), productViewModel, getViewLifecycleOwner());
        newRecView.setAdapter(newProductAdapter);

        saleRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        saleRecView.setHasFixedSize(true);
        saleProductAdapter = new ProductAdapter(saleProduct, getActivity(), productViewModel, getViewLifecycleOwner());
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
}