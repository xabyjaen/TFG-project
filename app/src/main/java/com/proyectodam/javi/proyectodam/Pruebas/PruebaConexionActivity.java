package com.proyectodam.javi.proyectodam.Pruebas;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.proyectodam.javi.proyectodam.R;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class PruebaConexionActivity extends AppCompatActivity {


    private static JSch jsch;

    public Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        final String username = "pi";

        final String password = "raspberry";

        final String ip = "192.168.50.10";


        Button boton = (Button) findViewById(R.id.prueba);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*EJECUTAR EN SEGUNDO PLANO*/
                new AsyncTask<String, Void, Void>() {

                    @Override
                    protected Void doInBackground(String... params) {
                        try {

                            String username = params[0];
                            String password = params[1];
                            String ip = params[2];

                            executeRemoteCommand(username, password, ip, 22);

                        } catch (Exception e) {
                            e.printStackTrace();

                        }


                        return null;
                    }
                }.execute(username, password, ip);

            }
        });

    }

    /*METODO EJECUTAR COMANDO*/
    public static String executeRemoteCommand(String username, String password, String ip,
                                              int port)
            throws Exception {

        port = 22;

        jsch = new JSch();

        Session sesion = jsch.getSession(username, ip, port);

        sesion.setPassword(password);

        /*PREGUNTAR POR INTERCAMBIO ESTRICTO DE LLAVES*/

        Properties prop = new Properties();

        prop.put("StrictHostKeyChecking", "no");

        sesion.setConfig(prop);

        /*CONECTAMOS LA SESION*/

        sesion.connect();

        /*CONFIGURACION DEL CANAL SSH*/

        ChannelExec canalssh = new ChannelExec();

        sesion.openChannel("exec");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        canalssh.setOutputStream(baos);

        /*EJECUTAMOS EL COMANDO*/

        canalssh.setCommand("ls -la");

        canalssh.connect(1000);

        canalssh.disconnect();

        canalssh.getOutputStream();

        return baos.toString();
    }

}
