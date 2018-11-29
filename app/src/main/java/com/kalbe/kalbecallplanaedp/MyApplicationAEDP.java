package com.kalbe.kalbecallplanaedp;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;

import com.kalbe.kalbecallplanaedp.BL.clsActivity;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.tLogError;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Fragment.FragmentDownloadData;
import com.kalbe.kalbecallplanaedp.Repo.tLogErrorRepo;
import com.kalbe.kalbecallplanaedp.Utils.ReceiverDownloadManager;
import com.kalbe.mobiledevknlibs.ErrorReporting.*;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dewi Oktaviani on 11/8/2018.
 */

@ReportsCrashes(
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
//        resDialogText = R.string.crash_dialog_text,
//        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
//        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
//        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. When defined, adds a user text field input with this text resource as a label
//        resDialogOkToast = R.string.crash_dialog_ok_toast, // optional. displays a Toast message when the user accepts to send a report.

        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.BRAND,
                ReportField.CUSTOM_DATA,
                ReportField.INITIAL_CONFIGURATION,
                ReportField.CRASH_CONFIGURATION,
                ReportField.USER_CRASH_DATE,
                ReportField.STACK_TRACE,
                ReportField.LOGCAT,
                ReportField.USER_COMMENT,
                ReportField.DEVICE_ID,
                ReportField.FILE_PATH}
)
public class MyApplicationAEDP extends Application {
    private Context mContext;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String fileName = "log_"+dateFormat.format(date)+".txt";
    @Override
    public void onCreate() {

        mContext = getApplicationContext();
        ACRA.init(this);
        mUserLogin dtUserLogin = new clsMainBL().getUserLogin(mContext);
        List<tLogError> dataErrorsss = null;
        try {
             dataErrorsss = (List<tLogError>) new tLogErrorRepo(mContext).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<tLogError> hahaha = dataErrorsss;
        List<tLogError> dataError = new tLogErrorRepo(mContext).getAllPushData();
        tLogError logError = new tLogError();
        if (dataError!=null){
            if (dataError.size()>0){
                boolean exist = false;
                String txtGuiId = "";
                for (tLogError data : dataError){
                    if (data.getTxtFileName().equals(fileName)){
                        txtGuiId = data.getTxtGuiId();
                        exist = true;
                        break;
                    }
                }

                if (exist){
                    logError.setTxtGuiId(txtGuiId);
                }else {
                    logError.setTxtGuiId(GenerateGuid());
                }
            }else {
                logError.setTxtGuiId(GenerateGuid());
            }
        }else {
            logError.setTxtGuiId(GenerateGuid());
        }
//        try {
//            new tLogErrorRepo(mContext).createOrUpdate(logError);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        ModelReport data = new ModelReport();
//        if (dtUserLogin!=null){
//            data.setIntUserID(dtUserLogin.getIntUserID());
//            data.setTxtUserName(dtUserLogin.getTxtUserName());
//            data.setIntRoleID(dtUserLogin.getIntRoleID());
//            data.setTxtRoleName(dtUserLogin.getTxtRoleName());
//        }else {
//            data.setIntUserID(0);
//            data.setTxtUserName(null);
//            data.setIntRoleID(0);
//            data.setTxtRoleName(null);
//        }
//        if (android.os.Build.DEVICE != null){
//            data.setDevice(android.os.Build.DEVICE);
//        }
//        if (android.os.Build.MODEL != null){
//            data.setModel(android.os.Build.MODEL);
//        }
//        if(android.os.Build.PRODUCT != null){
//            data.setProduct(android.os.Build.PRODUCT);
//        }
//        if (android.os.Build.VERSION.SDK!=null){
//            data.setVersionSDK(android.os.Build.VERSION.SDK);
//        }
//        if (System.getProperty("os.version") != null){
//            data.setOsVersion(System.getProperty("os.version"));
//        }

        String txtPath = new clsHardCode().txtFolderTemp;
//        Uri uriPath = UriData.getOutputMediaUriFolder(getApplicationContext(), txtPath);
//        ACRA.getErrorReporter().handleSilentException(new RuntimeException("whatever I want"));
        ACRA.getErrorReporter().setReportSender(new LocalReportSenderAcra(mContext,txtPath, dtUserLogin, logError, fileName));
//        ACRA.getErrorReporter().checkReportsOnApplicationStart();
//        ModelError modelErrors = LocalReportSenderAcra.getModelError();
//        if (modelErrors!=null){
//            tLogError logError = new tLogError();
//            logError.setTxtGuiId(new clsActivity().GenerateGuid());
//            if (dtUserLogin!=null){
//                logError.setTxtUserId(String.valueOf(dtUserLogin.getIntUserID()));
//            }
//            logError.setTxtDeviceName(android.os.Build.DEVICE);
//            logError.setTxtOs(System.getProperty("os.version"));
//            logError.setTxtFileName(modelErrors.get_txtFileName());
//            logError.setDtDateLog(modelErrors.get_dtDate());
//            logError.setBlobImg(null);
//            logError.setIntFlagPush(new clsHardCode().Save);
//
//            try {
////                new tLogErrorRepo(mContext).createOrUpdate(logError);
//                new clsHardCode().copydb(mContext);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        super.onCreate();
    }


    public String GenerateGuid(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }
}
