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

import com.kalbe.kalbecallplanaedp.Utils.CustomTablayout;
import com.kalbe.kalbecallplanaedp.Utils.CustomViewPager;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_akuisisi, container, false);

//        viewPager = (ViewPager) v.findViewById(R.id.view_pager_akuisisi);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_akuisisi);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_akuisisi);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);

        NamaTab.add("KTP/SIM");
        NamaTab.add("SIP");
        NamaTab.add("STR");
        NamaTab.add("KKI Online");

        MapTab.put("KTP/SIM", 1);
        MapTab.put("SIP", 2);
        MapTab.put("STR", 3);
        MapTab.put("KKI Online", 4);

        setupViewPager(customViewPager);
        tabLayout.setupWithViewPager(customViewPager);
//        customViewPager.setOffscreenPageLimit(NamaTab.size());
        customViewPager.setPagingEnabled(false);
        allotEachTabWithEqualWidth();
//        tabLayout.setTabNumbers(NamaTab.size());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String txtSubSubActivity = bundle.getString(SUB_SUB_ACTIVITY);
            for (int i=0; i<NamaTab.size(); i++){
                if (NamaTab.get(i)==txtSubSubActivity)
                    customViewPager.setCurrentItem(i);
            }

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int iterator = customViewPager.getCurrentItem();
                Bundle bundle = new Bundle();
                bundle.putString(SUB_SUB_ACTIVITY, NamaTab.get(iterator));
                Tools.intentFragmentSetArgument(FragmentAddAkuisisi.class, "Add Akuisisi", getContext(), bundle);
//                showDialogFullScreen();
            }
        });

        return v;
    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        for (int i =0; i < NamaTab.size(); i++){
            adapter.addFragment(new FragmentSubAkuisisi(NamaTab.get(i), MapTab.get(NamaTab.get(i))), NamaTab.get(i));
        }

//        adapter.addFragment(new FragmentSubAkuisisi("testing2", "testing222"), "testing222");
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
