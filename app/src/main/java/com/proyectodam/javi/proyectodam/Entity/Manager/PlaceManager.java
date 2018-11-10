package com.proyectodam.javi.proyectodam.Entity.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.proyectodam.javi.proyectodam.Helper.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Place;

import java.util.ArrayList;

public class PlaceManager {

    private ArrayList<String[]> latLng;
    private ArrayList<Place> listaPlaces;
    private ConexionSQLiteHelper conn;

    public ArrayList<String[]> findAllDataForMaps(Context context) {

        listaPlaces = this.getAllPLaces(context);
        latLng = new ArrayList<String[]>();
        latLng = this.getLatLngFromArray(listaPlaces);

        return latLng;
    }

    public ArrayList<String[]> getLatLngFromArray(ArrayList<Place> listaPlaces) {

        for (int i = 0; i < listaPlaces.size(); i++) {
            String[] coordinates = listaPlaces.get(i).getCoordinates().substring(10).split(",");
            coordinates[1] = coordinates[1].substring(0, coordinates[1].length() - 1);

            latLng.add(coordinates);
        }
        return this.latLng;
    }

    public String[] getLats(ArrayList<String[]> ltLg) {

        String[] lat = new String[ltLg.size()];
        for (int i = 0; i < ltLg.size(); i++) {
            lat[i] = latLng.get(i)[0];
        }
        return lat;
    }

    public String[] getLngs(ArrayList<String[]> ltLg) {

        String[] lng = new String[ltLg.size()];

        for (int i = 0; i < ltLg.size(); i++) {
            lng[i] = ltLg.get(i)[1];
        }
        return lng;
    }

    public ArrayList<Place> getAllPLaces(Context context) {

        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.PLACE_TABLE, null);
        listaPlaces = new ArrayList<Place>();

        while (cursor.moveToNext()) {
            Place place = new Place();
            place.setId(cursor.getInt(0));
            place.setName(cursor.getString(1));
            place.setCoordinates(cursor.getString(2));
            place.setFolderId(cursor.getInt(3));

            listaPlaces.add(place);
        }
        db.close();
        conn.close();

        return listaPlaces;
    }

    public Long saveNewPlace(String name, int folderId, String coordinates, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(StringHelper.FIELD_NAME, name);
        values.put(StringHelper.FIELD_COORDINATES, coordinates);
        values.put(StringHelper.FIELD_ID_FOLDER, folderId);

        Long idViaje = db.insert(StringHelper.PLACE_TABLE, StringHelper.FIELD_ID, values);

        return idViaje;
    }

    public void editPlace(String name, int fieldId, String coordinates, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        String whereClause = StringHelper.FIELD_ID + " = " + fieldId;

        values.put(StringHelper.FIELD_NAME, name);
        values.put(StringHelper.FIELD_COORDINATES, coordinates);

        db.update(StringHelper.PLACE_TABLE, values, whereClause, null );

    }

    public Place getPlaceByFolderId(Context context, Integer folderId)
    {
        Place place = new Place();
        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.PLACE_TABLE + " WHERE " + StringHelper.FIELD_ID_FOLDER + " = " + folderId, null);
            while (cursor.moveToNext()) {
                place.setId(cursor.getInt(0));
                place.setName(cursor.getString(1));
                place.setCoordinates(cursor.getString(2));
                place.setFolderId(cursor.getInt(3));
        }
        return place;
    }

}
