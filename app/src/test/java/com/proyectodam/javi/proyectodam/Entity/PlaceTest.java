package com.proyectodam.javi.proyectodam.Entity;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.BaseMatcher.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class PlaceTest {

    Place place;

    @Before
    public void setUp() throws Exception{
        this.place = new Place(1, "Jaen", "111111, 2222222", 2);
    }

    @Test
    public void newTravelTest() {

        Place assertPlace = new Place();
        assertPlace.setId(1);
        assertPlace.setName("Jaen");
        assertPlace.setCoordinates("111111, 2222222");
        assertPlace.setFolderId(2);

        assertEquals(assertPlace.getId(), this.place.getId());
        assertEquals(assertPlace.getName(), this.place.getName());
        assertEquals(assertPlace.getCoordinates(), this.place.getCoordinates());
        assertEquals(assertPlace.getFolderId(), this.place.getFolderId());
    }

}