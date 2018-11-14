package com.kalbe.kalbecallplanaedp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Common.tNotification;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsItemGroupNotifAdapter;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.Model.clsMaintenance;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tNotificationRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListHistory;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListMaintenance;
import com.kalbe.kalbecallplanaedp.adapter.ExpandableListAdapterNotif;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Dewi Oktaviani on 11/13/2018.
 */

public class FragmentHistory extends Fragment {
    View v;
    private static List<clsMaintenance> swipeListPlan = new ArrayList<>();
//    private static List<String> listDataHeader = new ArrayList<>();
    AdapterListHistory adapter;
    ExpandableListView mExpandableListView;
    ExpandableListAdapterNotif mExpandableListAdapter;
    private static List<clsItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private static HashMap<clsItemGroupNotifAdapter, List<clsListItemAdapter>> listDataChild = new HashMap<>();
//    private static HashMap<String, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    List<tRealisasiVisitPlan> listRealisasi;
    List<tProgramVisit> listVisitHeader;
    List<tProgramVisitSubActivity> listVisitDetail;
    tRealisasiVisitPlanRepo repoRealisasi;
    tProgramVisitRepo repoProgramVisit;
    tProgramVisitSubActivityRepo repoProgramVisitSubActivity;
    mActivityRepo repoActivity;
    LinearLayout lnEmpty;
    ListView listView;
    private String DT_CALL_PLAN = "Realisasi id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_n_order, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
//        listView = (ListView) v.findViewById(R.id.lv_infoprogram);
//        lnEmpty = (LinearLayout)v.findViewById(R.id.ln_emptyMain);
        repoProgramVisit = new tProgramVisitRepo(getContext());
        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        repoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        repoActivity = new mActivityRepo(getContext());
//        swipeListPlan.clear();
//        try {
//            listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllRealisasi();
//            if (listRealisasi!=null){
//                if (listRealisasi.size()>0){
//                    for (tRealisasiVisitPlan dataRealisasi : listRealisasi){
//                        tProgramVisitSubActivity data = (tProgramVisitSubActivity)repoProgramVisitSubActivity.findBytxtId(dataRealisasi.getTxtProgramVisitSubActivityId());
//                        clsMaintenance swpItem =  new clsMaintenance();
//                        mActivity dtActivity = null;
//                        try {
//                            dtActivity = (mActivity) repoActivity.findById(data.getIntActivityId());
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        if (dtActivity!=null){
//
//                            if (dtActivity.getIntActivityId()==1){
//                                swpItem.setTxtTittle(dtActivity.getTxtName() + " : " + data.getTxtDokterName()) ;
////                                    swpItem.setTxtSubTittle("Visit Doctor " + );
//                            }else if (dtActivity.getIntActivityId()==2){
////                                    swpItem.setTxtSubTittle("Visit " + data.getTxtApotekName());
//                                swpItem.setTxtTittle(dtActivity.getTxtName() + " : " + data.getTxtApotekName()) ;
//                            }else {
////                                    if (data.getTxtNotes()!=null)
////                                        swpItem.setTxtSubTittle(data.getTxtNotes());
//                                swpItem.setTxtTittle(dtActivity.getTxtName());
//                            }
//                        }else {
//                            swpItem.setTxtTittle("");
////                            swpItem.setTxtSubTittle("");
//                        }
//                        swpItem.setTxtId(dataRealisasi.getTxtRealisasiVisitId());
//                        swpItem.setIntImgView(data.getIntActivityId());
//                        swipeListPlan.add(swpItem);
//                        clsMaintenance swpItem1 =  new clsMaintenance();
//                        swpItem1.setTxtId(dataRealisasi.getTxtRealisasiVisitId());
//                        swpItem1.setIntImgView(data.getIntActivityId());
//                        swipeListPlan.add(swpItem1);
//                    }
//                    adapter = new AdapterListHistory(getContext(), swipeListPlan);
//                    listView.setAdapter(adapter);
//                    listView.setDivider(null);
//                    listView.setEmptyView(lnEmpty);
//
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        listDataHeader.clear();
        listDataChild.clear();
        try {
            listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllRealisasi();
            if (listRealisasi!=null){
                if (listRealisasi.size()>0){
                    int index = 0;
                    for (tRealisasiVisitPlan data : listRealisasi){
                        clsItemGroupNotifAdapter itemAdapter = new clsItemGroupNotifAdapter();
                        tProgramVisitSubActivity dataVisit = (tProgramVisitSubActivity)repoProgramVisitSubActivity.findBytxtId(data.getTxtProgramVisitSubActivityId());
                        mActivity activity = (mActivity) new mActivityRepo(getContext()).findById(dataVisit.getIntActivityId());

                        itemAdapter.setTxtTittle(activity.getTxtName());
                        if (dataVisit.getIntActivityId()==new clsHardCode().VisitDokter){
                            itemAdapter.setTxtSubTittle("Dokter " + dataVisit.getTxtDokterName());
                        }else if (dataVisit.getIntActivityId()==new clsHardCode().VisitApotek){
                            itemAdapter.setTxtSubTittle(dataVisit.getTxtApotekName());
                        }
                        if (dataVisit.getIntType()==new clsHardCode().Plan){
                            itemAdapter.setTxtImgName("PL");
                            itemAdapter.setIntColor(getResources().getColor(R.color.pink_400));
                        }else if (dataVisit.getIntType()==new clsHardCode().UnPlan){
                            itemAdapter.setTxtImgName("NP");
                            itemAdapter.setIntColor(getResources().getColor(R.color.green_400));
                        }
                        listDataHeader.add(itemAdapter);
                        List<clsListItemAdapter> listChildAdapter = new ArrayList<>();
                        List<tAkuisisiHeader> listAkuisisi = (List<tAkuisisiHeader>) new tAkuisisiHeaderRepo(getContext()).findByRealisasi(data.getTxtRealisasiVisitId());
                        if (listAkuisisi!=null){
                            if (listAkuisisi.size()>0){
                                clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                itemAdapter1.setTxtId(data.getTxtRealisasiVisitId());
                                itemAdapter1.setTxtTittle("Akuisisi");
//                                itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                                itemAdapter1.setTxtDate(String.valueOf(listAkuisisi.size()));
                                listChildAdapter.add(itemAdapter1);
                            }
                        }
                        tMaintenanceHeader maintenance = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByRealisasiId(data.getTxtRealisasiVisitId()) ;
                        if (maintenance!=null){
                            List<tMaintenanceDetail> maintenanceList = (List<tMaintenanceDetail>) new tMaintenanceDetailRepo(getContext()).findByHeaderId(maintenance.getTxtHeaderId());
                            if (maintenanceList!=null){
                                if (maintenanceList.size()>0){
                                    clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                    itemAdapter1.setTxtId(data.getTxtRealisasiVisitId());
                                    itemAdapter1.setTxtTittle("Maintenance");
//                                itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                                    itemAdapter1.setTxtDate(String.valueOf(maintenanceList.size()));
                                    listChildAdapter.add(itemAdapter1);
                                }
                            }
                        }


//                        List<tInfoProgramHeader> infoProgramList = (List<tInfoProgramHeader>) new tInfoProgramHeaderRepo(getContext()).findbyListRealisasiId(data.getTxtRealisasiVisitId());
                        tInfoProgramHeader infoProgramHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findbyRealisasiId(data.getTxtRealisasiVisitId());
                        if (infoProgramHeader!=null){
                            List<tInfoProgramDetail> infoProgramList = (List<tInfoProgramDetail>) new tInfoProgramDetailRepo(getContext()).findByHeaderId(infoProgramHeader.getTxtHeaderId());
                            if (infoProgramList!=null){
                                if (infoProgramList.size()>0){
                                    clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                    itemAdapter1.setTxtTittle("Info Program");
                                    itemAdapter1.setTxtId(data.getTxtRealisasiVisitId());
//                                itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                                    itemAdapter1.setTxtDate(String.valueOf(infoProgramList.size()));
                                    listChildAdapter.add(itemAdapter1);
                                }
                            }
                        }


                        listDataChild.put(listDataHeader.get(index), listChildAdapter);
                        index++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mExpandableListAdapter = new ExpandableListAdapterNotif(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle data = new Bundle();
                data.putString( DT_CALL_PLAN , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().equals("Akuisisi")){
                    new Tools().intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), data);
                }else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().equals("Maintenance")){
                    new Tools().intentFragmentSetArgument(FragementMaintenance.class, "Maintenance", getContext(), data);
                }else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().equals("Info Program")){
                    new Tools().intentFragmentSetArgument(FragementInfoProgram.class, "Info Program", getContext(), data);
                }

//                FragmentCallPlan fragmentCallPlan = new FragmentCallPlan();
//                fragmentCallPlan.setArguments(data);
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame, fragmentCallPlan);
//                fragmentTransaction.commit();
                return false;
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
