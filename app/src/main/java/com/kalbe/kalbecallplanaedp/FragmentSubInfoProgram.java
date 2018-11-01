package com.kalbe.kalbecallplanaedp;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsInfoProgram;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListInfoProgram;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubInfoProgram extends Fragment {
    View v;
    tInfoProgramHeader header;
    tInfoProgramHeaderRepo headerRepo;
    tInfoProgramDetail detail;
    List<tInfoProgramDetail> listDetail;
    tInfoProgramDetailRepo detailRepo;
    int intSubSubActivity;
    AdapterListInfoProgram adapter;
    ListView listView;
    CheckBox checkBox;
    private String PDF_View = "pdf viewer";
    private String ZOOM_IMAGE_INFO ="zoom image info program";
    private static List<clsInfoProgram> itemAdapterList = new ArrayList<>();

    public FragmentSubInfoProgram(tInfoProgramHeader header, int intSubSubActivity){
        this.header = header;
        this.intSubSubActivity = intSubSubActivity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_infoprogram, container, false);
        listView = (ListView) v.findViewById(R.id.lv_infoprogram);

        itemAdapterList.clear();
        clsInfoProgram itemAdapter = new clsInfoProgram();
        itemAdapter.setTxtTittle("Dokter Fauziyah");
        itemAdapter.setTxtSubTittle("Info Program Dokter Fauziyah");
        itemAdapter.setTxtDesc("testing");
        itemAdapter.setIntColor(R.color.purple_600);
        itemAdapter.setTxtImgName("P");
        itemAdapter.setChecked(true);
        itemAdapter.setTxtFileName(".jpg");
        itemAdapter.setIntFlagContent(new clsHardCode().AllInfo);
        itemAdapterList.add(itemAdapter);

        clsInfoProgram itemAdapter1 = new clsInfoProgram();
        itemAdapter1.setTxtTittle("Dokter Fauziyah");
        itemAdapter1.setTxtSubTittle("Info Program Dokter Fauziyah");
        itemAdapter1.setTxtDesc("testing");
        itemAdapter1.setIntColor(R.color.purple_600);
        itemAdapter1.setTxtImgName("P");
        itemAdapter1.setChecked(false);
        itemAdapter1.setIntFlagContent(new clsHardCode().OnlyFile);
        itemAdapterList.add(itemAdapter1);

        clsInfoProgram itemAdapter2 = new clsInfoProgram();
        itemAdapter2.setTxtTittle("Dokter Fauziyah");
        itemAdapter2.setTxtSubTittle("Hi this looks good, but in honeycomb if i have an activity over a listview and i click outside of the view, the touch event is passed to the listview even if i return true; Any idea how to resolve it ?");
        itemAdapter2.setTxtDesc("testing");
        itemAdapter2.setIntColor(R.color.purple_600);
        itemAdapter2.setTxtImgName("P");
        itemAdapter2.setChecked(false);
        itemAdapter2.setIntFlagContent(new clsHardCode().OnlyDesc);
        itemAdapterList.add(itemAdapter2);

//        clsListItemAdapter itemAdapter3 = new clsListItemAdapter();
//        itemAdapter3.setTxtTittle("Dokter Fauziyah");
//        itemAdapter3.setTxtSubTittle("Info Program Dokter Fauziyah");
//        itemAdapter3.setTxtDesc("testing");
//        itemAdapter3.setIntColor(R.color.purple_600);
//        itemAdapter3.setBoolSection(false);
//        itemAdapter3.setTxtImgName("P");
//        itemAdapterList.add(itemAdapter3);
//
//        clsListItemAdapter itemAdapter4 = new clsListItemAdapter();
//        itemAdapter4.setTxtTittle("Dokter Fauziyah");
//        itemAdapter4.setTxtSubTittle("Info Program Dokter Fauziyah");
//        itemAdapter4.setTxtDesc("testing");
//        itemAdapter4.setIntColor(R.color.purple_600);
//        itemAdapter4.setBoolSection(false);
//        itemAdapter4.setTxtImgName("P");
//        itemAdapterList.add(itemAdapter4);
//
//        clsListItemAdapter itemAdapter5 = new clsListItemAdapter();
//        itemAdapter5.setTxtTittle("Dokter Fauziyah");
//        itemAdapter5.setTxtSubTittle("Info Program Dokter Fauziyah");
//        itemAdapter5.setTxtDesc("testing");
//        itemAdapter5.setIntColor(R.color.purple_600);
//        itemAdapter5.setBoolSection(false);
//        itemAdapter5.setTxtImgName("P");
//        itemAdapterList.add(itemAdapter5);

        adapter = new AdapterListInfoProgram(getContext(), itemAdapterList);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        adapter.setOnItemClickListener(new AdapterListInfoProgram.onItemClickListener() {
            @Override
            public void onItemClick(View view, clsInfoProgram obj, int position) {
                ToastCustom.showToasty(getActivity(),"anyeong",4);
                String fileExtension = (obj.getTxtFileName()).substring((obj.getTxtFileName()).lastIndexOf("."));
//                if (fileExtension.equals(".jpg")){
//                    Intent intent = new Intent(getContext(), ImageViewerActivity.class);
//                    intent.putExtra(ZOOM_IMAGE_INFO, obj.getTxtId());
//                    startActivity(intent);
//                }else if (fileExtension.equals(".pdf")){
//                    Intent intent1 = new Intent(getContext(), PDFViewer.class);
//                    intent1.putExtra(PDF_View, obj.getTxtId());
//                    startActivity(intent1);
//                }
            }
        });

        adapter.setOnCheckboxClickListener(new AdapterListInfoProgram.onCheckboxClickListener() {
            @Override
            public void onItemClick(final View view, clsInfoProgram obj, int position) {

                if (view!=null){
                     checkBox = (CheckBox) view;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure? (it can't be undo)");

                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        checkBox.setChecked(true);
                        checkBox.setEnabled(false);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkBox.setChecked(false);
                        checkBox.setEnabled(true);
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
                alert.setCancelable(false);

            }
        });

        if (header!=null){
            try {
                listDetail = (List<tInfoProgramDetail>) detailRepo.findByHeaderId(header.getTxtHeaderId(), intSubSubActivity);
//                if (listDetail!=null){
//                    if (listDetail.size()>0){
//                        for (tInfoProgramDetail data : listDetail){
//                            if (data.)
//                        }
//                    }
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return v;
    }
}
