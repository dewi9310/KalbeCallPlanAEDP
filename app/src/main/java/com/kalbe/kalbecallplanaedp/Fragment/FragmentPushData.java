package com.kalbe.kalbecallplanaedp.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.clsPushData;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.VolleyUtils;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.responsePushData.ResponsePushData;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.adapter.ExpandableListAdapter;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentPushData extends Fragment{
    View v;
    ExpandableListView mExpandableListView;
    ExpandableListAdapter mExpandableListAdapter;
    private static List<clsListItemAdapter> swipeListPlan = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListUnplan = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListAkuisisi = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListMaintenance = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListInfoProgram = new ArrayList<>();
    private static List<String> listDataHeader = new ArrayList<>();
    private static HashMap<String, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    List<tRealisasiVisitPlan> listRealisasi;
    List<tProgramVisit> listVisitHeader;
    List<tProgramVisitSubActivity> listVisitDetail;
    List<tAkuisisiHeader> listAkuisisiHeader;
    List<tAkuisisiDetail> listAkuisisiDetail;
    List<tInfoProgramHeader> listInfoHeader;
    List<tInfoProgramDetail> listInfoDetail;
    List<tMaintenanceHeader> listMainHeader;
    List<tMaintenanceDetail> listMainDetail;
    tAkuisisiHeaderRepo akuisisiHeaderRepo;
    tAkuisisiDetailRepo akuisisiDetailRepo;
    tMaintenanceHeaderRepo maintenanceHeaderRepo;
    tMaintenanceDetailRepo maintenanceDetailRepo;
    tInfoProgramHeaderRepo infoProgramHeaderRepo;
    tInfoProgramDetailRepo infoProgramDetailRepo;
    tRealisasiVisitPlanRepo repoRealisasi;
    tProgramVisitRepo repoProgramVisit;
    tProgramVisitSubActivityRepo repoProgramVisitSubActivity;
    mActivityRepo repoActivity;
    mActivity dtActivity;
    mDokterRepo dokterRepo;
    mApotekRepo apotekRepo;

    private TableLayout tablePushData;
    FloatingActionButton button_push_data;
    private Gson gson;
    ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_push_data, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        button_push_data = (FloatingActionButton)v.findViewById(R.id.button_push_data);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        pDialog = new ProgressDialog(getContext(),ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

        repoProgramVisit = new tProgramVisitRepo(getContext());
        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        repoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        repoActivity = new mActivityRepo(getContext());
        akuisisiDetailRepo = new tAkuisisiDetailRepo(getContext());
        akuisisiHeaderRepo = new tAkuisisiHeaderRepo(getContext());
        maintenanceDetailRepo = new tMaintenanceDetailRepo(getContext());
        maintenanceHeaderRepo = new tMaintenanceHeaderRepo(getContext());
        infoProgramDetailRepo = new tInfoProgramDetailRepo(getContext());
        infoProgramHeaderRepo = new tInfoProgramHeaderRepo(getContext());
        apotekRepo = new mApotekRepo(getContext());
        dokterRepo = new mDokterRepo(getContext());

        setListData();
//        ListData();
        button_push_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    private void pushData() throws JSONException {
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Pushing Data");
//        pDialog.setTitle("Pushing Your data");
        pDialog.setMessage("Pushing Your data");
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pDialog.show();
        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        final clsPushData dtJson = new clsHelperBL().pushData(versionName, getContext());
        if (dtJson == null){
        }else {
            String linkPushData = new clsHardCode().linkPushData;
            new VolleyUtils().makeJsonObjectRequestPushData(getContext(), linkPushData, dtJson, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    ToastCustom.showToasty(getContext(),message,4);
//                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response!=null){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            ResponsePushData model = gson.fromJson(jsonObject.toString(), ResponsePushData.class);
                            boolean isStatus = model.getResult().isStatus();
                            String txtMessage = model.getResult().getMessage();
                            String txtMethod = model.getResult().getMethodName();
                            if (isStatus==true){
                                if (model.getData().getModelData()!=null){
                                    if (model.getData().getModelData().size()>0){
                                        new  clsHelperBL().SavePushData(getContext(), dtJson.getDataJson(), model);
                                    }
                                }
                                setListData();
                                ToastCustom.showToasty(getContext(),"Success Push Data",1);
                            }else {
                                ToastCustom.showToasty(getContext(),txtMessage, 4);
                            }

                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        ToastCustom.showToasty(getContext(),strErrorMsg,4);
                        pDialog.dismiss();
                    }
                }
            });
        }
    }
//    private void ListData(){
//        tablePushData.removeAllViews();
////        clsMobile_trVisitPlan_Detail _clsMobile_trVisitPlan_Detail=new clsMobile_trVisitPlan_Detail();
////        List<clsMobile_trVisitPlan_Detail> ListOfclsMobile_trVisitPlan_Detail=new Select().from(clsMobile_trVisitPlan_Detail.class).where(_clsMobile_trVisitPlan_Detail.txtConstintSubmit+"=2").execute();
//        final TableRow row = new TableRow(getContext());
//        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);
//        params.setMargins(1, 1, 1, 1);
//        TableRow tr = new TableRow(getContext());
//        String[] colTextHeader = {"No.", "Plan Date", "Category", "Desc"};
//        for (String text : colTextHeader) {
//            TextView tv = new TextView(getContext());
//
//            tv.setTextSize(14);
//            tv.setPadding(10, 10, 10, 10);
//            tv.setText(text);
//            tv.setGravity(Gravity.CENTER);
//            tv.setBackgroundColor(Color.parseColor("#81C784"));
//            tv.setTextColor(Color.WHITE);
//            tv.setLayoutParams(params);
//
//            tr.addView(tv);
//        }
//        tablePushData.addView(tr,0);
//
////        if(ListOfclsMobile_trVisitPlan_Detail.size()>0){
////            int index = 1;
////            for (clsMobile_trVisitPlan_Detail _DtDetail:ListOfclsMobile_trVisitPlan_Detail) {
////                tr = new TableRow(getContext());
////                TextView tv_index = new TextView(getContext());
////                tv_index.setTextSize(12);
////                tv_index.setPadding(10, 10, 10, 10);
////                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
////                tv_index.setTextColor(Color.BLACK);
////                tv_index.setGravity(Gravity.CENTER);
////                tv_index.setText(String.valueOf(index + "."));
////                tv_index.setLayoutParams(params);
////
////                tr.addView(tv_index);
////
////                TextView outlet_code = new TextView(getContext());
////                outlet_code.setTextSize(12);
////                outlet_code.setPadding(10, 10, 10, 10);
////                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
////                outlet_code.setTextColor(Color.BLACK);
////                outlet_code.setGravity(Gravity.CENTER);
////                String txtPlanDate=_DtDetail.dtPlanDate;
////                if(txtPlanDate==null){
////                    txtPlanDate="Unplan";
////                }
////                outlet_code.setText(txtPlanDate);
////                outlet_code.setLayoutParams(params);
////
////                tr.addView(outlet_code);
////
////                TextView outlet_name = new TextView(getContext());
////                outlet_name.setTextSize(12);
////                outlet_name.setPadding(10, 10, 10, 10);
////                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
////                outlet_name.setTextColor(Color.BLACK);
////                outlet_name.setGravity(Gravity.CENTER);
////                clsMobile_mVisitPlanCategory _dtclsMobile_mVisitPlanCategory=new clsMobile_mVisitPlanCategory();
////                List<clsMobile_mVisitPlanCategory> ListOfDataclsMobile_mVisitPlanCategory=new Select().from(clsMobile_mVisitPlanCategory.class).where(_dtclsMobile_mVisitPlanCategory.txtConstintCategoryID+"=?",_DtDetail.intCategoryID).execute();
////                if(ListOfDataclsMobile_mVisitPlanCategory.size()>0){
////                    outlet_name.setText(ListOfDataclsMobile_mVisitPlanCategory.get(0).txtCategoryName);
////                }else {
////                    outlet_name.setText("-");
////                }
////                outlet_name.setLayoutParams(params);
////
////                tr.addView(outlet_name);
////
////                TextView date = new TextView(getContext());
////                date.setTextSize(12);
////                date.setPadding(10, 10, 10, 10);
////                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
////                date.setTextColor(Color.BLACK);
////                date.setGravity(Gravity.CENTER);
////                date.setText(_DtDetail.txtDescription);
////                date.setLayoutParams(params);
////
////                tr.addView(date);
////
////                tablePushData.addView(tr,index++);
////            }
////        }
//    }

public void setListData(){
    listDataHeader.clear();
    listDataChild.clear();
    swipeListPlan.clear();
    swipeListUnplan.clear();
    swipeListAkuisisi.clear();
    swipeListInfoProgram.clear();
    swipeListMaintenance.clear();

    try {
        listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllPushData();
        if (listRealisasi!=null){
            if (listRealisasi.size()>0){
                for (tRealisasiVisitPlan data : listRealisasi){
//                    tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtPlanId(data.getTxtProgramVisitSubActivityId());
                    clsListItemAdapter swpItem =  new clsListItemAdapter();
                    mDokter dokter;
                    mApotek apotek;
                    String name = null;
                    if (!data.getTxtDokterId().equals("null")&&data.getTxtDokterId()!=null){
                        dtActivity =  (mActivity) repoActivity.findById(new clsHardCode().VisitDokter);
                        dokter = dokterRepo.findBytxtId(data.getTxtDokterId());
                        if (!dokter.getTxtLastName().equals("null")&&dokter.getTxtLastName()!=null){
                            name = "Visit Doctor " + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                        }else {
                            name = "Visit Doctor " + dokter.getTxtFirstName();
                        }

                        swpItem.setTxtImgName((dokter.getTxtFirstName().substring(0,1)).toUpperCase());
                    }else if (!data.getTxtApotekId().equals("null")&&data.getTxtApotekId()!=null){
                        dtActivity =  (mActivity) repoActivity.findById(new clsHardCode().VisitApotek);
                        apotek = apotekRepo.findBytxtId(data.getTxtApotekId());
                        name = "Visit " + apotek.getTxtName();
                        swpItem.setTxtImgName((apotek.getTxtName().substring(0,1)).toUpperCase());
                    }
                    swpItem.setTxtTittle(dtActivity.getTxtName());
                    swpItem.setTxtSubTittle(name);
                    swpItem.setIntColor(R.color.purple_600);
                    swpItem.setBoolSection(false);
                    swpItem.setTxtId(data.getTxtProgramVisitSubActivityId());
                    swipeListPlan.add(swpItem);
                }
            }
        }
        try {
            listAkuisisiHeader = akuisisiHeaderRepo.getAllPushData();
            if (listAkuisisiHeader!=null){
                if (listAkuisisiHeader.size()>0){
                    for (tAkuisisiHeader data : listAkuisisiHeader){
                        clsListItemAdapter swpItem =  new clsListItemAdapter();
                        String name = null;
                        mDokter dokter;
                        mApotek apotek;
                        if (data.getIntDokterId()!=null){
                            dtActivity =  (mActivity) repoActivity.findById(new clsHardCode().VisitDokter);
                            dokter = dokterRepo.findBytxtId(data.getIntDokterId());
                            name = "Visit Doctor" + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                            swpItem.setTxtImgName((dokter.getTxtFirstName().substring(0,1)).toUpperCase());
                        }else if (data.getIntApotekID()!=null){
                            dtActivity =  (mActivity) repoActivity.findById(new clsHardCode().VisitApotek);
                            apotek = apotekRepo.findBytxtId(data.getIntApotekID());
                            name = "Visit " + apotek.getTxtName();
                            swpItem.setTxtImgName((apotek.getTxtName().substring(0,1)).toUpperCase());
                        }
                        listAkuisisiDetail = (List<tAkuisisiDetail>) akuisisiDetailRepo.findByHeaderId(data.getTxtHeaderId());
                        if (listAkuisisiDetail!=null){
                            swpItem.setTxtDate(String.valueOf(listAkuisisiDetail.size()));
                        }
                        swpItem.setTxtTittle(dtActivity.getTxtName());
                        swpItem.setTxtSubTittle(name);
                        swpItem.setIntColor(R.color.blue_600);
                        swpItem.setBoolSection(false);
                        swpItem.setTxtId(data.getTxtHeaderId());
                        swipeListAkuisisi.add(swpItem);
                    }
                }
            }

            listMainHeader = maintenanceHeaderRepo.getAllPushData();
            if (listMainHeader!=null){
                if (listMainHeader.size()>0){
                    for (tMaintenanceHeader data : listMainHeader){
                        clsListItemAdapter swpItem =  new clsListItemAdapter();
                        String name = null;
                        mDokter dokter;
                        mApotek apotek;
                        if (data.getIntDokterId()!=null){
                            dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                            dokter = dokterRepo.findBytxtId(data.getIntDokterId());
                            name = "Visit Doctor " + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                            swpItem.setTxtImgName((dokter.getTxtFirstName().substring(0,1)).toUpperCase());
                        }else if (data.getIntApotekID()!=null){
                            dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                            apotek = apotekRepo.findBytxtId(data.getIntApotekID());
                            name = "Visit " + apotek.getTxtName();
                            swpItem.setTxtImgName((apotek.getTxtName().substring(0,1)).toUpperCase());
                        }
                        listMainDetail = (List<tMaintenanceDetail>) maintenanceDetailRepo.findByHeaderId(data.getTxtHeaderId());
                        if (listMainDetail!=null){
                            swpItem.setTxtDate(String.valueOf(listMainDetail.size()));
                        }
                        swpItem.setTxtTittle(dtActivity.getTxtName());
                        swpItem.setTxtSubTittle(name);
                        swpItem.setIntColor(R.color.pink_600);
                        swpItem.setBoolSection(false);
                        swpItem.setTxtId(data.getTxtHeaderId());
                        swipeListMaintenance.add(swpItem);
                    }
                }
            }

            listInfoHeader = infoProgramHeaderRepo.getAllPushData();
            if (listInfoHeader!=null){
                if (listInfoHeader.size()>0){
                    for (tInfoProgramHeader data : listInfoHeader){
                        clsListItemAdapter swpItem =  new clsListItemAdapter();
                        String name = null;
                        mDokter dokter;
                        mApotek apotek;
                        if (data.getIntDokterId()!=null){
                            dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                            dokter = dokterRepo.findBytxtId(data.getIntDokterId());
                            name = "Visit Doctor " + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                            swpItem.setTxtImgName((dokter.getTxtFirstName().substring(0,1)).toUpperCase());
                        }else if (data.getIntApotekId()!=null){
                            dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                            apotek = apotekRepo.findBytxtId(data.getIntApotekId());
                            name = "Visit " + apotek.getTxtName();
                            swpItem.setTxtImgName((apotek.getTxtName().substring(0,1)).toUpperCase());
                        }
                        listInfoDetail = (List<tInfoProgramDetail>) infoProgramDetailRepo.findByHeaderId(data.getTxtHeaderId());
                        if (listInfoDetail!=null){
                            swpItem.setTxtDate(String.valueOf(listInfoDetail.size()));
                        }
                        swpItem.setTxtTittle(dtActivity.getTxtName());
                        swpItem.setTxtSubTittle(name);
                        swpItem.setIntColor(R.color.green_500);
                        swpItem.setBoolSection(false);
                        swpItem.setTxtId(data.getTxtHeaderId());
                        swipeListInfoProgram.add(swpItem);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listDataHeader.add("Plan (" + String.valueOf(swipeListPlan.size()) + ")");
//        listDataHeader.add("Unplan (" + String.valueOf(swipeListUnplan.size()) + ")");
        listDataHeader.add("Akuisisi (" + String.valueOf(swipeListAkuisisi.size()) + ")");
        listDataHeader.add("Maintenance (" + String.valueOf(swipeListMaintenance.size()) + ")");
        listDataHeader.add("Info Program (" + String.valueOf(swipeListInfoProgram.size()) + ")");
        listDataChild.put(listDataHeader.get(0), swipeListPlan);
//        listDataChild.put(listDataHeader.get(1), swipeListUnplan);
        listDataChild.put(listDataHeader.get(1), swipeListAkuisisi);
        listDataChild.put(listDataHeader.get(2), swipeListMaintenance);
        listDataChild.put(listDataHeader.get(3), swipeListInfoProgram);
    } catch (SQLException e) {
        e.printStackTrace();
    }


    mExpandableListAdapter = new com.kalbe.kalbecallplanaedp.adapter.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
    mExpandableListView.setAdapter(mExpandableListAdapter);
    mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
}
}
