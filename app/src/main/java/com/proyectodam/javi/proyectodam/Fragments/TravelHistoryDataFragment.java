package com.proyectodam.javi.proyectodam.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.proyectodam.javi.proyectodam.Entity.Archivo;
import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Manager.ArchivosManager;
import com.proyectodam.javi.proyectodam.Entity.Manager.ViajeManager;
import com.proyectodam.javi.proyectodam.Entity.Viaje;
import com.proyectodam.javi.proyectodam.R;

import java.util.ArrayList;

public class TravelHistoryDataFragment extends Fragment {

    ListView historyList;
    ArrayList<String> listaInformacion;
    ArrayList<Viaje> listaViajes;
    ArrayList<Archivo> listaArchivos;
    ConexionSQLiteHelper conn;

    public TravelHistoryDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView) view.findViewById(R.id.HistoryList);

        conn = new ConexionSQLiteHelper(getActivity(), "bd_proyecto", null, 1);

        consultarArchivos();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaInformacion);
        historyList.setAdapter(adapter);

        return view;
    }

    private void consultarArchivos() {

        listaViajes = new ArrayList<Viaje>();
        listaArchivos = new ArrayList<Archivo>();
        ViajeManager viajeManager = new ViajeManager();
        ArchivosManager archivosManager = new ArchivosManager();

        listaViajes = viajeManager.getAllViajes(getActivity());
        listaInformacion = archivosManager.getAllArchivosAndLocations(getActivity(), listaViajes);
    }
}