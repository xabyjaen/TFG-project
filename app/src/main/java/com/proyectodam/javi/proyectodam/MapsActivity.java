package com.proyectodam.javi.proyectodam;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyectodam.javi.proyectodam.Entity.Manager.ViajeManager;
import com.proyectodam.javi.proyectodam.Entity.Viaje;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        }

        else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ViajeManager viajeManager = new ViajeManager();
        ArrayList<String[]> ltnLng= viajeManager.findAllDataForMaps(this);
        String [] lats = viajeManager.getLats(ltnLng);
        String [] lngs = viajeManager.getLngs(ltnLng);
        ArrayList<Viaje> viajes = new ArrayList<Viaje>();
        viajes = viajeManager.getAllViajes(this);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        for (int i = 0; i<ltnLng.size(); i++){

            String lugar = viajes.get(i).getLugar();
            String fecha = viajes.get(i).getFecha();
            float lat = Float.parseFloat(lats[i]);
            float lng = Float.parseFloat(lngs[i]);
            LatLng marker = new LatLng(lat, lng);

            mMap.addMarker(new MarkerOptions().position(marker).title(lugar+": "+fecha));
        }
    }

}
