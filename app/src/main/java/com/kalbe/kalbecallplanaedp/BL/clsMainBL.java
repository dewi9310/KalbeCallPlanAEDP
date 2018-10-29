package com.kalbe.kalbecallplanaedp.BL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;


import com.kalbe.kalbecallplanaedp.Common.clsStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserMappingArea;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.enumStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mTypeSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserMappingAreaRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.mobiledevknlibs.library.swipemenu.bean.SwipeMenu;
import com.kalbe.mobiledevknlibs.library.swipemenu.bean.SwipeMenuItem;
import com.kalbe.mobiledevknlibs.library.swipemenu.interfaces.SwipeMenuCreator;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class clsMainBL {
    public clsStatusMenuStart checkUserActive(Context context) throws ParseException {
        mUserLoginRepo login = new mUserLoginRepo(context);
        clsStatusMenuStart _clsStatusMenuStart =new clsStatusMenuStart();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String now = dateFormat.format(cal.getTime()).toString();
//        if(repo.CheckLoginNow()){
        List<mUserLogin> listDataLogin = new ArrayList<>();
        try {
            listDataLogin = (List<mUserLogin>) login.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        if (listDataLogin!=null)
        for (mUserLogin data : listDataLogin){
            if (!data.getTxtUserName().equals(null)){
                _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.UserActiveLogin);
            }
        }

        return _clsStatusMenuStart;
    }

    public mUserLogin getUserLogin(Context context){
        List <mUserLogin> dtList = new ArrayList<>();
        mUserLoginRepo dtRepo= new mUserLoginRepo(context);
        try {
            dtList = (List<mUserLogin>) dtRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtList.get(0);
    }

    public tRealisasiVisitPlan getDataCheckinActive(Context context){
        tRealisasiVisitPlan data = null;
        tRealisasiVisitPlanRepo absenRepo= new tRealisasiVisitPlanRepo(context);
        data = (tRealisasiVisitPlan) absenRepo.getDataCheckinActive();
        return data;
    }

    public boolean isDataReady(Context context){
     boolean valid = false;
        mActivityRepo dtActivityrepo = new mActivityRepo(context);
        mSubActivityRepo dtRepoSubActivity= new mSubActivityRepo(context);
        mSubSubActivityRepo dtRepoSubSubActivity= new mSubSubActivityRepo(context);
        mDokterRepo dokterRepo = new mDokterRepo(context);
        mApotekRepo apotekRepo = new mApotekRepo(context);
        mUserMappingAreaRepo dtRepoArea = new mUserMappingAreaRepo(context);

        List<mActivity> dataListActivity = new ArrayList<>();
        List<mSubActivity> dataListSubActivity = new ArrayList<>();
        List<mSubSubActivity> dataListSubSubActivity = new ArrayList<>();
        List<mApotek> dataListApotek = new ArrayList<>();
        List<mDokter> dataListDokter = new ArrayList<>();
        List<mUserMappingArea> dataListArea = new ArrayList<>();

        try {
            dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
            dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
            dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
            dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
            dataListApotek = (List<mApotek>) apotekRepo.findAll();
            dataListDokter = (List<mDokter>) dokterRepo.findAll();
            if (dataListArea.size()>0 && dataListActivity.size()>0 && dataListSubActivity.size()>0 && dataListSubSubActivity.size()>0 && dataListApotek.size()>0 && dataListDokter.size()>0){
                valid = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

     return valid;
    }
}
