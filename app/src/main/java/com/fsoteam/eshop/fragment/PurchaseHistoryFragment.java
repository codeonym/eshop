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
import com.fsoteam.eshop.adapter.OrderAdapter;
import com.fsoteam.eshop.model.Order;
import com.fsoteam.eshop.utils.OrderStatus;
import com.fsoteam.eshop.viewmodel.OrdersViewModel;

import java.util.ArrayList;

public class PurchaseHistoryFragment extends Fragment {

    private RecyclerView ordersRecView;
    private OrderAdapter orderAdapter;
    private LottieAnimationView animationView;

    private OrdersViewModel ordersViewModel;
    LinearLayout emptyOrdersMsgLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purshase_history, container, false);

        ordersRecView = view.findViewById(R.id.purshaseHistoryFragment_ordersRecyclerView);
        animationView = view.findViewById(R.id.animationViewPurchaseHistoryPage);
        emptyOrdersMsgLayout = view.findViewById(R.id.purshaseHistoryFragment_emptyOrdersMsgLayout);


        animationView.playAnimation();
        animationView.loop(true);
        emptyOrdersMsgLayout.setVisibility(View.VISIBLE);
        ordersRecView.setVisibility(View.GONE);

        ordersRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        ordersViewModel.getOrdersLiveData().observe(getViewLifecycleOwner(), ordersList -> {

            orderAdapter = new OrderAdapter(getActivity(), (ArrayList<Order>) ordersList);
            ordersRecView.setAdapter(orderAdapter);

            if (ordersList.isEmpty()) {
                animationView.playAnimation();
                animationView.loop(true);
                ordersRecView.setVisibility(View.GONE);
                emptyOrdersMsgLayout.setVisibility(View.VISIBLE);
            } else {
                emptyOrdersMsgLayout.setVisibility(View.GONE);
                ordersRecView.setVisibility(View.VISIBLE);
                animationView.pauseAnimation();
            }
        });

        ordersViewModel.filterOrders(OrderStatus.COMPLETED.toString());
        return view;
    }
}