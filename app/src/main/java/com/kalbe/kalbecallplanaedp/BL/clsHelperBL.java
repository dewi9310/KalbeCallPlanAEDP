package com.kalbe.kalbecallplanaedp.BL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kalbe.kalbecallplanaedp.Common.clsDataJson;
import com.kalbe.kalbecallplanaedp.Common.clsPushData;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mConfigData;
import com.kalbe.kalbecallplanaedp.Common.mCounterData;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramDetail;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceDetail;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.VolleyUtils;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Data.enumCounterData;
import com.kalbe.kalbecallplanaedp.Repo.clsTokenRepo;
import com.kalbe.kalbecallplanaedp.Repo.mConfigRepo;
import com.kalbe.kalbecallplanaedp.Repo.mCounterDataRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import com.kalbe.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.responsePushData.ResponsePushData;
import com.kalbe.kalbecallplanaedp.SplashActivity;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbe.mobiledevknlibs.Volley.volley.VolleyMultipartRequest;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class clsHelperBL {

    String access_token,clientId = "";
    List<clsToken> dataToken;

    public clsPushData pushData(String versionName, Context context){
        clsPushData dtclsPushData = new clsPushData();
        clsDataJson dtPush = new clsDataJson();
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            mUserLogin dataLogin = new clsMainBL().getUserLogin(context);
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                mCounterDataRepo _mCounterDataRepo = new mCounterDataRepo(context);
                mCounterData _mCounterData = new mCounterData();
                _mCounterData.setIntId(enumCounterData.MonitorScedule.getIdCounterData());
                _mCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _mCounterData.setTxtName("Monitor Service");
                _mCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_mCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tRealisasiVisitPlanRepo _tRealisasiVisitPlanRepo = new tRealisasiVisitPlanRepo(context);
            tAkuisisiHeaderRepo _tAkuisisiHeaderRepo = new tAkuisisiHeaderRepo(context);
            tAkuisisiDetailRepo _tAkuisisiDetailRepo = new tAkuisisiDetailRepo(context);
            tProgramVisitSubActivityRepo _tProgramVisitSubActivityRepo = new tProgramVisitSubActivityRepo(context);
            tMaintenanceHeaderRepo _tMaintenanceHeaderRepo = new tMaintenanceHeaderRepo(context);
            tMaintenanceDetailRepo _tMaintenanceDetailRepo = new tMaintenanceDetailRepo(context);
            tInfoProgramHeaderRepo _tInfoProgramHeaderRepo = new tInfoProgramHeaderRepo(context);
            tInfoProgramDetailRepo _tInfoProgramDetailRepo = new tInfoProgramDetailRepo(context);

            List<tRealisasiVisitPlan> ListoftRealisasiVisitData = _tRealisasiVisitPlanRepo.getAllPushData();
            List<tAkuisisiHeader> ListOftAkuisisiHeaderData = _tAkuisisiHeaderRepo.getAllPushData();
            List<tAkuisisiDetail> ListOftAkuisisiDetailData = _tAkuisisiDetailRepo.getPushAllData(ListOftAkuisisiHeaderData);
            List<tMaintenanceHeader> ListOftMaintenanceHeader = _tMaintenanceHeaderRepo.getAllPushData();
            List<tMaintenanceDetail> ListOfMaintenanceDetail = _tMaintenanceDetailRepo.getPushAllData(ListOftMaintenanceHeader);
            List<tInfoProgramHeader> ListOftInfoProgramHeader = _tInfoProgramHeaderRepo.getAllPushData();
            List<tInfoProgramDetail> ListOftInfoProgramDetail = _tInfoProgramDetailRepo.getPushAllData(ListOftInfoProgramHeader);
            List<tProgramVisitSubActivity> ListOftProgramSubActivity = _tProgramVisitSubActivityRepo.getAllPushData();

            FileUpload = new HashMap<>();
            if (ListOftAkuisisiHeaderData!=null){
                dtPush.setListDataOftAkuisisiHeader(ListOftAkuisisiHeaderData);
            }
            if (ListOftAkuisisiDetailData!=null){
                dtPush.setListDataOftAkuisisiDetail(ListOftAkuisisiDetailData);
                for (tAkuisisiDetail data : ListOftAkuisisiDetailData){
                    if (data.getTxtImg()!=null){
                        FileName.add(data.getTxtDetailId());
                        FileUpload.put(data.getTxtDetailId(), data.getTxtImg());
                    }
                }
            }

            if (ListOftProgramSubActivity!=null){
                dtPush.setListOfDatatProgramVisitSubActivity(ListOftProgramSubActivity);
            }

            if (ListOftMaintenanceHeader!=null){
                dtPush.setListOfDatatMaintenanceHeader(ListOftMaintenanceHeader);
            }
            if (ListOfMaintenanceDetail!=null){
                dtPush.setListOfDatatMaintenanceDetail(ListOfMaintenanceDetail);
            }

            if (ListOftInfoProgramHeader!=null){
                dtPush.setListOfDatatInfoProogramHeader(ListOftInfoProgramHeader);
            }

            if (ListOftInfoProgramDetail!=null){
                dtPush.setListOfDatatInfoProgramDetail(ListOftInfoProgramDetail);
            }

            if (ListoftRealisasiVisitData!=null){
                dtPush.setListOfDatatRealisasiVisitPlan(ListoftRealisasiVisitData);
                for (tRealisasiVisitPlan data : ListoftRealisasiVisitData){
                    if (data.getBlobImg1()!=null){
                        FileName.add("Visit" + data.getTxtRealisasiVisitId() + "-1");
                        FileUpload.put("Visit" + data.getTxtRealisasiVisitId() + "-1", data.getBlobImg1());
                    }
                    if (data.getBlobImg2()!=null){
                        FileName.add("Visit" + data.getTxtRealisasiVisitId() + "-2");
                        FileUpload.put("Visit" + data.getTxtRealisasiVisitId() + "-2", data.getBlobImg2());
                    }
                }
            }
        }else {
            dtPush = null;
        }
        dtclsPushData.setDataJson(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }

    public void SavePushData(Context context, clsDataJson dtJson, ResponsePushData jsonResult){
        try {
            for (int i = 0; i < jsonResult.getData().getModelData().size(); i++){
                if (jsonResult.getData().getModelData().get(i).isModStatus()==true&&jsonResult.getData().getModelData().get(i).getModName().equals("ListOfDatatRealisasiVisitPlan")){
                    for (tRealisasiVisitPlan data : dtJson.getListOfDatatRealisasiVisitPlan()){
                        data.setIntFlagPush(new clsHardCode().Sync);
                        new tRealisasiVisitPlanRepo(context).createOrUpdate(data);
                    }
                }

                if (jsonResult.getData().getModelData().get(i).isModStatus()==true&&jsonResult.getData().getModelData().get(i).getModName().equals("ListOfDatatProgramVisitSubActivity")){
                    for (tProgramVisitSubActivity data : dtJson.getListOfDatatProgramVisitSubActivity()){
                        data.setIntFlagPush(new clsHardCode().Sync);
                        new tProgramVisitSubActivityRepo(context).createOrUpdate(data);
                    }
                }

                if (jsonResult.getData().getModelData().get(i).isModStatus()==true&&jsonResult.getData().getModelData().get(i).getModName().equals("ListDataOftAkuisisiHeader")){
                    for (tAkuisisiHeader data : dtJson.getListDataOftAkuisisiHeader()){
                        data.setIntFlagPush(new clsHardCode().Sync);
                        new tAkuisisiHeaderRepo(context).createOrUpdate(data);
                    }
                }

                if (jsonResult.getData().getModelData().get(i).isModStatus()==true&&jsonResult.getData().getModelData().get(i).getModName().equals("ListOfDatatInforProgramHeader")){
                    for (tInfoProgramHeader data : dtJson.getListOfDatatInfoProogramHeader()){
                        data.setIntFlagPush(new clsHardCode().Sync);
                        new tInfoProgramHeaderRepo(context).createOrUpdate(data);
                    }
                }


                if (jsonResult.getData().getModelData().get(i).isModStatus()==true&&jsonResult.getData().getModelData().get(i).getModName().equals("ListOfDatatMaintenanceHeader")){
                    for (tMaintenanceHeader data : dtJson.getListOfDatatMaintenanceHeader()){
                        data.setIntFlagPush(new clsHardCode().Sync);
                        new tMaintenanceHeaderRepo(context).createOrUpdate(data);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void makeJsonObjectRequestPushData(final Context ctx, String strLinkAPI, final clsPushData mRequestBody, final VolleyResponseListener listener) {
        final String boundary = "apiclient-" + System.currentTimeMillis();
        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        final String[] body = new String[1];
        final String[] message = new String[1];

        final mConfigRepo configRepo = new mConfigRepo(ctx);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(ctx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                listener.onError(error.getMessage());
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh((Activity) ctx, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(ctx);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(ctx, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
//                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
//                                        Toast.makeText(ctx, "Please press the button again", Toast.LENGTH_SHORT).show();
////                                        login();
//                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                } else {
//                    Toast.makeText(context, "Error 500, Server Error", Toast.LENGTH_SHORT).show();
//                    finalDialog1.dismiss();
                }
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "multipart/form-data;boundary=" + boundary;
            }
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    return null;
//                }
//            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("txtParam", mRequestBody.getDataJson().txtJSON().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access category real path
                // for now just get bitmap data category ImageView
                if (mRequestBody.getFileName()!=null){
                    if (mRequestBody.getFileName().size()>0){
                        for (int i = 0; i< mRequestBody.getFileName().size(); i++){
                            params.put("image" + i, new DataPart("file_image" + i +".jpg", mRequestBody.getFileUpload().get(mRequestBody.getFileName()),"image/jpeg"));
                        }

                    }
                }
                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

//        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
    }

    public List<clsToken> getDataToken(Context context){
        List<clsToken> dtToken = null;
        try {
           clsTokenRepo tokenRepo = new clsTokenRepo(context);
            dtToken = (List<clsToken>) tokenRepo.findAll();
            if (dtToken.size() == 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtToken;
    }
    public void volleyRequestSendData(final Context ctx,String strLinkAPI, final clsPushData mRequestBody, final VolleyResponseListener listener) {
        final mConfigRepo configRepo = new mConfigRepo(ctx);
        RequestQueue queue = Volley.newRequestQueue(ctx);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(ctx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        com.kalbe.kalbecallplanaedp.Data.VolleyMultipartRequest multipartRequest = new com.kalbe.kalbecallplanaedp.Data.VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkRequestToken = new clsHardCode().linkToken;
                NetworkResponse networkResponse = error.networkResponse;

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    final String mRequestBody2 = "[" +  mRequestBody.getDataJson().txtJSON().toString() + "]";
                    params.put("txtParam", mRequestBody2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                if (mRequestBody.getFileUpload().get("input_struk") != null){
                    params.put("ImageStruk.jpg", new DataPart("ImageStruk.jpg", mRequestBody.getFileUpload().get("input_struk"), "image/jpeg"));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(500000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(multipartRequest);
    }
    public void volleyLogin(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        final mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh((Activity) context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(context, "Please press the button again", Toast.LENGTH_SHORT).show();
//                                        login();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else {
                    Toast.makeText(context, "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void volleyCheckVersion(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh((Activity)context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                       new SplashActivity().checkVersion(context);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else {
                    Toast.makeText(context, "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void volleyDownloadData(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh((Activity)context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            ToastCustom.showToasty(context,message,4);
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(context, "Please press the button again", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else {
                    Toast.makeText(context, "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void volleyDownloadDataKLB(final Context context, String strLinkAPI, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        StringRequest request = new StringRequest(Request.Method.GET, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finalDialog1.dismiss();
                } else {
                    ToastCustom.showToasty(context,"Failed Download Data, please check your connection",4);
//                    Toast.makeText(context, "Failed Download Data Doctor/Pharmacy", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                mConfigRepo configRepo = new mConfigRepo(context);
                mConfigData UserName = null;
                mConfigData Passoword = null;
                try {
                     UserName = (mConfigData) configRepo.findById(9);
                     Passoword = (mConfigData) configRepo.findById(10);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                Map<String,String> headers = SyncStateContract.Constants.getHeaders(context);
                HashMap<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = UserName.getTxtDefaultValue().toString() +":"+Passoword.getTxtDefaultValue().toString();
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
//        request.setRetryPolicy(new
//                DefaultRetryPolicy(60000,
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }
}
