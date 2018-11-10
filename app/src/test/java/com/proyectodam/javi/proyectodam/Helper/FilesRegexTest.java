package com.proyectodam.javi.proyectodam.Helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class FilesRegexTest {

    private FilesRegex regex;
    private ArrayList<String> list;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<String>();
    }

    @Test
    public void getCorrectQuery_WithSimpleName_AndReturnTheSameName_Test() {
        list.add("archivo1");
        regex = new FilesRegex(list);

        assertEquals( regex.getQuery(),"archivo1 ");
    }

    @Test
    public void getCorrectQuery_WithTwoNames_AndReturnTheQuery_Test() {
        list.add("archivo?1");
        list.add("archivo 2");
        regex = new FilesRegex(list);

        assertEquals( regex.getQuery(),"archivo\\?1 archivo\\ 2 ");
    }

}
