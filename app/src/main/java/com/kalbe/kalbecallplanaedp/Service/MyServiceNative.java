package com.kalbe.kalbecallplanaedp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.Common.clsPushData;
import com.kalbe.kalbecallplanaedp.Common.mConfigData;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Repo.mConfigRepo;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class MyServiceNative extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       throw new UnsupportedOperationException("Not Implement yet");
    }

    @Override
    public void onCreate() {
//        super.onCreate();
    }

    private static long UPDATE_INTERVAL = 1*360*1000;  //default
    private static long UPDATE_INTERVAL_TESTING = 3000;  //default
    private static Timer timer = new Timer();
    private void _startService(){
        long intInterval = 0;
        intInterval = UPDATE_INTERVAL_TESTING;
        if (timer!=null){
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    _doServiceWork();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 3000, intInterval);
    }

    private void _doServiceWork() throws JSONException{
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsPushData dtJson = new clsHelperBL().pushData(versionName, getApplicationContext());
        if (dtJson == null){
            _shutdownService();
        }else {
            String linkPushData = new clsHardCode().linkLogin;
            new clsHelperBL().makeJsonObjectRequestPushData(getApplicationContext(), linkPushData, dtJson, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {

                }
            });
        }
    }
    private void _shutdownService(){
        if (timer!=null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        _startService();
    }

    @Override
    public void onDestroy() {
        _shutdownService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _startService();
        return START_STICKY;
    }
}
