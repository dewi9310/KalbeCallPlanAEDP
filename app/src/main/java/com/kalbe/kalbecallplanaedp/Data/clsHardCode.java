package com.kalbe.kalbecallplanaedp.Data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;


import com.kalbe.kalbecallplanaedp.Repo.mConfigRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Rian Andrivani on 11/7/2017.
 */

public class clsHardCode {
    Context context;
//    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"data"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"app_database"+File.separator;
    /* path root */
    public String txtPathApp = "data" + File.separator + "data" + File.separator + "com.kalbenutritionals.kalbecallplanaedp" + File.separator + "databases" + File.separator;

//    public String txtFolderData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeCallPlanAEDP"+File.separator+"image_Person"+File.separator;
    public String txtPathUserData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"com.kalbenutritionals.kalbecallplanaedp"+File.separator+"user_data"+File.separator;
    public String txtPathTempData = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + "com.kalbenutritionals.kalbecallplanaedp" + File.separator + "tempdata" + File.separator;

    public String dbName = "KalbeCallPlanAEDP.db";
    public String txtFolderCheckIn = txtPathUserData + "Absen" + File.separator;
    public String txtFolderAkuisisi = txtPathUserData + "Akuisisi" + File.separator;
    public String txtFolderData = txtPathUserData + "Image_Person" + File.separator;
    public String linkMaster = new mConfigRepo(context).API + "mProduct";
    public String linkLogin = new mConfigRepo(context).API + "loginMobileApps";
    public String linkToken = new mConfigRepo(context).APIToken + "token";
//    public String LinkUser = new mConfigRepo(context).API + "loginMobileApps";
    public  String LinkMobileVersion = new mConfigRepo(context).API + "getLatestAndroidVersion";
    public  String LinkUserRole = new mConfigRepo(context).API + "getListRoleByUsername";
    public int Draft = 0;
    public int Save = 1;

    public String copydb(Context context) throws IOException {
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/"+ new clsHardCode().dbName;

        try {
            File dbFile = new File(CURRENT_DATABASE_PATH);
            FileInputStream fis = new FileInputStream(dbFile);
            String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"backupDbKalbeCallPlanAEDP";
            File yourFile = new File(txtPathUserData);
            yourFile.createNewFile();
            OutputStream output = new FileOutputStream(yourFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            String s= "hahaha";
        }

        return "cute";
    }

    public JSONObject pDeviceInfo(){
        String api =  android.os.Build.VERSION.SDK;      // API Level
        String device = android.os.Build.DEVICE;           // Device
        String model = android.os.Build.MODEL;            // Model
        String product = android.os.Build.PRODUCT;
        String osVersion = Build.VERSION.BASE_OS;
        JSONObject jDevInfo = new JSONObject();
        try {
            jDevInfo.put("os_version", osVersion);
            jDevInfo.put("version_sdk", api);
            jDevInfo.put("device", device);
            jDevInfo.put("model", model);
            jDevInfo.put("product", product);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jDevInfo;
    }
}
