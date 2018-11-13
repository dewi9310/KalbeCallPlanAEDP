package com.kalbe.kalbecallplanaedp.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
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
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.Utils.CustomViewPager;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.InputFilter.InputFilters;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

public class FragementMaintenance extends Fragment implements IOBackPressed{
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
    private FloatingActionButton fab;
    tMaintenanceHeaderRepo headerRepo;
    tMaintenanceDetailRepo detailRepo;
    private String SUB_SUB_ACTIVITY = "sub sub activity";
    int IntSubSubActivityid;
    String txtSubSubActivity;
    Dialog dialogCustom;
    String txtNoDoc;
    private String DT_CALL_PLAN = "Realisasi id";
    String txtRealisasiId;
    boolean valid = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_maintenance, container, false);

        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_maintenance);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout_maintenance);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_maintenance);

        Bundle data = this.getArguments();
        if (data != null) {
            txtRealisasiId = data.getString(DT_CALL_PLAN);
        }
        if (txtRealisasiId!=null && !txtRealisasiId.equals("")){
            valid = true;
        }
        subSubActivityRepo = new mSubSubActivityRepo(getContext());
        headerRepo = new tMaintenanceHeaderRepo(getContext());
        detailRepo = new tMaintenanceDetailRepo(getContext());
        if (valid){
            try {
                _mSubSubActivity  = (List<mSubSubActivity>) headerRepo.getIntSubDetailActivityId(txtRealisasiId);
                dtCheckinActive = new tRealisasiVisitPlanRepo(getContext()).findBytxtId(txtRealisasiId);
                dataPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dtCheckinActive.getTxtProgramVisitSubActivityId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            fab.setVisibility(View.GONE);
        }else {
            dtCheckinActive = new clsMainBL().getDataCheckinActive(getContext());
            try {
                dataPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dtCheckinActive.getTxtProgramVisitSubActivityId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            subSubActivityRepo = new mSubSubActivityRepo(getContext());
            try {
                if (dataPlan.getIntActivityId()== new clsHardCode().VisitDokter){
                    _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(2);
                }else if (dataPlan.getIntActivityId()==new clsHardCode().VisitApotek){
                    _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(5);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        setupViewPager(customViewPager);
        tabLayout.setupWithViewPager(customViewPager);
        allotEachTabWithEqualWidth();
        customViewPager.setCurrentItem(0);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String txtSubSubActivity = bundle.getString(SUB_SUB_ACTIVITY);
            for (int i=0; i<_mSubSubActivity.size(); i++){
                if (_mSubSubActivity.get(i).getTxtName().equals(txtSubSubActivity))
                    customViewPager.setCurrentItem(i);
            }

        }

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = customViewPager.getCurrentItem();
                IntSubSubActivityid = _mSubSubActivity.get(i).getIntSubSubActivityid();
                txtSubSubActivity = _mSubSubActivity.get(i).getTxtName();
                showCustomDialog();
            }
        });
        return v;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        tMaintenanceHeader dtHeader = null;
        List<String> listId = new ArrayList<>();
        for (int i =0; i < _mSubSubActivity.size(); i++){
            try {
                if (valid){
                    listId.add(new tMaintenanceHeaderRepo(getContext()).findByRealisasiId(txtRealisasiId).getTxtHeaderId());
                    if (dataPlan.getIntActivityId()==new clsHardCode().VisitDokter){
                        dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtDokterId(),dataPlan.getIntActivityId());
                    }else if (dataPlan.getIntActivityId()==new clsHardCode().VisitApotek){
                        dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtApotekId(), dataPlan.getIntActivityId());
                    }
                }else {
                    if (dataPlan.getIntActivityId()==new clsHardCode().VisitDokter){
                        listId = new tMaintenanceHeaderRepo(getContext()).findIdByOutletId(dtCheckinActive.getTxtDokterId(),dataPlan.getIntActivityId());
                        dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtDokterId(),dataPlan.getIntActivityId());
                    }else if (dataPlan.getIntActivityId()==new clsHardCode().VisitApotek){
                        listId = new tMaintenanceHeaderRepo(getContext()).findIdByOutletId(dtCheckinActive.getTxtApotekId(),dataPlan.getIntActivityId());
                        dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtApotekId(), dataPlan.getIntActivityId());
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            adapter.addFragment(new FragmentSubMaintenance(listId, dtHeader, _mSubSubActivity.get(i).getIntSubSubActivityid(), i), _mSubSubActivity.get(i).getTxtName());
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
        tv_title.setText(txtSubSubActivity);
        tv_subtitle.setText("Please fill number document");
        final EditText et_userName = (EditText) dialogCustom.findViewById(R.id.et_int_number_realisasi);
        et_userName.setHint("AA.2018.07");
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
                txtNoDoc = et_userName.getText().toString().trim();
                if (txtNoDoc.equals("")) {
                    ToastCustom.showToasty(getContext(),"Please fill number document...",4);
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

        builder.setTitle(txtSubSubActivity);
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                tMaintenanceHeader dtHeader = null;
                try {
                    mUserLogin dtLogin = new clsMainBL().getUserLogin(getContext());
//                    if (dataPlan.getIntActivityId()==1){
//                        dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtDokterId(),dataPlan.getIntActivityId());
//                    }else if (dataPlan.getIntActivityId()==2){
//                        dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtApotekId(), dataPlan.getIntActivityId());
//                    }

                    dtHeader = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByRealisasiId(dtCheckinActive.getTxtRealisasiVisitId());
                    if (dtHeader==null){
                        tMaintenanceHeader dt = new tMaintenanceHeader();
                        dt.setTxtHeaderId(new clsActivity().GenerateGuid());
                        dt.setTxtRealisasiVisitId(dtCheckinActive.getTxtRealisasiVisitId());
                        dt.setIntActivityId(dataPlan.getIntActivityId());
                        dt.setIntUserId(dtLogin.getIntUserID());
                        dt.setIntRoleId(dtLogin.getIntRoleID());
                        dt.setIntAreaId(dataPlan.getTxtAreaId());
                        if (dataPlan.getIntActivityId()==1){
                            dt.setIntDokterId(dtCheckinActive.getTxtDokterId());
                        }else if (dataPlan.getIntActivityId()==2){
                            dt.setIntApotekID(dtCheckinActive.getTxtApotekId());
                        }
                        dt.setIntFlagPush(new clsHardCode().Save);
                        headerRepo.createOrUpdate(dt);
                        dtHeader = dt;
                    }else {
                        tMaintenanceHeader dt = dtHeader;
                        dt.setIntFlagPush(new clsHardCode().Save);
                        headerRepo.createOrUpdate(dt);
//                        if (dtHeader.getTxtRealisasiVisitId().equals(dtCheckinActive.getTxtRealisasiVisitId())){
//
//                        }else {
//                            tMaintenanceHeader dt = new tMaintenanceHeader();
//                            dt.setTxtHeaderId(new clsActivity().GenerateGuid());
//                            dt.setTxtRealisasiVisitId(dtCheckinActive.getTxtRealisasiVisitId());
//                            dt.setIntActivityId(dataPlan.getIntActivityId());
//                            dt.setIntUserId(dtLogin.getIntUserID());
//                            dt.setIntRoleId(dtLogin.getIntRoleID());
//                            dt.setIntAreaId(dataPlan.getTxtAreaId());
//                            if (dataPlan.getIntActivityId()==1){
//                                dt.setIntDokterId(dtCheckinActive.getTxtDokterId());
//                            }else if (dataPlan.getIntActivityId()==2){
//                                dt.setIntApotekID(dtCheckinActive.getTxtApotekId());
//                            }
//                            dt.setIntFlagPush(new clsHardCode().Save);
//                            headerRepo.createOrUpdate(dt);
//                            dtHeader = dt;
//                        }
                    }
                    tMaintenanceDetail detail = new tMaintenanceDetail();
                    detail.setTxtDetailId(new clsActivity().GenerateGuid());
                    detail.setTxtHeaderId(dtHeader.getTxtHeaderId());
                    detail.setIntSubDetailActivityId(IntSubSubActivityid);
                    detail.setTxtNoDoc(txtNoDoc);
                    detailRepo.createOrUpdate(detail);

                    ToastCustom.showToasty(getContext(), "Saved", 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialogCustom.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString(SUB_SUB_ACTIVITY, txtSubSubActivity);
                Tools.intentFragmentSetArgument(FragementMaintenance.class, "Maintenance", getContext(), bundle);
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

    @Override
    public boolean onBackPressed() {
        if (valid){
            Tools.intentFragment(FragmentHistory.class, "History", getContext());
            return true;
        }else {
            return false;
        }
    }
}
