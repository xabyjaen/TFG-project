package com.proyectodam.javi.proyectodam.Entity.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Viaje;

import java.util.ArrayList;

public class ViajeManager {

    private ArrayList<String[]> latLng;
    private ArrayList<Viaje> listaViajes;
    private ConexionSQLiteHelper conn;

    public ArrayList<String[]> findAllDataForMaps(Context context) {

        listaViajes = this.getAllViajes(context);
        latLng = new ArrayList<String[]>();
        latLng = this.getLatLngFromArray(listaViajes);

        return latLng;
    }

    public ArrayList<String[]> getLatLngFromArray(ArrayList<Viaje> listaViajes) {

        for (int i = 0; i < listaViajes.size(); i++) {
            String[] coordenadas = listaViajes.get(i).getCoordenadas().substring(10).split(",");
            coordenadas[1] = coordenadas[1].substring(0, coordenadas[1].length() - 1);

            latLng.add(coordenadas);
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

    public ArrayList<Viaje> getAllViajes(Context context) {

        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.TABLA_VIAJES, null);
        listaViajes = new ArrayList<Viaje>();

        while (cursor.moveToNext()) {
            Viaje viaje = new Viaje();
            viaje.setId(cursor.getInt(0));
            viaje.setLugar(cursor.getString(1));
            viaje.setFecha(cursor.getString(2));
            viaje.setCoordenadas(cursor.getString(3));

            listaViajes.add(viaje);
        }
        db.close();
        conn.close();

        return listaViajes;
    }

//    public Viaje getViajeById(Context context, String id){
//
//        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);
//
//        SQLiteDatabase db = conn.getReadableDatabase();
//
//        db.q
//        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.TABLA_VIAJES, [id]);
//
//
//        return viaje;
//    }

    public Long saveViaje(String lugar, String fecha, String coordenadas, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();

        values.put(StringHelper.CAMPO_LUGAR, lugar);
        values.put(StringHelper.CAMPO_FECHA, fecha);
        values.put(StringHelper.CAMPO_COORDENADAS, coordenadas);

        Long idViaje = db.insert(StringHelper.TABLA_VIAJES, StringHelper.CAMPO_ID, values);

        return idViaje;
    }

}
