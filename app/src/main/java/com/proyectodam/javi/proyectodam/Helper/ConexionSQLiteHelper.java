package com.proyectodam.javi.proyectodam.Helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proyectodam.javi.proyectodam.Helper.StringHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StringHelper.CREATE_TABLE_FOLDER);
        db.execSQL(StringHelper.CREATE_TABLE_PLACE);
        db.execSQL(StringHelper.CREATE_TABLE_TRAVEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StringHelper.PLACE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + StringHelper.TRAVEL_TABLE );
        db.execSQL("DROP TABLE IF EXISTS " + StringHelper.FOLDER_TABLE);
        onCreate(db);
    }
}
