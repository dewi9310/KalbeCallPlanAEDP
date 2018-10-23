package com.kalbe.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbe.kalbecallplanaedp.Common.tProgramVisit;
import com.kalbe.kalbecallplanaedp.Data.DatabaseHelper;
import com.kalbe.kalbecallplanaedp.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class tProgramVisitRepo implements crud {
    DatabaseHelper helper;

    public tProgramVisitRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tProgramVisit object = (tProgramVisit) item;
        try {
            index = helper.gettProgramVisitDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tProgramVisit object = (tProgramVisit) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettProgramVisitDao().createOrUpdate(object);
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
        tProgramVisit object = (tProgramVisit) item;
        try {
            index = helper.gettProgramVisitDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tProgramVisit item = null;
        try{
            item = helper.gettProgramVisitDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tProgramVisit> items = null;
        try{
            items = helper.gettProgramVisitDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tProgramVisit findBytxtId(String txtId) throws SQLException {
        tProgramVisit item = new tProgramVisit();
        QueryBuilder<tProgramVisit, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettProgramVisitDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtProgramVisitId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
