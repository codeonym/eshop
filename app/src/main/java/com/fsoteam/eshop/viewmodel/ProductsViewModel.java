package com.fsoteam.eshop.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fsoteam.eshop.model.Category;
import com.fsoteam.eshop.model.Product;
import com.fsoteam.eshop.utils.DbCollections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();

    private DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference(DbCollections.PRODUCTS);
    private DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference(DbCollections.CATEGORIES);

    public MutableLiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public void loadProducts(String categoryId, boolean newestFirst, boolean bestSelling) {
        Query query;

        if (newestFirst) {
            query = productsRef.orderByChild("productAddDate");
        } else if (bestSelling) {
            query = productsRef.orderByChild("likesCount");
        } else {
            query = productsRef;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productList = new ArrayList<>();
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

                if(newestFirst || bestSelling)
                    Collections.reverse(productList);

                productsLiveData.setValue(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void loadCategories() {
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> categoryList = new ArrayList<>();
                Category defaultCategory = new Category();
                defaultCategory.setCategoryId("000");
                defaultCategory.setName("All");
                defaultCategory.setSelected(true);

                categoryList.add(defaultCategory);

                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    categoryList.add(category);
                }
                categoriesLiveData.setValue(categoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void searchProducts(String productName) {

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> tmp = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    if (product != null && product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                        tmp.add(product);
                    }
                }
                productsLiveData.setValue(tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }
}