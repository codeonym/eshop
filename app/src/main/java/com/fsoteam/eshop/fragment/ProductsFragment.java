package com.fsoteam.eshop.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.adapter.ProductAdapter;
import com.fsoteam.eshop.adapter.ProductsListCategoryAdapter;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.CustomUtils;
import com.fsoteam.eshop.viewmodel.ProductViewModel;
import com.fsoteam.eshop.viewmodel.ProductsViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private TextView productsSearchTv;
    private RecyclerView categoriesRecView;
    private ProductAdapter productAdapter;
    private ProductsListCategoryAdapter categoryAdapter;
    private LottieAnimationView animationView;

    private ProductsViewModel productsViewModel;

    private String categoryId;
    private boolean newestFirst;
    private boolean bestSelling;
    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

        productRecyclerView = view.findViewById(R.id.product_recycler_view);
        categoriesRecView = view.findViewById(R.id.category_recycler_view);
        // animationView = view.findViewById(R.id.animationViewProducts);

        Bundle args = getArguments();
        if (args != null) {
            newestFirst = Objects.equals(args.getString(CustomUtils.PRODUCTS_BY_NEWEST_FIRST), "true");
            categoryId = args.getString(CustomUtils.PRODUCTS_BY_CATEGORY_ID);
            bestSelling = Objects.equals(args.getString(CustomUtils.PRODUCTS_BY_BEST_SELLING), "true");
        }

        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productsViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), productList -> {
            productAdapter = new ProductAdapter((ArrayList<Product>) productList, getContext(), productViewModel, this);
            productRecyclerView.setAdapter(productAdapter);
        });
        productsViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categoryList -> {

            String catId = categoryId == null ? "000": categoryId;
            for (Category c : categoryList) {
                if (c != null) {
                    c.setSelected(false);
                }
            }

            for(Category c : categoryList) {
                if (c!= null && c.getCategoryId().equals(catId)) {
                    c.setSelected(true);
                }
            }
            categoryAdapter = new ProductsListCategoryAdapter(getContext(), (ArrayList<Category>) categoryList);
            categoryAdapter.setOnCategoryClickListener(new ProductsListCategoryAdapter.OnCategoryClickListener() {
                @Override
                public void onCategoryClick(String categoryId) {
                    ProductsFragment.this.categoryId = categoryId;
                    productsViewModel.loadProducts(categoryId, newestFirst, bestSelling);
                }
            });
            categoriesRecView.setAdapter(categoryAdapter);
        });

        productsViewModel.loadProducts(categoryId, newestFirst, bestSelling);
        productsViewModel.loadCategories();

        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productRecyclerView.setHasFixedSize(true);
        categoriesRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecView.setHasFixedSize(true);

        productsSearchTv = view.findViewById(R.id.products_fragment_list_search_bar);
        productsSearchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called to notify you that, within s, the count characters beginning at start have just replaced old text that had length before.
            }

            @Override
            public void afterTextChanged(Editable s) {
                productsViewModel.searchProducts(s.toString());

            }
        });

        return view;
    }
}