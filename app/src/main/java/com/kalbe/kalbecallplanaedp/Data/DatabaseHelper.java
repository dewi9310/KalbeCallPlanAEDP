package com.kalbe.kalbecallplanaedp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kalbe.kalbecallplanaedp.Common.clsLogin;
import com.kalbe.kalbecallplanaedp.Common.clsPhotoProfile;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mConfigData;
import com.kalbe.kalbecallplanaedp.Common.mMenuData;
import com.kalbe.kalbecallplanaedp.Common.mProduct;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mTypeSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.mUserRole;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Common.tOrderDetail;
import com.kalbe.kalbecallplanaedp.Common.tOrderHeader;


import java.sql.SQLException;

/**
 * Created by Rian Andrivani on 11/21/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static clsHardCode _path = new clsHardCode();
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = _path.dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    protected Dao<mConfigData, Integer> mConfigDao;

    protected Dao<clsLogin, Integer> loginDao;
    protected RuntimeExceptionDao<clsLogin, Integer> loginRuntimeDao = null;

    protected Dao<mMenuData, Integer> menuDao;
    protected RuntimeExceptionDao<mMenuData, Integer> menuRuntimeDao = null;

    protected Dao<clsPhotoProfile, Integer> profileDao;
    protected RuntimeExceptionDao<clsPhotoProfile, Integer> profileRuntimeDao;

    protected Dao<mProduct, Integer> productDao;
    protected RuntimeExceptionDao<mProduct, Integer> productRuntimeDao;

    protected Dao<tOrderHeader, Integer> orderHeaderDao;
    protected RuntimeExceptionDao<tOrderHeader, Integer> orderHeaderRuntimeDao;

    protected Dao<tOrderDetail, Integer> orderDetailDao;
    protected RuntimeExceptionDao<tOrderDetail, Integer> orderDetailRuntimeDao;

    protected Dao<clsToken, Integer> tokenDao;
    protected RuntimeExceptionDao<clsToken, Integer> tokenRuntimeDao;

    protected Dao<mUserRole, Integer> mUserRolesDao;
    protected RuntimeExceptionDao<clsToken, Integer> mUserRolesRuntimeDao;

    protected Dao<mUserLogin, Integer> mUserLoginsDao;
    protected RuntimeExceptionDao<clsToken, Integer> mUserLoginsRuntimeDao = null;

    protected Dao<tAkuisisiHeader, Integer> tAkuisisiHeaderDao;
    protected RuntimeExceptionDao<clsToken, Integer> tAkuisisiHeaderRuntimeDao;

    protected Dao<tAkuisisiDetail, Integer> tAkuisisiDetailDao;
    protected RuntimeExceptionDao<clsToken, Integer> tAkuisisiDetailRuntimeDao;

    protected Dao<mActivity, Integer> mActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mActivityRuntimeDao;

    protected Dao<mSubActivity, Integer> mSubActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mSubActivityRuntimeDao;

    protected Dao<mSubSubActivity, Integer> mSubSubActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mSubSubActivityRuntimeDao;

    protected Dao<mTypeSubSubActivity, Integer> mTypeSubSubActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mTypeSubSubActivityRuntimeDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, mConfigData.class);
//            TableUtils.createTableIfNotExists(connectionSource, clsLogin.class);
            TableUtils.createTableIfNotExists(connectionSource, clsToken.class);
            TableUtils.createTableIfNotExists(connectionSource, mMenuData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsPhotoProfile.class);
            TableUtils.createTableIfNotExists(connectionSource, mUserRole.class);
            TableUtils.createTableIfNotExists(connectionSource, mUserLogin.class);
            TableUtils.createTableIfNotExists(connectionSource, tAkuisisiHeader.class);
            TableUtils.createTableIfNotExists(connectionSource, tAkuisisiDetail.class);
            TableUtils.createTableIfNotExists(connectionSource, mActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mSubActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mSubSubActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mTypeSubSubActivity.class);

            //calon di delete
//            TableUtils.createTableIfNotExists(connectionSource, mProduct.class);
//            TableUtils.createTableIfNotExists(connectionSource, tOrderHeader.class);
//            TableUtils.createTableIfNotExists(connectionSource, tOrderDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Dao<clsLogin, Integer> dao = null;
        try {
            dao = getLoginDao();

//            if (oldVersion < 2) {
//                dao.executeRaw("ALTER TABLE `clsLogin` ADD COLUMN txtRefreshToken TEXT;");
//            }
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, mConfigData.class, true);
//            TableUtils.dropTable(connectionSource, clsLogin.class, true);
            TableUtils.dropTable(connectionSource, mMenuData.class, true);
            TableUtils.dropTable(connectionSource, clsPhotoProfile.class, true);


//            TableUtils.dropTable(connectionSource, mProduct.class, true);
//            TableUtils.dropTable(connectionSource, tOrderHeader.class, true);
//            TableUtils.dropTable(connectionSource, tOrderDetail.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDataAfterLogout(){
        try {
//            TableUtils.clearTable(connectionSource, clsLogin.class);
            TableUtils.clearTable(connectionSource, mMenuData.class);
            TableUtils.clearTable(connectionSource, clsPhotoProfile.class);
            TableUtils.clearTable(connectionSource, mUserRole.class);
            TableUtils.clearTable(connectionSource, mUserLogin.class);
            TableUtils.clearTable(connectionSource, tAkuisisiHeader.class);
            TableUtils.clearTable(connectionSource, tAkuisisiDetail.class);
            TableUtils.clearTable(connectionSource, mActivity.class);
            TableUtils.clearTable(connectionSource, mSubActivity.class);
            TableUtils.clearTable(connectionSource, mSubSubActivity.class);
            TableUtils.clearTable(connectionSource, mTypeSubSubActivity.class);

            //calon di delete
//            TableUtils.clearTable(connectionSource, mProduct.class);
//            TableUtils.clearTable(connectionSource, tOrderHeader.class);
//            TableUtils.clearTable(connectionSource, tOrderDetail.class);
            // after we drop the old databases, we create the new ones
//            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<mConfigData, Integer> getmConfigDao() throws SQLException {
        if (mConfigDao == null) {
            mConfigDao = getDao(mConfigData.class);
        }
        return mConfigDao;
    }

    public Dao<clsToken, Integer> getTokenDao() throws SQLException {
        if (tokenDao == null) {
            tokenDao = getDao(clsToken.class);
        }
        return tokenDao;
    }

    public Dao<clsLogin, Integer> getLoginDao() throws SQLException {
        if (loginDao == null) {
            loginDao = getDao(clsLogin.class);
        }
        return loginDao;
    }

    public Dao<mMenuData, Integer> getMenuDao() throws SQLException {
        if (menuDao == null) {
            menuDao = getDao(mMenuData.class);
        }
        return menuDao;
    }

    public Dao<clsPhotoProfile, Integer> getProfileDao() throws SQLException {
        if (profileDao == null) {
            profileDao = getDao(clsPhotoProfile.class);
        }
        return profileDao;
    }

    public Dao<mUserRole, Integer> getmUserRolesDao() throws SQLException {
        if (mUserRolesDao == null) {
            mUserRolesDao = getDao(mUserRole.class);
        }
        return mUserRolesDao;
    }

    public Dao<mUserLogin, Integer> getmUserLoginsDao() throws SQLException {
        if (mUserLoginsDao == null) {
            mUserLoginsDao = getDao(mUserLogin.class);
        }
        return mUserLoginsDao;
    }

    public Dao<tAkuisisiHeader, Integer> getAkuisisiHeaderDao() throws SQLException {
        if (tAkuisisiHeaderDao == null) {
            tAkuisisiHeaderDao = getDao(tAkuisisiHeader.class);
        }
        return tAkuisisiHeaderDao;
    }

    public Dao<tAkuisisiDetail, Integer> getAkuisisiDetailDao() throws SQLException {
        if (tAkuisisiDetailDao == null) {
            tAkuisisiDetailDao = getDao(tAkuisisiDetail.class);
        }
        return tAkuisisiDetailDao;
    }

    public Dao<mActivity, Integer> getmActivityDao() throws SQLException {
        if (mActivityDao == null) {
            mActivityDao = getDao(mActivity.class);
        }
        return mActivityDao;
    }

    public Dao<mSubActivity, Integer> getmSubActivityDao() throws SQLException {
        if (mSubActivityDao == null) {
            mSubActivityDao = getDao(mSubActivity.class);
        }
        return mSubActivityDao;
    }

    public Dao<mSubSubActivity, Integer> getmSubSubActivityDao() throws SQLException {
        if (mSubSubActivityDao == null) {
            mSubSubActivityDao = getDao(mSubSubActivity.class);
        }
        return mSubSubActivityDao;
    }

    public Dao<mTypeSubSubActivity, Integer> getmTypeSubSubActivityDao() throws SQLException {
        if (mTypeSubSubActivityDao == null) {
            mTypeSubSubActivityDao = getDao(mTypeSubSubActivity.class);
        }
        return mTypeSubSubActivityDao;
    }


//calon di delete
    public Dao<mProduct, Integer> getProductDao() throws SQLException {
        if (productDao == null) {
            productDao = getDao(mProduct.class);
        }
        return productDao;
    }

    public Dao<tOrderHeader, Integer> getOrderHeaderDao() throws SQLException {
        if (orderHeaderDao == null) {
            orderHeaderDao = getDao(tOrderHeader.class);
        }
        return orderHeaderDao;
    }

    public Dao<tOrderDetail, Integer> getOrderDetailDao() throws SQLException {
        if (orderDetailDao == null) {
            orderDetailDao = getDao(tOrderDetail.class);
        }
        return orderDetailDao;
    }



    @Override
    public void close() {
        mConfigDao = null;
        loginDao = null;
        tokenDao = null;
        menuDao = null;
        profileDao = null;
        tAkuisisiHeaderDao = null;
        tAkuisisiDetailDao = null;
        mUserLoginsDao = null;
        mUserRolesDao = null;
        mActivityDao = null;
        mSubActivityDao = null;
        mSubSubActivityDao = null;
        mTypeSubSubActivityDao = null;

        //delete
        productDao = null;
        orderHeaderDao = null;
        orderDetailDao = null;

    }
}
