package com.proyectodam.javi.proyectodam.Entity.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.proyectodam.javi.proyectodam.Entity.Folder;
import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Place;

import java.util.ArrayList;

public class FolderManager {

    private ArrayList<Folder> folderList;
    private ConexionSQLiteHelper conn;

    public void saveFolder(String name, String date, String files, Integer numberFiles, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();

        values.put(StringHelper.FIELD_NAME, name);
        values.put(StringHelper.FIELD_DATE, date);
        values.put(StringHelper.FIELD_FILES, files);
        values.put(StringHelper.FIELD_NUMBER_FILES, numberFiles);

        db.insert(StringHelper.FOLDER_TABLE, StringHelper.FIELD_ID, values);
    }

    public ArrayList<Folder> getAllFolders(Context context) {

        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.FOLDER_TABLE, null);
        folderList = new ArrayList<Folder>();

        while (cursor.moveToNext()) {
            Folder folder = new Folder();
            folder.setId(cursor.getInt(0));
            folder.setName(cursor.getString(1));
            folder.setDate(cursor.getString(2));
            folder.setFiles(cursor.getString(3));
            folder.setNumberFiles(cursor.getInt(4));

            folderList.add(folder);
        }
        db.close();
        conn.close();

        return folderList;
    }

    public Folder getFolderById(Context context, Integer folderId)
    {
        Folder folder = new Folder();
        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.FOLDER_TABLE +
                " WHERE " + StringHelper.FIELD_ID_PLACE + " = " + folderId, null);

        while (cursor.moveToNext()) {
            folder.setId(cursor.getInt(0));
            folder.setName(cursor.getString(1));
            folder.setFiles(cursor.getString(2));
            folder.setNumberFiles(cursor.getInt(3));
            folder.setDate(cursor.getString(4));
        }

        return folder;
    }

}
