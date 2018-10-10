package com.kalbe.kalbecallplanaedp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class HomeFragment extends Fragment {
    View v;
    private Toolbar toolbar;
    private LinearLayout ln_plan_home, ln_realisasi_home, ln_unplan_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ln_plan_home = (LinearLayout)v.findViewById(R.id.ln_plan_home);
        ln_realisasi_home = (LinearLayout) v.findViewById(R.id.ln_realisasi_home);
        ln_unplan_home = (LinearLayout) v.findViewById(R.id.ln_unplan_home);
        ln_plan_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new clsHardCode().copydb(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "bisa di klik", Toast.LENGTH_SHORT).show();
            }
        });

        ln_realisasi_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ini realisasi", Toast.LENGTH_SHORT).show();
            }
        });

        ln_unplan_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ini Unplan", Toast.LENGTH_SHORT).show();
            }
        });
//        initComponent();
        initToolbar();
        return v;
    }

    private void initToolbar() {



//        //for crate home button
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
    }

//    private void initComponent() {
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setElevation(0);
////        appBarLayout.setElevation(0);
////        appBarLayout.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//        toolbar.setBackgroundResource(R.drawable.klik_apotek);
//
//        final Toolbar toolbarHome = (Toolbar) v.findViewById(R.id.toolbarHome);
//
//        final CircularImageView image = (CircularImageView) v.findViewById(R.id.imageHome);
//        final CollapsingToolbarLayout collapsing_toolbar = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
//        ((AppBarLayout) v.findViewById(R.id.app_bar_layout)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                toolbarHome.setVisibility(View.GONE);
//                int min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2;
//                float scale = (float) (min_height + verticalOffset) / min_height;
//                image.setScaleX(scale >= 0 ? scale : 0);
//                image.setScaleY(scale >= 0 ? scale : 0);
//            }
//        });
//    }
}
