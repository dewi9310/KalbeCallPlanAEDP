package com.kalbe.kalbecallplanaedp;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Fragment.FragmentHistory;
import com.kalbe.kalbecallplanaedp.Fragment.FragmentListCallPlan;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class HomeFragment extends Fragment {
    View v;
    private Toolbar toolbar;
    private LinearLayout ln_plan_home, ln_realisasi_home, ln_unplan_home;
    TextView tv_plan_home, tv_unplan_home, tvRealisasi_home, tv_userName, tv_email;
    tRealisasiVisitPlanRepo repoRealisasi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ln_plan_home = (LinearLayout)v.findViewById(R.id.ln_plan_home);
        ln_realisasi_home = (LinearLayout) v.findViewById(R.id.ln_realisasi_home);
//        ln_unplan_home = (LinearLayout) v.findViewById(R.id.ln_unplan_home);
        tv_plan_home = (TextView)v.findViewById(R.id.tv_plan_home);
//        tv_unplan_home = (TextView)v.findViewById(R.id.tv_unplan_home);
        tv_userName = (TextView)v.findViewById(R.id.tv_user_name_home);
        tv_email = (TextView)v.findViewById(R.id.tv_email_home);

        tvRealisasi_home = (TextView)v.findViewById(R.id.tv_Realisasi_home);

        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());

        mUserLogin dtLogin = new clsMainBL().getUserLogin(getContext());
        tRealisasiVisitPlan dataCheckinActive = (tRealisasiVisitPlan) repoRealisasi.getDataCheckinActive();
        List<tRealisasiVisitPlan> listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllRealisasi();
        List<tRealisasiVisitPlan> listPlan = (List<tRealisasiVisitPlan>) repoRealisasi.getAllPlan();
        if (listRealisasi!=null){
            tvRealisasi_home.setText(String.valueOf(listRealisasi.size()));
        }

        if (listPlan!=null){
            tv_plan_home.setText(String.valueOf(listPlan.size()));
        }

        if (dataCheckinActive!=null){
            tv_userName.setText(dtLogin.getTxtUserName().toUpperCase() + " - Active");
        }else {
            tv_userName.setText(dtLogin.getTxtUserName().toUpperCase() + " - Inactive");
        }

        tv_email.setText(dtLogin.getTxtEmail());

        ln_plan_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Tools().intentFragment(FragmentListCallPlan.class, "Call Plan", getContext());
            }
        });

        ln_realisasi_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Tools().intentFragment(FragmentHistory.class, "History", getContext());
//                Toast.makeText(getContext(), "ini realisasi", Toast.LENGTH_SHORT).show();
            }
        });

//        ln_unplan_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "ini Unplan", Toast.LENGTH_SHORT).show();
//            }
//        });
        tv_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new clsHardCode().copydb(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                                Toast.makeText(getContext(), "Haiii", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

}
