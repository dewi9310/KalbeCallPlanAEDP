package com.kalbe.kalbecallplanaedp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentPushData extends Fragment{
    View v;

    private TableLayout tablePushData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_push_data, container, false);
        tablePushData = (TableLayout) v.findViewById(R.id.tablePushData);
        ListData();

        return v;
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
