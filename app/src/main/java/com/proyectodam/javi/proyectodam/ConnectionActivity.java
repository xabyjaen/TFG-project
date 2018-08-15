package com.proyectodam.javi.proyectodam;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.Entity.Helper.ConectionSsh;
import com.proyectodam.javi.proyectodam.Entity.Helper.ExecuteCommand;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

public class ConnectionActivity extends AppCompatActivity implements ExecuteCommand.AsyncResponseInterface, ConectionSsh.AsyncSessionResponseInterface{

    Button connectionButton;
    Session session;
    ExecuteCommand executeCommand;
    String response;
    ConectionSsh conectionSsh;
    TextView connectionMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        connectionButton = (Button) findViewById(R.id.connectionButton);
        connectionMessage = (TextView) findViewById(R.id.connection_text_view);

        conectionRaspberry();

        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                createFolders();
                executeCommands(StringHelper.LISTAR_DISPOSITIVOS, null);
            }
        });
    }

    @Override
    public void connectionSshProcessFinish(Session session) {
        this.session = session;
    }

    @Override
    public void executeCommandProcessFinish(String response) {
        this.response = response;
        if(this.response.equals("ERROR")){

            connectionMessage.setText("No se pudo conectar con la Raspberry");

            return;
        }
        if(!this.response.equals("")) {
            Intent intent = new Intent(ConnectionActivity.this, SelectDevicesActivity.class);
            intent.putExtra("response", response);
            this.conectionSsh.disconnectSession();
            startActivity(intent);

        }
    }

    public void createFolders()
    {
        executeCommands(StringHelper.CREAR_DIRECTORIO + StringHelper.DIRECTORIO + "A", null);
        executeCommands(StringHelper.CREAR_DIRECTORIO + StringHelper.DIRECTORIO + "B", null);
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

    public void conectionRaspberry()
    {
        conectionSsh = new ConectionSsh(this);
        conectionSsh.execute();
    }

}
