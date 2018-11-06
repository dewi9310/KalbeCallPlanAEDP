package com.kalbe.kalbecallplanaedp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalbe.kalbecallplanaedp.BL.clsActivity;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserMappingArea;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserMappingAreaRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

public class FragmentAddUnplan extends Fragment implements IOBackPressed{
    View v;
    AppCompatSpinner spnArea, spnActivity, spnOutlet;
    TextView tvOultet;
    EditText etDesc, etOutlet;
    CheckBox cbOutlet;
    Button btnCreate;
    LinearLayout lnOutlet;
    public List<String> listArea = new ArrayList<>();
    public HashMap<String, String> mapArea = new HashMap<>();
    public List<String> listActivity = new ArrayList<>();
    public HashMap<String, Integer> mapActivity = new HashMap<>();
    public List<String> listOutlet = new ArrayList<>();
    public HashMap<String, String> mapOutlet = new HashMap<>();
    mActivityRepo activityRepo;
    List<mUserMappingArea> listDtArea = new ArrayList<>();
    mUserMappingAreaRepo areaRepo;
    List<mActivity> listdtActivity = new ArrayList<>();
    mDokterRepo dokterRepo;
    mApotekRepo apotekRepo;
    ArrayAdapter<String> spinnerAdapterOutlet;
    tProgramVisitSubActivityRepo visitSubActivityRepo;
    tProgramVisit visitHeader;
    tProgramVisitRepo visitRepo;
    tRealisasiVisitPlanRepo realisasiVisitPlanRepo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_unplan, container, false);
        spnArea = (AppCompatSpinner)v.findViewById(R.id.spnArea_unplan);
        spnActivity = (AppCompatSpinner)v.findViewById(R.id.spnActivity_add_unplan);
        spnOutlet = (AppCompatSpinner)v.findViewById(R.id.spn_outlet_unplan);
        tvOultet = (TextView)v.findViewById(R.id.tv_outlet_unplan);
        etDesc = (EditText)v.findViewById(R.id.et_desc_unplan);
        etOutlet = (EditText)v.findViewById(R.id.etOutlet_unplan);
        cbOutlet = (CheckBox)v.findViewById(R.id.cb_outlet_unplan);
        btnCreate = (Button)v.findViewById(R.id.button_add_unplan);
        lnOutlet = (LinearLayout)v.findViewById(R.id.ln_cb_unplan);

        areaRepo = new mUserMappingAreaRepo(getContext());
        activityRepo = new mActivityRepo(getContext());
        apotekRepo = new mApotekRepo(getContext());
        dokterRepo = new mDokterRepo(getContext());
        visitSubActivityRepo = new tProgramVisitSubActivityRepo(getContext());
        visitRepo = new tProgramVisitRepo(getContext());
        realisasiVisitPlanRepo = new tRealisasiVisitPlanRepo(getContext());

        try {
            visitHeader = (tProgramVisit) visitRepo.findAll().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        listArea.clear();
        listActivity.clear();
        listOutlet.clear();
        listArea.add("Select One");
        listActivity.add("Select One");
        listOutlet.add("Select One");
        mapActivity.put("Select One", 0);
        mapOutlet.put("Select One", "-");
        spnActivity.setEnabled(false);
        spnOutlet.setEnabled(false);
        cbOutlet.setEnabled(false);
        lnOutlet.setVisibility(View.GONE);
        spnOutlet.setVisibility(View.GONE);

        try {
            listDtArea = (List<mUserMappingArea>) areaRepo.findAll();
            if (listDtArea!=null&&listDtArea.size()>0){
                for (int i = 0; i <listDtArea.size(); i++){
                    listArea.add(listDtArea.get(i).getTxtKecamatanID());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initializing an ArrayAdapter with initial text like select one
        final ArrayAdapter<String> spinnerAdapterArea = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item, listArea){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.green_200));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.green_400));
                }
                return view;
            }
        };
        spnArea.setAdapter(spinnerAdapterArea);

        // attaching data adapter to spinner
        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view;
                if (tv!=null){
                    if(i == 0){
                        // Set the hint text color gray
                        tv.setTextColor(getResources().getColor(R.color.green_200));
                    }
                    else {
                        tv.setTextColor(getResources().getColor(R.color.green_400));
                    }
                }

//                tv.setTextColor(getResources().getColor(R.color.green_300));
                if (i!=0){
                    onAreaSelected();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ToastCustom.showToasty(getActivity(),"Please select Area",4);
                // put code here
            }
        });

        // Initializing an ArrayAdapter with initial text like select one
        final ArrayAdapter<String> spinnerAdapterActivity = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item, listActivity){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.green_200));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.green_400));
                }
                return view;
            }
        };
        spnActivity.setAdapter(spinnerAdapterActivity);

        // attaching data adapter to spinner
        spnActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view;
                if (tv!=null){
                    if(i == 0){
                        // Set the hint text color gray
                        tv.setTextColor(getResources().getColor(R.color.green_200));
                    }
                    else {
                        tv.setTextColor(getResources().getColor(R.color.green_400));
                    }
                }
//                tv.setTextColor(getResources().getColor(R.color.green_300));
                if (i!=0){
                    onActivitySelected(mapActivity.get(spnActivity.getSelectedItem().toString()), spnArea.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ToastCustom.showToasty(getActivity(),"Please select Activity",4);
                // put code here
            }
        });

        // Initializing an ArrayAdapter with initial text like select one
         spinnerAdapterOutlet = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item, listOutlet){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.green_200));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.green_400));
                }
                return view;
            }
        };
        spnOutlet.setAdapter(spinnerAdapterOutlet);

        // attaching data adapter to spinner
        spnOutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tv = (TextView)view;
                if (tv!=null){
                    if(i == 0){
                        // Set the hint text color gray
                        tv.setTextColor(getResources().getColor(R.color.green_200));
                    }
                    else {
                        tv.setTextColor(getResources().getColor(R.color.green_400));
                    }
                }
//                if (tv!=null)
//                    tv.setTextColor(getResources().getColor(R.color.green_300));
//                    onActivitySelected(mapActivity.get(spnActivity.getSelectedItem().toString()), spnArea.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ToastCustom.showToasty(getActivity(),"Please select Activity",4);
                // put code here
            }
        });
        cbOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbOutlet.isChecked()==true){
                    spnOutlet.setVisibility(View.GONE);
                    etOutlet.setVisibility(View.VISIBLE);
                }else {
                    etOutlet.setVisibility(View.GONE);
                    spnOutlet.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure?");

                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        saveData();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        return v;
    }

    private void onAreaSelected(){
        try {
            listdtActivity = (List<mActivity>) activityRepo.findAll();
            if (listdtActivity!=null&&listdtActivity.size()>0){
                for (int i = 0; i <listdtActivity.size(); i++){
                    listActivity.add(listdtActivity.get(i).getTxtName());
                    mapActivity.put(listdtActivity.get(i).getTxtName(), listdtActivity.get(i).getIntActivityId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        spnActivity.setEnabled(true);
    }

    private void onActivitySelected(int intActivityId, String txtAreaId){
        if (intActivityId==1){
            tvOultet.setText("DOCTOR NAME");
            try {
                List<mDokter> dokterList = ( List<mDokter>)dokterRepo.findAll();
                if (dokterList!=null&&dokterList.size()>0){
                    for (int i = 0; i < dokterList.size(); i++){
                        if (dokterList.get(i).getTxtLastName()!=null){
                            listOutlet.add(dokterList.get(i).getTxtFirstName() + " " + dokterList.get(i).getTxtLastName());
                            mapOutlet.put(dokterList.get(i).getTxtFirstName() + " " + dokterList.get(i).getTxtLastName(), dokterList.get(i).getTxtId());
                        }else {
                            listOutlet.add(dokterList.get(i).getTxtFirstName());
                            mapOutlet.put(dokterList.get(i).getTxtFirstName(), dokterList.get(i).getTxtId());
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            lnOutlet.setVisibility(View.VISIBLE);
            cbOutlet.setEnabled(true);
            spnOutlet.setVisibility(View.VISIBLE);
            spnOutlet.setEnabled(true);
            spinnerAdapterOutlet.notifyDataSetChanged();
            spnOutlet.setAdapter(spinnerAdapterOutlet);
        }else if (intActivityId==2){
            tvOultet.setText("PHARMACY NAME");
            try {
                List<mApotek> apotekList = (List<mApotek>) apotekRepo.findAll();
                if (apotekList!=null&&apotekList.size()>0){
                    for (mApotek data : apotekList){
                        listOutlet.add(data.getTxtName());
                        mapOutlet.put(data.getTxtName(), data.getTxtCode());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            lnOutlet.setVisibility(View.VISIBLE);
            cbOutlet.setEnabled(true);
            spnOutlet.setVisibility(View.VISIBLE);
            spnOutlet.setEnabled(true);
            spinnerAdapterOutlet.notifyDataSetChanged();
            spnOutlet.setAdapter(spinnerAdapterOutlet);
        }else {
            lnOutlet.setVisibility(View.GONE);
            cbOutlet.setEnabled(false);
            spnOutlet.setVisibility(View.GONE);
            spnOutlet.setEnabled(false);
            etOutlet.setVisibility(View.GONE);
        }
        spnArea.setEnabled(false);
    }

    private void saveData(){
        boolean valid = true;
        String msg = "";
        if (spnArea.getSelectedItem().toString().equals("Select One")){
            valid = false;
            msg = "Please select Area";
//            ToastCustom.showToasty(getContext(), "Please select Area", 4);
        }else if (spnActivity.getSelectedItem().toString().equals("Select One")){
            valid = false;
            msg = "Please select Activity";
//            ToastCustom.showToasty(getContext(), "Please select Activity", 4);
        }else if (mapActivity.get(spnActivity.getSelectedItem())==1){
            if (cbOutlet.isChecked()){
                if (etOutlet.getText().toString().equals("")){
                    valid = false;
                    msg = "Please fill name of Doctor";
//                    ToastCustom.showToasty(getContext(), "Please fill name of Doctor", 4);
                }
            }else {
                if (spnOutlet.getSelectedItem().toString().equals("Select One")){
                    valid = false;
                    msg = "Please select Doctor";
//                    ToastCustom.showToasty(getContext(), "Please select Doctor", 4);
                }
            }
        }else if (mapActivity.get(spnActivity.getSelectedItem())==2){
            if (cbOutlet.isChecked()){
                if (etOutlet.getText().toString().equals("")){
                    valid = false;
                    msg = "Please fill name of Pharmacy";
//                    ToastCustom.showToasty(getContext(), "Please fill name of Pharmacy", 4);
                }
            }else {
                if (spnOutlet.getSelectedItem().toString().equals("Select One")){
                    valid = false;
                    msg = "Please select Pharmacy";
//                    ToastCustom.showToasty(getContext(), "Please select Pharmacy", 4);
                }
            }
        }

        if (valid){
            try {
                mUserLogin dtUserLogin = new clsMainBL().getUserLogin(getContext());
                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();

                tProgramVisitSubActivity dataPlan = new tProgramVisitSubActivity();
                dataPlan.setTxtProgramVisitSubActivityId(new clsActivity().GenerateGuid());
                if (mapActivity.get(spnActivity.getSelectedItem()) == 1) {
                    dataPlan.setTxtDokterId(mapOutlet.get(spnOutlet.getSelectedItem()));
                    dataPlan.setTxtDokterName(spnOutlet.getSelectedItem().toString());
                }else if (mapActivity.get(spnActivity.getSelectedItem())==2){
                    dataPlan.setTxtApotekId(mapOutlet.get(spnOutlet.getSelectedItem()));
                    dataPlan.setTxtApotekName(spnOutlet.getSelectedItem().toString());
                }
                dataPlan.setIntType(new clsHardCode().UnPlan);
                dataPlan.setTxtAreaName(spnArea.getSelectedItem().toString());
                dataPlan.setTxtNotes(etDesc.getText().toString());
                dataPlan.setTxtProgramVisitId(visitHeader.getTxtProgramVisitId());
                dataPlan.setIntActivityId(mapActivity.get(spnActivity.getSelectedItem()));
                dataPlan.setTxtAreaId(spnArea.getSelectedItem().toString());
                dataPlan.setDtStart(dateTimeFormat.format(cal.getTime()));
                dataPlan.setDtEnd(dateTimeFormat.format(cal.getTime()));
                dataPlan.setIntFlagPush(new clsHardCode().Save);
                visitSubActivityRepo.createOrUpdate(dataPlan);

                tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                data.setTxtRealisasiVisitId(new clsActivity().GenerateGuid());
                data.setTxtProgramVisitSubActivityId(dataPlan.getTxtProgramVisitSubActivityId());
                data.setIntUserId(dtUserLogin.getIntUserID());
                data.setIntRoleID(dtUserLogin.intRoleID);
                if (mapActivity.get(spnActivity.getSelectedItem()) == 1) {
                    if (cbOutlet.isChecked()){
                        data.setTxtDokterName(etOutlet.getText().toString());
                        data.setTxtDokterId(etOutlet.getText().toString());
                    }else {
                        data.setTxtDokterId(mapOutlet.get(spnOutlet.getSelectedItem()));
                        data.setTxtDokterName(spnOutlet.getSelectedItem().toString());
                    }

                }else if (mapActivity.get(spnActivity.getSelectedItem())==2){
                    if (cbOutlet.isChecked()){
                        data.setTxtApotekId(etOutlet.getText().toString());
                        data.setTxtApotekName(etOutlet.getText().toString());
                    }else {
                        data.setTxtApotekId(mapOutlet.get(spnOutlet.getSelectedItem()));
                        data.setTxtApotekName(spnOutlet.getSelectedItem().toString());
                    }
                }
                data.setDtCheckIn("");
                data.setDtCheckOut("");
                data.setDtDateRealisasi("");
                data.setDtDatePlan(dateFormat.format(cal.getTime()));///tanggal login
                data.setIntNumberRealisasi(0); //generate number
                data.setTxtAcc("");
                data.setTxtLat("");
                data.setTxtLong("");
                data.setTxtImgName1("");
                data.setBlobImg1(null);
                data.setTxtImgName2("");
                data.setBlobImg2(null);
                data.setIntStatusRealisasi(new clsHardCode().VisitPlan);
                data.setIntFlagPush(new clsHardCode().Save);
                realisasiVisitPlanRepo.createOrUpdate(data);

                ToastCustom.showToasty(getContext(),"Save",1);
                Tools.intentFragment(FragmentListCallPlan.class, "Call Plan", getContext());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            ToastCustom.showToasty(getContext(), msg, 4);
        }
    }
    @Override
    public boolean onBackPressed() {
        Tools.intentFragment(FragmentListCallPlan.class, "Call Plan", getContext());
        return true;
    }
}
