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
public class HistoryMainActivityTest {

    private HistoryMainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(HistoryMainActivity.class);

    }

    @Test
    public void validateTextViewContentTest() {
        Button historyList = activity.findViewById(R.id.btnList);
        Button transferList = activity.findViewById(R.id.btnTransfer);

        assertNotNull("historyList could not be found", historyList);
        assertNotNull("transferList could not be found", transferList);;
        assertEquals("historyList contains incorrect text", "Histórico", historyList.getText().toString());
        assertEquals("transferList contains incorrect text", "Donde he estado", transferList.getText().toString());
    }

}
