package com.kalbe.kalbecallplanaedp;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.Common.clsPushData;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.VolleyUtils;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.responsePushData.ResponsePushData;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentPushData extends Fragment{
    View v;

    private TableLayout tablePushData;
    Button button_push_data;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_push_data, container, false);
        tablePushData = (TableLayout) v.findViewById(R.id.tablePushData);
        button_push_data = (Button)v.findViewById(R.id.button_push_data);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        ListData();
        button_push_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    private void pushData() throws JSONException {
        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsPushData dtJson = new clsHelperBL().pushData(versionName, getContext());
        if (dtJson == null){
        }else {
            String linkPushData = new clsHardCode().linkPushData;
            new VolleyUtils().makeJsonObjectRequestPushData(getContext(), linkPushData, dtJson, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response!=null){
                        JSONObject jsonObject = new JSONObject();
                        ResponsePushData model = gson.fromJson(jsonObject.toString(), ResponsePushData.class);
                        boolean isStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethod = model.getResult().getMethodName();
                        if (isStatus==true){

                        }

                    }
                }
            });
            /*new clsHelperBL().volleyRequestSendData(MainMenu.this, linkPushData, dtJson, new VolleyResponseListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {

                }
            });*/
        }
    }
    private void ListData(){
        tablePushData.removeAllViews();
//        clsMobile_trVisitPlan_Detail _clsMobile_trVisitPlan_Detail=new clsMobile_trVisitPlan_Detail();
//        List<clsMobile_trVisitPlan_Detail> ListOfclsMobile_trVisitPlan_Detail=new Select().from(clsMobile_trVisitPlan_Detail.class).where(_clsMobile_trVisitPlan_Detail.txtConstintSubmit+"=2").execute();
        final TableRow row = new TableRow(getContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);
        params.setMargins(1, 1, 1, 1);
        TableRow tr = new TableRow(getContext());
        String[] colTextHeader = {"No.", "Plan Date", "Category", "Desc"};
        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#81C784"));
            tv.setTextColor(Color.WHITE);
            tv.setLayoutParams(params);

            tr.addView(tv);
        }
        tablePushData.addView(tr,0);

//        if(ListOfclsMobile_trVisitPlan_Detail.size()>0){
//            int index = 1;
//            for (clsMobile_trVisitPlan_Detail _DtDetail:ListOfclsMobile_trVisitPlan_Detail) {
//                tr = new TableRow(getContext());
//                TextView tv_index = new TextView(getContext());
//                tv_index.setTextSize(12);
//                tv_index.setPadding(10, 10, 10, 10);
//                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                tv_index.setTextColor(Color.BLACK);
//                tv_index.setGravity(Gravity.CENTER);
//                tv_index.setText(String.valueOf(index + "."));
//                tv_index.setLayoutParams(params);
//
//                tr.addView(tv_index);
//
//                TextView outlet_code = new TextView(getContext());
//                outlet_code.setTextSize(12);
//                outlet_code.setPadding(10, 10, 10, 10);
//                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                outlet_code.setTextColor(Color.BLACK);
//                outlet_code.setGravity(Gravity.CENTER);
//                String txtPlanDate=_DtDetail.dtPlanDate;
//                if(txtPlanDate==null){
//                    txtPlanDate="Unplan";
//                }
//                outlet_code.setText(txtPlanDate);
//                outlet_code.setLayoutParams(params);
//
//                tr.addView(outlet_code);
//
//                TextView outlet_name = new TextView(getContext());
//                outlet_name.setTextSize(12);
//                outlet_name.setPadding(10, 10, 10, 10);
//                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                outlet_name.setTextColor(Color.BLACK);
//                outlet_name.setGravity(Gravity.CENTER);
//                clsMobile_mVisitPlanCategory _dtclsMobile_mVisitPlanCategory=new clsMobile_mVisitPlanCategory();
//                List<clsMobile_mVisitPlanCategory> ListOfDataclsMobile_mVisitPlanCategory=new Select().from(clsMobile_mVisitPlanCategory.class).where(_dtclsMobile_mVisitPlanCategory.txtConstintCategoryID+"=?",_DtDetail.intCategoryID).execute();
//                if(ListOfDataclsMobile_mVisitPlanCategory.size()>0){
//                    outlet_name.setText(ListOfDataclsMobile_mVisitPlanCategory.get(0).txtCategoryName);
//                }else {
//                    outlet_name.setText("-");
//                }
//                outlet_name.setLayoutParams(params);
//
//                tr.addView(outlet_name);
//
//                TextView date = new TextView(getContext());
//                date.setTextSize(12);
//                date.setPadding(10, 10, 10, 10);
//                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
//                date.setTextColor(Color.BLACK);
//                date.setGravity(Gravity.CENTER);
//                date.setText(_DtDetail.txtDescription);
//                date.setLayoutParams(params);
//
//                tr.addView(date);
//
//                tablePushData.addView(tr,index++);
//            }
//        }
    }
}
