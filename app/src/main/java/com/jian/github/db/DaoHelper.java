package com.jian.github.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jian.github.App;
import com.ouyangzn.github.dao.DaoMaster;
import com.ouyangzn.github.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DaoHelper {

    private static DaoSession sDaoSession;

    private DaoHelper() {
    }

    public static void initDao(Context context) {
        DaoMaster.OpenHelper helper = new DaoMaster.OpenHelper(context, "open-resource") {
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // 数据库版本升级
            }
        };
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        if (sDaoSession == null) {
            initDao(App.getApp());
        }
        return sDaoSession;
    }
}
