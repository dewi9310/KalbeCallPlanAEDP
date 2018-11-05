package com.kalbe.kalbecallplanaedp.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.Common.clsPushData;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.SplashActivity;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rian Andrivani on 12/7/2017.
 */

public class VolleyUtils {
    String access_token,clientId = "";
    List<clsToken> dataToken;
    public void makeJsonObjectRequestToken(final Activity activity, String strLinkAPI, final String username, final String password, final String clientId, String progressBarType, final VolleyResponseListener listener) {
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
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
                String body, message;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("Message");
                        Toast.makeText(activity.getApplicationContext(), "Error 401 " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finalDialog1.dismiss();
                    if (error.getMessage() != null) {
                        listener.onError(error.getMessage());
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("error_description");
                        Toast.makeText(activity.getApplicationContext(), "Error 400, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finalDialog1.dismiss();
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR ){
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                } else {
                    popup();
                    finalDialog1.dismiss();
                }
            }
            public void popup() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

                builder.setTitle("Request Time Out");
                builder.setMessage("You Have to request again");

                builder.setPositiveButton("REFRESH", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new SplashActivity().requestToken(activity);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();

                alert.setCanceledOnTouchOutside(false);
                alert.setCancelable(false);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("username", username);
                params.put("password", password);
                params.put("client_id", clientId);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }

    public void requestTokenWithRefresh(final Activity activity, String strLinkAPI, final String refreshToken, final String clientId, final VolleyResponseListener listener) {
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body, message;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("Message");
                        Toast.makeText(activity.getApplicationContext(), "Error 401, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (error.getMessage() != null) {
                        listener.onError(error.getMessage());
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.optString("error_description");
                        if (message.equals("")) {
                            message = jsonObject.optString("error");
                        }
                        Toast.makeText(activity.getApplicationContext(), "Error 400, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR ){
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Something Error, please request again", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "refresh_token");
                params.put("client_id", clientId);
                params.put("refresh_token", refreshToken);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }
    public void makeJsonObjectRequestPushData(final Context ctx, String strLinkAPI, final clsPushData mRequestBody, final VolleyResponseListener listener) {
//        strLinkAPI =  strLinkAPI+"?txtParam=\"test\"";

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
                error.printStackTrace();
                NetworkResponse networkResponse = error.networkResponse;
//                int a = networkResponse.statusCode;
                if (networkResponse==null){
                    ToastCustom.showToasty(ctx,"Please check your connection...",4);
                }else {
                    listener.onError(error.getMessage());
                    try {
                        String responseBody = new String( error.networkResponse.data, "utf-8" );
                        JSONObject jsonObject = new JSONObject( responseBody );
                    } catch ( JSONException e ) {
                        //Handle a malformed json response
                        String b = "hasd";
                    } catch (UnsupportedEncodingException e){
                        String c = "hasd";
                    }
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = new clsHelperBL().getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
//                access_token = "BRIVeCejVsSyXviEg56KyrqRl3ZjhrK7qanAeIEsJGJYWQhjhTVk-DHV7Mlsbdsx3ddSPB-zxBmRpoIynoA7tU2rU5qnmgT6-4aGjdF5XS__rVPcZDdqyTRIFSbW9CkAMX476bCdUZwnzr_5uCocTPgpPupl-ppyJ2GRm2n3rzNDDlgxYlS4raRDBUSwl_Bdicy9OfDr2Idci-5Kfnx5yYUOGUxGh6msTpP9fFpc4WkJR2CdLWNsZgcZRYhZBjNhx9TOwgki1LXFdVzbpEy1u_7FyQ3bJuKCo6k3rwg-i21IOF0BjXJYVhluFLpAkZQW81NyJfRYMlAeUAFMQcc_PS8zbmfuMIm-EJi_qj2Y_mJogttj-8sn7Vd-qLLJKnHU";
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
                            String fileName = mRequestBody.getFileName().get(i).toString();
                            params.put(fileName, new DataPart(fileName + ".jpg", mRequestBody.getFileUpload().get(fileName),"image/jpeg"));
//                            params.put("image1", new DataPart("file_image1.jpg", mRequestBody.getFileUpload().get("FUAbsen-1"), "image/jpeg"));
                        }

                    }
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(50000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
    }
}
