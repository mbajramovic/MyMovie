package com.example.xy.rmazadaca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 8.4.2017..
 */

public class FragmentZanrova extends Fragment {
    private ArrayList<Zanr> listaZanrova = new ArrayList<Zanr>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_reziseri_i_zanrovi, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments().containsKey("Zlista")) {
            listaZanrova = getArguments().getParcelableArrayList("Zlista");
            //popuniListu();
            ListView lv = (ListView)getView().findViewById(R.id.listView);
            MojAdapaterZanr adapter = new MojAdapaterZanr(getActivity(), listaZanrova, getResources());
            lv.setAdapter(adapter);

        }
    }
}
