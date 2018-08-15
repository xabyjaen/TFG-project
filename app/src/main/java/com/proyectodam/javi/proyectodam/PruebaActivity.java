package com.proyectodam.javi.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.Entity.Helper.ConectionSsh;
import com.proyectodam.javi.proyectodam.Entity.Helper.ExecuteCommand;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

public class PruebaActivity extends AppCompatActivity implements ExecuteCommand.AsyncResponseInterface {

    private Button btnConexion;
    private Button archivos;
    private Button desconectar;
    private Session session;
    private TextView texto;
    private ConectionSsh conexionSsh;
    private String command;
    private String result;
    private StringHelper stringHelper = new StringHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_prueba);

        desconectar = findViewById(R.id.desconectar);
        archivos = findViewById(R.id.archivos);
        texto = findViewById(R.id.texto);
        btnConexion = findViewById(R.id.conexion);

        btnConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                conexionSsh = new ConectionSsh();
//                conexionSsh.execute();
            }
        });
    }

    @Override
    public void executeCommandProcessFinish(String response) {

    }
}