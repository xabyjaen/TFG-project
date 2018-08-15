package com.proyectodam.javi.proyectodam.Entity.Helper;

/**
 * Created by javi on 15/03/18.
 */

public class StringHelper {
    public static final String TABLA_VIAJES = "viajes";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_LUGAR = "lugar";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_COORDENADAS= "coordenadas";

    public static final String TABLA_ARCHIVOS = "archivos";
    public static final String CAMPO_ID_VIAJE = "id_viaje";
    public static final String CAMPO_ARCHIVOS = "archivos";
    public static final String CAMPO_NUMERO_ARCHIVOS = "numero_archivos";

    public static final String CREAR_TABLA_VIAJES = "CREATE TABLE " + TABLA_VIAJES + " (" +
            CAMPO_ID + " INTEGER NOT NULL PRIMARY KEY," +
            CAMPO_LUGAR + " TEXT, " +
            CAMPO_FECHA + " TEXT," +
            CAMPO_COORDENADAS + " TEXT" +
            ");";

    public static final String CREAR_TABLA_ARCHIVOS = "CREATE TABLE " + TABLA_ARCHIVOS + " (" +
            CAMPO_ID + " INTEGER NOT NULL PRIMARY KEY," +
            CAMPO_ID_VIAJE + " INTEGER NOT NULL," +
            CAMPO_ARCHIVOS + " TEXT, " +
            CAMPO_NUMERO_ARCHIVOS + " INTEGER, " +
            "FOREIGN KEY(" + CAMPO_ID_VIAJE + ") REFERENCES " + TABLA_VIAJES + "(" + CAMPO_ID + ")"+
            ");";

    public static final String USER = "pi";
    public static String PASSWORD = "raspberry";
    public static String HOST = "192.168.50.10";

    public static final String CARPETA_USB_A = "/media/usbA";
    public static final String CARPETA_USB_B = "/media/usbB";
    public static final String CREAR_DIRECTORIO = "sudo mkdir ";
    public static final String DIRECTORIO = "/media/usb";
    public static final String LISTAR_DISPOSITIVOS = "lsblk";
    public static final String DISPOSITIVO = "/dev/sd";
    public static final String MONTAR_MEMORIA = "sudo mount ";
    public static final String LISTAR_ARCHIVOS = "find ";
    public static final String CORTAR_ARCHIVOS = "sudo mv ";
    public static final String DESMONTAR_MEMORIA = "sudo umount /media/usb";

}
