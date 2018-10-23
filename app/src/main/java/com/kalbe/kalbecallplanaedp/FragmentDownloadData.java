package com.kalbe.kalbecallplanaedp;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mTypeSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserMappingArea;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivityAttachment;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Repo.clsTokenRepo;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mTypeSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserMappingAreaRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityAttachmentRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadAllData.DownloadAllData;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadApotek.DownlaodApotek;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadDokter.DownloadDokter;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadSubActivity.DownloadSubActivity;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadSubActivityDetail.DownloadSubActivityDetail;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadmActivity.DownloadmActivity;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtMappingArea.DownloadtMappingArea;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit.DownloadtProgramVisit;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentDownloadData extends Fragment{
    View v;

    ArrayAdapter<String> dataAdapter;
    List<String> itemList = new ArrayList<>();
    private LinearLayout ln_download_all, ln_branch_downlaod, ln_download_apotek,ln_download_dokter, ln_download_activity,ln_download_subactivity,ln_download_subsub_activity, ln_download_typesub_activity;
    private  TextView tv_download_branch, tv_download_apotek,tv_download_dokter, tv_download_activity, tv_download_subactivity, tv_download_subsubactivity, tv_download_typesubsubactivity;
    private TextView tv_count_apotek,tv_count_branch,tv_count_dokter, tv_count_download_activity,tv_count_download_subactivity, tv_count_download_subsubactivity, tv_count_download_typesubsubactivity;
    private Gson gson;
    List<clsToken> dataToken;
    clsTokenRepo tokenRepo;
    mUserLoginRepo loginRepo;
    List<mActivity> dataListActivity = new ArrayList<>();
    List<mSubActivity> dataListSubActivity = new ArrayList<>();
    List<mSubSubActivity> dataListSubSubActivity = new ArrayList<>();
    List<mApotek> dataListApotek = new ArrayList<>();
    List<mDokter> dataListDokter = new ArrayList<>();
    List<mUserMappingArea> dataListArea = new ArrayList<>();
    List<tProgramVisit> dataListProgramVist = new ArrayList<>();
    List<tProgramVisitSubActivity> dataListProgramVisitSubActivity = new ArrayList<>();
    List<tProgramVisitSubActivityAttachment> dataListProgramVisitAttachment = new ArrayList<>();
    List<tRealisasiVisitPlan> dataListRealisasi = new ArrayList<>();
    mActivityRepo dtActivityrepo;
    mApotekRepo apotekRepo;
    mDokterRepo dokterRepo;
    mTypeSubSubActivityRepo dtRepoTypeSubSubActivity;
    mSubActivityRepo dtRepoSubActivity;
    mSubSubActivityRepo dtRepoSubSubActivity;
    mUserMappingAreaRepo dtRepoArea;
    tProgramVisitRepo dtRepoProgramVisit;
    tProgramVisitSubActivityRepo dtRepoProgramVisitSubActivity;
    tProgramVisitSubActivityAttachmentRepo dtRepoProVisitAttch;
    tRealisasiVisitPlanRepo dtRepoRealisasi;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_download_data, container, false);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        ln_download_all = (LinearLayout)v.findViewById(R.id.ln_download_all);
        ln_branch_downlaod = (LinearLayout) v.findViewById(R.id.ln_branch_download);
        ln_download_apotek = (LinearLayout) v.findViewById(R.id.ln_download_apotek);
        ln_download_dokter = (LinearLayout)v.findViewById(R.id.ln_download_dokter);
        ln_download_activity = (LinearLayout)v.findViewById(R.id.ln_download_activity);
        ln_download_subactivity = (LinearLayout)v.findViewById(R.id.ln_download_subactivity);
        ln_download_subsub_activity = (LinearLayout)v.findViewById(R.id.ln_download_subsub_activity);
//        ln_download_typesub_activity = (LinearLayout)v.findViewById(R.id.ln_download_typesub_activity);

        /*nama download*/
        tv_download_branch = (TextView) v.findViewById(R.id.tv_download_branch);
        tv_download_apotek = (TextView) v.findViewById(R.id.tv_download_apotek);
        tv_download_dokter = (TextView)v.findViewById(R.id.tv_download_dokter);
        tv_download_activity = (TextView)v.findViewById(R.id.tv_download_activity);
        tv_download_subactivity = (TextView)v.findViewById(R.id.tv_download_subactivity);
        tv_download_subsubactivity = (TextView)v.findViewById(R.id.tv_download_subsubactivity);
//        tv_download_typesubsubactivity = (TextView)v.findViewById(R.id.tv_download_typesubsubactivity);

        /*count*/
        tv_count_branch = (TextView)v.findViewById(R.id.tv_count_branch);
        tv_count_apotek = (TextView)v.findViewById(R.id.tv_count_apotek);
        tv_count_dokter = (TextView)v.findViewById(R.id.tv_count_dokter);
        tv_count_download_activity = (TextView) v.findViewById(R.id.tv_count_download_activity);
        tv_count_download_subactivity = (TextView) v.findViewById(R.id.tv_count_download_subactivity);
        tv_count_download_subsubactivity = (TextView) v.findViewById(R.id.tv_count_download_subsubactivity);
//        tv_count_download_typesubsubactivity = (TextView)v.findViewById(R.id.tv_count_download_typesubsubactivity);


        loginRepo = new mUserLoginRepo(getContext());
        dtActivityrepo = new mActivityRepo(getContext());
        dtRepoTypeSubSubActivity = new mTypeSubSubActivityRepo(getContext());
        dtRepoSubActivity= new mSubActivityRepo(getContext());
        dtRepoSubSubActivity= new mSubSubActivityRepo(getContext());
        dokterRepo = new mDokterRepo(getContext());
        apotekRepo = new mApotekRepo(getContext());
        dtRepoArea = new mUserMappingAreaRepo(getContext());
        dtRepoProgramVisit = new tProgramVisitRepo(getContext());
        dtRepoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        dtRepoProVisitAttch = new tProgramVisitSubActivityAttachmentRepo(getContext());
        dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());

        try {
            dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
            if (dataListArea!=null){
                tv_count_branch.setText(String.valueOf(dataListArea.size()));
            }
            dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
            if (dataListActivity!=null){
                tv_count_download_activity.setText(String.valueOf(dataListActivity.size()));
            }
            dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
            if (dataListSubActivity!=null){
                tv_count_download_subactivity.setText(String.valueOf(dataListSubActivity.size()));
            }

            dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
            if (dataListSubSubActivity!=null){
                tv_count_download_subsubactivity.setText(String.valueOf(dataListSubSubActivity.size()));
            }

            dataListApotek = (List<mApotek>) apotekRepo.findAll();
            if (dataListApotek!=null){
                tv_count_apotek.setText(String.valueOf(dataListApotek.size()));
            }

            dataListDokter = (List<mDokter>) dokterRepo.findAll();
            if (dataListDokter!=null){
                tv_count_dokter.setText(String.valueOf(dataListDokter.size()));
            }

            dataListProgramVist = (List<tProgramVisit>) dtRepoProgramVisit.findAll();
            if (dataListProgramVist!=null){

            }
//            dataListTypeSubSubActivity = (List<mTypeSubSubActivity>) dtRepoTypeSubSubActivity.findAll();
//            if (dataListTypeSubSubActivity!=null){
//                tv_count_download_typesubsubactivity.setText(String.valueOf(dataListTypeSubSubActivity.size()));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ln_download_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAllData();
            }
        });

        ln_branch_downlaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListArea!=null){
                    if (dataListArea.size()>0){
                        for (mUserMappingArea data : dataListArea){
                            itemList.add(String.valueOf(data.getIntUserMappingAreaId()) + " - " + data.getTxtKecamatanID());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
              onButtonOnClick(ln_branch_downlaod, tv_download_branch, "mUserMappingArea");
            }
        });

        ln_download_apotek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListApotek = (List<mApotek>) apotekRepo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListApotek!=null){
                    if (dataListApotek.size()>0){
                        for (mApotek data : dataListApotek){
                            itemList.add(String.valueOf(data.getTxtCode()) + " - " + data.getTxtName());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_apotek, tv_download_apotek, "mApotek");
            }
        });

        ln_download_dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListDokter = (List<mDokter>) dokterRepo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListDokter!=null){
                    if (dataListDokter.size()>0){
                        for (mDokter data : dataListDokter){
                            itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_dokter, tv_download_dokter, "mDokter");
            }
        });
        ln_download_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListActivity!=null){
                    if (dataListActivity.size()>0){
                        for (mActivity data : dataListActivity){
                            itemList.add(String.valueOf(data.getIntActivityId()) + " - " + data.getTxtName());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_activity, tv_download_activity, "mActivity");
            }
        });

        ln_download_subactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListSubActivity!=null){
                    if (dataListSubActivity.size()>0){
                        for (mSubActivity data : dataListSubActivity){
                            itemList.add(String.valueOf(data.getIntSubActivityid() + " - " + data.getTxtName()));
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_subactivity, tv_download_subactivity, "mSubActivity");
            }
        });

        ln_download_subsub_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListSubSubActivity!=null){
                    if (dataListSubSubActivity.size()>0){
                        for (mSubSubActivity data : dataListSubSubActivity){
                            itemList.add(String.valueOf(data.getIntSubSubActivityid() + " - " + data.getTxtName()));
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_subsub_activity, tv_download_subsubactivity, "mSubSubActivity");
            }
        });

//        ln_download_typesub_activity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemList.clear();
//                try {
//                    dataListTypeSubSubActivity = (List<mTypeSubSubActivity>) dtRepoTypeSubSubActivity.findAll();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                if (dataListTypeSubSubActivity!=null){
//                    if (dataListTypeSubSubActivity.size()>0){
//                        for (mTypeSubSubActivity data : dataListTypeSubSubActivity){
//                            itemList.add(String.valueOf(data.getIntTypeSubSubActivityId() + " - " + data.getTxtName()));
//                        }
//                    }
//                }
//                itemList.add("1" + " - " + "tipe sub sub activity");
//                onButtonOnClick(ln_download_typesub_activity, tv_download_typesubsubactivity, "mTypeSubSubActivity");
//
//            }
//        });
        return v;
    }

    private  void onButtonOnClick(LinearLayout ln_click, TextView tv_click, final String txtDownlaod){
        RelativeLayout rl_download = (RelativeLayout) ln_click.getChildAt(0);
        ImageView img_download = (ImageView) rl_download.getChildAt(0);
        int color = ImageViewCompat.getImageTintList(img_download).getDefaultColor();
        String item =  tv_click.getText().toString();

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        AppBarLayout appBarLayout = (AppBarLayout) dialog.findViewById(R.id.appbar_download);
        final AppCompatSpinner spnDownload = (AppCompatSpinner) dialog.findViewById(R.id.spnDownload);
        final  TextView tv_download_title = (TextView) dialog.findViewById(R.id.tv_title_download);
        final  Button btn_download = (Button) dialog.findViewById(R.id.btn_download);
        Drawable drawable = getResources().getDrawable(R.drawable.btn_rounded_green_300);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        btn_download.setBackgroundColor(color);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green_300));
        tv_download_title.setText(item);

        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,itemList);
//        final ArrayAdapter<String> array = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, itemList);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spnDownload.setAdapter(dataAdapter);
        spnDownload.setSelection(0);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDataResult(txtDownlaod);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void displayDataResult(String txtDownlaod) {
        if (txtDownlaod.equals("mActivity")){
            downloadActivity();
        }else if (txtDownlaod.equals("mSubActivity")){
            downloadSubActivity();
        } else if (txtDownlaod.equals("mSubSubActivity")){
            downloadSubSubActivity();
        } else if (txtDownlaod.equals("mApotek")){
            downloadApotek(false);
        } else if (txtDownlaod.equals("mDokter")){
            downloadDokter(false);
        } else if (txtDownlaod.equals("mUserMappingArea")){
            downloadArea();
        } else if (txtDownlaod.equals("tProgramVisit")){
            downloadtCallPlan();
        }

    }

    private JSONObject ParamDownloadMaster(){
        JSONObject jsonObject = new JSONObject();
        try {
            mUserLogin data = new clsMainBL().getUserLogin(getContext());
            jsonObject.put("userId", data.getIntUserID());
            jsonObject.put("intRoleId", data.getIntRoleID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void downloadAllData() {
        String strLinkAPI = new clsHardCode().linkDownloadAll;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadAllData model = gson.fromJson(jsonObject.toString(), DownloadAllData.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){

                            downloadApotek(true);
                            downloadDokter(true);
                            if (model.getData().getDataVMmUserMappingArea()!=null){
                                if (model.getData().getDataVMmUserMappingArea().size()>0){
                                    dtRepoArea = new mUserMappingAreaRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataVMmUserMappingArea().size(); i++){
                                        mUserMappingArea data = new mUserMappingArea();
                                        data.setIntUserMappingAreaId(model.getData().getDataVMmUserMappingArea().get(i).getIntUserMappingAreaId());
                                        data.setIntUserId(model.getData().getDataVMmUserMappingArea().get(i).getIntUserId());
                                        data.setTxtKecamatanID(model.getData().getDataVMmUserMappingArea().get(i).getTxtKecamatanID());
                                        dtRepoArea.createOrUpdate(data);
                                    }
                                }
                                tv_count_branch.setText(String.valueOf(model.getData().getDataVMmUserMappingArea().size()));
                            }
                            if (model.getData().getDataVMActivity()!=null){
                                if (model.getData().getDataVMActivity().size()>0){
                                    dtActivityrepo = new mActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataVMActivity().size(); i++){
                                        mActivity data = new mActivity();
                                        data.setIntActivityId(model.getData().getDataVMActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getDataVMActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataVMActivity().get(i).getTxtDescription());
                                        dtActivityrepo.createOrUpdate(data);
                                    }
                                }
                                tv_count_download_activity.setText(String.valueOf(model.getData().getDataVMActivity().size()));
                            }

                            if (model.getData().getDataVMSubActvty()!=null){
                                if (model.getData().getDataVMSubActvty().size()>0){
                                    dtRepoSubActivity = new mSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataVMSubActvty().size(); i++){
                                        mSubActivity data = new mSubActivity();
                                        data.setIntSubActivityid(model.getData().getDataVMSubActvty().get(i).getIntSubActivityId());
                                        data.setIntActivityid(model.getData().getDataVMSubActvty().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getDataVMSubActvty().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataVMSubActvty().get(i).getTxtDescription());
                                        dtRepoSubActivity.createOrUpdate(data);
                                    }
                                }
                                tv_count_download_subactivity.setText(String.valueOf(model.getData().getDataVMSubActvty().size()));
                            }

                            if (model.getData().getDataVMmSubDetailActivity()!=null){
                                if (model.getData().getDataVMmSubDetailActivity().size()>0){
                                    dtRepoSubSubActivity = new mSubSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataVMmSubDetailActivity().size(); i++){
                                        mSubSubActivity data = new mSubSubActivity();
                                        data.setIntSubActivityid(model.getData().getDataVMmSubDetailActivity().get(i).getIntSubActivityId());
                                        data.setIntSubSubActivityid(model.getData().getDataVMmSubDetailActivity().get(i).getIntSubDetailActivityId());
                                        data.setTxtName(model.getData().getDataVMmSubDetailActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataVMmSubDetailActivity().get(i).getTxtDescription());
                                        data.setIntType(model.getData().getDataVMmSubDetailActivity().get(i).getIntFlag());
                                        dtRepoSubSubActivity.createOrUpdate(data);
                                    }
                                }
                                tv_count_download_subsubactivity.setText(String.valueOf(model.getData().getDataVMmSubDetailActivity().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadArea() {
        String strLinkAPI = new clsHardCode().linkDownloadArea;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtMappingArea model = gson.fromJson(jsonObject.toString(), DownloadtMappingArea.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtMappingArea()!=null){
                                if (model.getData().getLtMappingArea().size()>0){
                                    dtRepoArea = new mUserMappingAreaRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtMappingArea().size(); i++){
                                        mUserMappingArea data = new mUserMappingArea();
                                        data.setIntUserMappingAreaId(model.getData().getLtMappingArea().get(i).getIntUserMappingAreaId());
                                        data.setIntUserId(model.getData().getLtMappingArea().get(i).getIntUserId());
                                        data.setTxtKecamatanID(model.getData().getLtMappingArea().get(i).getTxtKecamatanID());

                                       dtRepoArea.createOrUpdate(data);
                                        itemList.add(String.valueOf(model.getData().getLtMappingArea().get(i).getIntUserMappingAreaId()) + " - " + model.getData().getLtMappingArea().get(i).getTxtKecamatanID());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_branch.setText(String.valueOf(model.getData().getLtMappingArea().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadActivity() {
        String strLinkAPI = new clsHardCode().linkmActivity;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadmActivity model = gson.fromJson(jsonObject.toString(), DownloadmActivity.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getActivity()!=null){
                                if (model.getData().getActivity().size()>0){
                                    dtActivityrepo = new mActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getActivity().size(); i++){
                                        mActivity data = new mActivity();
                                        data.setIntActivityId(model.getData().getActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getActivity().get(i).getTxtDescription());
                                        dtActivityrepo.createOrUpdate(data);
                                        itemList.add(String.valueOf(model.getData().getActivity().get(i).getIntActivityId()) + " - " + model.getData().getActivity().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_activity.setText(String.valueOf(model.getData().getActivity().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadSubActivity() {
        String strLinkAPI = new clsHardCode().linkSubActivity;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadSubActivity model = gson.fromJson(jsonObject.toString(), DownloadSubActivity.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtSubActivity()!=null){
                                if (model.getData().getLtSubActivity().size()>0){
                                    dtRepoSubActivity = new mSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtSubActivity().size(); i++){
                                        mSubActivity data = new mSubActivity();
                                        data.setIntSubActivityid(model.getData().getLtSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityid(model.getData().getLtSubActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getLtSubActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getLtSubActivity().get(i).getTxtDescription());
                                        dtRepoSubActivity.createOrUpdate(data);
                                        itemList.add(String.valueOf(model.getData().getLtSubActivity().get(i).getIntSubActivityId()) + " - " + model.getData().getLtSubActivity().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_subactivity.setText(String.valueOf(model.getData().getLtSubActivity().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadSubSubActivity() {
        String strLinkAPI = new clsHardCode().linkSubSubActivity;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadSubActivityDetail model = gson.fromJson(jsonObject.toString(), DownloadSubActivityDetail.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtSubActivityDetailData()!=null){
                                if (model.getData().getLtSubActivityDetailData().size()>0){
                                    dtRepoSubSubActivity = new mSubSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtSubActivityDetailData().size(); i++){
                                        mSubSubActivity data = new mSubSubActivity();
                                        data.setIntSubActivityid(model.getData().getLtSubActivityDetailData().get(i).getIntSubActivityId());
                                        data.setIntSubSubActivityid(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId());
                                        data.setTxtName(model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getLtSubActivityDetailData().get(i).getTxtDescription());
                                        data.setIntType(model.getData().getLtSubActivityDetailData().get(i).getIntFlag());
                                        dtRepoSubSubActivity.createOrUpdate(data);
                                        itemList.add(String.valueOf(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId()) + " - " + model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_subsubactivity.setText(String.valueOf(model.getData().getLtSubActivityDetailData().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadApotek(final boolean isFromDownloadAll) {
        String strLinkAPI = new clsHardCode().linkApotek;
        new clsHelperBL().volleyDownloadDataKLB(getActivity(), strLinkAPI, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownlaodApotek model = gson.fromJson(jsonObject.toString(), DownlaodApotek.class);
                        boolean txtStatus = model.isStatus();
                        String txtMessage = model.getMessage();

                        if (txtStatus == true){
                            apotekRepo = new mApotekRepo(getContext());
                            if (model.getData()!=null){
                                itemList.clear();
                                if (model.getData().size()>0){
                                    for (int i = 0; i < model.getData().size(); i++){
                                        mApotek data = new mApotek();
                                        data.setTxtCode(model.getData().get(i).getCode());
                                        data.setTxtName(model.getData().get(i).getName());
                                        data.setTxtKecId(model.getData().get(i).getKecId());
                                        data.setTxtKecName(model.getData().get(i).getKecName());

                                        apotekRepo.createOrUpdate(data);
                                        itemList.add(model.getData().get(i).getCode() + " - " + model.getData().get(i).getName());
                                    }
                                }
                                if (!isFromDownloadAll){
                                    dataAdapter.notifyDataSetChanged();
                                }
                                tv_count_apotek.setText(String.valueOf(model.getData().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
//                            Toast.makeText(getApplicationContext(), txtMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadDokter(final boolean isFromDownloadAll) {
        String strLinkAPI = new clsHardCode().linkDokter;
        new clsHelperBL().volleyDownloadDataKLB(getActivity(), strLinkAPI, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadDokter model = gson.fromJson(jsonObject.toString(), DownloadDokter.class);
                        boolean txtStatus = model.isStatus();
                        String txtMessage = model.getMessage();

                        if (txtStatus == true){
                            dokterRepo = new mDokterRepo(getContext());
                            if (model.getData()!=null){
                                itemList.clear();
                                if (model.getData().size()>0){
                                    for (int i = 0; i < model.getData().size(); i++){
                                        mDokter data = new mDokter();
                                        data.setTxtId(model.getData().get(i).getId());
                                        data.setTxtFirstName(model.getData().get(i).getFirstname());
                                        data.setTxtLastName(model.getData().get(i).getLastname());
                                        data.setTxtGender(model.getData().get(i).getGender());
                                        data.setTxtSpecialist(model.getData().get(i).getSpecialist());
                                        data.setTxtType(model.getData().get(i).getType());

                                        dokterRepo.createOrUpdate(data);
                                        itemList.add(model.getData().get(i).getId() + " - " + model.getData().get(i).getFirstname());
                                    }
                                }
                                if (!isFromDownloadAll){
                                    dataAdapter.notifyDataSetChanged();
                                }
                                tv_count_dokter.setText(String.valueOf(model.getData().size()));
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadtCallPlan() {
        String strLinkAPI = new clsHardCode().linkProgramVisit;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtProgramVisit model = gson.fromJson(jsonObject.toString(), DownloadtProgramVisit.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getTProgramVisit()!=null){
                                if (model.getData().getTProgramVisit().size()>0){
                                    dtRepoProgramVisit = new tProgramVisitRepo(getContext());
                                    for (int i = 0; i <model.getData().getTProgramVisit().size(); i++){
                                        tProgramVisit data = new tProgramVisit();
                                        data.setTxtProgramVisitId(model.getData().getTProgramVisit().get(i).getTxtProgramVisitId());
                                        data.setIntUserId(model.getData().getTProgramVisit().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getTProgramVisit().get(i).getIntRoleId());
                                        data.setDtStart(model.getData().getTProgramVisit().get(i).getDtStart());
                                        data.setDtEnd(model.getData().getTProgramVisit().get(i).getDtEnd());
                                        data.setIntStatus(model.getData().getTProgramVisit().get(i).getIntStatus());
                                        data.setIntType(model.getData().getTProgramVisit().get(i).getIntType());
                                        data.setTxtNotes(model.getData().getTProgramVisit().get(i).getTxtNotes());
                                        dtRepoProgramVisit.createOrUpdate(data);
//                                        itemList.add(String.valueOf(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId()) + " - " + model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
//                                tv_count_download_subsubactivity.setText(String.valueOf(model.getData().getLtSubActivityDetailData().size()));
                            }
                            if (model.getData().getTProgramVisitSubActivity()!=null){
                                if (model.getData().getTProgramVisitSubActivity().size()>0){
                                    dtRepoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getTProgramVisitSubActivity().size(); i++){
                                        tProgramVisitSubActivity data = new tProgramVisitSubActivity();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getTProgramVisitSubActivity().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtProgramVisitId(model.getData().getTProgramVisitSubActivity().get(i).getTxtProgramVisitId());
                                        data.setTxtApotekId(model.getData().getTProgramVisitSubActivity().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getTProgramVisitSubActivity().get(i).getTxtApotekName());
                                        data.setIntType(model.getData().getTProgramVisitSubActivity().get(i).getIntType());
                                        data.setTxtAreaId(model.getData().getTProgramVisitSubActivity().get(i).getTxtAreaId());
                                        data.setTxtAreaName(model.getData().getTProgramVisitSubActivity().get(i).getTxtAreaName());
                                        data.setTxtDokterId(model.getData().getTProgramVisitSubActivity().get(i).getTxtDokterId());
                                        data.setTxtDokterName(model.getData().getTProgramVisitSubActivity().get(i).getTxtDokterName());
                                        data.setIntSubActivityId(model.getData().getTProgramVisitSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityId(model.getData().getTProgramVisitSubActivity().get(i).getIntActivityId());
                                        data.setDtStart(model.getData().getTProgramVisitSubActivity().get(i).getDtStart());
                                        data.setDtEnd(model.getData().getTProgramVisitSubActivity().get(i).getDtEnd());
                                        data.setTxtNotes(model.getData().getTProgramVisitSubActivity().get(i).getTxtNotes());
                                        dtRepoProgramVisitSubActivity.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getTProgramVisitSubActivityAttachment()!=null){
                                if (model.getData().getTProgramVisitSubActivityAttachment().size()>0){
                                    dtRepoProVisitAttch = new tProgramVisitSubActivityAttachmentRepo(getContext());
                                    for (int i = 0; i <model.getData().getTProgramVisitSubActivityAttachment().size(); i++){
                                        tProgramVisitSubActivityAttachment data = new tProgramVisitSubActivityAttachment();
                                        data.setTxtProgramVisitSubActivityAttachmentId(model.getData().getTProgramVisitSubActivityAttachment().get(i).getTxtProgramVisitSubActivityAttachmentId());
                                        data.setTxtFileName(model.getData().getTProgramVisitSubActivityAttachment().get(i).getTxtFilePath());
//                                        data.setBlobFile();
                                        data.setTxtNoDocument(model.getData().getTProgramVisitSubActivityAttachment().get(i).getTxtNoDocument());
                                        data.setTxtProgramVisitSubActivityId(model.getData().getTProgramVisitSubActivityAttachment().get(i).getTxtProgramVisitSubActivityId());
                                        data.setDtExpiredDate(model.getData().getTProgramVisitSubActivityAttachment().get(i).getDtExpiredDate());
                                        dtRepoProVisitAttch.createOrUpdate(data);
                                    }
                                }
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
