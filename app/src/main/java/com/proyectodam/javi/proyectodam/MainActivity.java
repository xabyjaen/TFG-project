package com.proyectodam.javi.proyectodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
