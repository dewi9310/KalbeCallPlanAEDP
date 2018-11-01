package com.kalbe.kalbecallplanaedp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by ASUS on 01/11/2018.
 */
@SuppressLint("ValidFragment")
public class FragmentNResep extends Fragment {
    private ViewPager viewPager;
    View v;
    private ListView mListView;
    FloatingActionButton fab;
    public FragmentNResep(FloatingActionButton fab) {
    this.fab = fab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_n_resep, container, false);

        return v;
    }
}
