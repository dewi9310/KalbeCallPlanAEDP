package com.kalbe.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbe.kalbecallplanaedp.Common.tMaintenanceHeader;
import com.kalbe.kalbecallplanaedp.Data.DatabaseHelper;
import com.kalbe.kalbecallplanaedp.Data.DatabaseManager;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

public class tMaintenanceHeaderRepo implements crud {
    DatabaseHelper helper;

    public tMaintenanceHeaderRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tMaintenanceHeader object = (tMaintenanceHeader) item;
        try {
            index = helper.gettMaintenanceHeaderDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tMaintenanceHeader object = (tMaintenanceHeader) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettMaintenanceHeaderDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        tMaintenanceHeader object = (tMaintenanceHeader) item;
        try {
            index = helper.gettMaintenanceHeaderDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tMaintenanceHeader item = null;
        try{
            item = helper.gettMaintenanceHeaderDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tMaintenanceHeader> items = null;
        try{
            items = helper.gettMaintenanceHeaderDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tMaintenanceHeader findByHeaderId(String intHeaderId) throws SQLException {
        tMaintenanceHeader item = new tMaintenanceHeader();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tMaintenanceHeader> getAllPushData () {
        tMaintenanceHeader item = new tMaintenanceHeader();
        List<tMaintenanceHeader> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }
}
