package com.rj.subscription.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rj.subscription.bean.PubsubNode;
import com.rj.subscription.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class DBManager {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public final static String TAG = DBManager.class.getName();

    public DBManager(Context context) {
        dbHelper = new DatabaseHelper(context, Constants.DB_NAME, null, 1);
    }

    public List<PubsubNode> queryAll() {
        List<PubsubNode> localDB;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        localDB = cursorToAppList(cursor);
        return localDB;
    }

    private List<PubsubNode> cursorToAppList(Cursor cursor) {
        List<PubsubNode> list = null;
        if (cursor != null) {
            list = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String subId = cursor.getString(cursor.getColumnIndex(Constants.ID_COLUMN));
                String subName = cursor.getString(cursor.getColumnIndex(Constants.NAME_COLUMN));
                String subIcon = cursor.getString(cursor.getColumnIndex(Constants.ICON_COLUMN));
                String subDesc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIBE_COLUMN));
                int notice = cursor.getInt(cursor.getColumnIndex(Constants.ISNOTICE_COLUMN));
                int pub = cursor.getInt(cursor.getColumnIndex(Constants.ISPUB_COLUMN));
                PubsubNode subInfo = new PubsubNode();
                subInfo.setId(subId);
                subInfo.setName(subName);
                subInfo.setLogo(subIcon);
                subInfo.setDescription(subDesc);
                subInfo.setIsNotice(notice);
                subInfo.setPub(pub);
                list.add(subInfo);
                cursor.moveToNext();
            }
        }
        return list;
    }


    //插入一条数据
    public void insertSubscriptionNumInfo(PubsubNode subInfo) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = subscriptionNumInfoToContentValues(subInfo);
        database.insert(Constants.TABLE_NAME, null, values);
        database.close();
    }


    //删除一条数据
    public void deleteSubscriptionNumInfo(PubsubNode subInfo) {
        database = dbHelper.getWritableDatabase();
        database.delete(Constants.TABLE_NAME, Constants.ID_COLUMN + " =? ", new String[]{subInfo.getId()});
        database.close();
    }


    //更新一条数据
    public void updateSubscriptionNumInfo(PubsubNode subInfo) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = subscriptionNumInfoToContentValues(subInfo);
        int rows = database.update(Constants.TABLE_NAME, values, Constants.ID_COLUMN + " =?", new String[]{subInfo.getId()});
        if (rows != 0) {
            Log.d(TAG, "更新数据成功");
        } else {
            Log.d(TAG, "更新数据失败");
        }
        database.close();
    }


    //把PubsubNode对象转为ContentValues对象
    private ContentValues subscriptionNumInfoToContentValues(PubsubNode subInfo) {
        ContentValues values = new ContentValues();
        values.put(Constants.ID_COLUMN, subInfo.getId());
        values.put(Constants.NAME_COLUMN, subInfo.getName());
        values.put(Constants.ICON_COLUMN, subInfo.getLogo());
        values.put(Constants.DESCRIBE_COLUMN, subInfo.getDescription());
        values.put(Constants.ISNOTICE_COLUMN, subInfo.getIsNotice());
        values.put(Constants.ISPUB_COLUMN, subInfo.isPub());
        return values;
    }

    public void deleteAll() {
        try {
            List<PubsubNode> subList = queryAll();
            if (subList.size() > 0) {
                for (PubsubNode info : subList) {
                    deleteSubscriptionNumInfo(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}