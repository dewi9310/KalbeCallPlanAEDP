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
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    List<tRealisasiVisitPlan> listRealisasi;
    List<tProgramVisit> listVisitHeader;
    List<tProgramVisitSubActivity> listVisitDetail;
    tRealisasiVisitPlanRepo repoRealisasi;
    tProgramVisitRepo repoProgramVisit;
    tProgramVisitSubActivityRepo repoProgramVisitSubActivity;
    mActivityRepo repoActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_callplan_fragment, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_add_unplan);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        repoProgramVisit = new tProgramVisitRepo(getContext());
        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        repoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        repoActivity = new mActivityRepo(getContext());

        listDataHeader.clear();
        listDataChild.clear();
        swipeListPlan.clear();
        swipeListUnplan.clear();

        try {
            listVisitDetail = (List<tProgramVisitSubActivity>) repoProgramVisitSubActivity.findAll();
//            listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.findAll();
            if (listVisitDetail!=null){
                if (listVisitDetail.size()>0){
                    for (tProgramVisitSubActivity data : listVisitDetail){
//                        tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtId(data.getTxtProgramVisitSubActivityId());
                        clsListItemAdapter swpItem =  new clsListItemAdapter();
                        if (data.getIntType()==1){
                            mActivity dtActivity = null;
                            try {
                                dtActivity = (mActivity) repoActivity.findById(data.getIntActivityId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (dtActivity!=null){
                                swpItem.setTxtTittle(dtActivity.getTxtName());
                                if (dtActivity.getIntActivityId()==1){
                                    swpItem.setTxtSubTittle("Visit Doctor " + data.getTxtDokterName());
                                }else if (dtActivity.getIntActivityId()==2){
                                    swpItem.setTxtSubTittle("Visit " + data.getTxtApotekName());
                                }else {
                                    if (data.getTxtNotes()!=null)
                                    swpItem.setTxtSubTittle(data.getTxtNotes());
                                }
                            }else {
                                swpItem.setTxtTittle("");
                                swpItem.setTxtSubTittle("");
                            }
                            swpItem.setTxtDate(parseDate(data.getDtStart()));
                            swpItem.setIntColor(R.color.purple_600);
                            swpItem.setBoolSection(false);
                            swpItem.setTxtImgName("PL");
                            swpItem.setTxtId(data.getTxtProgramVisitSubActivityId());
                            swipeListPlan.add(swpItem);
                        }else if (data.getIntType()==2){
                            mActivity dtActivity = null;
                            try {
                                dtActivity = (mActivity) repoActivity.findById(data.getIntActivityId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (dtActivity!=null){
                                swpItem.setTxtTittle(dtActivity.getTxtName());
                                if (dtActivity.getIntActivityId()==1){
                                    swpItem.setTxtSubTittle("Visit Doctor " + data.getTxtDokterName());
                                }else if (dtActivity.getIntActivityId()==2){
                                    swpItem.setTxtSubTittle("Visit " + data.getTxtApotekName());
                                }
                            }else {
                                swpItem.setTxtTittle("");
                                swpItem.setTxtSubTittle("");
                            }
                            swpItem.setTxtDate(parseDate(data.getDtStart()));
                            swpItem.setIntColor(getResources().getColor(R.color.blue_500));
                            swpItem.setBoolSection(false);
                            swpItem.setTxtImgName("NP");
                            swpItem.setTxtId(data.getTxtProgramVisitSubActivityId());
                            swipeListUnplan.add(swpItem);
                        }
                    }

                    listDataHeader.add("Plan");
                    listDataHeader.add("Unplan");
                    listDataChild.put(listDataHeader.get(0), swipeListPlan);
                    listDataChild.put(listDataHeader.get(1), swipeListUnplan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



//        clsListItemAdapter swpItem;
//        _clsMainBL=new clsMainBL();

//        swpItem = new clsListItemAdapter();
//        swpItem.setTxtTittle("Visit Dokter");
//        swpItem.setTxtSubTittle("Visit Dokter Fauziyah");
//        swpItem.setTxtDate("02-10-2018");
//        swpItem.setIntColor(R.color.purple_600);
//        swpItem.setBoolSection(false);
//        swpItem.setTxtImgName("PL");
//        swipeListPlan.add(swpItem);
//        swpItem = new clsListItemAdapter();
//        swpItem.setTxtTittle("Visit Dokter");
//        swpItem.setTxtSubTittle("Visit Dokter Azizah");
//        swpItem.setTxtDate("02-10-2018");
//        swpItem.setIntColor(getResources().getColor(R.color.blue_500));
//        swpItem.setBoolSection(false);
//        swpItem.setTxtImgName("NP");
//        swipeListUnplan.add(swpItem);



        mExpandableListAdapter = new com.kalbe.kalbecallplanaedp.adapter.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle data = new Bundle();

                data.putString( DT_CALL_PLAN , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
//                data.putSerializable(DT_CALL_PLAN, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
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
                toolbar.setTitle("Add Call Plan Unplan");

                FragmentAddUnplan fragmentAddUnplan = new FragmentAddUnplan();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragmentAddUnplan);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    private String parseDate(String dateExp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

}
