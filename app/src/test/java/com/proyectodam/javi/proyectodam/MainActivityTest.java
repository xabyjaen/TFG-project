package com.proyectodam.javi.proyectodam;

import android.os.Build;
import android.widget.Button;

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
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        Button instructionsButton = (Button) activity.findViewById(R.id.instructions_button);
        Button historyButton = (Button) activity.findViewById(R.id.history_main_button);
        Button transferButton = (Button) activity.findViewById(R.id.transfer_main_button);

        assertNotNull("Button instructions_button could not be found", instructionsButton);
        assertNotNull("Button history_main_button could not be found", historyButton);
        assertNotNull("Button transfer_main_button could not be found", transferButton);
        assertEquals("instructions_button contains incorrect text", "Instrucciones", instructionsButton.getText().toString());
        assertEquals("history_main_button contains incorrect text", "Historial", historyButton.getText().toString());
        assertEquals("transfer_main_button contains incorrect text", "Transferir archivos", transferButton.getText().toString());
    }
}
