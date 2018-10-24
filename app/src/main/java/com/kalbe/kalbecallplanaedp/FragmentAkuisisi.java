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

import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_akuisisi, container, false);

//        viewPager = (ViewPager) v.findViewById(R.id.view_pager_akuisisi);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_akuisisi);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_akuisisi);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);

        subSubActivityRepo = new mSubSubActivityRepo(getContext());
        try {
            _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (_mSubSubActivity!=null&&_mSubSubActivity.size()>0){

        }

        setupViewPager(customViewPager);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int iterator = customViewPager.getCurrentItem();
                Bundle bundle = new Bundle();
                bundle.putString(SUB_SUB_ACTIVITY, _mSubSubActivity.get(iterator).getTxtName());
                Tools.intentFragmentSetArgument(FragmentAddAkuisisi.class, "Add Akuisisi", getContext(), bundle);
            }
        });

        return v;
    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for (int i =0; i < _mSubSubActivity.size(); i++){
            adapter.addFragment(new FragmentSubAkuisisi(_mSubSubActivity.get(i).getTxtName(), _mSubSubActivity.get(i).getIntSubSubActivityid(), _mSubSubActivity.get(i).getIntType()), _mSubSubActivity.get(i).getTxtName());
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
