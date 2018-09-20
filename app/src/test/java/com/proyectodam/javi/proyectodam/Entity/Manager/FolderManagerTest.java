package com.proyectodam.javi.proyectodam.Entity.Manager;

import android.database.sqlite.SQLiteDatabase;

import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.BaseMatcher.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class FolderManagerTest {

    private SQLiteDatabase db;

    @Before
    public void setUp()
    {
        db = mock(SQLiteDatabase.class);
    }

    @Test
    public void saveFolderTest()
    {

    }
}