package com.proyectodam.javi.proyectodam;

import android.os.Build;
import android.widget.Button;
import android.widget.ListView;

import com.proyectodam.javi.proyectodam.Helper.ExecuteCommand;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class SelectFilesActivityTest {

    private SelectFilesActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(SelectFilesActivity.class);

    }

    @Test
    public void validateTextViewContentTest() {
        Button transferButton = activity.findViewById(R.id.transfer_button);
        ListView filesListView = activity.findViewById(R.id.files_list_view);
//        TextView resumeFilesTextView = activity.findViewById(R.id.resumeFiles);
//        EditText dateEditText = activity.findViewById(R.id.FechaLugar);
//        EditText folderNameEditText = ;

        assertNotNull("Button transfer button could not be found", transferButton);
        assertNotNull("dilesListView could not be found", filesListView);
//        assertNotNull("TextView resumeFiles could not be found", resumeFilesTextView);
//        assertNotNull("EditText dateEditText_main_button could not be found", dateEditText);
        assertEquals("transferButton contains incorrect text", "Transferir", transferButton.getText().toString());
//        assertEquals("resumeFilesTextView contains incorrect text", "", resumeFilesTextView.getText().toString());
//        assertEquals("EditText contains incorrect text", "", dateEditText.getText().toString());
    }

    @Test
    public void formViajeTest_whenThreeFilesSelected_andReturnString(){
        activity.selectedItems.add("file1");
        activity.selectedItems.add("file2");
        activity.selectedItems.add("file3");

        activity.FormViaje();

        assertEquals("file1\n" + "file2\n" + "file3\n", activity.filesSelected);
    }

//    @Test
//    public void executeCommandsTest_WhenOrderIsNotNull(){
//        ExecuteCommand executeCommand =  mock(ExecuteCommand.class);
//        doNothing().when(executeCommand);
//        doNothing().when(executeCommand).execute();
//        activity.executeCommands("orden", "comando");
//        verify(activity.executeCommand, Mockito.times(1)).execute();
//    }

    @Test
    public void getFilesToListViewTest_WhenTwoResults_AndTwoItemsInListView(){
        activity.response = "folder/file \n folder1/file1";
        activity.getFilesToListView();

        ListView filesListView = activity.findViewById(R.id.files_list_view);
        String item1 = filesListView.getItemAtPosition(0).toString();
        String item2 = filesListView.getItemAtPosition(1).toString();

        assertEquals("folder/file ", item1);
        assertEquals(" folder1/file1", item2);
    }

}
