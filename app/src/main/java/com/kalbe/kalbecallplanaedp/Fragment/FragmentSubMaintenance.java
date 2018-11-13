package com.kalbe.kalbecallplanaedp.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsInfoProgram;
import com.kalbe.kalbecallplanaedp.Model.clsMaintenance;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListInfoProgram;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListMaintenance;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubMaintenance extends Fragment {
    View v;
    tMaintenanceHeader header;
    tMaintenanceHeaderRepo headerRepo;
    List<tMaintenanceDetail> listDetail;
    List<String> listHeaderId;
    tMaintenanceDetailRepo detailRepo;
    int intSubSubActivity;
    AdapterListMaintenance adapter;
    ListView listView;
    private static List<clsMaintenance> itemAdapterList0 = new ArrayList<>();
    private static List<clsMaintenance> itemAdapterList1 = new ArrayList<>();
    mDokter dokter;
    String strName;
    int index;
    LinearLayout lnEmpty;

    public FragmentSubMaintenance(List<String> listHeaderId, tMaintenanceHeader header, int intSubSubActivity, int index){
        this.header = header;
        this.intSubSubActivity = intSubSubActivity;
        this.index = index;
        this.listHeaderId = listHeaderId;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_infoprogram, container, false);
        listView = (ListView) v.findViewById(R.id.lv_infoprogram);
        lnEmpty = (LinearLayout)v.findViewById(R.id.ln_emptyMain);

        detailRepo = new tMaintenanceDetailRepo(getContext());
        headerRepo = new tMaintenanceHeaderRepo(getContext());
        if (index==0){
            itemAdapterList0.clear();
        }else if (index==1){
            itemAdapterList1.clear();
        }


        if (header!=null){
            try {
                if (header.getIntActivityId()==new clsHardCode().VisitDokter){
                   dokter  = new mDokterRepo(getContext()).findBytxtId(header.getIntDokterId());
                   if (dokter.getTxtLastName()!=null){
                       strName = dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                   }else {
                       strName = dokter.getTxtFirstName();
                   }
                }else {
                    strName = new mApotekRepo(getContext()).findBytxtId(header.getIntApotekID()).getTxtName();
                }

                listDetail = (List<tMaintenanceDetail>) detailRepo.findByListHeaderIdandSubsubId(listHeaderId, intSubSubActivity);
                if (listDetail!=null){
                    if (listDetail.size()>0){
                        for (tMaintenanceDetail data : listDetail){
                            clsMaintenance itemAdapter = new clsMaintenance();
                            itemAdapter.setTxtId(data.getTxtDetailId());
                            itemAdapter.setTxtTittle(strName); //nama dokter substring(0,1)
                            itemAdapter.setIntColor(R.color.green_300);
                            itemAdapter.setTxtImgName((strName.substring(0,1)).toUpperCase());
                            itemAdapter.setTxtSubTittle(data.getTxtNoDoc());
                            if (index==0){
                                itemAdapterList0.add(itemAdapter);
                            }else if (index==1){
                                itemAdapterList1.add(itemAdapter);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (index==0){
            adapter = new AdapterListMaintenance(getContext(), itemAdapterList0);
        }else if (index==1){
            adapter = new AdapterListMaintenance(getContext(), itemAdapterList1);
        }

        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setEmptyView(lnEmpty);

        return v;
    }
}
