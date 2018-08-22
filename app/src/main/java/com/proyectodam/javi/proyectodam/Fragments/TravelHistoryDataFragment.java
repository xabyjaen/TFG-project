package com.proyectodam.javi.proyectodam.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.proyectodam.javi.proyectodam.Entity.Folder;
import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Manager.FolderManager;
import com.proyectodam.javi.proyectodam.Entity.Manager.PlaceManager;
import com.proyectodam.javi.proyectodam.Entity.Place;
import com.proyectodam.javi.proyectodam.R;

import java.util.ArrayList;

public class TravelHistoryDataFragment extends Fragment {

    ListView historyList;
    ArrayList<String> listaInformacion;
    ArrayList<Place> listaPlaces;
    ArrayList<Folder> listaFolders;
    ConexionSQLiteHelper conn;

    public TravelHistoryDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView) view.findViewById(R.id.HistoryList);

        conn = new ConexionSQLiteHelper(getActivity(), "bd_proyecto", null, 1);

        searchFolders();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaInformacion);
        historyList.setAdapter(adapter);

        return view;
    }

    private void searchFolders() {

        listaPlaces = new ArrayList<Place>();
        listaFolders = new ArrayList<Folder>();
        PlaceManager placeManager = new PlaceManager();
        FolderManager folderManager = new FolderManager();

        listaPlaces = placeManager.getAllPLaces(getActivity());
//        listaInformacion = folderManager.getAllFolderAndLocations(getActivity(), listaPlaces);
    }
}