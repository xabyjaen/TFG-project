package com.proyectodam.javi.proyectodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.proyectodam.javi.proyectodam.Pruebas.PruebaConexionActivity;

public class MainActivity extends AppCompatActivity {

    Button prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prueba  = findViewById(R.id.prueba);
        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PruebaActivity.class);
                startActivity(intent);
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
