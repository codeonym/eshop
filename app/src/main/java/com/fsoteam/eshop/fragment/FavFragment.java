package com.fsoteam.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.airbnb.lottie.LottieAnimationView;
import com.fsoteam.eshop.R;

public class FavFragment extends Fragment {

    private LottieAnimationView animationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        animationView = view.findViewById(R.id.animationViewFavPage);

        animationView.playAnimation();
        animationView.loop(true);

        return view;
    }
}