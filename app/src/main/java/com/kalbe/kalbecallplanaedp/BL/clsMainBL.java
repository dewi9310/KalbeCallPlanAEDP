package com.kalbe.kalbecallplanaedp.BL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.TypedValue;


import com.kalbe.kalbecallplanaedp.Common.clsStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserMappingArea;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.enumStatusMenuStart;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mTypeSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserMappingAreaRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.mobiledevknlibs.library.swipemenu.bean.SwipeMenu;
import com.kalbe.mobiledevknlibs.library.swipemenu.bean.SwipeMenuItem;
import com.kalbe.mobiledevknlibs.library.swipemenu.interfaces.SwipeMenuCreator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class clsMainBL {

    public clsStatusMenuStart checkUserActive(Context context) throws ParseException, SQLException {
        tRealisasiVisitPlanRepo realisasiVisitPlanRepo = new tRealisasiVisitPlanRepo(context);
        tProgramVisitSubActivityRepo visitSubActivityRepo = new tProgramVisitSubActivityRepo(context);
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        mUserLogin dtLogin = getUserLogin(context);
        clsStatusMenuStart _clsStatusMenuStart =new clsStatusMenuStart();
//        if (dtLogin!=null){
//
//        }

        if(loginRepo.CheckLoginNow(context)){
            _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.UserActiveLogin);
        }else {

            boolean valid = false;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);

            tRealisasiVisitPlan dataCheckinActive = realisasiVisitPlanRepo.getDataCheckinActive();
            if (dataCheckinActive!=null){
                //checkout
                dataCheckinActive.setDtDateRealisasi(dateFormat.format(dateTimeFormat.parse(dtLogin.getDtLogIn())));
                dataCheckinActive.setDtCheckOut(dateTimeFormat.format(cal.getTime()));
                dataCheckinActive.setIntStatusRealisasi(new clsHardCode().Realisasi);
                dataCheckinActive.setIntFlagPush(new clsHardCode().Save);
                dataCheckinActive.setIntNumberRealisasi(0);
                realisasiVisitPlanRepo.createOrUpdate(dataCheckinActive);

                tProgramVisitSubActivity dtVisit = visitSubActivityRepo.findBytxtId(dataCheckinActive.getTxtProgramVisitSubActivityId());
                dtVisit.setIntFlagPush(new clsHardCode().Save);
                visitSubActivityRepo.createOrUpdate(dtVisit);
            }

            tRealisasiVisitPlanRepo _tRealisasiVisitPlanRepo = new tRealisasiVisitPlanRepo(context);
            tProgramVisitSubActivityRepo _tProgramVisitSubActivityRepo = new tProgramVisitSubActivityRepo(context);
            tAkuisisiHeaderRepo _tAkuisisiHeaderRepo = new tAkuisisiHeaderRepo(context);
            tAkuisisiDetailRepo _tAkuisisiDetailRepo = new tAkuisisiDetailRepo(context);
            tMaintenanceHeaderRepo _tMaintenanceHeaderRepo = new tMaintenanceHeaderRepo(context);
            tMaintenanceDetailRepo _tMaintenanceDetailRepo = new tMaintenanceDetailRepo(context);
            tInfoProgramHeaderRepo _tInfoProgramHeaderRepo = new tInfoProgramHeaderRepo(context);
            tInfoProgramDetailRepo _tInfoProgramDetailRepo = new tInfoProgramDetailRepo(context);

            List<tRealisasiVisitPlan> ListoftRealisasiVisitData = _tRealisasiVisitPlanRepo.getAllPushData();
            List<tProgramVisitSubActivity> ListOftProgramSubActivity = _tProgramVisitSubActivityRepo.getAllPushData();
            List<tAkuisisiHeader> ListOftAkuisisiHeaderData = _tAkuisisiHeaderRepo.getAllPushData();
            List<tMaintenanceHeader> ListOftMaintenanceHeader = _tMaintenanceHeaderRepo.getAllPushData();
            List<tInfoProgramHeader> ListOftInfoProgramHeader = _tInfoProgramHeaderRepo.getAllPushData();


            if (ListoftRealisasiVisitData.size()>0 && valid==false){
                valid = true;
            }

            if (ListOftProgramSubActivity.size()>0 && valid==false){
                valid = true;
            }

            if (ListOftAkuisisiHeaderData.size()>0 && valid==false){
                valid = true;
            }

            if (ListOftMaintenanceHeader.size()>0 && valid==false){
                valid = true;
            }

            if (ListOftInfoProgramHeader.size()>0 && valid==false){
                valid = true;
            }


            if (valid==true){
                _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.PushDataMobile);
            }else {
                _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.FormLogin);
            }
        }

//        List<mUserLogin> listDataLogin = new ArrayList<>();
//        try {
//            listDataLogin = (List<mUserLogin>) login.findAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (listDataLogin!=null)


//        for (mUserLogin data : listDataLogin){
//            if (data.getTxtUserName()!=null){
//                if (!data.getTxtUserName().equals("null")||!data.getTxtUserName().equals(null)){
//
//                }
//            }
//        }

        return _clsStatusMenuStart;
    }

    public mUserLogin getUserLogin(Context context){
        List <mUserLogin> dtList = new ArrayList<>();
        mUserLoginRepo dtRepo= new mUserLoginRepo(context);
        try {
            dtList = (List<mUserLogin>) dtRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dtList.size()>0){
            return dtList.get(0);
        }else {
            return null;
        }
    }

    public tRealisasiVisitPlan getDataCheckinActive(Context context){
        tRealisasiVisitPlan data = null;
        tRealisasiVisitPlanRepo absenRepo= new tRealisasiVisitPlanRepo(context);
        data = (tRealisasiVisitPlan) absenRepo.getDataCheckinActive();
        return data;
    }

    public boolean isDataReady(Context context){
     boolean valid = false;
        mActivityRepo dtActivityrepo = new mActivityRepo(context);
        mSubActivityRepo dtRepoSubActivity= new mSubActivityRepo(context);
        mSubSubActivityRepo dtRepoSubSubActivity= new mSubSubActivityRepo(context);
        mDokterRepo dokterRepo = new mDokterRepo(context);
        mApotekRepo apotekRepo = new mApotekRepo(context);
        mUserMappingAreaRepo dtRepoArea = new mUserMappingAreaRepo(context);

        List<mActivity> dataListActivity = new ArrayList<>();
        List<mSubActivity> dataListSubActivity = new ArrayList<>();
        List<mSubSubActivity> dataListSubSubActivity = new ArrayList<>();
        List<mApotek> dataListApotek = new ArrayList<>();
        List<mDokter> dataListDokter = new ArrayList<>();
        List<mUserMappingArea> dataListArea = new ArrayList<>();

        try {
            dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
            dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
            dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
            dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
            dataListApotek = (List<mApotek>) apotekRepo.findAll();
            dataListDokter = (List<mDokter>) dokterRepo.findAll();
            if (dataListArea.size()>0 && dataListActivity.size()>0 && dataListSubActivity.size()>0 && dataListSubSubActivity.size()>0 && dataListApotek.size()>0 && dataListDokter.size()>0){
                valid = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

     return valid;
    }

    public byte[] arrayDecryptFile(byte[] blobFile){
        String key = "kalbenutritionals";
        byte[] arrayFileDecrypt = null;
        try {
            byte[] array = blobFile;
            byte[] keyInByte = getKeyBytes(key);
            arrayFileDecrypt = decrypt(array, keyInByte, keyInByte);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return arrayFileDecrypt;
    }

    public byte[] getFile() throws FileNotFoundException {
        byte[] data = null;
        byte[] inarry = null;
        String path = new clsHardCode().txtFolderAkuisisi + "dewi";
        try {
            InputStream is = new FileInputStream(path); // use recorded file instead of getting file from assets folder.
            int length = is.available();
            data = new byte[length];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(data)) != -1) {
                output.write(data, 0, bytesRead);
            }
            inarry = output.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inarry;

    }

    public byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes= new byte[16];
        String characterEncoding = "UTF-8";
        byte[] parameterKeyBytes= key.getBytes(characterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

    public  byte[] decrypt(byte[] cipherText, byte[] key, byte [] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String cipherTransformation = "AES/CBC/PKCS5Padding";
        String aesEncryptionAlgorithm = "AES";
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    public void createFolderApp(){
        clsHardCode clsdthc = new clsHardCode();
        File appDir=new File(clsdthc.txtPathApp);

        if(!appDir.exists() && !appDir.isDirectory())
        {
            // create empty directory
            if (appDir.mkdirs())
            {
                Log.i("CreateDir","App dir created");
            }
            else
            {
                Log.w("CreateDir","Unable to create app dir!");
            }
        }
        else
        {
            Log.i("CreateDir","App dir already exists");
        }
    }
}
