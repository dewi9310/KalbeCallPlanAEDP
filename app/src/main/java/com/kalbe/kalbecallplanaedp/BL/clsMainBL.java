package com.kalbe.kalbecallplanaedp.BL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;


import com.kalbe.kalbecallplanaedp.Common.clsStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.enumStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
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
}
