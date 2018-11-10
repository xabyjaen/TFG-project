package com.proyectodam.javi.proyectodam.Entity.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.proyectodam.javi.proyectodam.Helper.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Travel;

public class TravelManager {

    private ConexionSQLiteHelper conn;


    public Travel gerTravelByFolderID(Context context, Integer folderId){
        Travel travel = new Travel();

        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.TRAVEL_TABLE + " WHERE " + StringHelper.FIELD_ID_PLACE + " = " + folderId, null);

            while (cursor.moveToNext()) {
                travel.setId(cursor.getInt(0));
                travel.setComments(cursor.getString(1));
                travel.setPeople(cursor.getString(2));
                travel.setIdPlace(cursor.getInt(3));
                travel.setIdFolder(cursor.getInt(4));
            }
        return travel;
    }

    public Long saveNewTravel(String persons, String comments, Integer folderId, Integer placeId, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(StringHelper.FIELD_PEOPLE, persons);
        values.put(StringHelper.FIELD_COMMENTS, comments);
        values.put(StringHelper.FIELD_ID_FOLDER, folderId);
        values.put(StringHelper.FIELD_ID_PLACE, placeId);

        Long idTravel = db.insert(StringHelper.TRAVEL_TABLE, StringHelper.FIELD_ID, values);

        return idTravel;
    }

    public void editTravel(String persons, String comments, Integer fieldId, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        String whereClause = StringHelper.FIELD_ID + " = " + fieldId;

        values.put(StringHelper.FIELD_PEOPLE, persons);
        values.put(StringHelper.FIELD_COMMENTS, comments);

        db.update(StringHelper.TRAVEL_TABLE, values, whereClause, null );


    }


}
