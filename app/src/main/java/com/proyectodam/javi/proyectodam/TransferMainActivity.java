package com.proyectodam.javi.proyectodam;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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


public class TransferMainActivity extends AppCompatActivity implements ExecuteCommand.AsyncResponseInterface, ConectionSsh.AsyncSessionResponseInterface {

    ImageButton refreshButton;
    Button transferButton;
    TextView lugar;
    EditText fecha;
    String coordenadas;
    TextView resumeFiles;
    Spinner deviceOut;
    Spinner deviceIn;
    ListView archivos1;
    ArrayList<String> selectedItems;
    ArrayList<String> transferData;
    String resume = "";
    Integer numeroArchivos = 0;
    PlaceAutocompleteFragment autocompleteFragment;
    ExecuteCommand executeCommand;
    String response ="";
    ConectionSsh conectionSsh;
    Session session;
    Button listDevices;
    Button listFilesA;
    String[] arrayDevices = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_main);

        deviceOut = (Spinner) findViewById(R.id.deviceOut);
        deviceIn = (Spinner) findViewById(R.id.deviceIn);
        archivos1 = (ListView) findViewById(R.id.lista1);
        selectedItems = new ArrayList<String>();
        transferData = new ArrayList<String>();
        refreshButton = (ImageButton) findViewById(R.id.refreshButton);
        listFilesA = (Button) findViewById(R.id.listFilesA);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.arrayFiles, android.R.layout.simple_list_item_multiple_choice);
        conectionRaspberry();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                createFolders();
                executeCommands(StringHelper.LISTAR_DISPOSITIVOS, null);
            }
        });

        listDevices = (Button) findViewById(R.id.button);
        listDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareDeviceSpinners();
            }
        });

        listFilesA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String device = deviceIn.getSelectedItem().toString();
                executeCommands(StringHelper.MONTAR_MEMORIA + device + " " + StringHelper.CARPETA_USB_A, null);
                executeCommands(StringHelper.LISTAR_ARCHIVOS + StringHelper.CARPETA_USB_A + " -type f", null);

                String files = response;
            }
        });

        archivos1.setAdapter(adapter1);
        archivos1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        archivos1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) ((TextView) view).getText();

                if (selectedItems.contains(selectedItem)) {

                    resume = "";
                    numeroArchivos = numeroArchivos - 1;

                    selectedItems.remove(selectedItem);
                } else {
                    selectedItems.add(selectedItem);
                    numeroArchivos = numeroArchivos + 1;
                }
            }
        });

        transferButton = findViewById(R.id.Transfer);
        transferButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FormViaje().show();
            }
        });
    }

    public AlertDialog FormViaje()
    {
        AlertDialog.Builder FormViajes = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_viaje, null);
        resumeFiles = v.findViewById(R.id.resumeFiles);
        for (int i = 0; i < selectedItems.size(); i++) {
            resume = resume + selectedItems.get(i) + " - ";
        }
        resumeFiles.setText("TOTAL : " + numeroArchivos + "\n" + resume);
        lugar = v.findViewById(R.id.LugarText);
        fecha = v.findViewById(R.id.FechaLugar);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.fragmentAutocompletar);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                lugar.setText(place.getName());
                coordenadas = place.getLatLng().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), "Error: " + status, Toast.LENGTH_SHORT).show();
            }
        });

        FormViajes.setTitle("Datos del viaje");
        FormViajes.setView(v);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog(fecha);
            }
        });
        FormViajes.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                transferData.add(String.valueOf(lugar.getText()));
                transferData.add(String.valueOf(fecha.getText()));
                registroViaje();
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

    @Override
    public void executeCommandProcessFinish(String response)
    {

        this.response = response;
        makeToast();
    }

    @Override
    public void connectionSshProcessFinish(Session session)
    {
        this.session = session;
    }

    private void showDatePickerDialog(final EditText fecha)
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void registroViaje()
    {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_proyecto", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ViajeManager viajeManager = new ViajeManager();
        Long idViaje = viajeManager.saveViaje(lugar.getText().toString(), fecha.getText().toString(), coordenadas, db);

        ArchivosManager archivosManager = new ArchivosManager();
        archivosManager.saveArchivos(idViaje, this.resume, numeroArchivos, db);

        Toast.makeText(getApplicationContext(), "Registrado Viaje: " + lugar.getText().toString(), Toast.LENGTH_SHORT).show();

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
            prepareDeviceSpinners();

        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        conectionSsh.disconnectSession();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        conectionSsh.disconnectSession();
    }

    public void conectionRaspberry()
    {
        conectionSsh = new ConectionSsh(this);
        conectionSsh.execute();
    }

    public void createFolders()
    {
        executeCommands(StringHelper.CREAR_DIRECTORIO + StringHelper.DIRECTORIO + "A", null);
        executeCommands(StringHelper.CREAR_DIRECTORIO + StringHelper.DIRECTORIO + "B", null);
    }

    public String[] prepareDevices(){


        if (this.response.toLowerCase().contains(StringHelper.DISPOSITIVO+"a1".toLowerCase())){
            arrayDevices[0] = StringHelper.DISPOSITIVO +"a1";
        }
        else{
            arrayDevices[0] = "";
        }
        if (this.response.toLowerCase().contains(StringHelper.DISPOSITIVO+"b1".toLowerCase())){
            arrayDevices[1] = StringHelper.DISPOSITIVO +"b1";
        }
        else{
            arrayDevices[1] = "";
        }
        if (this.response.toLowerCase().contains(StringHelper.DISPOSITIVO+"c1".toLowerCase())){
            arrayDevices[2] = StringHelper.DISPOSITIVO +"c1";
        }
        else{
            arrayDevices[2] = "";
        }
        if (this.response.toLowerCase().contains(StringHelper.DISPOSITIVO+"d".toLowerCase())){
            arrayDevices[3] = StringHelper.DISPOSITIVO +"d1";
        }
        else{
            arrayDevices[3] = "";
        }

        return arrayDevices;
    }

    public void prepareDeviceSpinners(){

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, prepareDevices());

        deviceOut.setAdapter(adapter);
        deviceIn.setAdapter(adapter);
    }

    public void makeToast(){
        if(!this.response.equals("")){
        Toast toast = Toast.makeText(this, this.response, Toast.LENGTH_SHORT);
        toast.show();
        }
    }
}
