package com.kalbe.kalbecallplanaedp.Utils;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;

import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.SnackBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Dewi Oktaviani on 11/7/2018.
 */

public class ReceiverDownloadManager {
    private List<Long> longList;
    private CoordinatorLayout coordinatorLayout;
    private List<Long> listAll;
    Snackbar snackbar;
    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public ReceiverDownloadManager(List<Long> longList) {
        this.longList = longList;
    }

    private void deleteMediaStorageDirtemp (){
        File mediaStorageDir = new File(new clsHardCode().txtFolderDownload + File.separator);
        if (mediaStorageDir.exists()){
            if (mediaStorageDir.isDirectory()){
                for (File currentFile : mediaStorageDir.listFiles()){
                    currentFile.delete();
                }
            }
            mediaStorageDir.delete();
        }
    }
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                if (downloadId!=-1){
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {
                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            String title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                            String txtId = c.getString(c.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
                            try {
                                byte[] file = PickFile.getByteArrayFileToSave(Uri.parse(uriString), context);
                                if (title.contains("Info Program")){
                                    tInfoProgramDetail data = new tInfoProgramDetailRepo(context).findByDetailId(txtId);
                                    data.setBlobFile(file);
                                    new tInfoProgramDetailRepo(context).createOrUpdate(data);
                                }else if (title.contains("Akuisisi")){
                                    tAkuisisiDetail data = new tAkuisisiDetailRepo(context).findByDetailId(txtId);
                                    data.setTxtImg(file);
                                    new tAkuisisiDetailRepo(context).createOrUpdate(data);
                                }else if (title.contains("Realisasi Pertama")){
                                    tRealisasiVisitPlan data = new tRealisasiVisitPlanRepo(context).findBytxtId(txtId);
                                    data.setBlobImg1(file);
                                    new tRealisasiVisitPlanRepo(context).createOrUpdate(data);
                                }else if (title.contains("Realisasi Kedua")){
                                    tRealisasiVisitPlan data = new tRealisasiVisitPlanRepo(context).findBytxtId(txtId);
                                    data.setBlobImg2(file);
                                    new tRealisasiVisitPlanRepo(context).createOrUpdate(data);
                                }
//                                Toast.makeText(getContext(), String.valueOf(file), Toast.LENGTH_LONG).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                if (longList!=null){
                    longList.remove(downloadId);
//                if (longList.size()>0){
//                    snackbar.
//                    snackbar = SnackBar.snackbarIndefinite(coordinatorLayout, "Download File " + String.valueOf(sumAvailabe)+ "/" + String.valueOf(listAll.size()), R.color.red_bold);
//                    snackbar.show();
//                }
                    if (longList.isEmpty()){
//                    snackbar.dismiss();
                        deleteMediaStorageDirtemp();
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.drawable.ic_notif)
                                        .setContentTitle("Anyeong")
                                        .setContentText("All Download completed");


                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(455, mBuilder.build());
                    }
                }
            }
        }
    };


}
