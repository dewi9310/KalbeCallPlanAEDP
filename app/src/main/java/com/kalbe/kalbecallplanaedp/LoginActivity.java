package com.kalbe.kalbecallplanaedp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserRole;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Repo.clsTokenRepo;
import com.kalbe.kalbecallplanaedp.Repo.mMenuRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserRoleRepo;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbe.kalbecallplanaedp.Utils.AuthenticatorUtil;
import com.kalbe.kalbecallplanaedp.Utils.DrawableClickListener;
import com.kalbe.mobiledevknlibs.InputFilter.InputFilters;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ACCOUNT_NAME;
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
    private Gson gson;

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

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

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
        // Spinner Drop down elements

        roleName.add("Select One");
        HMRole.put("Select One", 0);

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
                        etUsername.requestFocusFromTouch();
                        ToastCustom.showToasty(LoginActivity.this,"Please input username",4);
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
                    tv.setTextColor(getResources().getColor(R.color.green_300));
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
                ToastCustom.showToasty(LoginActivity.this,"Please select role",4);
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

        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                            keyCode == KeyEvent.KEYCODE_ENTER) {
                        btnSubmit.performClick();
                        return true;
                    }
                }

                return false;
            }
        });

        etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSubmit.performClick();
                    return true;
                }
                return false;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etUsername.getText().toString().equals("")){
                    ToastCustom.showToasty(LoginActivity.this,"Please fill Username",4);
                }else if (etPassword.getText().toString().equals("")){
                    ToastCustom.showToasty(LoginActivity.this,"Please fill Password",4);
                } else if (roleName.size()==1){
                    getRole();
                } else if (HMRole.get(spnRoleLogin.getSelectedItem())==0){
                    ToastCustom.showToasty(LoginActivity.this,"Please select role",4);
                }
                else {
                    popupSubmit();
                }
            }
        });
//
//        checkVersion();
        btnRefreshApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new clsHardCode().copydb(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                        LoginMobileApps model = gson.fromJson(jsonObject.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();

                        String accessToken = "dummy_access_token";

                        if (txtStatus == true){
                            loginRepo = new mUserLoginRepo(getApplicationContext());
                            mUserLogin data = new mUserLogin();
                            data.setIntUserID(model.getData().getIntUserID());
                            data.setTxtUserName(model.getData().getTxtUserName());
                            data.setTxtNick(model.getData().getTxtNick());
                            data.setTxtEmpID(model.getData().getTxtEmpID());
                            data.setTxtEmail(model.getData().getTxtEmail());
                            data.setIntDepartmentID(model.getData().getIntDepartmentID());
                            data.setIntLOBID(model.getData().getIntLOBID());
                            data.setTxtCompanyCode(model.getData().getTxtCompanyCode());
                            if (model.getData().getMUserRole()!=null){
                                data.setIntRoleID(model.getData().getMUserRole().getIntRoleID());
                                data.setTxtRoleName(model.getData().getMUserRole().getTxtRoleName());
                            }
                            data.setDtLogIn(parseDate(model.getData().getDtDateLogin()));
                            loginRepo.createOrUpdate(data);

                            Log.d("Data info", "Login Success");

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

    private String parseDate(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return "";
        }
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

    private void getRole(){
        String strLinkAPI = new clsHardCode().LinkUserRole;
        JSONObject resJson = new JSONObject();
        JSONObject jData = new JSONObject();
        txtUsername = etUsername.getText().toString();
        txtPassword = etPassword.getText().toString();
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
       new clsHelperBL().volleyLogin(LoginActivity.this, strLinkAPI, mRequestBody, "Getting your role......", new VolleyResponseListener() {
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
                            HMRole.put("Select One", 0);
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
                                }
                                else {
                                    spnRoleLogin.setEnabled(false);
                                }
                            }
                        }else {
                            spnRoleLogin.setEnabled(false);
                            etUsername.requestFocus();
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
}
