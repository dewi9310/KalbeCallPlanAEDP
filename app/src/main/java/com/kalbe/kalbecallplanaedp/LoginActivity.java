package com.kalbe.kalbecallplanaedp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.Common.clsLogin;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mConfigData;
import com.kalbe.kalbecallplanaedp.Common.mMenuData;
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
import com.kalbe.mobiledevknlibs.InputFilter.InputFilters;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;


import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ACCOUNT_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;


/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class LoginActivity extends AccountAuthenticatorActivity {
    Account availableAccounts[];
    String name[];
    private String mAuthTokenType;
    private AlertDialog mAlertDialog;
    private AccountManager mAccountManager;
    private final String TAG = this.getClass().getSimpleName();
    //batas aman

    private static final int REQUEST_READ_PHONE_STATE = 0;
    private String IS_FROM_PICK_ACCOUNT = "is from pick account";
    EditText etUsername, etPassword;
    private AppCompatSpinner spnRoleLogin;
    String txtUsername, txtPassword, imeiNumber, deviceName, access_token, selectedRole;
    String clientId = "";
    Button btnSubmit, btnExit, btnRefreshApp;
//    Spinner spnRole;
    private final List<String> roleName = new ArrayList<String>();
    private int intSet = 1;
    int intProcesscancel = 0;
    private HashMap<String, Integer> HMRole = new HashMap<>();
    List<clsToken> dataToken;
    mUserLoginRepo loginRepo;
    clsTokenRepo tokenRepo;
    mMenuRepo menuRepo;
    boolean isFromPickAccount = false;

    @Override
    public void onBackPressed() {
        if (isFromPickAccount){
            new AuthenticatorUtil().showAccountPicker(LoginActivity.this, mAccountManager, AUTHTOKEN_TYPE_FULL_ACCESS);
        }else {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));
        }

        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        //change view
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int height = size.y;
//        int heightForm =(int) Math.round(0.3 * height);
//        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.ln_form_login2);
//
//        // get layout parameters for that view
//        ViewGroup.LayoutParams params = mainLayout.getLayoutParams();
//
//        // change height of the params e.g. 480dp
//        params.height = heightForm;
//
//        // initialize new parameters for my element
//        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(params));

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

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        isFromPickAccount = getIntent().getBooleanExtra(IS_FROM_PICK_ACCOUNT, false);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AUTHTOKEN_TYPE_FULL_ACCESS;


        etUsername = (EditText) findViewById(R.id.editTextUsername);
        etPassword = (EditText) findViewById(R.id.editTextPass);
        spnRoleLogin = (AppCompatSpinner) findViewById(R.id.spnRoleLogin);
        btnSubmit = (Button) findViewById(R.id.buttonLogin);
//        btnExit = (Button) findViewById(R.id.buttonExit);
        btnRefreshApp = (Button) findViewById(R.id.buttonRefreshApp);
//        spnRole = (Spinner) findViewById(R.id.spnRole);
//        final CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        char[] chars = {'.'};
        InputFilters.etCapsTextWatcherNoSpaceAtFirst(etUsername, null, chars);
        spnRoleLogin.setEnabled(false);
        if (accountName != null){
            etUsername.setText(accountName);
        }
//
        try {
            loginRepo = new mUserLoginRepo(getApplicationContext());
            tokenRepo = new clsTokenRepo(getApplicationContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            if (dataToken.size() == 0) {
                requestToken(this, true);
            }else if (isFromPickAccount==false){
                checkVersion(this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Spinner Drop down elements

        roleName.add("Select One");

        etUsername.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    intProcesscancel = 0;
                    txtUsername = etUsername.getText().toString();
                    txtPassword = etPassword.getText().toString();
                    if (!txtUsername.equals("")) {
                        getRole();
                    } else {
                        etUsername.requestFocus();
                        ToastCustom.showToasty(LoginActivity.this,"Please input username",4);
//                        ToastCustom.showToastSPGMobile(LoginActivity.this, "Please input username", false);
                    }
                    return true;
                }
                return false;
            }
        });

        // Creating adapter for spinnerTelp
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roleName);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Initializing an ArrayAdapter with initial text like select one
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_dropdown_item, roleName){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // attaching data adapter to spinner
        spnRoleLogin.setAdapter(spinnerArrayAdapter);

        spnRoleLogin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRole = spnRoleLogin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // put code here
            }
        });

        etPassword.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etPassword) {
            public boolean onDrawableClick() {
                if (intSet == 1) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_lock,0);
                    intSet = 0;
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_lock_close,0);
                    intSet = 1;
                }

                return true;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressView.setVisibility(View.VISIBLE);
//                progressView.startAnimation();

                if (etUsername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if (etPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    popupSubmit();
                }
            }
        });
//
//        btnExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//
//                builder.setTitle("Exit");
//                builder.setMessage("Are you sure to exit?");
//
//                builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//
//                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });
//
//        checkVersion();
        btnRefreshApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Ping", Toast.LENGTH_SHORT).show();
//                requestToken(LoginActivity.this);
            }
        });
    }

    private void popupSubmit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are You sure?");

        builder.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                login();
                dialog.dismiss();
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

    // sesuaikan username dan password dengan data di server
    private void login() {
        txtUsername = etUsername.getText().toString();
        txtPassword = etPassword.getText().toString();
        int intRoleId = HMRole.get(spnRoleLogin.getSelectedItem());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        final String now = dateFormat.format(cal.getTime()).toString();
        String strLinkAPI = new clsHardCode().linkLogin;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();

        try {
            jData.put("username",txtUsername );
            jData.put("intRoleId", intRoleId);
            jData.put("password", txtPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new clsTokenRepo(getApplicationContext());
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
        final String accountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        final boolean newAccount = getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false);

        final Bundle datum = new Bundle();
         new clsHelperBL().volleyLogin(LoginActivity.this, strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
//                            listMenu(LoginActivity.this);

                            datum.putString(AccountManager.KEY_ACCOUNT_NAME, txtUsername);
                            datum.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                            datum.putString(AccountManager.KEY_AUTHTOKEN, accessToken);
                            datum.putString(PARAM_USER_PASS, txtPassword);
                            datum.putString(ARG_AUTH_TYPE, mAuthTokenType);
                            datum.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
                            res = new Intent();
                            res.putExtras(datum);
                            finishLogin(res, mAccountManager);

                            Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                            finish();
                            startActivity(intent);

                        } else {
                            ToastCustom.showToasty(LoginActivity.this,txtMessage,4);
//                            Toast.makeText(getApplicationContext(), txtMessage, Toast.LENGTH_SHORT).show();
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

    public void finishLogin(Intent intent, AccountManager mAccountManager) {
        Log.d("kalbe", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        String authtokenType = intent.getStringExtra(ARG_AUTH_TYPE);
            if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
                Log.d("kalbe", TAG + "> finishLogin > addAccountExplicitly");
                // Creating the account on the device and setting the auth token we got
                // (Not setting the auth token will cause another call to the server to authenticate the user)
                mAccountManager.addAccountExplicitly(account, accountPassword, null);
                mAccountManager.setAuthToken(account, authtokenType, authtoken);
            } else {
                Log.d("kalbe", TAG + "> finishLogin > setPassword");
                mAccountManager.setPassword(account, accountPassword);
            }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void checkVersion(final Context context){
        String txtVersionName = null;
        try {
            txtVersionName = context.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mConfigData dtMConfigData = null;
        try {
             dtMConfigData = (mConfigData) new mConfigRepo(context).findById(7);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String strLinkAPI = new clsHardCode().LinkMobileVersion;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jData.put("version_name",txtVersionName );
            jData.put("application_name", dtMConfigData.getTxtValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new clsTokenRepo(context);
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
        new clsHelperBL().volleyCheckVersion(context, strLinkAPI, mRequestBody, "Checking your version......", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response!=null){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        JSONObject jsn = jsonObject.getJSONObject("result");
                        String txtStatus = jsn.getString("status");
                        String txtMessage = jsn.getString("message");
                        String txtMethode_name = jsn.getString("method_name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private void getRole(){
        String strLinkAPI = new clsHardCode().LinkUserRole;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jData.put("username",txtUsername );
            jData.put("intRoleId", 0);
            jData.put("password", txtPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tokenRepo = new clsTokenRepo(getApplicationContext());
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
       new clsHelperBL().volleyCheckVersion(LoginActivity.this, strLinkAPI, mRequestBody, "Getting your role......", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
                        roleName.clear();
                        HMRole.clear();
                        if (txtStatus==true){
                            roleName.add("Select One");
                            if (arrayData != null) {
                                if (arrayData.length()>0){
                                    int index = 0;
                                    for (int i = 0; i < arrayData.length(); i++){
                                        JSONObject object = arrayData.getJSONObject(i);
                                        String txtRoleName = object.getString("txtRoleName");
                                        int intRoleId = object.getInt("intRoleId");
                                        roleName.add(txtRoleName);

                                        mUserRole data = new mUserRole();
                                        data.setTxtId(String.valueOf(index));
                                        data.setIntRoleId(intRoleId);
                                        data.setTxtRoleName(txtRoleName);;
                                        mUserRoleRepo userRoleRepo = new mUserRoleRepo(getApplicationContext());
                                        userRoleRepo.createOrUpdate(data);

                                        HMRole.put(txtRoleName, intRoleId);
                                        index++;
                                    }
                                    spnRoleLogin.setEnabled(true);
                                }else {
                                    spnRoleLogin.setEnabled(false);
                                }
                            }
                        }else {
                            spnRoleLogin.setEnabled(false);
                            ToastCustom.showToasty(LoginActivity.this,txtMessage,4);
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

    public void requestToken(final Activity activity,final boolean isFirst){
        String username = "";
        String strLinkAPI = new clsHardCode().linkToken;

        mConfigRepo configRepo = new mConfigRepo(activity.getApplicationContext());
        tokenRepo = new clsTokenRepo(activity.getApplicationContext());
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            mConfigData configDataUser = (mConfigData) configRepo.findById(5);
            username = configDataUser.getTxtDefaultValue().toString();
            clientId = configDataClient.getTxtDefaultValue().toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new VolleyUtils().makeJsonObjectRequestToken(activity, strLinkAPI, username, "", clientId, "Request Token, Please Wait", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {
                    try {
                        String accessToken = "";
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

                        Log.d("Data info", "get access_token & refresh_token, Success");
                        if (isFirst){
                            checkVersion(activity);
                        }

//                        Toast.makeText(activity.getApplicationContext(), "Ready For Login", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void listMenu(Activity activity) {
        mMenuData menu = new mMenuData();
        menu.setIntId(Integer.parseInt("1"));
        menu.setIntOrder(1);
        menu.setIntParentID(109);
        menu.setTxtDescription("mn1");
        menu.setTxtLink("com.kalbe.project.templatemobile.FragmentFirstMenu");
        menu.setIntMenuID("220");
        menu.setTxtVisible("null");
        menu.setTxtIcon("null");
        menu.setTxtMenuName("Menu ke-1");

        mMenuData menu2 = new mMenuData();
        menu2.setIntId(Integer.parseInt("2"));
        menu2.setIntOrder(4);
        menu2.setIntParentID(47);
        menu2.setTxtDescription("mn2");
//        menu2.setTxtLink("com.kalbe.project.templatemobile.FragmentSecondMenu");
        menu2.setIntMenuID("98");
        menu2.setTxtVisible("null");
        menu2.setTxtIcon("null");
        menu2.setTxtMenuName("Menu ke-2");

        menuRepo = new mMenuRepo(activity.getApplicationContext());
        menuRepo.createOrUpdate(menu);
        menuRepo.createOrUpdate(menu2);
    }
}
