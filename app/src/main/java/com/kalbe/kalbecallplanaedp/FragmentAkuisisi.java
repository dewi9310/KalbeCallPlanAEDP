package com.kalbe.kalbecallplanaedp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalbe.kalbecallplanaedp.BL.clsActivity;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
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
import com.kalbe.mobiledevknlibs.InputFilter.InputFilters;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    tAkuisisiHeaderRepo headerRepo;
    String txtUserName;
    int IntSubSubActivityid;
    String txtSubSubActivity;
    Dialog dialogCustom;

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
        headerRepo = new tAkuisisiHeaderRepo(getContext());
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
                if (_mSubSubActivity.get(i).getTxtName().equals(txtSubSubActivity))
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
                if (_mSubSubActivity.get(i).getIntType()==new clsHardCode().TypeText){
                    IntSubSubActivityid = _mSubSubActivity.get(i).getIntSubSubActivityid();
                    txtSubSubActivity = _mSubSubActivity.get(i).getTxtName();
                    showCustomDialog();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString(SUB_SUB_ACTIVITY, _mSubSubActivity.get(i).getTxtName());
                    Tools.intentFragmentSetArgument(FragmentAddAkuisisi.class, "Add Akuisisi", getContext(), bundle);
                }
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

    private void showCustomDialog() {
         dialogCustom = new Dialog(getActivity());
        dialogCustom.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogCustom.setContentView(R.layout.dialog_checkout);
        dialogCustom.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogCustom.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tv_title = (TextView)dialogCustom.findViewById(R.id.cd_title);
        TextView tv_subtitle = (TextView)dialogCustom.findViewById(R.id.cd_subtitle);
        tv_title.setText("Registration");
        tv_subtitle.setText("Please fill username which you want to use");
        final EditText et_userName = (EditText) dialogCustom.findViewById(R.id.et_int_number_realisasi);
        et_userName.setHint("NICE.PEOPLE");
//        et_userName.setInputType(InputType.TYPE_CLASS_TEXT);
        char[] chars = {'.'};
        InputFilters.etCapsTextWatcherNoSpaceAtFirst(et_userName, null, chars);
        ((AppCompatButton) dialogCustom.findViewById(R.id.btn_cancel_realisasi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustom.dismiss();
            }
        });

        ((AppCompatButton) dialogCustom.findViewById(R.id.btn_submit_realisasi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUserName = et_userName.getText().toString().trim();
                if (txtUserName.equals("")) {
                    ToastCustom.showToasty(getContext(),"Please fill username which you want to use...",4);
                } else {
                    saveData();
                }
            }
        });

        dialogCustom.show();
        dialogCustom.getWindow().setAttributes(lp);
    }

    public void saveData(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setTitle("Registration");
        builder.setMessage("Are you sure to use that username?");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                try {
                    mUserLogin dtLogin = new clsMainBL().getUserLogin(getContext());

                    DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    tAkuisisiHeader dt = new tAkuisisiHeader();
                    dt.setTxtHeaderId(new clsActivity().GenerateGuid());
                    dt.setDtExpiredDate(null);
                    dt.setTxtNoDoc("");
                    dt.setTxtUserName(txtUserName);
                    dt.setIntFlagPush(new clsHardCode().Save);
                    dt.setIntSubSubActivityId(IntSubSubActivityid);
                    dt.setIntUserId(dtLogin.getIntUserID());
                    dt.setIntRoleId(dtLogin.getIntRoleID());
                    dt.setIntAreaId(dataPlan.getTxtAreaId());
                    if (dataPlan.getIntActivityId()==1){
                        dt.setIntDokterId(dtCheckinActive.getTxtDokterId());
                    }else if (dataPlan.getIntActivityId()==2){
                        dt.setIntApotekID(dtCheckinActive.getTxtApotekId());
                    }
                    dt.setTxtRealisasiVisitId(dtCheckinActive.getTxtRealisasiVisitId());
                    dt.setIntFlagShow(new clsHardCode().Save);
                    dt.setIntSubSubActivityTypeId(new clsHardCode().TypeText);
                    headerRepo.createOrUpdate(dt);
                    ToastCustom.showToasty(getContext(), "Saved", 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialogCustom.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString(SUB_SUB_ACTIVITY, txtSubSubActivity);
                Tools.intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), bundle);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
