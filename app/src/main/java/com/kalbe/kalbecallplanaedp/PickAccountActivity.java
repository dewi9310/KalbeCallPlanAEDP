package com.kalbe.kalbecallplanaedp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.kalbe.kalbecallplanaedp.Common.clsLogin;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mConfigData;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserRole;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.VolleyUtils;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Repo.clsLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.clsTokenRepo;
import com.kalbe.kalbecallplanaedp.Repo.mConfigRepo;
import com.kalbe.kalbecallplanaedp.Repo.mMenuRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserRoleRepo;
import com.kalbe.kalbecallplanaedp.adapter.CardAppAdapter;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_AVAILABLE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;

public class PickAccountActivity extends Activity {

    Account availableAccounts[];
    String name[];
    private String mAuthTokenType;
    private AlertDialog mAlertDialog;
    private AccountManager mAccountManager;
    private final String TAG = this.getClass().getSimpleName();
    //batas aman

    private static final int REQUEST_READ_PHONE_STATE = 0;
    EditText etUsername, etPassword;
    String txtUsername, txtPassword, imeiNumber, deviceName, access_token;
    String clientId = "";
    Button btnSubmit, btnExit;
    private HashMap<String, Integer> HMRole = new HashMap<>();
    private String single_choice_selected;
    private String[] role;
    private int intSet = 1;
    int intProcesscancel = 0;
    List<clsToken> dataToken;
    mUserLoginRepo loginRepo;
    clsTokenRepo tokenRepo;
    mMenuRepo menuRepo;
    ListView listView;
    View parent_view;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Are you sure to exit?");

        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        setContentView(R.layout.activity_pick_account);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

            imeiNumber = tm.getDeviceId().toString();
            deviceName = Build.MANUFACTURER+" "+ Build.MODEL;
        } else {
            //TODO
            imeiNumber = tm.getDeviceId().toString();
            deviceName = Build.MANUFACTURER+" "+ Build.MODEL;
        }

        mAccountManager = AccountManager.get(getBaseContext());
//        try {
//            tokenRepo = new clsTokenRepo(getApplicationContext());
//            dataToken = (List<clsToken>) tokenRepo.findAll();
//            if (dataToken.size() == 0) {
//                new LoginActivity().requestToken(this, true);
//            }else {
//                new LoginActivity().checkVersion(this);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;

        listView = (ListView) findViewById(R.id.lvPickAccount);
        parent_view = findViewById(android.R.id.content);

        String[] names = getIntent().getStringArrayExtra(ARG_ARRAY_ACCOUNT_NAME);
        Parcelable[] parceAccount = getIntent().getParcelableArrayExtra(ARG_ARRAY_ACCOUNT_AVAILABLE);
        final List<String> account = new ArrayList<>();
        List<Integer> icon = new ArrayList<>();
        if (parceAccount!=null){
            availableAccounts = Arrays.copyOf(parceAccount, parceAccount.length, Account[].class);
            if (availableAccounts.length>0){
                name = new String[names.length+1];
                for (int i=0; i<names.length; i++){
                    account.add(names[i]);
                    icon.add(R.drawable.profile);
                }
                account.add("Add New Account");
                icon.add(R.drawable.add_pick_acount);
            }
        }

        listView.setAdapter(new CardAppAdapter(this,  account, icon));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (account.get(position).equals("Add New Account")){
                    new AuthenticatorUtil().addNewAccount(PickAccountActivity.this, mAccountManager, AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, true);
                } else {
                    new AuthenticatorUtil().getExistingAccountAuthToken(PickAccountActivity.this, mAccountManager,availableAccounts[position], AUTHTOKEN_TYPE_FULL_ACCESS, parent_view);
                }
            }
        });
    }

    public void getRole(final String[] data_token, final Activity activity, final AccountManager mAccountManager, View parent_view){
        String strLinkAPI = new clsHardCode().LinkUserRole;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        txtUsername = data_token[0];
        txtPassword = data_token[1];
        try {
            jData.put("username",txtUsername );
            jData.put("intRoleId", 0);
            jData.put("password", txtPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new clsTokenRepo(activity);
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", jData);
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyCheckVersion(activity, strLinkAPI, mRequestBody, "Getting your role......", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response!=null){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        JSONObject jsn = jsonObject.getJSONObject("result");
                        boolean txtStatus = jsn.getBoolean("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");
                        JSONArray arrayData = jsonObject.getJSONArray("data");
                        if (txtStatus==true){
                            if (arrayData != null) {
                                if (arrayData.length()>0){
                                    int index = 0;
                                    role = new String[arrayData.length()];
                                    for (int i = 0; i < arrayData.length(); i++){
                                        JSONObject object = arrayData.getJSONObject(i);
                                        String txtRoleName = object.getString("txtRoleName");
                                        int intRoleId = object.getInt("intRoleId");
                                        role[i] = txtRoleName;

                                        mUserRole data = new mUserRole();
                                        data.setTxtId(String.valueOf(index));
                                        data.setIntRoleId(intRoleId);
                                        data.setTxtRoleName(txtRoleName);;
                                        mUserRoleRepo userRoleRepo = new mUserRoleRepo(activity.getApplicationContext());
                                        userRoleRepo.createOrUpdate(data);

                                        HMRole.put(txtRoleName, intRoleId);
                                        index++;
                                    }

                                    single_choice_selected = "";
                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                                    builder.setTitle("ROLE");
                                    builder.setSingleChoiceItems(role, -1, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            single_choice_selected = role[i];
                                        }
                                    });
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            login(data_token, HMRole.get(single_choice_selected), activity, mAccountManager);
                                        }
                                    });
                                    builder.setNegativeButton("CANCEL", null);
                                    builder.show();
                                }else {
//                                    spnRoleLogin.setEnabled(false);
                                }
                            }
                        }else {
                            ToastCustom.showToasty(activity.getApplicationContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

//    private void showSingleChoiceDialog(final  Activity activity, final View parent_view) {
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
//        builder.setTitle("Phone Ringtone");
//        builder.setSingleChoiceItems(RINGTONE, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                single_choice_selected = RINGTONE[i];
//            }
//        });
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                Snackbar.make(parent_view, "selected : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();
//                login();
//            }
//        });
//        builder.setNegativeButton("CANCEL", null);
//        builder.show();
//    }
    public void login(final String[] data_token, int txtRoleName, final Activity activity, final AccountManager mAccountManager) {
        txtUsername = data_token[0];
        txtPassword = data_token[1];
        int intRoleId = txtRoleName;
        String strLinkAPI = new clsHardCode().linkLogin;
        JSONObject resJson = new JSONObject();
        this.mAccountManager = mAccountManager;

        JSONObject jData = new JSONObject();

        try {
            jData.put("username",txtUsername );
            jData.put("intRoleId", intRoleId);
            jData.put("password", txtPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            loginRepo = new mUserLoginRepo(activity.getApplicationContext());
            tokenRepo = new clsTokenRepo(activity.getApplicationContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", jData);
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        final String accountType = data_token[2];
        final boolean newAccount = false;
        final Bundle datum = new Bundle();
        volleyLogin(activity, strLinkAPI, mRequestBody, data_token, txtRoleName, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsn = jsonObject.getJSONObject("result");
                        boolean txtStatus = jsn.getBoolean("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");

                        String accessToken = "dummy_access_token";

                        if (txtStatus == true){
                            JSONObject objData = jsonObject.getJSONObject("data");
                            mUserLogin data = new mUserLogin();
                            data.setIntUserID(objData.getInt("IntUserID"));
                            data.setTxtUserName(objData.getString("TxtUserName"));
                            data.setTxtNick(objData.getString("TxtNick"));
                            data.setTxtEmpID(objData.getString("TxtEmpID"));
                            data.setTxtEmail(objData.getString("TxtEmail"));
                            data.setIntDepartmentID(objData.getString("IntDepartmentID"));
                            data.setIntLOBID(objData.getString("IntLOBID"));
                            data.setTxtCompanyCode(objData.getString("TxtCompanyCode"));
                            loginRepo.createOrUpdate(data);

                            Log.d("Data info", "Login Success");
                            new LoginActivity().listMenu(activity);

                            datum.putString(AccountManager.KEY_ACCOUNT_NAME, txtUsername);
                            datum.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                            datum.putString(AccountManager.KEY_AUTHTOKEN, accessToken);
                            datum.putString(PARAM_USER_PASS, txtPassword);
                            datum.putString(ARG_AUTH_TYPE, data_token[3]);
                            datum.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
                            res = new Intent();
                            res.putExtras(datum);
                            new LoginActivity().finishLogin(res, mAccountManager);

                            Intent intent = new Intent(activity, MainMenu.class);
                            activity.finish();
                            activity.startActivity(intent);

                        } else {
                            ToastCustom.showToasty(activity.getApplicationContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void volleyLogin(final Activity activity, String strLinkAPI, final String mRequestBody, final String[] data_token, final int txtRoleName, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        mConfigRepo configRepo = new mConfigRepo(activity.getApplicationContext());
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = (List<clsToken>) tokenRepo.findAll();
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

                    new VolleyUtils().requestTokenWithRefresh(activity, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(activity.getApplicationContext(), "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        login(data_token, txtRoleName, activity, mAccountManager);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
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
                try {
                    tokenRepo = new clsTokenRepo(activity.getApplicationContext());
                    dataToken = (List<clsToken>) tokenRepo.findAll();
                    access_token = dataToken.get(0).getTxtUserToken();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

    private void logout() {
        Intent intent = new Intent(PickAccountActivity.this, SplashActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Account[] tes = new AuthenticatorUtil().countingAccount(mAccountManager);
        int countlistView = listView.getAdapter().getCount() -1;
        if (tes.length<countlistView){
//            logout();
        }
        super.onResume();
    }
}
