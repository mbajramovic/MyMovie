package com.example.xy.rmazadaca;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.xy.rmazadaca.GlumacDBOpenHelper.DATABASE_TABLE;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.DATABASE_VERSION;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_BIOGRAFIJA;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_GODINARODJENJA;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_GODINASMRTI;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_ID;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_IMBD;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_IMEIPREZIME;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_MJESTORODJENJA;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_RATING;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_SLIKA;
import static com.example.xy.rmazadaca.GlumacDBOpenHelper.GLUMAC_SPOL;
import static com.example.xy.rmazadaca.ReziserDBOpenHelper.GLUMCI_IDs;
import static com.example.xy.rmazadaca.ReziserDBOpenHelper.REZISER_ID;
import static com.example.xy.rmazadaca.ReziserDBOpenHelper.REZISER_IMEIPREZIME;

/**
 * Created by Bajramovic Maid on 8.4.2017..
 */

public class FragmentGlumaca extends Fragment implements PretraziGlumce.onGlumacSearchDone {
    public static ArrayList<Glumac> listaGlumaca = new ArrayList<Glumac>();
    ListView lv;
    onItemClick oic;
    private String _keyWords;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView)getView().findViewById(R.id.listView);
        Button searchButton = (Button)getView().findViewById(R.id.searchButton);
        final EditText keyWords = (EditText)getView().findViewById(R.id.editText);
        searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _keyWords = keyWords.getText().toString();
                    // klikom na drugog glumca brišu se svi prikupljeni zanrovi i reziseri
                    Pocetna.listaGlumaca = new ArrayList<Glumac>();
                    Pocetna.listaRezisera = new ArrayList<Reziser>();
                    Pocetna.listaZanrova = new ArrayList<Zanr>();
                    if (keyWords.length() > 5) {
                        if (keyWords.getText().toString().substring(0, 5).equals("actor")) {
                            String[] kolone = new String[]{GLUMAC_ID, GLUMAC_IMEIPREZIME, GLUMAC_GODINARODJENJA, GLUMAC_MJESTORODJENJA, GLUMAC_GODINASMRTI, GLUMAC_BIOGRAFIJA, GLUMAC_SPOL, GLUMAC_RATING, GLUMAC_SLIKA, GLUMAC_IMBD};
                            GlumacDBOpenHelper glumacDBOpenHelper = new GlumacDBOpenHelper(Pocetna.pocetna, "string", null, DATABASE_VERSION);
                            SQLiteDatabase db = glumacDBOpenHelper.getWritableDatabase();
                            String WHERE = GLUMAC_IMEIPREZIME + " like '%" + keyWords.getText().toString().substring(6, keyWords.getText().toString().length()) + "%';";
                            Cursor cursor = db.query(GlumacDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
                            int ind = cursor.getColumnIndex(GLUMAC_IMEIPREZIME);
                            GlumacCursorAdapter glumacCursorAdapter = new GlumacCursorAdapter(Pocetna.pocetna, R.layout.element_liste, cursor, DATABASE_VERSION);
                            lv.setAdapter(glumacCursorAdapter);
                        }
                        else if (keyWords.length() > 8) {
                            if (keyWords.getText().toString().substring(0,8).equals("director")) {
                                String[] kolone = new String[]{REZISER_ID, REZISER_IMEIPREZIME, GLUMCI_IDs};
                                ReziserDBOpenHelper reziserDBOpenHelper = new ReziserDBOpenHelper(Pocetna.pocetna, "s", null, ReziserDBOpenHelper.DATABASE_VERSION);
                                String WHERE = REZISER_IMEIPREZIME + " like '%" + keyWords.getText().toString().substring(9, keyWords.getText().toString().length()) + "%';";
                                SQLiteDatabase db = reziserDBOpenHelper.getWritableDatabase();
                                Cursor cursor = db.query(ReziserDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
                                String glumci_ids = null;
                                if (cursor != null) {
                                    if (cursor.moveToFirst())
                                        glumci_ids = cursor.getString(cursor.getColumnIndex(GLUMCI_IDs));
                                }

                                String[] glumci = glumci_ids.split(",");
                                GlumacDBOpenHelper glumacDBOpenHelper = new GlumacDBOpenHelper(Pocetna.pocetna, "S", null, DATABASE_VERSION);
                                db = glumacDBOpenHelper.getWritableDatabase();
                                kolone = new String[]{GLUMAC_ID, GLUMAC_IMEIPREZIME, GLUMAC_GODINARODJENJA, GLUMAC_MJESTORODJENJA, GLUMAC_GODINASMRTI, GLUMAC_BIOGRAFIJA, GLUMAC_SPOL, GLUMAC_RATING, GLUMAC_SLIKA, GLUMAC_IMBD};
                                WHERE = GLUMAC_ID;
                                Integer s = glumci.length;
                                Log.d("MYTAG", s.toString());
                                for (int i = 0; i < glumci.length; i++) {
                                    if (i != glumci.length  - 1)
                                        WHERE += "=" + Integer.parseInt(glumci[i]) + " OR " + GLUMAC_ID;
                                    else
                                        WHERE += "=" + Integer.parseInt(glumci[i]) + ";";
                                }
                                Log.d("MYTAG", WHERE);
                                cursor = db.query(DATABASE_TABLE, kolone, WHERE, null, null, null, null);
                                GlumacCursorAdapter glumacCursorAdapter = new GlumacCursorAdapter(Pocetna.pocetna, R.layout.element_liste, cursor, DATABASE_VERSION);
                                lv.setAdapter(glumacCursorAdapter);


                            }
                            else
                                theMovieDB();
                        }
                        else
                            theMovieDB();
                    }
                    else {
                        theMovieDB();
                    }
                }
            });
        try {
            oic = (onItemClick)getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oic.onItemClicked(position);
            }
        });
    }

    @Override
    public void onDone(ArrayList<Glumac> _listaGlumaca) {
        listaGlumaca = _listaGlumaca;
        Pocetna.listaGlumaca = listaGlumaca;
        MojAdapter adapter = new MojAdapter(getActivity(), Pocetna.listaGlumaca, getResources());
        lv = (ListView)getView().findViewById(R.id.listView);
        Pocetna.bookmarked = false;
        lv.setAdapter(adapter);
    }

    public interface onItemClick {
        public void onItemClicked(int pos);
    }

    private void theMovieDB()
    {
        Pocetna.listaZanrova = new ArrayList<Zanr>();
        Pocetna.listaRezisera = new ArrayList<Reziser>();
        Pocetna.listaGlumaca = new ArrayList<Glumac>();
        new PretraziGlumce((PretraziGlumce.onGlumacSearchDone) FragmentGlumaca.this).execute(_keyWords);
        MojAdapter adapter = new MojAdapter(getActivity(), listaGlumaca, getResources());
        lv.setAdapter(adapter);
    }
}
