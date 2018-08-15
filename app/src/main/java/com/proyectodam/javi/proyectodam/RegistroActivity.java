package com.proyectodam.javi.proyectodam;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;

public class RegistroActivity extends AppCompatActivity {

    EditText id;
    EditText Lugar;
    EditText Fecha;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        id = (EditText)findViewById(R.id.viajeId);
        Lugar = (EditText)findViewById(R.id.viajeLugar);
        Fecha = (EditText)findViewById(R.id.viajeFecha);
        ok = (Button)findViewById(R.id.viajeOk);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistroUsuarios();
            }
        });
    }

    public void RegistroUsuarios(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_proyecto", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(StringHelper.CAMPO_ID, id.getText().toString());
        values.put(StringHelper.CAMPO_LUGAR, Lugar.getText().toString());
        values.put(StringHelper.CAMPO_FECHA, Fecha.getText().toString());

        Long idResultante = db.insert(StringHelper.TABLA_VIAJES, StringHelper.CAMPO_ID, values);

        Toast.makeText(getApplicationContext(), "Id Registro: "+ idResultante, Toast.LENGTH_SHORT).show();

        db.close();

    }
}
