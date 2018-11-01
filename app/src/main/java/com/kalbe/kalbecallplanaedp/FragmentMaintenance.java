package com.kalbe.kalbecallplanaedp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Utils.CustomViewPager;
import com.kalbe.kalbecallplanaedp.Utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/29/2018.
 * Edited by ROC on 01/11/2018
 */

public class FragmentMaintenance extends Fragment{
    View v;
    private CustomViewPager customViewPager;
    private FloatingActionButton fab;
    TabLayout tabLayout;
    private String MAINTENANCE = "Maintenance";
    public static final int DIALOG_QUEST_CODE = 2018;
    public List<String> NamaTab = new ArrayList<>();
//    public HashMap<String, Integer> MapTab = new HashMap<>();
//    mSubActivity _mSubActivity;
//    List<mSubSubActivity> _mSubSubActivity;
//    mActivity _mActivity;
//    mSubSubActivityRepo subSubActivityRepo;
//    mSubActivityRepo subActivityRepo;
//    mActivityRepo activityRepo;
    tRealisasiVisitPlan dtCheckinActive;
//    tProgramVisitSubActivity dataPlan;
//    tAkuisisiHeader dtHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_maintenance, container, false);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_akuisisi);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_akuisisi);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        dtCheckinActive = new clsMainBL().getDataCheckinActive(getContext());
        setupViewPager(customViewPager, fab);
        customViewPager.setPagingEnabled(true);
        allotEachTabWithEqualWidth();
        tabLayout.setupWithViewPager(customViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int i = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = customViewPager.getCurrentItem();
                Bundle bundle = new Bundle();
                bundle.putString(MAINTENANCE, "Name");
                Tools.intentFragmentSetArgument(FragmentAddAkuisisi.class, "Add Akuisisi", getContext(), bundle);
            }
        });


        return v;
    }
    private void setupViewPager(ViewPager viewPager, FloatingActionButton fab){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentNResep(fab), "No Resep");
        adapter.addFragment(new FragmentNOrder(fab), "No Order");

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
