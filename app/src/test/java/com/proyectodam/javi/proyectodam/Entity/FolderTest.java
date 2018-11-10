package com.proyectodam.javi.proyectodam.Entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class FolderTest {

    private Folder folder;

    @Before
    public void setUp() throws Exception{
        this.folder = new Folder(1, "carpeta", "archivo1", 12, "12-10-18" );
    }

    @Test
    public void newTravelTest() {

        Folder assertFolder = new Folder();
        assertFolder.setId(1);
        assertFolder.setName("carpeta");
        assertFolder.setFiles("archivo1");
        assertFolder.setNumberFiles(12);
        assertFolder.setDate("12-10-18");

        assertEquals(assertFolder.getId(), this.folder.getId());
        assertEquals(assertFolder.getName(), this.folder.getName());
        assertEquals(assertFolder.getFiles(), this.folder.getFiles());
        assertEquals(assertFolder.getNumberFiles(), this.folder.getNumberFiles());
        assertEquals(assertFolder.getDate(), this.folder.getDate());
    }

}