package com.proyectodam.javi.proyectodam.Entity.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.proyectodam.javi.proyectodam.Entity.Archivo;
import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Viaje;

import java.util.ArrayList;

public class ArchivosManager {

    private ArrayList<Archivo> listaArchivos;
    private ArrayList<String> listaInformacion;
    private ConexionSQLiteHelper conn;

    public ArrayList<String> getAllArchivosAndLocations(Context context, ArrayList<Viaje> listaViajes)
    {
        this.listaArchivos = new ArrayList<>();
        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        for (int i = 0; i<listaViajes.size(); i++) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.TABLA_ARCHIVOS + " WHERE " + StringHelper.CAMPO_ID_VIAJE + " = " + listaViajes.get(i).getId(), null);

            while (cursor.moveToNext()) {
                Archivo archivo = new Archivo();
                archivo.setId(cursor.getInt(0));
                archivo.setIdViaje(cursor.getInt(1));
                archivo.setArchivos(cursor.getString(2));
                archivo.setNumeroArchivos(cursor.getInt(3));

                listaArchivos.add(archivo);
            }
        }
        db.close();

        listaInformacion = this.getAllDataInformation(listaViajes);
        return listaInformacion;
    }

    public Archivo getArchivoByViajeId(Context context, Integer viajeId)
    {
        Archivo archivo = new Archivo();
        this.conn = new ConexionSQLiteHelper(context, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + StringHelper.TABLA_ARCHIVOS + " WHERE " + StringHelper.CAMPO_ID_VIAJE + " = " + viajeId, null);

        while (cursor.moveToNext()) {
            archivo.setId(cursor.getInt(0));
            archivo.setArchivos(cursor.getString(2));
            archivo.setNumeroArchivos(cursor.getInt(3));
        }

        return archivo;
    }

    private ArrayList<String> getAllDataInformation(ArrayList<Viaje> listaViajes)
    {
        listaInformacion = new ArrayList<String>();

        for (int i=0;i<listaArchivos.size();i++){
            listaInformacion.add(listaViajes.get(i).getLugar() + " / TOTAL : " + listaArchivos.get(i).getNumeroArchivos() + "\n" + listaArchivos.get(i).getArchivos());
        }
        return listaInformacion;
    }

    public void saveArchivos(Long idViaje, String resume, Integer numeroArchivos, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();

        values.put(StringHelper.CAMPO_ID_VIAJE, idViaje);
        values.put(StringHelper.CAMPO_ARCHIVOS, resume);
        values.put(StringHelper.CAMPO_NUMERO_ARCHIVOS, numeroArchivos);

        db.insert(StringHelper.TABLA_ARCHIVOS, StringHelper.CAMPO_ID, values);
    }

}
