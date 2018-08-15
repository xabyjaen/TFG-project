package com.proyectodam.javi.proyectodam.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.proyectodam.javi.proyectodam.R;

public class TransferListFragment extends Fragment {

    ListView historyList;

    public TransferListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyList = (ListView) view.findViewById(R.id.HistoryList);
        String[] values = new String[] {
                "Fragment1",
                "Fragment2",
                "Fragment3",
                "Fragment4",
                "Fragment5",
                "Fragment6",
                "Fragment7",
                "Fragment8",
                "Fragment9",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        historyList.setAdapter(adapter);

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                
            }
        });
        return view;
    }
}