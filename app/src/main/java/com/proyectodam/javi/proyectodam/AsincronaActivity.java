package com.proyectodam.javi.proyectodam;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AsincronaActivity extends AppCompatActivity {

    private final boolean CANCELAR_SI_MAS_DE_100_IMAGENES = false;

    private final String TAG_LOG = "test";

    private TextView TV_mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asincrona);

        TV_mensaje = (TextView) findViewById(R.id.TextView_mensajesAlUsuario);

        Button B_probarHacerDosCosasALaVez = (Button) findViewById(R.id.button_probarComoPodemosHacerOtraCosa);
        B_probarHacerDosCosasALaVez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG_LOG, "...Haciendo otra cosa el usuario sobre el hilo PRINCIPAL a la vez que carga...");
                Toast toast = Toast.makeText(getApplicationContext(), "...Haciendo otra cosa el usuario sobre el hilo PRINCIPAL a la vez que carga...", Toast.LENGTH_SHORT);
                toast.show();
                new DescargarImagenesDeInternetEnOtroHilo(CANCELAR_SI_MAS_DE_100_IMAGENES).execute(" Estos Strings van a variableNoUsada que no usaremos en este ejemplo y podiamos haber declarado como Void "," si lo necesitaramos podemos cambiar el String por otro tipo de datos "," y podemos añadir más de 4 datos que los de este ejemplo, todos los que necesitemos "," y recuerda que se usan como un array, para acceder en concreto a este usaríamos variableNoUsada[3] "); //Arrancamos el AsyncTask. el método "execute" envía datos directamente a doInBackground()
            }
        });


    }

    private class DescargarImagenesDeInternetEnOtroHilo extends AsyncTask<String, Float, Integer> {
        private boolean cancelarSiHayMas100Archivos;
        private ProgressBar miBarraDeProgreso;


        public DescargarImagenesDeInternetEnOtroHilo(boolean cancelarSiHayMas100Archivos) {
            this.cancelarSiHayMas100Archivos = cancelarSiHayMas100Archivos;
        }

        @Override
        protected void onPreExecute() {
            TV_mensaje.setText("ANTES de EMPEZAR la descarga. Hilo PRINCIPAL");
            Log.v(TAG_LOG, "ANTES de EMPEZAR la descarga. Hilo PRINCIPAL");

            miBarraDeProgreso = (ProgressBar) findViewById(R.id.progressBar_indicador);
        }


        @Override
        protected Integer doInBackground(String... variableNoUsada) {

            int cantidadImagenesDescargadas = 0;
            float progreso = 0.0f;

            while (!isCancelled() && cantidadImagenesDescargadas<200){ //Suponemos que tenemos 200 imágenes en algún lado de Internet. isCancelled() comprueba si hemos cancelado con cancel() el hilo en segundo plano.
                cantidadImagenesDescargadas++;
                Log.v(TAG_LOG, "Imagen descargada número "+cantidadImagenesDescargadas+". Hilo en SEGUNDO PLANO");


                //Simulamos la descarga de una imagen. Iría aquí el código........................
                try {
                    Thread.sleep((long) (Math.random()*10)); //Simula el tiempo aleatorio de descargar una imagen, al dormir unos milisegundos aleatorios al hilo en segundo plano
                } catch (InterruptedException e) {
                    cancel(true); //Cancelamos si entramos al catch porque algo ha ido mal
                    e.printStackTrace();
                }
                //Simulamos la descarga de una imagen. Iría aquí el código........................


                progreso+=0.5;
                publishProgress(progreso); //Enviamos el progreso a "onProgressUpdate" para que se lo muestre al usuario, pues en el hilo principal no podemos llamar a nada de la interfaz

                if (cancelarSiHayMas100Archivos && cantidadImagenesDescargadas>100){ //Si hemos decidido cancelar al pasar de 100 imágenes descargadas entramos aquí.
                    cancel(true);
                }
            }

            return cantidadImagenesDescargadas;
        }

        @Override
        protected void onProgressUpdate(Float... porcentajeProgreso) {
            TV_mensaje.setText("Progreso descarga: "+porcentajeProgreso[0]+"%. Hilo PRINCIPAL");
            Log.v(TAG_LOG, "Progreso descarga: "+porcentajeProgreso[0]+"%. Hilo PRINCIPAL");

            miBarraDeProgreso.setProgress( Math.round(porcentajeProgreso[0]) );
        }

        @Override
        protected void onPostExecute(Integer cantidadProcesados) {
            TV_mensaje.setText("DESPUÉS de TERMINAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            Log.v(TAG_LOG, "DESPUÉS de TERMINAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");

            TV_mensaje.setTextColor(Color.GREEN);
        }

        @Override
        protected void onCancelled (Integer cantidadProcesados) {
            TV_mensaje.setText("DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");
            Log.v(TAG_LOG, "DESPUÉS de CANCELAR la descarga. Se han descarcado "+cantidadProcesados+" imágenes. Hilo PRINCIPAL");

            TV_mensaje.setTextColor(Color.RED);
        }

    }


}