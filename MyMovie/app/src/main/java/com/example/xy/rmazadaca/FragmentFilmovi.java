package com.example.xy.rmazadaca;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 12.6.2017..
 */

public class FragmentFilmovi extends Fragment implements PretraziFilmove.onFilmSearchDone {
    public ArrayList<String> listaFilmova = new ArrayList<>();
    ListView listView;
    onItemClick oic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filmovi_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getView().findViewById(R.id.listaFilmova);
        Button pretraziFilmove = (Button)getView().findViewById(R.id.pretraziFilmove);
        final EditText filmoviKeyWord = (EditText)getView().findViewById(R.id.filmoviKeyWord);

        pretraziFilmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pocetna.listaFilmova = new ArrayList<String>();
                new PretraziFilmove((PretraziFilmove.onFilmSearchDone)FragmentFilmovi.this).execute(filmoviKeyWord.getText().toString());
            }
        });

        try {
            oic = (FragmentFilmovi.onItemClick)getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oic.onFilmItemClicked(position);
            }
        });

    }

    @Override
    public void onDoneFilm(ArrayList<String> listaFilmova) {
        this.listaFilmova = listaFilmova;
        Pocetna.listaFilmova = listaFilmova;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.element_liste_reziser, R.id.textView4, listaFilmova);
        listView.setAdapter(adapter);
    }

    public interface onItemClick {
        public void onFilmItemClicked(int pos);
    }
}
