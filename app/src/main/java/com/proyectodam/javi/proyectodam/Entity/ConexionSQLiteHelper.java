package com.proyectodam.javi.proyectodam.Entity;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StringHelper.CREAR_TABLA_VIAJES);
        db.execSQL(StringHelper.CREAR_TABLA_ARCHIVOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS viajes");
        db.execSQL("DROP TABLE IF EXISTS archivos");
        onCreate(db);
    }
}
