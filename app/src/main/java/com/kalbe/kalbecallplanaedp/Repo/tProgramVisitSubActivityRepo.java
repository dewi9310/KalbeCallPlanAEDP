package com.kalbe.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import com.kalbe.kalbecallplanaedp.Data.DatabaseHelper;
import com.kalbe.kalbecallplanaedp.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class tProgramVisitSubActivityRepo implements crud{
    DatabaseHelper helper;

    public tProgramVisitSubActivityRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tProgramVisitSubActivity object = (tProgramVisitSubActivity) item;
        try {
            index = helper.gettProgramVisitSubActivityDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tProgramVisitSubActivity object = (tProgramVisitSubActivity) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettProgramVisitSubActivityDao().createOrUpdate(object);
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
        tProgramVisitSubActivity object = (tProgramVisitSubActivity) item;
        try {
            index = helper.gettProgramVisitSubActivityDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tProgramVisitSubActivity item = null;
        try{
            item = helper.gettProgramVisitSubActivityDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tProgramVisitSubActivity> items = null;
        try{
            items = helper.gettProgramVisitSubActivityDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tProgramVisitSubActivity findBytxtId(String txtId) throws SQLException {
        tProgramVisitSubActivity item = new tProgramVisitSubActivity();
        QueryBuilder<tProgramVisitSubActivity, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettProgramVisitSubActivityDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtProgramVisitSubActivityId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }



}
