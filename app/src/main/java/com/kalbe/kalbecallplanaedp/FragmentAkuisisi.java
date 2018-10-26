package com.kalbe.kalbecallplanaedp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Utils.CustomTablayout;
import com.kalbe.kalbecallplanaedp.Utils.CustomViewPager;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

public class FragmentAkuisisi extends Fragment{
    View v;
    private CustomViewPager customViewPager;
    private FloatingActionButton fab;
    TabLayout tabLayout;
    private String SUB_SUB_ACTIVITY = "sub sub activity";
    public static final int DIALOG_QUEST_CODE = 2018;
    public List<String> NamaTab = new ArrayList<>();
    public HashMap<String, Integer> MapTab = new HashMap<>();
    mSubActivity _mSubActivity;
    List<mSubSubActivity> _mSubSubActivity;
    mActivity _mActivity;
    mSubSubActivityRepo subSubActivityRepo;
    mSubActivityRepo subActivityRepo;
    mActivityRepo activityRepo;
    tRealisasiVisitPlan dtCheckinActive;
    tProgramVisitSubActivity dataPlan;
    tAkuisisiHeader dtHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_akuisisi, container, false);

//        viewPager = (ViewPager) v.findViewById(R.id.view_pager_akuisisi);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_akuisisi);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_akuisisi);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        dtCheckinActive = new clsMainBL().getDataCheckinActive(getContext());
        try {
            dataPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dtCheckinActive.getTxtProgramVisitSubActivityId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        subSubActivityRepo = new mSubSubActivityRepo(getContext());
        try {
            if (dataPlan.getIntActivityId()==1){
                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(1);
            }else if (dataPlan.getIntActivityId()==2){
                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (_mSubSubActivity!=null&&_mSubSubActivity.size()>0){

        }

        setupViewPager(customViewPager, fab);
        tabLayout.setupWithViewPager(customViewPager);
//        customViewPager.setOffscreenPageLimit(NamaTab.size());
        customViewPager.setPagingEnabled(false);
        allotEachTabWithEqualWidth();
//        tabLayout.setTabNumbers(NamaTab.size());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String txtSubSubActivity = bundle.getString(SUB_SUB_ACTIVITY);
            for (int i=0; i<_mSubSubActivity.size(); i++){
                if (_mSubSubActivity.get(i).getTxtName()==txtSubSubActivity)
                    customViewPager.setCurrentItem(i);
            }

        }
        final int iterator = customViewPager.getCurrentItem();
        if (iterator==0){
            try {
                if (dataPlan.getIntActivityId()==1){
                    dtHeader = (tAkuisisiHeader) new tAkuisisiHeaderRepo(getContext()).findBySubSubIdAndDokterId(_mSubSubActivity.get(0).getIntSubSubActivityid(), dtCheckinActive.getTxtDokterId(), new clsHardCode().Save);
                }else if (dataPlan.getIntActivityId()==2){
                    dtHeader = (tAkuisisiHeader) new tAkuisisiHeaderRepo(getContext()).findBySubSubIdAndApotekId(_mSubSubActivity.get(0).getIntSubSubActivityid(), dtCheckinActive.getTxtApotekId(), new clsHardCode().Save);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (dtHeader!=null){
                fab.setVisibility(View.GONE);
            }else {
                fab.setVisibility(View.VISIBLE);
            }
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int i = tab.getPosition();

                try {
                    if (dataPlan.getIntActivityId()==1){
                        dtHeader = (tAkuisisiHeader) new tAkuisisiHeaderRepo(getContext()).findBySubSubIdAndDokterId(_mSubSubActivity.get(i).getIntSubSubActivityid(), dtCheckinActive.getTxtDokterId(), new clsHardCode().Save);
                    }else if (dataPlan.getIntActivityId()==2){
                        dtHeader = (tAkuisisiHeader) new tAkuisisiHeaderRepo(getContext()).findBySubSubIdAndApotekId(_mSubSubActivity.get(i).getIntSubSubActivityid(), dtCheckinActive.getTxtApotekId(), new clsHardCode().Save);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dtHeader!=null){
                    fab.setVisibility(View.GONE);
                }else {
                    fab.setVisibility(View.VISIBLE);
                }
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
                bundle.putString(SUB_SUB_ACTIVITY, _mSubSubActivity.get(i).getTxtName());
                Tools.intentFragmentSetArgument(FragmentAddAkuisisi.class, "Add Akuisisi", getContext(), bundle);
            }
        });

        return v;
    }
    private void setupViewPager(ViewPager viewPager, FloatingActionButton fab){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        tAkuisisiHeader dtHeader = null;
        for (int i =0; i < _mSubSubActivity.size(); i++){
            try {
                if (dataPlan.getIntActivityId()==1){
                    dtHeader = (tAkuisisiHeader) new tAkuisisiHeaderRepo(getContext()).findBySubSubIdAndDokterId(_mSubSubActivity.get(i).getIntSubSubActivityid(), dtCheckinActive.getTxtDokterId(), new clsHardCode().Save);
                }else if (dataPlan.getIntActivityId()==2){
                    dtHeader = (tAkuisisiHeader) new tAkuisisiHeaderRepo(getContext()).findBySubSubIdAndApotekId(_mSubSubActivity.get(i).getIntSubSubActivityid(), dtCheckinActive.getTxtApotekId(), new clsHardCode().Save);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            adapter.addFragment(new FragmentSubAkuisisi(dtHeader, _mSubSubActivity.get(i).getIntType(), fab), _mSubSubActivity.get(i).getTxtName());
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

    private class ViewPagerAdapter extends FragmentPagerAdapter{

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
