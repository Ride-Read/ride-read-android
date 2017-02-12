package com.rideread.rideread.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rideread.rideread.common.Constants;
import com.rideread.rideread.gen.DaoMaster;
import com.rideread.rideread.gen.DaoSession;

/**
 * Created by Jackbing on 2017/2/12.
 */

public class RideReadDBHelper {

    private  DaoMaster.DevOpenHelper helper;
    private static RideReadDBHelper dbhelper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession mDaoSession;

    public static RideReadDBHelper getInstance() {
        if (dbhelper == null) {
            synchronized (RideReadDBHelper.class) {
                if (dbhelper == null) {
                    dbhelper = new RideReadDBHelper();
                }
            }
        }
        return dbhelper;
    }



    public void init(Context context,String dbname,SQLiteDatabase.CursorFactory factory){
        helper=new DaoMaster.DevOpenHelper(context,dbname,factory);

    }

    public DaoMaster getDaoMaster() {
        if(daoMaster==null){
            daoMaster=new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public void setDaoMaster(DaoMaster daoMaster) {
        this.daoMaster = daoMaster;
    }

    public SQLiteDatabase getDb() {
        if(db==null){
            db=helper.getWritableDatabase();
        }
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DaoSession getmDaoSession() {
       if(mDaoSession==null){
           mDaoSession=getDaoMaster().newSession();
       }
        return mDaoSession;
    }

    public void setmDaoSession(DaoSession mDaoSession) {
        this.mDaoSession = mDaoSession;
    }
}
