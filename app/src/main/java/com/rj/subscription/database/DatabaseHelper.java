package com.rj.subscription.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rj.subscription.util.Constants;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SUBSCRIPTION_NUMBER_TABLE_CREATE = "create table "
            + Constants.TABLE_NAME
            + " ("
            + Constants.ID_COLUMN
            + " text, "
            + Constants.NAME_COLUMN
            + " text, "
            + Constants.ICON_COLUMN
            + " text, "
            + Constants.DESCRIBE_COLUMN
            + " text, "
            + Constants.ISNOTICE_COLUMN
            + " INTERGER , "
            + Constants.ISPUB_COLUMN
            + " INTERGER  "
            + " );"
            + "";
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SUBSCRIPTION_NUMBER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
