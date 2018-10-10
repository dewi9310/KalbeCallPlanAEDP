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

import com.kalbe.kalbecallplanaedp.Utils.CustomViewPager;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

public class FragmentAkuisisi extends Fragment{
    View v;
    private ViewPager viewPager;
    private CustomViewPager customViewPager;
    private FloatingActionButton fab;
    TabLayout tabLayout;
    public static final int DIALOG_QUEST_CODE = 2018;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_akuisisi, container, false);

//        viewPager = (ViewPager) v.findViewById(R.id.view_pager_akuisisi);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_akuisisi);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_akuisisi);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        setupViewPager(customViewPager);
        tabLayout.setupWithViewPager(customViewPager);
        customViewPager.setOffscreenPageLimit(2);
        customViewPager.setPagingEnabled(false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFullScreen();
            }
        });

        return v;
    }

    private void showDialogFullScreen(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentAddAkuisisi newFragment = new FragmentAddAkuisisi();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallBackResult(new FragmentAddAkuisisi.CallBackResult() {
            @Override
            public void sendResult(int requestCode, Object obj) {
                if (requestCode==DIALOG_QUEST_CODE){

                }
            }
        });
    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentSubAkuisisi("testing", "testing"), "testing");
        adapter.addFragment(new FragmentSubAkuisisi("testing2", "testing222"), "testing222");
        viewPager.setAdapter(adapter);
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
