package com.proyectodam.javi.proyectodam.ActivityTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivityTools {

    public final void getAlertDialog(Activity activity)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
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
}
