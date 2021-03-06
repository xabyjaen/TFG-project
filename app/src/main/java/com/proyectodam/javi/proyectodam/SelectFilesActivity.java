package com.proyectodam.javi.proyectodam;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.Helper.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Helper.ConectionSsh;
import com.proyectodam.javi.proyectodam.Helper.ExecuteCommand;
import com.proyectodam.javi.proyectodam.Helper.FilesRegex;
import com.proyectodam.javi.proyectodam.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Manager.FolderManager;
import com.proyectodam.javi.proyectodam.Fragments.DatePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SelectFilesActivity extends AppCompatActivity implements ExecuteCommand.AsyncResponseInterface, ConectionSsh.AsyncSessionResponseInterface{

    ConectionSsh connectionSsh;
    ExecuteCommand executeCommand;
    Session session;
    String response;
    String deviceIn;
    String deviceOut;
    String[] filesArray;
    ListView filesListView;
    ArrayList<String> selectedItems;
    ArrayList<String> transferData;
    String filesSelected = "";
    String filesToMoveQuery = "";
    Integer numeroArchivos = 0;
    Button transferButton;
    TextView resumeFilesTextView;
    EditText dateEditText;
    EditText folderNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_files);
        this.filesListView = (ListView) findViewById(R.id.files_list_view);
        this.transferButton = (Button) findViewById(R.id.transfer_button);
        this.transferData = new ArrayList<String>();
        ConnectionRaspberry();
        getExtras();

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormViaje().show();
            }
        });
    }

    public AlertDialog FormViaje()
    {
        AlertDialog.Builder FormViajes = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_form_register, null);
        resumeFilesTextView = v.findViewById(R.id.resumeFiles);

        FilesRegex filesRegex = new FilesRegex(selectedItems);
        filesToMoveQuery = filesRegex.getQuery();


        for (int i = 0; i < selectedItems.size(); i++) {
            filesSelected = filesSelected + selectedItems.get(i) + "\n";
        }
        resumeFilesTextView.setText("TOTAL : " + numeroArchivos + "\n");
        folderNameEditText = v.findViewById(R.id.folder_text_view);

        dateEditText = v.findViewById(R.id.FechaLugar);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        dateEditText.setText(dateFormat.format(date));

        FormViajes.setTitle("Datos del viaje");
        FormViajes.setView(v);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog(dateEditText);
            }
        });
        FormViajes.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                transferData.add(String.valueOf(dateEditText.getText()));
                executeCommands(StringHelper.CORTAR_ARCHIVOS + filesToMoveQuery + StringHelper.CARPETA_USB_B, null);
                registerFolder();

                Intent intent = new Intent(SelectFilesActivity.this, AfterTransferActivity.class);
                startActivity(intent);
            }
        });
        FormViajes.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = FormViajes.create();
        return dialog;
    }

    private void showDatePickerDialog(final EditText dateEditText)
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                dateEditText.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void registerFolder()
    {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        FolderManager folderManager = new FolderManager();
        folderManager.saveFolder(folderNameEditText.getText().toString(), dateEditText.getText().toString(), this.filesSelected, numeroArchivos, db);

        Toast.makeText(getApplicationContext(), "Registrada Transferencia: " + folderNameEditText.getText().toString(), Toast.LENGTH_SHORT).show();

        db.close();
        conn.close();
    }

    public void executeCommands(String command, String order)
    {
        if (order ==  null) {
            boolean bol = session.isConnected();

            executeCommand = new ExecuteCommand(this, connectionSsh.getSession(), command, this);
            executeCommand.execute();
        }
    }

    private void getExtras()
    {
        this.deviceIn = getIntent().getStringExtra("deviceIn");
        this.deviceOut = getIntent().getStringExtra("deviceOut");
    }

    public void ConnectionRaspberry()
    {
        this.connectionSsh = new ConectionSsh(this);
        connectionSsh.execute();
    }

    @Override
    public void executeCommandProcessFinish(String response) {
        this.response = response;

        if (!response.equals("")){
            getFilesToListView();
        }
    }

    @Override
    public void connectionSshProcessFinish(Session session) {
        this.session = session;
        executeCommands(StringHelper.MONTAR_MEMORIA + deviceIn + " " + StringHelper.CARPETA_USB_A, null);
        executeCommands(StringHelper.MONTAR_MEMORIA + deviceOut + " " + StringHelper.CARPETA_USB_B, null);
        executeCommands(StringHelper.LISTAR_ARCHIVOS + StringHelper.CARPETA_USB_A + " -type f", null);
    }

    public void getFilesToListView()
    {
        filesArray = this.response.split("\n");
        ArrayList<String> filesList = new ArrayList<>();
        for (int i=0; i<filesArray.length; i++){
            filesList.add(filesArray[i]);
        }
        selectedItems = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, filesList);
        filesListView.setAdapter(adapter);
        filesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) ((TextView) view).getText();

                if (selectedItems.contains(selectedItem)) {

                    filesSelected = "";
                    numeroArchivos = numeroArchivos - 1;

                    selectedItems.remove(selectedItem);
                } else {
                    selectedItems.add(selectedItem);
                    numeroArchivos = numeroArchivos + 1;
                }
            }
        });
    }
}
