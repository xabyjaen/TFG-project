package com.proyectodam.javi.proyectodam;

import android.os.Build;
import android.widget.Button;
import android.widget.ListView;
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
public class ErrorActivityTest {

    private ErrorActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(ErrorActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView errorTextView = (TextView) activity.findViewById(R.id.message_error);
        Button returnButton = (Button) activity.findViewById(R.id.button_return_to_main);

        assertNotNull("message_error could not be found", errorTextView);
        assertNotNull("returnButton could not be found", returnButton);;
        assertEquals("message_error contains incorrect text", "Se ha producido un error inesperado", errorTextView.getText().toString());
        assertEquals("returnButton contains incorrect text", "Volver al menu principal", returnButton.getText().toString());
    }

}
