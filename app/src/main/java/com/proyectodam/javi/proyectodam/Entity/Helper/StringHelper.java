package com.proyectodam.javi.proyectodam.Entity.Helper;

/**
 * Created by javi on 15/03/18.
 */

public class StringHelper {

    //SQLITE

    public static final String FOLDER_TABLE = "Folder";
    public static final String FIELD_ID = "id";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_FILES = "files";
    public static final String FIELD_NUMBER_FILES = "files_number";

    public static final String PLACE_TABLE = "Place";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_COORDINATES = "coordinates";
    public static final String FIELD_ID_FOLDER = "id_folder";

    public static final String TRAVEL_TABLE = "Travel";
    public static final String FIELD_COMMENTS = "comments";
    public static final String FIELD_PEOPLE = "people";
    public static final String FIELD_ID_PLACE = "id_place";


    public static final String CREATE_TABLE_FOLDER = "CREATE TABLE " + FOLDER_TABLE + " (" +
            FIELD_ID + " INTEGER NOT NULL PRIMARY KEY, " +
            FIELD_NAME + " TEXT, " +
            FIELD_DATE + " TEXT, " +
            FIELD_FILES + " TEXT, " +
            FIELD_NUMBER_FILES + " INTEGER" +
            ");";


    public static final String CREATE_TABLE_PLACE = "CREATE TABLE " + PLACE_TABLE + " (" +
            FIELD_ID + " INTEGER NOT NULL PRIMARY KEY," +
            FIELD_NAME + " TEXT, " +
            FIELD_COORDINATES + " TEXT, " +
            FIELD_ID_FOLDER + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + FIELD_ID_FOLDER + ") REFERENCES " + FOLDER_TABLE + "(" + FIELD_ID + ")" +
            "ON DELETE CASCADE"+
            ");";


    public static final String CREATE_TABLE_TRAVEL = "CREATE TABLE " + TRAVEL_TABLE + " (" +
            FIELD_ID + " INTEGER NOT NULL PRIMARY KEY," +
            FIELD_COMMENTS + " TEXT, " +
            FIELD_PEOPLE + " TEXT, " +
            FIELD_ID_FOLDER + " INTEGER NOT NULL, " +
            FIELD_ID_PLACE + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + FIELD_ID_PLACE + ") REFERENCES " + PLACE_TABLE + "(" + FIELD_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + FIELD_ID_FOLDER + ") REFERENCES " + FOLDER_TABLE + "(" + FIELD_ID + ") ON DELETE CASCADE" +
            ");";



    // RASPBERRY COMMANDS

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
    public static final String DESMONTAR_MEMORIA = "sudo umount ";

}
