package com.kalbe.kalbecallplanaedp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListNOrder;
import com.kalbe.kalbecallplanaedp.widget.LineItemDecoration;

/**
 * Created by ASUS on 01/11/2018.
 */
@SuppressLint("ValidFragment")
public class FragmentNOrder extends Fragment {
    private ViewPager viewPager;
    View v;
    private RecyclerView recyclerView;
    private ListView mListView;
    private AdapterListNOrder mAdapter;
    FloatingActionButton fab;
    Context context;
    public FragmentNOrder(FloatingActionButton fab) {
        this.fab = fab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_n_order, container, false);
        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewnOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new LineItemDecoration(context, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);


        return v;
    }
}
