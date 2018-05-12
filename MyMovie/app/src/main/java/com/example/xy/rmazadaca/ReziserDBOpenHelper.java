package com.example.xy.rmazadaca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bajramovic Maid on 10.6.2017..
 */

public class ReziserDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyMovieBase.db";
    public static final String DATABASE_TABLE = "Reziseri";
    public static final int DATABASE_VERSION = 2;
    public static final String REZISER_ID = "_id";
    public static final String REZISER_IMEIPREZIME = "Ime_i_prezime";
    public static final String GLUMCI_IDs = "glumci_id";

    private static final String DATABASE_CREATE = "create table if not exists " + DATABASE_TABLE + " (" +
                                                   REZISER_ID + " integer primary key autoincrement, " +
                                                   REZISER_IMEIPREZIME + " text, " +
                                                   GLUMCI_IDs + " text);";

    String[] kolone = new String[]{REZISER_ID, REZISER_IMEIPREZIME, GLUMCI_IDs};

    public ReziserDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void dodajRezisere(Integer idGlumca) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Reziser> reziseri = Pocetna.listaRezisera;
        String WHERE = null;
        for (int i = 0; i < reziseri.size(); i++) {
            Log.d("MYTAG", "u petlji si.");
            WHERE = REZISER_IMEIPREZIME + " like '" + reziseri.get(i).getFirstName() + "'";
            Cursor cursor = db.query(ReziserDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
            cursor.moveToNext();
            if (cursor.getCount() == 0) {
                Log.d("MYTAG", "u ifu si");
                ContentValues noviReziser = new ContentValues();
                noviReziser.put(REZISER_IMEIPREZIME, reziseri.get(i).getFirstName());
                noviReziser.put(GLUMCI_IDs, idGlumca.toString());
                db.insert(ReziserDBOpenHelper.DATABASE_TABLE, null, noviReziser);
            } else {
                String glumci = cursor.getString(cursor.getColumnIndex(GLUMCI_IDs));
                Integer reziserID = cursor.getInt(cursor.getColumnIndex(REZISER_ID));
                glumci += "," + idGlumca.toString();
                ContentValues noviGl = new ContentValues();
                noviGl.put(GLUMCI_IDs, glumci);
                db.update(ReziserDBOpenHelper.DATABASE_TABLE, noviGl, REZISER_ID + "=" + reziserID, null);
            }
        }

    }

    public void brisiRezisere(Integer idGlumca) {
        SQLiteDatabase db = this.getWritableDatabase();
        String WHERE = "";
        for (int i = 0; i < Pocetna.listaRezisera.size(); i++) {
            WHERE = REZISER_IMEIPREZIME + " like '" + Pocetna.listaRezisera.get(i).getFirstName() + "';";
            Cursor cursor = db.query(ReziserDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String glumciIDs = cursor.getString(cursor.getColumnIndex(GLUMCI_IDs));
                    String[] glumci = glumciIDs.split(",");
                    if (glumci.length == 1) {
                        db.delete(ReziserDBOpenHelper.DATABASE_TABLE, WHERE, null);
                    }
                    else {
                        ArrayList<String> lista = new ArrayList<>(Arrays.asList(glumci));
                        lista.remove(idGlumca.toString());
                        glumci = lista.toArray(glumci);
                        glumciIDs = new String();
                        for (int j = 0; j < glumci.length; j++) {
                            if (glumci[j] != null)
                                glumciIDs += glumci[j] + ",";
                        }
                        glumciIDs = glumciIDs.substring(0, glumciIDs.length() - 1);
                        ContentValues cv = new ContentValues();
                        cv.put(GLUMCI_IDs, glumciIDs);
                        WHERE = REZISER_IMEIPREZIME + " like '" + cursor.getString(cursor.getColumnIndex(REZISER_IMEIPREZIME)) + "';";
                        db.update(ReziserDBOpenHelper.DATABASE_TABLE, cv, WHERE, null);
                    }
                }
            }

        }


    }

    public void ucitajRezisere(Integer idGlumca) {
        Pocetna.listaRezisera = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String WHERE = GLUMCI_IDs + " like '%" + idGlumca.toString() + "%';";
        Cursor cursor = db.query(ReziserDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
        if (cursor != null) {
            while(cursor.moveToNext()) {
                Reziser reziser = new Reziser();
                reziser.setFirstName(cursor.getString(cursor.getColumnIndex(REZISER_IMEIPREZIME)));
                reziser.setLastName("");
                Pocetna.listaRezisera.add(reziser);
            }
        }
    }
}
