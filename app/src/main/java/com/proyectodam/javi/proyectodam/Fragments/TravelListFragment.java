package com.proyectodam.javi.proyectodam.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.proyectodam.javi.proyectodam.Entity.Folder;
import com.proyectodam.javi.proyectodam.Helper.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Manager.FolderManager;
import com.proyectodam.javi.proyectodam.Entity.Manager.PlaceManager;
import com.proyectodam.javi.proyectodam.Entity.Manager.TravelManager;
import com.proyectodam.javi.proyectodam.Entity.Place;
import com.proyectodam.javi.proyectodam.Entity.Travel;
import com.proyectodam.javi.proyectodam.R;

import java.util.ArrayList;

public class TravelListFragment extends Fragment {

    ListView historyList;
    ArrayList<String> informationList;
    ArrayList<Folder> listFolder;
    ConexionSQLiteHelper conn;
    PlaceAutocompleteFragment autocompleteFragment;
    TextView placeTextView;
    public String coordinates;

    public TravelListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView) view.findViewById(R.id.HistoryList);

        conn = new ConexionSQLiteHelper(getActivity(), "bd_proyecto", null, 1);

        getFolders();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, this.informationList);

        historyList.setAdapter(adapter);

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Folder folder = listFolder.get(position);
                getFolderDataDialog(folder);
            }
        });

        return view;
    }

    public void getFolderDataDialog(final Folder folder)
    {
        final AlertDialog.Builder folderDataDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_folder_data, null);

        TextView folderInfo = v.findViewById(R.id.info_folder_text_view);
        Button showFilesButton = v.findViewById(R.id.show_files_button);
        Button editTravelButton = v.findViewById(R.id.edit_travel_button);

        showFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                .setMessage(folder.getFiles())
                .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
            }
        });

        editTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFormTravelDialog(folder);
            }
        });

        folderInfo.setText("Total Archivos: "+ folder.getNumberFiles() + "\n" + "Fecha de descarga: " + folder.getDate());
        folderDataDialog.setTitle(folder.getName());
        folderDataDialog.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        folderDataDialog.setView(v);
        AlertDialog dialog = folderDataDialog.create();
        dialog.show();
    }

    public void getFormTravelDialog(final Folder folder)
    {
        final AlertDialog.Builder formTravelDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_travel_form, null);

        final EditText personsTextView = (v.findViewById(R.id.persons_edit_text));
        final EditText commentsTextView = (v.findViewById(R.id.comments_edit_text));

        placeTextView = v.findViewById(R.id.LugarText);
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragmentAutocompletar);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(com.google.android.gms.location.places.Place place) {
                placeTextView.setText(place.getName());
                coordinates = place.getLatLng().toString();
            }
            @Override
            public void onError(Status status) {
                getFragmentManager().beginTransaction().remove(autocompleteFragment).commit();
            }
        });

        formTravelDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getFragmentManager().beginTransaction().remove(autocompleteFragment).commit();
                dialog.cancel();
            }
        });

        final PlaceManager placeManager = new PlaceManager();
        final Place place = placeManager.getPlaceByFolderId(getActivity(), folder.getId());
        final TravelManager travelManager = new TravelManager();
        final Travel travel = travelManager.gerTravelByFolderID(getActivity(), folder.getId());


        if (place.getId() != null && travel.getId() != null) {
            placeTextView.setText(place.getName());
            personsTextView.setText(travel.getPeople());
            commentsTextView.setText(travel.getComments());
        }

            formTravelDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), "bd_proyecto", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();

                if (place.getId() == null && travel.getId() == null) {
                    Long idViaje = placeManager.saveNewPlace(
                            placeTextView.getText().toString(),
                            folder.getId(),
                            coordinates,
                            db);

                    Integer viajeId = (int) (long) idViaje.intValue();
                    travelManager.saveNewTravel(
                            personsTextView.getText().toString(),
                            commentsTextView.getText().toString(),
                            folder.getId(),
                            viajeId,
                            db
                    );
                }
                else {
                    placeManager.editPlace(
                            placeTextView.getText().toString(),
                            place.getId(),
                            coordinates,
                            db);
                    travelManager.editTravel(
                            personsTextView.getText().toString(),
                            commentsTextView.getText().toString(),
                            travel.getId(),
                            db
                    );
                }
                db.close();
                conn.close();
                getFragmentManager().beginTransaction().remove(autocompleteFragment).commit();
            }
        });

        formTravelDialog.setView(v);
        AlertDialog dialog = formTravelDialog.create();
        dialog.show();

    }

    private void getFolders() {

        SQLiteDatabase db = conn.getReadableDatabase();
        FolderManager folderManager = new FolderManager();
        this.listFolder = folderManager.getAllFolders(getActivity());
        getInformationList();
    }

    private void getInformationList() {
        this.informationList = new ArrayList<String>();

        for (int i = 0; i< this.listFolder.size(); i++){
            this.informationList.add(this.listFolder.get(i).getName()+" - "+this.listFolder.get(i).getDate());
        }
    }
}