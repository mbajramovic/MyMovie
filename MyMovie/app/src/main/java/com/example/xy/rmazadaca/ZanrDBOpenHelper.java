package com.example.xy.rmazadaca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maid Bajramovic on 13.6.2017..
 */

public class ZanrDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyMovieBase.db";
    public static final String DATABASE_TABLE = "Zanrovi";
    public static final int DATABASE_VERSION = 2;
    public static final String ZANR_ID = "_id";
    public static final String ZANR_NAZIV = "naziv_zanra";
    public static final String GLUMCI_IDs = "glumci_id";

    private static final String DATABASE_CREATE = "create table if not exists " + DATABASE_TABLE + " (" +
            ZANR_ID + " integer primary key autoincrement, " +
            ZANR_NAZIV + " text, " +
            GLUMCI_IDs + " text);";

    String[] kolone = new String[]{ZANR_ID, ZANR_NAZIV, GLUMCI_IDs};

    public ZanrDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

    public void dodajZanrove(Integer idGlumca) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Zanr> zanrovi = Pocetna.listaZanrova;
        String WHERE = null;
        for (int i = 0; i < zanrovi.size(); i++) {
            Log.d("MYTAG", "u petlji si.");
            WHERE = ZANR_NAZIV + " like '" + zanrovi.get(i).getNaziv() + "'";
            Cursor cursor = db.query(ZanrDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
            cursor.moveToNext();
            if (cursor.getCount() == 0) {
                Log.d("MYTAG", "u ifu si");
                ContentValues noviZanr = new ContentValues();
                noviZanr.put(ZANR_NAZIV, zanrovi.get(i).getNaziv());
                noviZanr.put(GLUMCI_IDs, idGlumca.toString());
                db.insert(ZanrDBOpenHelper.DATABASE_TABLE, null, noviZanr);
            } else {
                String glumci = cursor.getString(cursor.getColumnIndex(GLUMCI_IDs));
                Integer zanrID = cursor.getInt(cursor.getColumnIndex(ZANR_ID));
                glumci += "," + idGlumca.toString();
                ContentValues noviGl = new ContentValues();
                noviGl.put(GLUMCI_IDs, glumci);
                db.update(ZanrDBOpenHelper.DATABASE_TABLE, noviGl, ZANR_ID + "=" + zanrID, null);
            }
        }

    }

    public void brisiZanrove(Integer idGlumca) {
        SQLiteDatabase db = this.getWritableDatabase();
        String WHERE = "";
        for (int i = 0; i < Pocetna.listaZanrova.size(); i++) {
            WHERE = ZANR_NAZIV + " like '" + Pocetna.listaZanrova.get(i).getNaziv() + "';";
            Cursor cursor = db.query(ZanrDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String glumciIDs = cursor.getString(cursor.getColumnIndex(GLUMCI_IDs));
                    String[] glumci = glumciIDs.split(",");
                    if (glumci.length == 1) {
                        db.delete(ZanrDBOpenHelper.DATABASE_TABLE, WHERE, null);
                    }
                    else {
                        ArrayList<String> lista = new ArrayList<>(Arrays.asList(glumci));
                        lista.remove(idGlumca.toString());
                        glumci = lista.toArray(glumci);
                        glumciIDs = "";
                        for (int j = 0; j < glumci.length; j++) {
                            if (glumci[j] != null)
                                glumciIDs += glumci[j] + ",";
                        }
                        glumciIDs = glumciIDs.substring(0, glumciIDs.length() - 1);
                        ContentValues cv = new ContentValues();
                        cv.put(GLUMCI_IDs, glumciIDs);
                        WHERE = ZANR_NAZIV + " like '" + cursor.getString(cursor.getColumnIndex(ZANR_NAZIV)) + "';";
                        db.update(ZanrDBOpenHelper.DATABASE_TABLE, cv, WHERE, null);
                    }
                }
            }

        }


    }

    public void ucitajZanrove(Integer idGlumca) {
        Pocetna.listaZanrova = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String WHERE = GLUMCI_IDs + " like '%" + idGlumca.toString() + "%';";
        Cursor cursor = db.query(ZanrDBOpenHelper.DATABASE_TABLE, kolone, WHERE, null, null, null, null);
        if (cursor != null) {
            while(cursor.moveToNext()) {
                Zanr zanr = new Zanr();
                zanr.setNaziv(cursor.getString(cursor.getColumnIndex(ZANR_NAZIV)));
                Pocetna.listaZanrova.add(zanr);
            }
        }
    }
}
