package com.rideread.rideread.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.loopj.android.http.LogHandler;
import com.rideread.rideread.bean.RegionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/1.
 */

public class RegionDao {

    private Context mContext;
    private SQLiteDatabase db;

    public RegionDao(Context context) {
        this.mContext = context;
        this.db = RegionDBHelper.getInstance(context).getReadableDatabase();
    }

    /**
     * 加载省份
     *
     * @return
     */
    public List<RegionModel> loadProvinceList() {
        List<RegionModel> provinceList = new ArrayList<>();

        select();
        String sql = "SELECT ID,NAME FROM PROVINCE";
        Log.e("db is null ","is ="+(db==null));
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);

            RegionModel regionModel = new RegionModel();
            regionModel.setId(id);
            regionModel.setName(name);

            provinceList.add(regionModel);
        }

        return provinceList;
    }

    public void select(){
        String sql = "select name from sqlite_master where type='table' order by name;";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            String name = cursor.getString(0);
            Log.e("table",name);
        }
    }

    /**
     * 加载城市
     *
     * @param provinceId
     * @return
     */
    public List<RegionModel> loadCityList(Long provinceId) {
        List<RegionModel> provinceList = new ArrayList<>();

        String sql = "SELECT ID,NAME FROM CITY WHERE PID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(provinceId)});
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);

            RegionModel regionModel = new RegionModel();
            regionModel.setId(id);
            regionModel.setName(name);

            provinceList.add(regionModel);
        }

        return provinceList;
    }

    /**
     * 加载地区
     *
     * @param cityId
     * @return
     */
    public List<RegionModel> loadCountyList(Long cityId) {
        List<RegionModel> provinceList = new ArrayList<>();

        String sql = "SELECT ID,NAME FROM AREA WHERE PID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cityId)});
        while (cursor.moveToNext()) {
            Long id = cursor.getLong(0);
            String name = cursor.getString(1);

            RegionModel regionModel = new RegionModel();
            regionModel.setId(id);
            regionModel.setName(name);

            provinceList.add(regionModel);
        }

        return provinceList;
    }
}
