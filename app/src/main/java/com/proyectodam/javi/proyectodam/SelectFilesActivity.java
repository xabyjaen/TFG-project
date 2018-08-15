package com.proyectodam.javi.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.Entity.Helper.ConectionSsh;
import com.proyectodam.javi.proyectodam.Entity.Helper.ExecuteCommand;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

public class SelectFilesActivity extends AppCompatActivity implements ExecuteCommand.AsyncResponseInterface{

    ConectionSsh connectionSsh;
    ExecuteCommand executeCommand;
    Session session;
    String response;
    String deviceIn;
    String deviceOut;
    ListView filesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_files);
        getExtras();
        executeCommands(StringHelper.LISTAR_ARCHIVOS + deviceIn + " -type f", null);
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
        Bundle b = this.getIntent().getExtras();
        if (b != null){
            this.connectionSsh = b.getParcelable("connection");
            this.session = this.connectionSsh != null ? this.connectionSsh.getSession() : null;
        }
    }


    @Override
    public void executeCommandProcessFinish(String response) {
        this.response = response;

    }
}
