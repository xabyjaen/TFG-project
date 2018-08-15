package com.proyectodam.javi.proyectodam.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.proyectodam.javi.proyectodam.Entity.Archivo;
import com.proyectodam.javi.proyectodam.Entity.ConexionSQLiteHelper;
import com.proyectodam.javi.proyectodam.Entity.Helper.StringHelper;
import com.proyectodam.javi.proyectodam.Entity.Manager.ArchivosManager;
import com.proyectodam.javi.proyectodam.Entity.Viaje;
import com.proyectodam.javi.proyectodam.R;

import java.util.ArrayList;

public class TravelListFragment extends Fragment {

    ListView historyList;
    ArrayList<String> informationList;
    ArrayList<Viaje> travelList;
    ConexionSQLiteHelper conn;

    public TravelListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView) view.findViewById(R.id.HistoryList);

        conn = new ConexionSQLiteHelper(getActivity(), "bd_proyecto", null, 1);

        getTravels();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, this.informationList);

        historyList.setAdapter(adapter);

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Viaje viajeId = travelList.get(position);
                ArchivosManager manager = new ArchivosManager();
                Archivo archivo = manager.getArchivoByViajeId(getActivity(), viajeId.getId());

                new AlertDialog.Builder(getActivity())
                        .setTitle(viajeId.getLugar())
                        .setMessage("Total Archivos: "+archivo.getNumeroArchivos() +
                                "\n("+archivo.getArchivos()+")" ).show();
            }
        });


//          TODO Para insertar
//        insertarRegistroAleatorio(2, "Jaen", "25/8/2018");
//        insertarRegistroAleatorio(3, "Albacete", "26/8/2018");
//        insertarRegistroAleatorio(4, "Murcia", "27/8/2018");

        return view;
    }


    private void getTravels() {

        SQLiteDatabase db = conn.getReadableDatabase();
        travelList = new ArrayList<Viaje>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ StringHelper.TABLA_VIAJES, null);

        while (cursor.moveToNext()){
            Viaje viaje = new Viaje();
            viaje.setId(cursor.getInt(0));
            viaje.setLugar(cursor.getString(1));
            String fechaString = cursor.getString(2);
            viaje.setCoordenadas( cursor.getString(3));

//            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//            Date parsed = null;
//            try {
//                parsed = format.parse(fechaString);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            viaje.setFecha(fechaString);
            travelList.add(viaje);
        }

        getInformationList();
    }

    private void getInformationList() {
        this.informationList = new ArrayList<String>();

        for (int i = 0; i< travelList.size(); i++){
            this.informationList.add(this.travelList.get(i).getLugar()+" - "+this.travelList.get(i).getFecha());
        }
    }

    /* TODO Eliminar este método*/
    private void insertarRegistroAleatorio(Integer id, String lugar, String fecha)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), "bd_proyecto", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(StringHelper.CAMPO_ID, id);
        values.put(StringHelper.CAMPO_LUGAR, lugar);
        values.put(StringHelper.CAMPO_FECHA, fecha);

        Long idResultante = db.insert(StringHelper.TABLA_VIAJES, StringHelper.CAMPO_ID, values);

        Toast.makeText(getActivity(), "Id Registro: "+ idResultante, Toast.LENGTH_SHORT).show();

        db.close();
    }

}