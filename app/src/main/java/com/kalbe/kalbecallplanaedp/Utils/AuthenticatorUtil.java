package com.kalbe.kalbecallplanaedp.Utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.stmt.query.In;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.LoginActivity;
import com.kalbe.kalbecallplanaedp.PickAccountActivity;
import com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_AVAILABLE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_DATA_ACCESS_TOKEN;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_DATA_TOKEN;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;

/**
 * Created by Dewi Oktaviani on 1/22/2018.
 */

public class AuthenticatorUtil{
    String[] accounts;
    List<clsToken>dataToken;
    private String IS_FROM_PICK_ACCOUNT = "is from pick account";
    /**
     * Add new account to the account manager
     * @param accountType
     * @param authTokenType
     */
    public void addNewAccount(final Activity context, final AccountManager mAccountManager, final String accountType, final String authTokenType, final boolean isFromPickAccount) {
        Bundle bundles = new Bundle();
        bundles.putString(ARG_ARRAY_DATA_TOKEN, "Add account inside apps");
        final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, "app", null, bundles, context, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();
                    boolean newAccount = bnd.getBoolean(AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT, false);
                    final Account availableAccounts[] = countingAccount(mAccountManager);

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    intent.putExtra(AccountGeneral.ARG_AUTH_TYPE, authTokenType);
                    intent.putExtra(AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
                    intent.putExtra(ARG_ARRAY_ACCOUNT_AVAILABLE, availableAccounts);
                    intent.putExtra(IS_FROM_PICK_ACCOUNT, isFromPickAccount);
                    context.finish();
                    context.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    /**
     * Show all the accounts registered on the account manager. Request an auth token upon user select.
     * @param authTokenType
     */
    public void showAccountPicker(final Activity context, final AccountManager mAccountManager, final String authTokenType) {
        final Account availableAccounts[] = countingAccount(mAccountManager);
        if (availableAccounts.length == 0) {
            addNewAccount(context, mAccountManager, AccountGeneral.ACCOUNT_TYPE, authTokenType, false);
        } else {
            String name[] = new String[availableAccounts.length];
            for (int i = 0; i < availableAccounts.length; i++) {
                name[i] = availableAccounts[i].name;
            }

            Intent myIntent = new Intent(context, PickAccountActivity.class);
            myIntent.putExtra(ARG_ARRAY_ACCOUNT_AVAILABLE, availableAccounts);
            myIntent.putExtra(ARG_ARRAY_ACCOUNT_NAME, name);
            context.finish();
            context.startActivity(myIntent);
        }
    }

    public Account[] countingAccount(final AccountManager mAccountManager){
        final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
        return availableAccounts;
    }
    /**
     * Get the auth token for an existing account on the AccountManager
     * @param account
     * @param authTokenType
     */
    public void getExistingAccountAuthToken(final Activity activity, final AccountManager mAccountManager, final Account account, final String authTokenType, View parent_view) {
        String userName = account.name;
        String accountType = account.type;
        final String password = mAccountManager.getPassword(account);
        accounts = new String[]{userName, password, accountType, authTokenType};
        new PickAccountActivity().getRole(accounts, activity, mAccountManager, parent_view);
        Log.d("kalbe", "GetToken Bundle is ");
    }

    public void RenameAccount(final AccountManager mAccountManager, final Account account, Activity activity, final String accountPassword, final String authtokenType){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mAccountManager.removeAccount(account, activity, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> future) {
                    try {
                        Bundle bundle = future.getResult();
                        String authtoken = "dummy_access_token";
                        mAccountManager.addAccountExplicitly(account, accountPassword, null);
                        mAccountManager.setAuthToken(account, authtokenType, authtoken);
                    } catch (OperationCanceledException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }
    }

}