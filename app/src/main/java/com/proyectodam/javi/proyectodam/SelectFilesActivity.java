package com.proyectodam.javi.proyectodam;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Helper.ConectionSsh;
import com.proyectodam.javi.proyectodam.Entity.Helper.ExecuteCommand;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Manager.ArchivosManager;
import com.proyectodam.javi.proyectodam.Entity.Manager.ViajeManager;
import com.proyectodam.javi.proyectodam.Fragments.DatePickerFragment;

import java.util.ArrayList;
import java.util.Arrays;

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
    Integer numeroArchivos = 0;
    Button transferButton;
    TextView resumeFilesTextView;
    TextView placeTextView;
    EditText dateEditText;
    PlaceAutocompleteFragment autocompleteFragment;
    String coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_files);
        this.filesListView = (ListView) findViewById(R.id.files_list_view);
        this.transferButton = (Button) findViewById(R.id.transfer_button);
        transferData = new ArrayList<String>();
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
        View v = inflater.inflate(R.layout.dialog_viaje, null);
        resumeFilesTextView = v.findViewById(R.id.resumeFiles);
        for (int i = 0; i < selectedItems.size(); i++) {
            filesSelected = filesSelected + selectedItems.get(i) + " ";
        }
        resumeFilesTextView.setText("TOTAL : " + numeroArchivos + "\n");
        placeTextView = v.findViewById(R.id.LugarText);
        dateEditText = v.findViewById(R.id.FechaLugar);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.fragmentAutocompletar);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                placeTextView.setText(place.getName());
                coordinates = place.getLatLng().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), "Error: " + status, Toast.LENGTH_SHORT).show();
            }
        });

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
                transferData.add(String.valueOf(placeTextView.getText()));
                transferData.add(String.valueOf(dateEditText.getText()));
                registerTravel();
                executeCommands(StringHelper.CORTAR_ARCHIVOS + filesSelected + StringHelper.CARPETA_USB_B + "/carpetaCopiada", null);
                finish();
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

    public void registerTravel()
    {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ViajeManager viajeManager = new ViajeManager();
        Long idViaje = viajeManager.saveViaje(placeTextView.getText().toString(), dateEditText.getText().toString(), coordinates, db);

        ArchivosManager archivosManager = new ArchivosManager();
        archivosManager.saveArchivos(idViaje, this.filesSelected, numeroArchivos, db);

        Toast.makeText(getApplicationContext(), "Registrado Viaje: " + placeTextView.getText().toString(), Toast.LENGTH_SHORT).show();

        db.close();
        conn.close();
    }

    public void executeCommands(String command, String order)
    {
        if (order ==  null) {
            executeCommand = new ExecuteCommand(this, this.session, command);
            executeCommand.execute();
        }
        else if (order.equals("prepareDeviceSpinners")){
            executeCommand = new ExecuteCommand(this, this.session, command);
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
