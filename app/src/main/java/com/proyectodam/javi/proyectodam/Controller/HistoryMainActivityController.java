package com.proyectodam.javi.proyectodam.Controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.proyectodam.javi.proyectodam.Fragments.TravelListFragment;
import com.proyectodam.javi.proyectodam.R;

public class HistoryMainActivityController {

    private FragmentManager fragmentManager;

    public HistoryMainActivityController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public final void getFragment() {
        Fragment fragment;
        fragment = new TravelListFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.RelativeLayout, fragment);
        fragmentTransaction.commit();
    }
}
