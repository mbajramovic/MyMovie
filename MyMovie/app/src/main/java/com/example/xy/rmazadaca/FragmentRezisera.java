package com.example.xy.rmazadaca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 8.4.2017..
 */

public class FragmentRezisera extends Fragment {
    ArrayList<Reziser> listaRezisera = new ArrayList<Reziser>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_reziseri_i_zanrovi, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments().containsKey("Rlista")) {
            listaRezisera = getArguments().getParcelableArrayList("Rlista");
            //popuniListu();
            ListView lv = (ListView)getView().findViewById(R.id.listView);
            MojAdapterReziser adapter = new MojAdapterReziser(getActivity(), listaRezisera, getResources());
            lv.setAdapter(adapter);
        }
    }
}
