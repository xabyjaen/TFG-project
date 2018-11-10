package com.proyectodam.javi.proyectodam;

import android.os.Build;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
public class SelectDevicesActivityTest {

    private SelectDevicesActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(SelectDevicesActivity.class);
        activity.devices = "sda1";

    }

    @Test
    public void validateTextViewContentTest() {
        Button continueButton = activity.findViewById(R.id.continue_to_files_button);;
        Spinner deviceOut = activity.findViewById(R.id.device_out_spinner);
        Spinner deviceIn = activity.findViewById(R.id.device_in_spinner);
        TextView devicesAvailablesTextView = activity.findViewById(R.id.devices_available_text_view);

        assertNotNull("continueButton transfer button could not be found", continueButton);
        assertNotNull("deviceOut could not be found", deviceOut);
        assertNotNull("deviceIn could not be found", deviceIn);
        assertNotNull("devicesAvailablesTextView could not be found", devicesAvailablesTextView);
        assertEquals("continueButton contains incorrect text", "Transferir", continueButton.getText().toString());
        assertEquals("devicesAvailablesTextView contains incorrect text", "Transferir", devicesAvailablesTextView.getText().toString());
    }
}
