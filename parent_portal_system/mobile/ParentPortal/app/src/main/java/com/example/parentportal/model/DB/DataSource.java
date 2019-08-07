package com.example.parentportal.model.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataSource {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public Cursor getAllKids() {

        return sqLiteDatabase.query(TableItems.TABLE_KID,
                null,
                null,
                null,
                null,
                null,
                null);

    }

    public Cursor checkParent() {
        return sqLiteDatabase.query(TableItems.TABLE_PARENT,
                null,
                null,
                null,
                null,
                null,
                null);

    }

    public Cursor singleStudent(String id) {
//        id = "\'" + id + "\'";
        Log.i("The id is", id);

        String[] selectionArgs = {id};
        String selection = TableItems.KID_ID + " = ?";
        return sqLiteDatabase.query(TableItems.TABLE_KID,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    public long createItem(ContentValues contentValues, String tableName) {

        return sqLiteDatabase.insert(tableName, null, contentValues);

    }

}
