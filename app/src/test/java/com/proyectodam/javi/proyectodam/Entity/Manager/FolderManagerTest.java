package com.proyectodam.javi.proyectodam.Entity.Manager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.proyectodam.javi.proyectodam.Entity.Folder;
import com.proyectodam.javi.proyectodam.Helper.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Helper.StringHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class FolderManagerTest {


    @Mock
    private SQLiteDatabase db;

    @Mock
    private ConexionSQLiteHelper conn;

    @Mock
    private Context context;

    private FolderManager folderManager;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        folderManager = new FolderManager();
//        context = mock(Context.class);
//        conn = mock(ConexionSQLiteHelper.class);
//        db = mock(SQLiteDatabase.class);
    }

    @Test
    public void getAllFoldersTest()
    {
        ArrayList<Folder> folderList= new ArrayList<Folder>();
        Cursor cursor = mock(Cursor.class);
        when(conn.getReadableDatabase()).thenReturn(db);
        when(db.rawQuery("SELECT * FROM " + StringHelper.FOLDER_TABLE, null)).thenReturn(cursor);
//        doReturn(db).when(conn).getReadableDatabase();
//        doReturn(cursor).when(db).rawQuery("SELECT * FROM " + StringHelper.FOLDER_TABLE, null);
        when(cursor.moveToNext()).thenReturn(false);

        folderManager.getAllFolders(context);

    }
}