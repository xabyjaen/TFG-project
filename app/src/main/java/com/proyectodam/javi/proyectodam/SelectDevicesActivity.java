package com.proyectodam.javi.proyectodam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.Entity.Helper.ConectionSsh;
import com.proyectodam.javi.proyectodam.Entity.Helper.ExecuteCommand;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

public class SelectDevicesActivity extends AppCompatActivity {

    Button continueButton;
    Spinner deviceOut;
    Spinner deviceIn;
    String[] arrayDevices = new String[4];
    String devices;

    TextView devicesAvailablesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_devices);
        deviceOut = (Spinner) findViewById(R.id.device_out_spinner);
        deviceIn = (Spinner) findViewById(R.id.device_in_spinner);
        devicesAvailablesTextView = (TextView) findViewById(R.id.devices_available_text_view);

        getExtras();
        prepareDeviceSpinners();
        devicesAvailablesTextView.setText(this.devices);

        continueButton = (Button) findViewById(R.id.continue_to_files_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectDevicesActivity.this, SelectFilesActivity.class);
                intent.putExtra("deviceIn", deviceIn.getSelectedItem().toString());
                intent.putExtra("deviceOut", deviceOut.getSelectedItem().toString());

                startActivity(intent);
            }
        });

    }

    private void getExtras()
    {
        this.devices = getIntent().getStringExtra("response");
    }

    public void prepareDeviceSpinners(){

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, prepareDevices());

        deviceOut.setAdapter(adapter);
        deviceIn.setAdapter(adapter);
    }

    public String[] prepareDevices(){


        if (this.devices.toLowerCase().contains("sda1".toLowerCase())){
            arrayDevices[0] = StringHelper.DISPOSITIVO +"a1";
        }
        else{
            arrayDevices[0] = "";
        }
        if (this.devices.toLowerCase().contains("sdb1".toLowerCase())){
            arrayDevices[1] = StringHelper.DISPOSITIVO +"b1";
        }
        else{
            arrayDevices[1] = "";
        }
        if (this.devices.toLowerCase().contains("sdc1".toLowerCase())){
            arrayDevices[2] = StringHelper.DISPOSITIVO +"c1";
        }
        else{
            arrayDevices[2] = "";
        }
        if (this.devices.toLowerCase().contains("sdd1".toLowerCase())){
            arrayDevices[3] = StringHelper.DISPOSITIVO +"d1";
        }
        else{
            arrayDevices[3] = "";
        }

        return arrayDevices;
    }
}
