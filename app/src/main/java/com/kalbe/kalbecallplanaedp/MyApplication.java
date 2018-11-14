package com.kalbe.kalbecallplanaedp;

import android.app.Application;
import android.app.DownloadManager;
import android.content.IntentFilter;

import com.kalbe.kalbecallplanaedp.Fragment.FragmentDownloadData;
import com.kalbe.kalbecallplanaedp.Utils.ReceiverDownloadManager;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/8/2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        registerReceiver( new FragmentDownloadData().receiver, new IntentFilter(
//                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
