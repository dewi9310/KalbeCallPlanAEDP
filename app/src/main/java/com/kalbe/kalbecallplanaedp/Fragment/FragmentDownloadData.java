package com.kalbe.kalbecallplanaedp.Fragment;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivityAttachment;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.MainMenu;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.clsTokenRepo;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mTypeSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserMappingAreaRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityAttachmentRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadAllData.DownloadAllData;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadApotek.DownlaodApotek;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadDokter.DownloadDokter;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadSubActivity.DownloadSubActivity;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadSubActivityDetail.DownloadSubActivityDetail;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadTRealisasi.DownloadTRealisasi;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadmActivity.DownloadmActivity;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtAkuisisi.DownloadtAkuisisi;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtInfoProgram.DownloadtInfoProgram;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtMaintenance.DownloadtMaintenance;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtMappingArea.DownloadtMappingArea;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit.DownloadtProgramVisit;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private LinearLayout ln_download_all, ln_branch_downlaod, ln_download_apotek,ln_download_dokter, ln_download_activity,ln_download_subactivity,ln_download_subsub_activity, ln_download_realisasi, ln_download_akuisisi, ln_download_maintenance, ln_download_infoprogram;
    private  TextView tv_download_branch, tv_download_apotek,tv_download_dokter, tv_download_activity, tv_download_subactivity, tv_download_subsubactivity, tv_download_typesubsubactivity, tv_download_realisasi, tv_download_akuisisi, tv_download_maintenance, tv_download_infoprogram;
    private TextView tv_count_apotek,tv_count_branch,tv_count_dokter, tv_count_download_activity,tv_count_download_subactivity, tv_count_download_subsubactivity, tv_count_realisasi, tv_count_akuisisi, tv_count_maintenance, tv_count_infoprogram;
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
    List<tAkuisisiHeader> dataListAkuisisi = new ArrayList<>();
    List<tMaintenanceHeader> dataListMaintenance = new ArrayList<>();
    List<tInfoProgramHeader> dataLIstInfoProgram = new ArrayList<>();
    mActivityRepo dtActivityrepo;
    mApotekRepo apotekRepo;
    mDokterRepo dokterRepo;
    mTypeSubSubActivityRepo dtRepoTypeSubSubActivity;
    mSubActivityRepo dtRepoSubActivity;
    mSubSubActivityRepo dtRepoSubSubActivity;
    mUserMappingAreaRepo dtRepoArea;
    tProgramVisitRepo dtRepoProgramVisit;
    tProgramVisitSubActivityRepo dtRepoProgramVisitSubActivity;
//    tProgramVisitSubActivityAttachmentRepo dtRepoProVisitAttch;
    tRealisasiVisitPlanRepo dtRepoRealisasi;
    tAkuisisiHeaderRepo dtRepoAkuisisiHeader;
    tAkuisisiDetailRepo dtRepoAkuisisiDetail;
    tMaintenanceHeaderRepo dtRepoMaintenanceHeader;
    tMaintenanceDetailRepo dtRepoMaintenanceDetail;
    tInfoProgramHeaderRepo dtRepoInfoProgHeader;
    tInfoProgramDetailRepo dtRepoInfoProgDetail;


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
        ln_download_realisasi = (LinearLayout)v.findViewById(R.id.ln_download_realisasi);
        ln_download_akuisisi = (LinearLayout)v.findViewById(R.id.ln_download_akuisisi);
        ln_download_maintenance = (LinearLayout)v.findViewById(R.id.ln_download_maintenance);
        ln_download_infoprogram = (LinearLayout)v.findViewById(R.id.ln_download_infoprogram);
//        ln_download_typesub_activity = (LinearLayout)v.findViewById(R.id.ln_download_typesub_activity);

        /*nama download*/
        tv_download_branch = (TextView) v.findViewById(R.id.tv_download_branch);
        tv_download_apotek = (TextView) v.findViewById(R.id.tv_download_apotek);
        tv_download_dokter = (TextView)v.findViewById(R.id.tv_download_dokter);
        tv_download_activity = (TextView)v.findViewById(R.id.tv_download_activity);
        tv_download_subactivity = (TextView)v.findViewById(R.id.tv_download_subactivity);
        tv_download_subsubactivity = (TextView)v.findViewById(R.id.tv_download_subsubactivity);
        tv_download_realisasi = (TextView)v.findViewById(R.id.tv_download_realisasi);
        tv_download_akuisisi = (TextView)v.findViewById(R.id.tv_download_akuisisi);
        tv_download_maintenance = (TextView)v.findViewById(R.id.tv_download_maintenance);
        tv_download_infoprogram = (TextView)v.findViewById(R.id.tv_download_infoprogram);
//        tv_download_typesubsubactivity = (TextView)v.findViewById(R.id.tv_download_typesubsubactivity);

        /*count*/
        tv_count_branch = (TextView)v.findViewById(R.id.tv_count_branch);
        tv_count_apotek = (TextView)v.findViewById(R.id.tv_count_apotek);
        tv_count_dokter = (TextView)v.findViewById(R.id.tv_count_dokter);
        tv_count_download_activity = (TextView) v.findViewById(R.id.tv_count_download_activity);
        tv_count_download_subactivity = (TextView) v.findViewById(R.id.tv_count_download_subactivity);
        tv_count_download_subsubactivity = (TextView) v.findViewById(R.id.tv_count_download_subsubactivity);
        tv_count_realisasi = (TextView)v.findViewById(R.id.tv_count_realisasi);
        tv_count_akuisisi = (TextView)v.findViewById(R.id.tv_count_akuisisi);
        tv_count_maintenance = (TextView)v.findViewById(R.id.tv_count_maintenance);
        tv_count_infoprogram = (TextView)v.findViewById(R.id.tv_count_infoprogram);
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
//        dtRepoProVisitAttch = new tProgramVisitSubActivityAttachmentRepo(getContext());
        dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        dtRepoAkuisisiHeader = new tAkuisisiHeaderRepo(getContext());
        dtRepoAkuisisiDetail = new tAkuisisiDetailRepo(getContext());
        dtRepoMaintenanceHeader = new tMaintenanceHeaderRepo(getContext());
        dtRepoMaintenanceDetail = new tMaintenanceDetailRepo(getContext());
        dtRepoInfoProgHeader = new tInfoProgramHeaderRepo(getContext());
        dtRepoInfoProgDetail = new tInfoProgramDetailRepo(getContext());

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

//            dataListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
//            if (dataListRealisasi!=null){
//                tv_count_realisasi.setText(String.valueOf(dataListRealisasi.size()));
//            }

            dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>)dtRepoProgramVisitSubActivity.findAll();
            if (dataListProgramVisitSubActivity!=null){
                tv_count_realisasi.setText(String.valueOf(dataListProgramVisitSubActivity.size()));
            }

            dataListAkuisisi = (List<tAkuisisiHeader>) dtRepoAkuisisiHeader.findAll();
            if (dataListAkuisisi!=null){
                tv_count_akuisisi.setText(String.valueOf(dataListAkuisisi.size()));
            }

            dataListMaintenance = (List<tMaintenanceHeader>) dtRepoMaintenanceHeader.findAll();
            if (dataListMaintenance!=null){
                tv_count_maintenance.setText(String.valueOf(dataListMaintenance.size()));
            }

            dataLIstInfoProgram = (List<tInfoProgramHeader>) dtRepoInfoProgHeader.findAll();
            if (dataLIstInfoProgram!=null){
                tv_count_infoprogram.setText(String.valueOf(dataLIstInfoProgram.size()));
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
                            if (data.getTxtLastName()!=null){
                                itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName() + " " + data.getTxtLastName());
                            }else {
                                itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName());
                            }

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
                            mActivity activity = null;
                            try {
                                activity = (mActivity) dtActivityrepo.findById(data.getIntActivityid());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            itemList.add(String.valueOf(data.getIntSubActivityid()) + ". " + activity.getTxtName() + " - " + data.getTxtName());
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
                    if (dataListSubSubActivity!=null){
                        if (dataListSubSubActivity.size()>0){
                            for (mSubSubActivity data : dataListSubSubActivity){
                                mSubActivity _mSubActivity = (mSubActivity) dtRepoSubActivity.findById(data.getIntSubActivityid());
                                mActivity _mActivity = (mActivity) dtActivityrepo.findById(_mSubActivity.getIntActivityid());
                                itemList.add(String.valueOf(data.getIntSubSubActivityid()) + ". " + _mActivity.getTxtName() + " - " + _mSubActivity.getTxtName() + " - " + data.getTxtName());
                            }
                        }else {
                            itemList.add(" - ");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                onButtonOnClick(ln_download_subsub_activity, tv_download_subsubactivity, "mSubSubActivity");
            }
        });

        ln_download_realisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>) dtRepoProgramVisitSubActivity.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListProgramVisitSubActivity!=null){
                    if (dataListProgramVisitSubActivity.size()>0){
                        int index = 0;
                        for (tProgramVisitSubActivity data : dataListProgramVisitSubActivity){
                            index++;
                            mActivity activity = null;
                            try {
                                activity = (mActivity) dtActivityrepo.findById(data.getIntActivityId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (data.getIntActivityId()==1){
                                itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtDokterName());
                            }else if (data.getIntActivityId()==2){
                                itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtApotekName());
                            }else {
                                itemList.add(String.valueOf(index)+ " - " + activity.getTxtName());
                            }
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_realisasi, tv_download_realisasi, "tRealisasiVisitPlan");
            }
        });


        ln_download_akuisisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListAkuisisi = (List<tAkuisisiHeader>) dtRepoAkuisisiHeader.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListAkuisisi!=null){
                    if (dataListAkuisisi.size()>0){
                        int index = 0;
                        for (tAkuisisiHeader data : dataListAkuisisi){
                            index++;
                            String name = null;
                            try {
                                if (data.getIntDokterId()!=null){
                                    name = "Dokter" + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(data.getIntDokterId()).getTxtLastName();
                                }else if (data.getIntApotekID()!=null){
                                    name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            itemList.add(String.valueOf(index) + " - Akuisisi - " + name);
//                            if (data.getIntDokterId()!=null){
//                                itemList.add(String.valueOf(index)+ " - " + data.getIntDokterId());
//                            }else if (data.getIntApotekID()!=null){
//                                itemList.add(String.valueOf(index)+ " - " + data.getIntApotekID());
//                            }
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_akuisisi, tv_download_akuisisi, "tAkuisisiHeader");
            }
        });

        ln_download_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListMaintenance = (List<tMaintenanceHeader>) dtRepoMaintenanceHeader.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListMaintenance!=null){
                    if (dataListMaintenance.size()>0){
                        int index = 0;
                        for (tMaintenanceHeader data : dataListMaintenance){
                            index++;
                            String name = null;
                            try {
                                if (data.getIntActivityId()==1){
                                    name = "Dokter" + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(data.getIntDokterId()).getTxtLastName();
                                }else if (data.getIntActivityId()==2){
                                    name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            itemList.add(String.valueOf(index) + " - Maintenance - " + name);
//                            if (data.getIntDokterId()!=null){
//                                itemList.add(String.valueOf(index)+ " - " + data.getIntDokterId());
//                            }else if (data.getIntApotekID()!=null){
//                                itemList.add(String.valueOf(index)+ " - " + data.getIntApotekID());
//                            }
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_maintenance, tv_download_maintenance, "tMaintenanceHeader");
            }
        });

        ln_download_infoprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataLIstInfoProgram = (List<tInfoProgramHeader>) dtRepoInfoProgHeader.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataLIstInfoProgram!=null){
                    if (dataLIstInfoProgram.size()>0){
                        int index = 0;
                        for (tInfoProgramHeader data : dataLIstInfoProgram){
                            index++;
                            String name = null;
                            try {
                                if (data.getIntActivityId()==1){
                                    name = "Dokter" + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(data.getIntDokterId()).getTxtLastName();
                                }else if (data.getIntActivityId()==2){
                                    name = apotekRepo.findBytxtId(data.getIntApotekId()).getTxtName();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            itemList.add(String.valueOf(index) + " - Info Program - " + name);
//                            if (data.getIntDokterId()!=null){
//                                itemList.add(String.valueOf(index)+ " - " + data.getIntDokterId());
//                            }else if (data.getIntApotekId()!=null){
//                                itemList.add(String.valueOf(index)+ " - " + data.getIntApotekId());
//                            }
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_infoprogram, tv_download_infoprogram, "tInfoProgramHeader");
            }
        });

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
        boolean isDataReady = new clsMainBL().isDataReady(getContext());
        if (txtDownlaod.equals("mActivity")){
            downloadActivity();
        }else if (txtDownlaod.equals("mSubActivity")){
            try {
                dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dataListActivity.size()>0){
                downloadSubActivity();
            }else {
                ToastCustom.showToasty(getContext(),"Please download data activity",4);
            }

        } else if (txtDownlaod.equals("mSubSubActivity")){
            try {
                dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dataListSubActivity.size()>0){
                downloadSubSubActivity();
            }else {
                ToastCustom.showToasty(getContext(),"Please download data sub activity",4);
            }

        } else if (txtDownlaod.equals("mApotek")){
            downloadApotek(false);
        } else if (txtDownlaod.equals("mDokter")){
            downloadDokter(false);
        } else if (txtDownlaod.equals("mUserMappingArea")){
            downloadArea();
        }else{
            if (isDataReady){
                if (txtDownlaod.equals("tRealisasiVisitPlan")){
                    downloadtCallPlan();
                }else if (txtDownlaod.equals("tAkuisisiHeader")){
                    downloadtAkuisisi();
                }else if (txtDownlaod.equals("tMaintenanceHeader")){
                    downloadtMaintenace();
                }else if (txtDownlaod.equals("tInfoProgramHeader")){
                    downloadtInfoProgram();
                }
            }else {
                ToastCustom.showToasty(getContext(),"Please download all data master",4);
            }
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
                ToastCustom.showToasty(getContext(),message,4);
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                            if (model.getData().getDataMappingArea().getLtMappingArea()!=null){
                                if (model.getData().getDataMappingArea().getLtMappingArea().size()>0){
                                    dtRepoArea = new mUserMappingAreaRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataMappingArea().getLtMappingArea().size(); i++){
                                        mUserMappingArea data = new mUserMappingArea();
                                        data.setIntUserMappingAreaId(model.getData().getDataMappingArea().getLtMappingArea().get(i).getIntUserMappingAreaId());
                                        data.setIntUserId(model.getData().getDataMappingArea().getLtMappingArea().get(i).getIntUserId());
                                        data.setTxtKecamatanID(model.getData().getDataMappingArea().getLtMappingArea().get(i).getTxtKecamatanID());
                                        dtRepoArea.createOrUpdate(data);
                                    }
                                }
                                tv_count_branch.setText(String.valueOf(model.getData().getDataMappingArea().getLtMappingArea().size()));
                            }
                            if (model.getData().getDataActivity().getLtActivity()!=null){
                                if (model.getData().getDataActivity().getLtActivity().size()>0){
                                    dtActivityrepo = new mActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataActivity().getLtActivity().size(); i++){
                                        mActivity data = new mActivity();
                                        data.setIntActivityId(model.getData().getDataActivity().getLtActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getDataActivity().getLtActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataActivity().getLtActivity().get(i).getTxtDescription());
                                        dtActivityrepo.createOrUpdate(data);
                                    }
                                }
                                tv_count_download_activity.setText(String.valueOf(model.getData().getDataActivity().getLtActivity().size()));
                            }

                            if (model.getData().getDataSubActivity().getLtSubActivity()!=null){
                                if (model.getData().getDataSubActivity().getLtSubActivity().size()>0){
                                    dtRepoSubActivity = new mSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataSubActivity().getLtSubActivity().size(); i++){
                                        mSubActivity data = new mSubActivity();
                                        data.setIntSubActivityid(model.getData().getDataSubActivity().getLtSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityid(model.getData().getDataSubActivity().getLtSubActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getDataSubActivity().getLtSubActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataSubActivity().getLtSubActivity().get(i).getTxtDescription());
                                        dtRepoSubActivity.createOrUpdate(data);
                                    }
                                }
                                tv_count_download_subactivity.setText(String.valueOf(model.getData().getDataSubActivity().getLtSubActivity().size()));
                            }

                            if (model.getData().getDataSubActivityDetail().getLtSubActivityDetailData()!=null){
                                if (model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().size()>0){
                                    dtRepoSubSubActivity = new mSubSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().size(); i++){
                                        mSubSubActivity data = new mSubSubActivity();
                                        data.setIntSubActivityid(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getIntSubActivityId());
                                        data.setIntSubSubActivityid(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId());
                                        data.setTxtName(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getTxtDescription());
                                        data.setIntType(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getIntFlag());
                                        dtRepoSubSubActivity.createOrUpdate(data);
                                    }
                                }
                                tv_count_download_subsubactivity.setText(String.valueOf(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().size()));
                            }

                            if (model.getData().getDatatProgramVisitDetail().getTProgramVisit()!=null){
                                if (model.getData().getDatatProgramVisitDetail().getTProgramVisit().size()>0){
                                    dtRepoProgramVisit = new tProgramVisitRepo(getContext());
                                    for (int i = 0; i <model.getData().getDatatProgramVisitDetail().getTProgramVisit().size(); i++){
                                        tProgramVisit data = new tProgramVisit();
                                        data.setTxtProgramVisitId(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getTxtProgramVisitId());
                                        data.setIntUserId(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntRoleId());
                                        data.setDtStart(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getDtEnd()));
                                        data.setIntStatus(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntStatus());
                                        data.setIntType(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntType());
                                        data.setTxtNotes(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getTxtNotes());
                                        dtRepoProgramVisit.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity()!=null){
                                if (model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().size()>0){
                                    dtRepoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().size(); i++){
                                        tProgramVisitSubActivity data = new tProgramVisitSubActivity();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtProgramVisitId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtProgramVisitId());
                                        data.setTxtApotekId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtApotekName());
                                        data.setIntType(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getIntFlag());
                                        data.setTxtAreaId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtAreaId());
                                        data.setTxtAreaName(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtAreaName());
                                        data.setTxtDokterId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtDokterId());
                                        data.setTxtDokterName(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtDokterName());
                                        data.setIntSubActivityId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getIntActivityId());
                                        data.setDtStart(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getDtEnd()));
                                        data.setTxtNotes(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtDescription());
                                        dtRepoProgramVisitSubActivity.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getDatatProgramVisitDetail().getRealisasiData()!=null){
                                if (model.getData().getDatatProgramVisitDetail().getRealisasiData().size()>0){
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    for (int i = 0; i <model.getData().getDatatProgramVisitDetail().getRealisasiData().size(); i++){
                                        tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtRealisasiVisitId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                        data.setIntRoleID(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntRoleId());
                                        data.setTxtDokterId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtDokterId());
                                        data.setIntUserId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntUserId());
                                        data.setTxtDokterName(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtNamaDokter());
                                        data.setTxtApotekId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtNamaApotek());
                                        data.setDtCheckIn(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtCheckin()));
                                        data.setDtCheckOut(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtChekout()));
                                        data.setDtDateRealisasi(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtDateRealisasi()));
                                        data.setDtDatePlan(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtDatePlan()));
                                        data.setIntNumberRealisasi(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntRealisasiNumber());
                                        data.setTxtAcc(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtAccurasi());
                                        data.setTxtLong(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtLongitude());
                                        data.setTxtLat(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtLatitude());
                                        data.setTxtImgName1(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Name());
                                        data.setTxtImgName2(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Name());
                                        if (getLogoImage(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Path())!=null){
                                            data.setBlobImg1(getLogoImage(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Path()));
                                        }else {
                                            data.setBlobImg1(null);
                                        }
                                        if (getLogoImage(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Path())!=null){
                                            data.setBlobImg2(getLogoImage(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Path()));
                                        }else {
                                            data.setBlobImg2(null);
                                        }
                                        dtRepoRealisasi.createOrUpdate(data);
                                    }
                                }
                                tv_count_realisasi.setText(String.valueOf(model.getData().getDatatProgramVisitDetail().getRealisasiData().size()));
                            }

                            if (model.getData().getDataAkuisisiData().getAkuisisiHeader()!=null){
                                if (model.getData().getDataAkuisisiData().getAkuisisiHeader().size()>0){
                                    dtRepoAkuisisiHeader = new tAkuisisiHeaderRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataAkuisisiData().getAkuisisiHeader().size(); i++){
                                        tAkuisisiHeader data = new tAkuisisiHeader();
                                        data.setTxtHeaderId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTAkuisisiHeaderId());
                                        data.setIntSubSubActivityId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntSubDetailActivityId());
                                        data.setIntSubSubActivityTypeId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntFlag());
                                        data.setTxtNoDoc(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtNoDoc());
                                        data.setDtExpiredDate(parseDate(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getDtExpiredDate()));
                                        data.setIntUserId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtDokterId());
                                        data.setIntApotekID(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtApotekId());
                                        data.setIntAreaId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntAreaId());
//                                        data.setTxtRealisasiVisitId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntFlag());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        data.setIntFlagShow(new clsHardCode().Save);
                                        dtRepoAkuisisiHeader.createOrUpdate(data);
                                    }
                                }
                                tv_count_akuisisi.setText(String.valueOf(model.getData().getDataAkuisisiData().getAkuisisiHeader().size()));
                            }

                            if (model.getData().getDataAkuisisiData().getAkuisisiDetail()!=null){
                                if (model.getData().getDataAkuisisiData().getAkuisisiDetail().size()>0){
                                    dtRepoAkuisisiDetail = new tAkuisisiDetailRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataAkuisisiData().getAkuisisiDetail().size(); i ++){
                                        tAkuisisiDetail data = new tAkuisisiDetail();
                                        data.setTxtHeaderId(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtAkuisisiHeaderId());
                                        data.setTxtDetailId(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId());
                                        data.setTxtImgName(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtImageName());
                                        byte[] file = getLogoImage(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtLink());
                                        if (file!=null){
                                            data.setTxtImg(file);
                                        }else {
                                            data.setTxtImg(null);
                                        }
                                        dtRepoAkuisisiDetail.createOrUpdate(data);
                                    }
                                }
                            }

                            if (model.getData().getDataMaintenanceData().getLtMaintenanceHeader()!=null){
                                if (model.getData().getDataMaintenanceData().getLtMaintenanceHeader().size()>0){
                                 dtRepoMaintenanceHeader = new tMaintenanceHeaderRepo(getContext());
                                 for (int i = 0; i < model.getData().getDataMaintenanceData().getLtMaintenanceHeader().size(); i++){
                                     tMaintenanceHeader data = new tMaintenanceHeader();
                                     data.setTxtHeaderId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getTxtMaintenanceHeaderId());
                                     data.setTxtRealisasiVisitId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getTxtRealisasiVisitId());
                                     data.setIntActivityId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntActivityId());
                                     data.setIntUserId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntUserId());
                                     data.setIntRoleId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntRoleId());
                                     data.setIntDokterId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntDokterId());
                                     data.setIntApotekID(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntApotekId());
                                     data.setIntAreaId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntAreaId());
                                     data.setIntFlagPush(new clsHardCode().Sync);
                                     dtRepoMaintenanceHeader.createOrUpdate(data);
                                 }
                                }
                                tv_count_maintenance.setText(String.valueOf(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().size()));
                            }

                            if (model.getData().getDataMaintenanceData().getLtMaintenanceDetail()!=null){
                                if (model.getData().getDataMaintenanceData().getLtMaintenanceDetail().size()>0){
                                 dtRepoMaintenanceDetail = new tMaintenanceDetailRepo(getContext());
                                 for (int i = 0; i < model.getData().getDataMaintenanceData().getLtMaintenanceDetail().size(); i ++){
                                     tMaintenanceDetail data = new tMaintenanceDetail();
                                     data.setTxtDetailId(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtMaintenanceDetailId());
                                     data.setTxtHeaderId(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtMaintenanceHeaderId());
                                     data.setIntSubDetailActivityId(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getIntSubDetailActivityId());
                                     data.setTxtNoDoc(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtNoResep());
//                                     data.setTxtNoResep(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtNoResep());
//                                     data.setTxtNoOrder(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtNoOrder());
                                     dtRepoMaintenanceDetail.createOrUpdate(data);
                                 }
                                }
                            }

                            if (model.getData().getDataInfoProgram().getLtInfoHeader()!=null){
                                if (model.getData().getDataInfoProgram().getLtInfoHeader().size()>0){
                                    dtRepoInfoProgHeader = new tInfoProgramHeaderRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataInfoProgram().getLtInfoHeader().size(); i++){
                                        tInfoProgramHeader data = new tInfoProgramHeader();
                                        data.setTxtHeaderId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getTxtInfoProgramHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntDokterId());
                                        data.setIntApotekId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Draft);
                                        dtRepoInfoProgHeader.createOrUpdate(data);
                                    }
                                }
                                tv_count_infoprogram.setText(String.valueOf(model.getData().getDataInfoProgram().getLtInfoHeader().size()));
                            }

                            if (model.getData().getDataInfoProgram().getLtInfoDetail()!=null){
                                if (model.getData().getDataInfoProgram().getLtInfoDetail().size()>0){
                                    dtRepoInfoProgDetail = new tInfoProgramDetailRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataInfoProgram().getLtInfoDetail().size(); i++){
                                        tInfoProgramDetail data = new tInfoProgramDetail();
                                        data.setTxtHeaderId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtInfoProgramHeaderId());
                                        data.setTxtDetailId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
                                        data.setIntSubDetailActivityId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getIntSubDetailActivityId());
                                        data.setTxtFileName(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtFileName());
                                        byte[] file = getLogoImage(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtFileLinkEncrypt());
                                        if (file!=null){
                                            data.setBlobFile(file);
                                        }else {
                                            data.setBlobFile(null);
                                        }
                                        data.setBoolFlagChecklist(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).isBitCheck());
                                        data.setDtChecklist(parseDate(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getDtDateChecklist()));
                                        data.setDescription(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtDesc());;
                                        dtRepoInfoProgDetail.createOrUpdate(data);
                                    }
                                }
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
//
//                            ToastCustom.showToasty(getContext(),"Success Download",1);
//                            Intent myIntent = new Intent(getContext(), MainMenu.class);
//                            getActivity().finish();
//                            startActivity(myIntent);
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
                            checkMenu();
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
                            if (model.getData().getLtActivity()!=null){
                                if (model.getData().getLtActivity().size()>0){
                                    dtActivityrepo = new mActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtActivity().size(); i++){
                                        mActivity data = new mActivity();
                                        data.setIntActivityId(model.getData().getLtActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getLtActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getLtActivity().get(i).getTxtDescription());
                                        dtActivityrepo.createOrUpdate(data);
                                        itemList.add(String.valueOf(model.getData().getLtActivity().get(i).getIntActivityId()) + " - " + model.getData().getLtActivity().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_activity.setText(String.valueOf(model.getData().getLtActivity().size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
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
                                        mActivity activity = (mActivity) dtActivityrepo.findById(model.getData().getLtSubActivity().get(i).getIntActivityId());
                                        itemList.add(String.valueOf(1+i) + ". " + activity.getTxtName() + " - " + model.getData().getLtSubActivity().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_subactivity.setText(String.valueOf(model.getData().getLtSubActivity().size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
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

                                        mSubActivity _mSubActivity = (mSubActivity) dtRepoSubActivity.findById(data.getIntSubActivityid());
                                        mActivity _mActivity = (mActivity) dtActivityrepo.findById(_mSubActivity.getIntActivityid());
                                        itemList.add(String.valueOf(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId()) + ". " + _mActivity.getTxtName() + " - " + _mSubActivity.getTxtName() + " - " + model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
//                                        itemList.add(String.valueOf(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId()) + " - " + model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_subsubactivity.setText(String.valueOf(model.getData().getLtSubActivityDetailData().size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
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
                ToastCustom.showToasty(getContext(),message,4);
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                           checkMenu();
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
                ToastCustom.showToasty(getContext(),message,4);
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
                                        if (model.getData().get(i).getLastname()!=null){
                                            itemList.add(model.getData().get(i).getId() + " - " + model.getData().get(i).getFirstname() + " " + model.getData().get(i).getLastname());
                                        }else {
                                            itemList.add(model.getData().get(i).getId() + " - " + model.getData().get(i).getFirstname());
                                        }
                                    }
                                }
                                if (!isFromDownloadAll){
                                    dataAdapter.notifyDataSetChanged();
                                }
                                tv_count_dokter.setText(String.valueOf(model.getData().size()));
                                checkMenu();
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
                                        data.setDtStart(parseDate(model.getData().getTProgramVisit().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getTProgramVisit().get(i).getDtEnd()));
                                        data.setIntStatus(model.getData().getTProgramVisit().get(i).getIntStatus());
                                        data.setIntType(model.getData().getTProgramVisit().get(i).getIntType());
                                        data.setTxtNotes(model.getData().getTProgramVisit().get(i).getTxtNotes());
                                        dtRepoProgramVisit.createOrUpdate(data);
//                                        itemList.add(String.valueOf(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId()) + " - " + model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                    }
                                }
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
                                        data.setIntType(model.getData().getTProgramVisitSubActivity().get(i).getIntFlag());
                                        data.setTxtAreaId(model.getData().getTProgramVisitSubActivity().get(i).getTxtAreaId());
                                        data.setTxtAreaName(model.getData().getTProgramVisitSubActivity().get(i).getTxtAreaName());
                                        data.setTxtDokterId(model.getData().getTProgramVisitSubActivity().get(i).getTxtDokterId());
                                        data.setTxtDokterName(model.getData().getTProgramVisitSubActivity().get(i).getTxtDokterName());
                                        data.setIntSubActivityId(model.getData().getTProgramVisitSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityId(model.getData().getTProgramVisitSubActivity().get(i).getIntActivityId());
                                        data.setDtStart(parseDate(model.getData().getTProgramVisitSubActivity().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getTProgramVisitSubActivity().get(i).getDtEnd()));
                                        data.setTxtNotes(model.getData().getTProgramVisitSubActivity().get(i).getTxtDescription());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoProgramVisitSubActivity.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getRealisasiData()!=null){
                                if (model.getData().getRealisasiData().size()>0){
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    for (int i = 0; i <model.getData().getRealisasiData().size(); i++){
                                        tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getRealisasiData().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtRealisasiVisitId(model.getData().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                        data.setIntRoleID(model.getData().getRealisasiData().get(i).getIntRoleId());
                                        data.setTxtDokterId(model.getData().getRealisasiData().get(i).getTxtDokterId());
                                        data.setIntUserId(model.getData().getRealisasiData().get(i).getIntUserId());
                                        data.setTxtDokterName(model.getData().getRealisasiData().get(i).getTxtNamaDokter());
                                        data.setTxtApotekId(model.getData().getRealisasiData().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getRealisasiData().get(i).getTxtNamaApotek());
                                        data.setDtCheckIn(parseDate(model.getData().getRealisasiData().get(i).getDtCheckin()));
                                        data.setDtCheckOut(parseDate(model.getData().getRealisasiData().get(i).getDtChekout()));
                                        data.setDtDateRealisasi(parseDate(model.getData().getRealisasiData().get(i).getDtDateRealisasi()));
                                        data.setDtDatePlan(parseDate(model.getData().getRealisasiData().get(i).getDtDatePlan()));
                                        data.setIntNumberRealisasi(model.getData().getRealisasiData().get(i).getIntRealisasiNumber());
                                        data.setTxtAcc(model.getData().getRealisasiData().get(i).getTxtAccurasi());
                                        data.setTxtLong(model.getData().getRealisasiData().get(i).getTxtLongitude());
                                        data.setTxtLat(model.getData().getRealisasiData().get(i).getTxtLatitude());
                                        data.setTxtImgName1(model.getData().getRealisasiData().get(i).getTxtImage1Name());
                                        data.setTxtImgName2(model.getData().getRealisasiData().get(i).getTxtImage2Name());
                                        if (getLogoImage(model.getData().getRealisasiData().get(i).getTxtImage1Path())!=null){
                                            data.setBlobImg1(getLogoImage(model.getData().getRealisasiData().get(i).getTxtImage1Path()));
                                        }
                                        if (getLogoImage(model.getData().getRealisasiData().get(i).getTxtImage2Path())!=null){
                                            data.setBlobImg2(getLogoImage(model.getData().getRealisasiData().get(i).getTxtImage2Path()));
                                        }
                                        dtRepoRealisasi.createOrUpdate(data);
                                    }
                                }
                                dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>) dtRepoProgramVisitSubActivity.findAll();
//                                dataListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
                                int index = 0;
                                for (tProgramVisitSubActivity data : dataListProgramVisitSubActivity){
                                    index++;
                                    mActivity activity = (mActivity) dtActivityrepo.findById(data.getIntActivityId());
                                    if (data.getIntActivityId()==1){
                                        itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtDokterName());
                                    }else if (data.getIntActivityId()==2){
                                        itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtApotekName());
                                    }else {
                                        itemList.add(String.valueOf(index)+ " - " + activity.getTxtName());
                                    }

                                }
                                tv_count_realisasi.setText(String.valueOf(dataListProgramVisitSubActivity.size()));
                                dataAdapter.notifyDataSetChanged();
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

    private void downloadtAkuisisi() {
        String strLinkAPI = new clsHardCode().linkAkuisisi;
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
                        DownloadtAkuisisi model = gson.fromJson(jsonObject.toString(), DownloadtAkuisisi.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getAkuisisiHeader()!=null){
                                if (model.getData().getAkuisisiHeader().size()>0){
                                    dtRepoAkuisisiHeader = new tAkuisisiHeaderRepo(getContext());
                                    int index = 0;
                                    for (int i = 0; i <model.getData().getAkuisisiHeader().size(); i++){
                                        index++;
                                        tAkuisisiHeader data = new tAkuisisiHeader();
                                        data.setTxtHeaderId(model.getData().getAkuisisiHeader().get(i).getTAkuisisiHeaderId());
                                        data.setIntSubSubActivityId(model.getData().getAkuisisiHeader().get(i).getIntSubDetailActivityId());
                                        data.setIntSubSubActivityTypeId(model.getData().getAkuisisiHeader().get(i).getIntFlag());
                                        data.setTxtNoDoc(model.getData().getAkuisisiHeader().get(i).getTxtNoDoc());
                                        data.setDtExpiredDate(model.getData().getAkuisisiHeader().get(i).getDtExpiredDate());
                                        data.setIntUserId(model.getData().getAkuisisiHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getAkuisisiHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getAkuisisiHeader().get(i).getTxtDokterId());
                                        data.setIntApotekID(model.getData().getAkuisisiHeader().get(i).getTxtApotekId());
                                        data.setIntAreaId(model.getData().getAkuisisiHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Sync);
//                                        data.setTxtRealisasiVisitId(model.getData().getAkuisisiHeader().get(i).txr);
                                        data.setIntFlagShow(new clsHardCode().Save);
                                        dtRepoAkuisisiHeader.createOrUpdate(data);
                                        String name ="";
                                        if (model.getData().getAkuisisiHeader().get(i).getTxtDokterId()!=null){
                                            name = "Dokter" + dokterRepo.findBytxtId(model.getData().getAkuisisiHeader().get(i).getTxtDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(model.getData().getAkuisisiHeader().get(i).getTxtDokterId()).getTxtLastName();
                                        }else if (model.getData().getAkuisisiHeader().get(i).getTxtApotekId()!=null){
                                            name = apotekRepo.findBytxtId(model.getData().getAkuisisiHeader().get(i).getTxtApotekId()).getTxtName();
                                        }
                                        itemList.add(String.valueOf(index) + " - Akuisisi - " + name);
                                    }
                                    tv_count_akuisisi.setText(String.valueOf(model.getData().getAkuisisiHeader().size()));
                                }
                                dataAdapter.notifyDataSetChanged();

                                if (model.getData().getAkuisisiDetail()!=null){
                                    if (model.getData().getAkuisisiDetail().size()>0){
                                        dtRepoAkuisisiDetail = new tAkuisisiDetailRepo(getContext());
                                        for (int i = 0; i < model.getData().getAkuisisiDetail().size(); i++){
                                            tAkuisisiDetail data = new tAkuisisiDetail();
                                            data.setTxtHeaderId(model.getData().getAkuisisiDetail().get(i).getTxtAkuisisiHeaderId());
                                            data.setTxtDetailId(model.getData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId());
                                            data.setTxtImgName(model.getData().getAkuisisiDetail().get(i).getTxtImageName());
                                            if (getLogoImage(model.getData().getAkuisisiDetail().get(i).getTxtImagePath())!=null){
                                                data.setTxtImg(getLogoImage(model.getData().getAkuisisiDetail().get(i).getTxtImagePath()));
                                            }
                                            dtRepoAkuisisiDetail.createOrUpdate(data);
                                        }
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

    private void downloadtMaintenace() {
        String strLinkAPI = new clsHardCode().linkMaintenance;
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
                        DownloadtMaintenance model = gson.fromJson(jsonObject.toString(), DownloadtMaintenance.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtMaintenanceHeader()!=null){
                                if (model.getData().getLtMaintenanceHeader().size()>0){
                                    dtRepoMaintenanceHeader = new tMaintenanceHeaderRepo(getContext());
                                    int index = 0;
                                    for (int i = 0; i <model.getData().getLtMaintenanceHeader().size(); i++){
                                        index++;
                                        tMaintenanceHeader data = new tMaintenanceHeader();
                                        data.setTxtHeaderId(model.getData().getLtMaintenanceHeader().get(i).getTxtMaintenanceHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getLtMaintenanceHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getLtMaintenanceHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getLtMaintenanceHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getLtMaintenanceHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getLtMaintenanceHeader().get(i).getIntDokterId());
                                        data.setIntApotekID(model.getData().getLtMaintenanceHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getLtMaintenanceHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoMaintenanceHeader.createOrUpdate(data);
                                        String name ="";
                                        if (model.getData().getLtMaintenanceHeader().get(i).getIntActivityId()==1){
                                            name = "Dokter" + dokterRepo.findBytxtId(model.getData().getLtMaintenanceHeader().get(i).getIntDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(model.getData().getLtMaintenanceHeader().get(i).getIntDokterId()).getTxtLastName();
                                        }else {
                                            name = apotekRepo.findBytxtId(model.getData().getLtMaintenanceHeader().get(i).getIntApotekId()).getTxtName();
                                        }
                                        itemList.add(String.valueOf(index) + " - Maintenace - " + name);
//                                        itemList.add(String.valueOf(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId()) + " - " + model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                    }
                                    tv_count_maintenance.setText(String.valueOf(model.getData().getLtMaintenanceHeader().size()));
                                }
                                dataAdapter.notifyDataSetChanged();

                                if (model.getData().getLtMaintenanceDetail()!=null){
                                    if (model.getData().getLtMaintenanceDetail().size()>0){
                                        dtRepoMaintenanceDetail = new tMaintenanceDetailRepo(getContext());
                                        for (int i = 0; i < model.getData().getLtMaintenanceDetail().size(); i++){
                                            tMaintenanceDetail data = new tMaintenanceDetail();
                                            data.setTxtHeaderId(model.getData().getLtMaintenanceDetail().get(i).getTxtMaintenanceHeaderId());
                                            data.setTxtDetailId(model.getData().getLtMaintenanceDetail().get(i).getTxtMaintenanceDetailId());
                                            data.setIntSubDetailActivityId(model.getData().getLtMaintenanceDetail().get(i).getIntSubDetailActivityId());
                                            data.setTxtNoDoc(model.getData().getLtMaintenanceDetail().get(i).getTxtNoResep());
//                                            data.setTxtNoResep(model.getData().getLtMaintenanceDetail().get(i).getTxtNoResep());
//                                            data.setTxtNoOrder(model.getData().getLtMaintenanceDetail().get(i).getTxtNoOrder());
                                            dtRepoMaintenanceDetail.createOrUpdate(data);
                                        }
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
    private void downloadtInfoProgram() {
        String strLinkAPI = new clsHardCode().linkInfoProgram;
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
                        DownloadtInfoProgram model = gson.fromJson(jsonObject.toString(), DownloadtInfoProgram.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtInfoHeader()!=null){
                                if (model.getData().getLtInfoHeader().size()>0){
                                    dtRepoInfoProgHeader = new tInfoProgramHeaderRepo(getContext());
                                    int index = 0;
                                    for (int i = 0; i <model.getData().getLtInfoHeader().size(); i++){
                                        index++;
                                        tInfoProgramHeader data = new tInfoProgramHeader();
                                        data.setTxtHeaderId(model.getData().getLtInfoHeader().get(i).getTxtInfoProgramHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getLtInfoHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getLtInfoHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getLtInfoHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getLtInfoHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getLtInfoHeader().get(i).getIntDokterId());
                                        data.setIntApotekId(model.getData().getLtInfoHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getLtInfoHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Draft);
                                        dtRepoInfoProgHeader.createOrUpdate(data);
                                        String name ="";
                                        if (model.getData().getLtInfoHeader().get(i).getIntActivityId()==1){
                                            name = "Dokter" + dokterRepo.findBytxtId(model.getData().getLtInfoHeader().get(i).getIntDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(model.getData().getLtInfoHeader().get(i).getIntDokterId()).getTxtLastName();
                                        }else {
                                            name = apotekRepo.findBytxtId(model.getData().getLtInfoHeader().get(i).getIntApotekId()).getTxtName();
                                        }
                                        itemList.add(String.valueOf(index) + " - Info Program - " + name);
                                    }
                                    tv_count_infoprogram.setText(String.valueOf(model.getData().getLtInfoHeader().size()));
                                }
                                dataAdapter.notifyDataSetChanged();

                                if (model.getData().getLtInfoDetail()!=null){
                                    if (model.getData().getLtInfoDetail().size()>0){
                                        dtRepoInfoProgDetail = new tInfoProgramDetailRepo(getContext());
                                        for (int i = 0; i < model.getData().getLtInfoDetail().size(); i++){
                                            tInfoProgramDetail data = new tInfoProgramDetail();
                                            data.setTxtHeaderId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramHeaderId());
                                            data.setTxtDetailId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
                                            data.setIntSubDetailActivityId(model.getData().getLtInfoDetail().get(i).getIntSubDetailActivityId());
                                            data.setTxtFileName(model.getData().getLtInfoDetail().get(i).getTxtFileName());
                                            data.setBoolFlagChecklist(model.getData().getLtInfoDetail().get(i).isBitCheck());
                                            data.setDtChecklist(parseDate(model.getData().getLtInfoDetail().get(i).getDtDateChecklist()));
                                            if (getLogoImage(model.getData().getLtInfoDetail().get(i).getTxtFileLinkEncrypt())!=null){
                                                data.setBlobFile(getLogoImage(model.getData().getLtInfoDetail().get(i).getTxtFileLinkEncrypt()));
                                            }else {
                                                data.setBlobFile(null);
                                            }
                                            data.setDescription(model.getData().getLtInfoDetail().get(i).getTxtDesc());
                                            dtRepoInfoProgDetail.createOrUpdate(data);
                                        }
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

    private String parseDate(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return null;
        }
    }

    private byte[] getLogoImage(String url) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            String contentType = ucon.getHeaderField("Content-Type");
            boolean image = contentType.startsWith("image/");
            boolean text = contentType.startsWith("application/");

            if (image || text) {
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayBuffer baf = new ByteArrayBuffer(500);
                int current;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }

                return baf.toByteArray();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

    private void checkMenu(){
        boolean isDataReady = new clsMainBL().isDataReady(getContext());
        if (isDataReady){
            ToastCustom.showToasty(getContext(),"Success Download",1);
            Intent myIntent = new Intent(getContext(), MainMenu.class);
            getActivity().finish();
            startActivity(myIntent);
        }
    }
}