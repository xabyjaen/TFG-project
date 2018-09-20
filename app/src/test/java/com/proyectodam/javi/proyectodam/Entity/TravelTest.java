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
public class TravelTest {

    Travel travel;

    @Before
    public void setUp() throws Exception{
        this.travel = new Travel(1, "comments", "people", 2, 2);
    }

    @Test
    public void newTravelTest() {

        Travel assertTravel = new Travel();
        assertTravel.setId(1);
        assertTravel.setComments("comments");
        assertTravel.setPeople("people");
        assertTravel.setIdPlace(2);
        assertTravel.setIdFolder(2);

        assertEquals(assertTravel.getId(), this.travel.getId());
        assertEquals(assertTravel.getPeople(), this.travel.getPeople());
        assertEquals(assertTravel.getComments(), this.travel.getComments());
        assertEquals(assertTravel.getIdFolder(), this.travel.getIdFolder());
        assertEquals(assertTravel.getIdPlace(), this.travel.getIdPlace());
    }

}