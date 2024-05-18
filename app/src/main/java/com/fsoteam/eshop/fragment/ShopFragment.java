package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fsoteam.eshop.adapter.CategoryAdapter;
import com.fsoteam.eshop.adapter.CoverOfferAdapter;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Offer;
import com.fsoteam.eshop.viewmodel.ShopViewModel;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.utils.CustomUtils;
import java.util.ArrayList;


public class ShopFragment extends Fragment {

    private RecyclerView coverRecView_shopFrag;
    private RecyclerView categoriesRecView;
    private ArrayList<Category> cateList;
    private ArrayList<Offer> coverOffer;
    private CategoryAdapter categoryAdapter;
    private CoverOfferAdapter coverOfferAdapter;

    private ShopViewModel shopViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        cateList = new ArrayList<>();
        coverOffer = new ArrayList<>();

        coverRecView_shopFrag = view.findViewById(R.id.coverRecView_shopFrag);
        categoriesRecView = view.findViewById(R.id.categoriesRecView);

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            cateList.clear();
            cateList.addAll(categories);
            categoryAdapter.notifyDataSetChanged();
        });
        shopViewModel.getCoverOffersLiveData().observe(getViewLifecycleOwner(), offers -> {
            coverOffer.clear();
            coverOffer.addAll(offers);
            coverOfferAdapter.notifyDataSetChanged();
        });

        shopViewModel.loadCategories();
        shopViewModel.loadCoverOffers();

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
}