package com.proyectodam.javi.proyectodam;

import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
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
public class AfterTransferActivityTest {

    private AfterTransferActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(AfterTransferActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        Button returnToMainButton = (Button) activity.findViewById(R.id.returnToMainButton);
        ImageView imageView = (ImageView) activity.findViewById(R.id.loadingView);

        assertNotNull("returnToMain could not be found", returnToMainButton);
        assertNotNull("imageView could not be found", imageView);;
        assertEquals("returnToMain contains incorrect text", "Volver al inicio", returnToMainButton.getText().toString());
    }
}
