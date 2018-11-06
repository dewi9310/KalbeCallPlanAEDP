package com.kalbe.kalbecallplanaedp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Utils.CustomViewPager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

public class FragementInfoProgram extends Fragment {
    View v;
    private CustomViewPager customViewPager;
    TabLayout tabLayout;
    List<mSubSubActivity> _mSubSubActivity;
    mActivity _mActivity;
    mSubSubActivityRepo subSubActivityRepo;
    mSubActivityRepo subActivityRepo;
    mActivityRepo activityRepo;
    tRealisasiVisitPlan dtCheckinActive;
    tProgramVisitSubActivity dataPlan;
    tInfoProgramHeader dtHeader;
//    tInfoProgramDetail

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_infoprogram, container, false);

        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_InfoProgram);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout_infoprogram);
        dtCheckinActive = new clsMainBL().getDataCheckinActive(getContext());
        try {
            dataPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dtCheckinActive.getTxtProgramVisitSubActivityId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (dataPlan.getIntActivityId()==new clsHardCode().VisitDokter){
                dtHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtDokterId(),dataPlan.getIntActivityId());
            }else if (dataPlan.getIntActivityId()==new clsHardCode().VisitApotek){
                dtHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtApotekId(), dataPlan.getIntActivityId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        subSubActivityRepo = new mSubSubActivityRepo(getContext());
        try {
            if (dataPlan.getIntActivityId()==1){
                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(3);
            }else if (dataPlan.getIntActivityId()==2){
                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(6);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setupViewPager(customViewPager);
        tabLayout.setupWithViewPager(customViewPager);
        allotEachTabWithEqualWidth();
        customViewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                customViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        for (int i =0; i < _mSubSubActivity.size(); i++){
            adapter.addFragment(new FragmentSubInfoProgram(dtHeader, _mSubSubActivity.get(i).getIntSubSubActivityid(), i), _mSubSubActivity.get(i).getTxtName());
        }

        viewPager.setAdapter(adapter);
    }

    private void allotEachTabWithEqualWidth() {

        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = slidingTabStrip.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = 1;
            tab.setLayoutParams(layoutParams);
        }

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
