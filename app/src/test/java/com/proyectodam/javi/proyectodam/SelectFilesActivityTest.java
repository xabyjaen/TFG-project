package com.proyectodam.javi.proyectodam;

import android.support.test.rule.ActivityTestRule;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.BaseMatcher.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class SelectFilesActivityTest {

    @Mock
    ActivityTestRule<SelectFilesActivity> activityRule;

    @Before
    public void setUp() throws Exception {
        activityRule = new ActivityTestRule<>(SelectFilesActivity.class, true, false);
    }

        @Test
    public void FormViajeTest()
    {

    }
}