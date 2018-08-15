package com.proyectodam.javi.proyectodam;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.proyectodam.javi.proyectodam.Fragments.TravelListFragment;

public class HistoryMainActivity extends AppCompatActivity {

    Button historyList;
    Button transferList;
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);

        historyList=findViewById(R.id.btnList);
        historyList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new TravelListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.RelativeLayout, fragment);
                fragmentTransaction.commit();
            }
        });


        transferList=findViewById(R.id.btnTransfer);
        transferList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryMainActivity.this, MapsActivity.class);
                startActivity(intent);
//                Fragment fragment;
//                fragment = new TravelHistoryDataFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.RelativeLayout, fragment);
//                fragmentTransaction.commit();
            }
        });
    }
}
