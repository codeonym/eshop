package com.fsoteam.eshop.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
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
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ProductsFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private TextView productsSearchTv;
    private RecyclerView categoriesRecView;
    private ProductAdapter productAdapter;
    private ProductsListCategoryAdapter categoryAdapter;
    private ArrayList<Product> productList;
    private ArrayList<Category> categoryList;
    private LottieAnimationView animationView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference productsRef = db.getReference(DbCollections.PRODUCTS);
    private DatabaseReference categoriesRef = db.getReference(DbCollections.CATEGORIES);
    private String categoryId;
    private boolean newestFirst;
    private boolean bestSelling;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        productList = new ArrayList<>();
        categoryList = new ArrayList<>();

        Category allCategory = new Category();
        allCategory.setCategoryId("000");
        allCategory.setSelected(true);
        allCategory.setName("All");
        categoryList.add(allCategory);

        productRecyclerView = view.findViewById(R.id.product_recycler_view);
        categoriesRecView = view.findViewById(R.id.category_recycler_view);
        // animationView = view.findViewById(R.id.animationViewProducts);

        Bundle args = getArguments();
        if (args != null) {
            newestFirst = Objects.equals(args.getString(CustomUtils.PRODUCTS_BY_NEWEST_FIRST), "true");
            categoryId = args.getString(CustomUtils.PRODUCTS_BY_CATEGORY_ID);
            bestSelling = Objects.equals(args.getString(CustomUtils.PRODUCTS_BY_BEST_SELLING), "true");

        }

        setCategoryData();
        setProductData();

        productAdapter = new ProductAdapter(productList, getContext());
        categoryAdapter = new ProductsListCategoryAdapter(getContext(), categoryList);
        categoryAdapter.setOnCategoryClickListener(new ProductsListCategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(String categoryId) {
                ProductsFragment.this.categoryId = categoryId;
                setProductData();
            }
        });

        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productRecyclerView.setHasFixedSize(true);
        categoriesRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecView.setHasFixedSize(true);

        productRecyclerView.setAdapter(productAdapter);
        categoriesRecView.setAdapter(categoryAdapter);

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
                filter(s.toString());
            }
        });

        return view;
    }
    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();

        for (Product item : productList) {
            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        productAdapter.filterList(filteredList);
    }
    private void setCategoryData() {
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    categoryList.add(category);
                }
                if(categoryId != null){
                    for (Category c : categoryList) {
                        if (c.getCategoryId().equals(categoryId)) {
                            for (Category category : categoryList) {
                                category.setSelected(false);
                            }
                            c.setSelected(true);
                            break;
                        }
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ProductsFragment", "Error getting documents: ", databaseError.toException());
            }
        });
    }
    private void setProductData() {
        Query query;

        if (newestFirst) {
            query = productsRef.orderByChild("productAddDate");
        } else if (bestSelling) {
            query = productsRef.orderByChild("productRating");
        } else {
            query = productsRef;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    if (product != null) {
                        if(categoryId == null || categoryId.equals("000")){
                            productList.add(product);
                        } else {
                            if(product.getProductCategory() != null && product.getProductCategory().getCategoryId().equals(categoryId)){
                                productList.add(product);
                            }
                        }
                    }
                }

                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ProductsFragment", "Error getting documents: ", databaseError.toException());
            }
        });
    }

}
