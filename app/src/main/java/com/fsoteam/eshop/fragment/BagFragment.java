package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.adapter.CartAdapter;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.viewmodel.CartViewModel;
import com.fsoteam.eshop.model.Product;
import java.util.ArrayList;

public class BagFragment extends Fragment {

    private RecyclerView cartRecView;
    private CartAdapter cartAdapter;
    private LottieAnimationView animationView;
    private TextView totalPriceBagFrag;
    private ArrayList<Product> Item;
    private float sum = 0.0f;
    private CartViewModel cartViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        cartRecView = view.findViewById(R.id.cartRecView);
        animationView = view.findViewById(R.id.animationViewCartPage);
        totalPriceBagFrag = view.findViewById(R.id.totalPriceBagFrag);
        LinearLayout bottomCartLayout = view.findViewById(R.id.bottomCartLayout);
        LinearLayout emptyBagMsgLayout = view.findViewById(R.id.emptyBagMsgLayout);
        TextView MybagText = view.findViewById(R.id.MybagText);
        Item = new ArrayList<>();

        animationView.playAnimation();
        animationView.loop(true);
        bottomCartLayout.setVisibility(View.GONE);
        MybagText.setVisibility(View.GONE);
        emptyBagMsgLayout.setVisibility(View.VISIBLE);

        cartRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(getActivity());
        cartRecView.setAdapter(cartAdapter);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cartViewModel.getAllproducts().observe(getViewLifecycleOwner(), list -> {
            cartAdapter.updateList(list);
            Item.clear();
            sum = 0;
            Item.addAll(list);

            if (list.size() == 0) {
                animationView.playAnimation();
                animationView.loop(true);
                bottomCartLayout.setVisibility(View.GONE);
                MybagText.setVisibility(View.GONE);
                emptyBagMsgLayout.setVisibility(View.VISIBLE);
            } else {
                emptyBagMsgLayout.setVisibility(View.GONE);
                bottomCartLayout.setVisibility(View.VISIBLE);
                MybagText.setVisibility(View.VISIBLE);
                animationView.pauseAnimation();
            }

            for (Product product : Item) {
                sum += product.getProductPrice();
            }
            totalPriceBagFrag.setText("$" + sum);
        });

        return view;
    }
}