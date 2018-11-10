package com.proyectodam.javi.proyectodam;

import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MapsActivityTest {

    private MapsActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MapsActivity.class);

    }

    @Test
    public void validateTextViewContentTest() {

    }

}
