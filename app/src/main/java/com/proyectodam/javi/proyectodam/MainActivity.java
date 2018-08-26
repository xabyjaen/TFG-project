package com.proyectodam.javi.proyectodam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button instructionsButton;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getApplication();

        instructionsButton = (Button) findViewById(R.id.instructions_button);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Instrucciones de uso");
                alertDialog.setMessage(
                        "Instrucciones para transferencia de datos: \n \n" +
                                " 1. Conectar la Raspberry y conectarse al punto de acceso. \n" +
                                " 2. Pulsa TRANSFERIR ARCHIVOS > CONECTAR A RASPBERRY. \n" +
                                " 3. Seleccione el dispositivo de salida y de entrada y pulse continuar. \n" +
                                " 4. Seleccione los archivos del dispositivo de salida que desea mover. \n" +
                                " 5. Seleccione un nombre para la transferencia y la fecha. \n" +
                                " 6. La transferencia puede tardar dependiendo del tamaño de lo que se quiere transferir. \n \n" +
                                "Una vez realizada la transferencia, puede ver el historial de la descarga pulsando HISTORIAL>HISTÓRICO. \n \n" +
                                "Puede añadir información del viaje, tales como el Lugar, las personas que fueron al viaje y comentarios pulsando EDITAR VIAJE. \n \n" +
                                "Para poder editar el lugar con google places es necesario estar conectado a Internet. \n \n" +
                                "Una vez añadido un lugar, es posible ver la ubicación exacta de todos lo viajes pulsando DONDE HE ESTADO."
                );
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    public void historyAccess(View v) {
            Intent intent = new Intent(MainActivity.this, HistoryMainActivity.class);
            startActivity(intent);
    }

    public void transferAccess(View v) {
        Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
        startActivity(intent);
    }
}
