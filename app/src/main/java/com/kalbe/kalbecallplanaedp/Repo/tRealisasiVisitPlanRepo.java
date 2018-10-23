package com.kalbe.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbe.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import com.kalbe.kalbecallplanaedp.Data.DatabaseHelper;
import com.kalbe.kalbecallplanaedp.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class tRealisasiVisitPlanRepo implements crud { DatabaseHelper helper;

    public tRealisasiVisitPlanRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tRealisasiVisitPlan object = (tRealisasiVisitPlan) item;
        try {
            index = helper.gettRealisasiVisitPlanDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tRealisasiVisitPlan object = (tRealisasiVisitPlan) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettRealisasiVisitPlanDao().createOrUpdate(object);
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
        tRealisasiVisitPlan object = (tRealisasiVisitPlan) item;
        try {
            index = helper.gettRealisasiVisitPlanDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tRealisasiVisitPlan item = null;
        try{
            item = helper.gettRealisasiVisitPlanDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tRealisasiVisitPlan> items = null;
        try{
            items = helper.gettRealisasiVisitPlanDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tRealisasiVisitPlan findBytxtId(String txtId) throws SQLException {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtRealisasiVisitId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
