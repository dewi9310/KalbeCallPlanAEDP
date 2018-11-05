package com.kalbe.kalbecallplanaedp;


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
import android.widget.ListView;

import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsInfoProgram;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.adapter.AdapterListInfoProgram;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

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
    mDokter dokter;
    mApotek apotek;
    String strName;

    public FragmentSubInfoProgram(tInfoProgramHeader header, int intSubSubActivity){
        this.header = header;
        this.intSubSubActivity = intSubSubActivity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_infoprogram, container, false);
        listView = (ListView) v.findViewById(R.id.lv_infoprogram);

        detailRepo = new tInfoProgramDetailRepo(getContext());
        headerRepo = new tInfoProgramHeaderRepo(getContext());

        itemAdapterList.clear();
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
                    strName = new mApotekRepo(getContext()).findBytxtId(header.getIntApotekId()).getTxtName();
                }

                listDetail = (List<tInfoProgramDetail>) detailRepo.findByHeaderIdandSubsubId(header.getTxtHeaderId(), 3);
                if (listDetail!=null){
                    if (listDetail.size()>0){
                        for (tInfoProgramDetail data : listDetail){
                            clsInfoProgram itemAdapter = new clsInfoProgram();
                            itemAdapter.setTxtId(data.getTxtDetailId());
                            itemAdapter.setTxtTittle(strName); //nama dokter substring(0,1)
                            itemAdapter.setIntColor(R.color.purple_600);
                            itemAdapter.setTxtImgName((strName.substring(0,1)).toUpperCase());
                            itemAdapter.setChecked(data.isBoolFlagChecklist());
                            if ((data.getDescription()!=null&&!data.getDescription().equals("")) && (data.getBlobFile()!=null)){
                                itemAdapter.setTxtSubTittle(data.getDescription());
                                itemAdapter.setTxtDesc(data.getTxtFileName());
                                itemAdapter.setTxtFileName(data.getTxtFileName()+".pdf");
                                itemAdapter.setIntFlagContent(new clsHardCode().AllInfo);
                            }else if (data.getDescription()!=null&&!data.getDescription().equals("")){
                                itemAdapter.setTxtSubTittle(data.getDescription());
                                itemAdapter.setTxtDesc("");
                                itemAdapter.setTxtFileName("");
                                itemAdapter.setIntFlagContent(new clsHardCode().OnlyDesc);
                            }else if (data.getBlobFile()!=null){
                                itemAdapter.setTxtSubTittle("");
                                itemAdapter.setTxtDesc(data.getTxtFileName());
                                itemAdapter.setTxtFileName(data.getTxtFileName() + ".pdf");
                                itemAdapter.setIntFlagContent(new clsHardCode().OnlyFile);
                            }
                            itemAdapterList.add(itemAdapter);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

//
//        clsInfoProgram itemAdapter = new clsInfoProgram();
//        itemAdapter.setTxtTittle("Dokter Fauziyah"); //nama dokter substring(0,1)
//        itemAdapter.setTxtSubTittle("Info Program Dokter Fauziyah");
//        itemAdapter.setTxtDesc("testing");
//        itemAdapter.setIntColor(R.color.purple_600);
////        itemAdapter.setIntColor(new Random().nextInt());
//        itemAdapter.setTxtImgName("P");
//        itemAdapter.setChecked(true);
//        itemAdapter.setTxtFileName(".jpg");
//        itemAdapter.setIntFlagContent(new clsHardCode().AllInfo);
//        itemAdapterList.add(itemAdapter);
//
//        clsInfoProgram itemAdapter1 = new clsInfoProgram();
//        itemAdapter1.setTxtTittle("Dokter Fauziyah");
//        itemAdapter1.setTxtSubTittle("Info Program Dokter Fauziyah");
//        itemAdapter1.setTxtDesc("testing");
//        itemAdapter1.setIntColor(R.color.purple_600);
//        itemAdapter1.setTxtImgName("P");
//        itemAdapter1.setChecked(false);
//        itemAdapter1.setIntFlagContent(new clsHardCode().OnlyFile);
//        itemAdapterList.add(itemAdapter1);
//
//        clsInfoProgram itemAdapter2 = new clsInfoProgram();
//        itemAdapter2.setTxtTittle("Dokter Fauziyah");
//        itemAdapter2.setTxtSubTittle("Hi this looks good, but in honeycomb if i have an activity over a listview and i click outside of the view, the touch event is passed to the listview even if i return true; Any idea how to resolve it ?");
//        itemAdapter2.setTxtDesc("testing");
//        itemAdapter2.setIntColor(R.color.purple_600);
//        itemAdapter2.setTxtImgName("P");
//        itemAdapter2.setChecked(false);
//        itemAdapter2.setIntFlagContent(new clsHardCode().OnlyDesc);
//        itemAdapterList.add(itemAdapter2);

        adapter = new AdapterListInfoProgram(getContext(), itemAdapterList);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        adapter.setOnItemClickListener(new AdapterListInfoProgram.onItemClickListener() {
            @Override
            public void onItemClick(View view, clsInfoProgram obj, int position) {
//                ToastCustom.showToasty(getActivity(),"anyeong",4);
                String fileExtension = (obj.getTxtFileName()).substring((obj.getTxtFileName()).lastIndexOf("."));
                if (fileExtension.equals(".jpg")){
                    Intent intent = new Intent(getContext(), ImageViewerActivity.class);
                    intent.putExtra(ZOOM_IMAGE_INFO, obj.getTxtId());
                    startActivity(intent);
                }else if (fileExtension.equals(".pdf")){
                    Intent intent1 = new Intent(getContext(), PDFViewer.class);
                    intent1.putExtra(PDF_View, obj.getTxtId());
                    startActivity(intent1);
                }
            }
        });

        adapter.setOnCheckboxClickListener(new AdapterListInfoProgram.onCheckboxClickListener() {
            @Override
            public void onItemClick(final View view, final clsInfoProgram obj, int position) {

                if (view!=null){
                     checkBox = (CheckBox) view;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure? (it can't be undo)");

                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            detail = detailRepo.findByDetailId(obj.getTxtId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        saveData();
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

        return v;
    }

    public void saveData(){
        try {
            tInfoProgramHeader dtHeader = header;
            dtHeader.setIntFlagPush(new clsHardCode().Save);
            headerRepo.createOrUpdate(dtHeader);
            DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            tInfoProgramDetail data = detail;
//            data.setTxtHeaderId(detail.getTxtHeaderId());
//            data.setTxtDetailId(detail.getTxtDetailId());
//            data.setIntSubDetailActivityId(detail.getIntSubDetailActivityId());
//            data.setTxtFileName(detail.getTxtFileName());
//            data.setBlobFile(detail.getBlobFile());
            data.setBoolFlagChecklist(checkBox.isChecked());
            data.setDtChecklist(dateTimeFormat.format(cal.getTime()));
//            data.setDescription(data.getDescription());
            detailRepo.createOrUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getRandomColor(){
        Random rnd = new Random();
//        return Color.argb(255, rnd.nextInt(50), rnd.nextInt(50), rnd.nextInt(50));
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
