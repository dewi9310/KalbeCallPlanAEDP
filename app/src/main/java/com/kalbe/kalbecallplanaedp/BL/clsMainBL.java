package com.kalbe.kalbecallplanaedp.BL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;


import com.kalbe.kalbecallplanaedp.Common.clsLogin;
import com.kalbe.kalbecallplanaedp.Common.clsStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.clsLoginRepo;
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
        List<mUserLogin> listDataLogin = null;
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
    public static SwipeMenuCreator setCreator(final Context _ctx, final Map<String, HashMap> map) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                HashMap<String, String> map2 = new HashMap<String, String>();

                for (int i = 0; i < map.size(); i++) {
                    map2 = map.get(String.valueOf(i));

                    // create "open" item
                    SwipeMenuItem menuItem = new SwipeMenuItem(_ctx);
                    // set item background
                    menuItem.setBackground(new ColorDrawable(Color.parseColor(map2.get("bgColor"))));
                    // set item width
                    menuItem.setWidth(dp2px(_ctx, 90));
                    // set item title

                    if (map2.get("name") == "View") {
                        int icon = R.drawable.ic_view;
                        menuItem.setIcon(icon);
                    } else if (map2.get("name") == "Edit") {
                        int icon = R.drawable.ic_edit;
                        menuItem.setIcon(icon);
                    } else if (map2.get("name") == "Delete") {
                        int icon = R.drawable.ic_delete;
                        menuItem.setIcon(icon);
                    }
                    // add to menu
                    menu.addMenuItem(menuItem);
                }
                // create "delete" item
                // SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                // deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                // deleteItem.setWidth(dp2px(90));
                // set a icon
                // deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                // menu.addMenuItem(deleteItem);
            }
        };

        return creator;

    }

    private static int dp2px(Context _ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, _ctx.getResources().getDisplayMetrics());
    }
}
