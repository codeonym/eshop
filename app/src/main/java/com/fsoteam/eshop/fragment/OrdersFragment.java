package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private RecyclerView ordersRecView;
    private OrderAdapter orderAdapter;
    private LottieAnimationView animationView;

    private OrdersViewModel ordersViewModel;
    LinearLayout emptyOrdersMsgLayout;
    private TabLayout tabLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        ordersRecView = view.findViewById(R.id.ordersfragment_ordersRecyclerView);
        animationView = view.findViewById(R.id.animationViewOrdersPage);
        tabLayout = view.findViewById(R.id.tabLayout);
        emptyOrdersMsgLayout = view.findViewById(R.id.orders_emptyOrdersMsgLayout);


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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int id = tab.getId();

                int position = tab.getPosition();
                switch (position){
                    case 0:
                        ordersViewModel.loadOrders();
                        break;
                    case 1:
                        ordersViewModel.filterOrders(OrderStatus.PENDING.toString());
                        break;
                    case 2:
                        ordersViewModel.filterOrders(OrderStatus.PROCESSING.toString());
                        break;
                    case 3:
                        ordersViewModel.filterOrders(OrderStatus.PACKAGED.toString());
                        break;
                    case 4:
                        ordersViewModel.filterOrders(OrderStatus.SHIPPED.toString());
                        break;
                    case 5:
                        ordersViewModel.filterOrders(OrderStatus.ON_HOLD.toString());
                        break;
                    case 6:
                        ordersViewModel.filterOrders(OrderStatus.DELIVERED.toString());
                        break;
                    case 7:
                        ordersViewModel.filterOrders(OrderStatus.CONFIRMED.toString());
                        break;
                    case 8:
                        ordersViewModel.filterOrders(OrderStatus.REFUND_REQUESTED.toString());
                        break;
                    case 9:
                        ordersViewModel.filterOrders(OrderStatus.RETURNED.toString());
                        break;
                    case 10:
                        ordersViewModel.filterOrders(OrderStatus.REFUNDED.toString());
                        break;
                    case 11:
                        ordersViewModel.filterOrders(OrderStatus.CANCELLED.toString());
                        break;
                    case 12:
                        ordersViewModel.filterOrders(OrderStatus.REJECTED.toString());
                        break;
                    default:
                        ordersViewModel.loadOrders();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        ordersViewModel.loadOrders();
        return view;
    }
}