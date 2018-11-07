package com.kalbe.kalbecallplanaedp.Utils;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Dewi Oktaviani on 11/7/2018.
 */

public class ReceiverDownloadManager {
    private List<Long> longList;

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public ReceiverDownloadManager(List<Long> longList) {
        this.longList = longList;
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

                longList.remove(downloadId);
                if (getLongList().isEmpty()){
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
    };


}
