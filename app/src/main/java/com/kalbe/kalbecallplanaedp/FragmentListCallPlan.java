package com.kalbe.kalbecallplanaedp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/2/2018.
 */

public class FragmentListCallPlan extends Fragment{

    View v;
    ExpandableListView mExpandableListView;
    com.kalbe.kalbecallplanaedp.adapter.ExpandableListAdapter mExpandableListAdapter;
    private FloatingActionButton fab;
    private static List<clsListItemAdapter> swipeListPlan = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListUnplan = new ArrayList<>();
    private static List<String> listDataHeader = new ArrayList<>();
    private static HashMap<String, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    private Toolbar toolbar;
    clsMainBL _clsMainBL=null;
    private String DT_CALL_PLAN = "dtCallPlan";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_callplan_fragment, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_add_unplan);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        clsListItemAdapter swpItem;
        _clsMainBL=new clsMainBL();
        swipeListPlan.clear();
        swipeListUnplan.clear();
        swpItem = new clsListItemAdapter();
        swpItem.setTxtTittle("Visit Dokter");
        swpItem.setTxtSubTittle("Visit Dokter Fauziyah");
        swpItem.setTxtDate("02-10-2018");
        swpItem.setIntColor(R.color.purple_600);
        swpItem.setBoolSection(false);
        swpItem.setTxtImgName("PL");
        swipeListPlan.add(swpItem);
        swpItem = new clsListItemAdapter();
        swpItem.setTxtTittle("Visit Dokter");
        swpItem.setTxtSubTittle("Visit Dokter Azizah");
        swpItem.setTxtDate("02-10-2018");
        swpItem.setIntColor(getResources().getColor(R.color.blue_500));
        swpItem.setBoolSection(false);
        swpItem.setTxtImgName("NP");
        swipeListUnplan.add(swpItem);

        listDataHeader.clear();
        listDataHeader.add("Plan");
        listDataHeader.add("Unplan");
        listDataChild.clear();
        listDataChild.put(listDataHeader.get(0), swipeListPlan);
        listDataChild.put(listDataHeader.get(1), swipeListUnplan);

        mExpandableListAdapter = new com.kalbe.kalbecallplanaedp.adapter.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle data = new Bundle();

//                data.putString( DT_CALL_PLAN , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString());
                data.putSerializable(DT_CALL_PLAN, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
                FragmentCallPlan fragmentCallPlan = new FragmentCallPlan();
                fragmentCallPlan.setArguments(data);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragmentCallPlan);
                fragmentTransaction.commit();
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Add Call Plan Unplan");

                FragmentAddUnplan fragmentAddUnplan = new FragmentAddUnplan();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragmentAddUnplan);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

}
