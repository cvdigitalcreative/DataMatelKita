package com.digitalcreative.aplikasidatamining.Controller;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    public static final String DBNAME = "notes_db.db"; //<<<< must be same as file name
    public static final int DBVERSION = 1;

    public DBHelper(Context context) {
        super(context,DBNAME,null,DBVERSION);
    }
}