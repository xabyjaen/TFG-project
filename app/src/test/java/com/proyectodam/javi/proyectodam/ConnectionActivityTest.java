package com.proyectodam.javi.proyectodam;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.proyectodam.javi.proyectodam.Helper.ExecuteCommand;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.VerificationCollector;
import org.mockito.stubbing.OngoingStubbing;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import static org.mockito.Mockito.*;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
//@RunWith(MockitoJUnitRunner.class)
public class ConnectionActivityTest {

    private ConnectionActivity activity;

    @Mock
    ExecuteCommand command;

//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public VerificationCollector verificationCollector = MockitoJUnit.collector();


    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(ConnectionActivity.class);
        command = Mockito.mock(ExecuteCommand.class);

    }

    @Test
    public void validateTextViewContentTest() {
        Button connectionButton = (Button) activity.findViewById(R.id.connectionButton);
        TextView connectionMessage = activity.findViewById(R.id.connection_text_view);

        assertNotNull("connectionButton could not be found", connectionButton);
        assertNotNull("connectionMessage could not be found", connectionMessage);;
        assertEquals("connectionButton contains incorrect text", "Conectar a Raspberry", connectionButton.getText().toString());
        assertEquals("returnButton contains incorrect text", "Para iniciar una transferencia de archivos conectese\n" +
                "        a la WIFI de la Raspberry y pulse CONECTAR A LA RASPBERRY", connectionMessage.getText().toString());
    }

    @Test
    public void executeCommandProcessFinishTest() {
        activity.executeCommands("command", "orden");
        verifyZeroInteractions(command);
    }

    @Test
    public void executeCommandProcessFinishTest_whenOrderIsNull() {
        when(command.execute()).thenReturn(null);
        activity.executeCommands("command", null);
        verify(command, times(1)).execute();
    }
}
