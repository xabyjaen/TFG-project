package com.proyectodam.javi.proyectodam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.proyectodam.javi.proyectodam.Controller.MainActivityController;


public class MainActivity extends AppCompatActivity {

    Button instructionsButton;
    Context mContext;
    MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getApplication();
        instructionsButton = (Button) findViewById(R.id.instructions_button);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = new MainActivityController();
                controller.getAlertDialog(MainActivity.this);
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
