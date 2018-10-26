package com.kalbe.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbe.kalbecallplanaedp.Common.tInfoProgramHeader;
import com.kalbe.kalbecallplanaedp.Data.DatabaseHelper;
import com.kalbe.kalbecallplanaedp.Data.DatabaseManager;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

public class tInfoProgramHeaderRepo implements crud {
    DatabaseHelper helper;

    public tInfoProgramHeaderRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tInfoProgramHeader object = (tInfoProgramHeader) item;
        try {
            index = helper.gettInfoProgramHeaderDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tInfoProgramHeader object = (tInfoProgramHeader) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettInfoProgramHeaderDao().createOrUpdate(object);
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
        tInfoProgramHeader object = (tInfoProgramHeader) item;
        try {
            index = helper.gettInfoProgramHeaderDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tInfoProgramHeader item = null;
        try{
            item = helper.gettInfoProgramHeaderDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tInfoProgramHeader> items = null;
        try{
            items = helper.gettInfoProgramHeaderDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tInfoProgramHeader findByHeaderId(String intHeaderId) throws SQLException {
        tInfoProgramHeader item = new tInfoProgramHeader();
        QueryBuilder<tInfoProgramHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettInfoProgramHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tInfoProgramHeader> getAllPushData () {
        tInfoProgramHeader item = new tInfoProgramHeader();
        List<tInfoProgramHeader> listData = new ArrayList<>();
        QueryBuilder<tInfoProgramHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettInfoProgramHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }
}
