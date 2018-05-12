package com.example.xy.rmazadaca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bajramovic Maid on 7.6.2017..
 */

public class GlumacDBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyMovieBase.db";
    public static final String DATABASE_TABLE = "Glumci";
    public static final int DATABASE_VERSION = 2;
    public static final String GLUMAC_ID = "_id";
    public static final String GLUMAC_IMEIPREZIME = "Ime_i_prezime";
    public static final String GLUMAC_GODINARODJENJA = "Godina_rodjenja";
    public static final String GLUMAC_MJESTORODJENJA = "Mjesto_rodjenja";
    public static final String GLUMAC_GODINASMRTI = "Godina_smrti";
    public static final String GLUMAC_SPOL = "Spol";
    public static final String GLUMAC_BIOGRAFIJA = "Biografija";
    public static final String GLUMAC_RATING = "Rating";
    public static final String GLUMAC_SLIKA = "Slika";
    public static final String GLUMAC_IMBD = "Imbd";

    private static final String DATABASE_CREATE = "create table if not exists " + DATABASE_TABLE + " (" +
                                                   GLUMAC_ID + " integer primary key autoincrement, " +
                                                   GLUMAC_IMEIPREZIME + " text not null, " +
                                                   GLUMAC_MJESTORODJENJA + " text, " +
                                                   GLUMAC_GODINARODJENJA + " text, " +
                                                   GLUMAC_GODINASMRTI + " text, " +
                                                   GLUMAC_SPOL + " text not null, " +
                                                   GLUMAC_BIOGRAFIJA + " text, " +
                                                   GLUMAC_RATING + " text, " +
                                                   GLUMAC_SLIKA + " text, " +
                                                   GLUMAC_IMBD + " text);";

    String[] kolone = new String[]{GLUMAC_ID, GLUMAC_IMEIPREZIME, GLUMAC_GODINARODJENJA, GLUMAC_MJESTORODJENJA, GLUMAC_GODINASMRTI, GLUMAC_BIOGRAFIJA, GLUMAC_SPOL, GLUMAC_RATING, GLUMAC_SLIKA, GLUMAC_IMBD };

    public GlumacDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

    public void dodajGlumca(Glumac glumac) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues noviGlumac = new ContentValues();
        noviGlumac.put(GLUMAC_IMEIPREZIME, glumac.getImeGlumca());
        noviGlumac.put(GLUMAC_MJESTORODJENJA, glumac.getMjestoRodjenja());
        noviGlumac.put(GLUMAC_GODINARODJENJA, Integer.toString(glumac.getGodinaRodjenja()));
        noviGlumac.put(GLUMAC_GODINASMRTI, Integer.toString(glumac.getGodinaSmrti()));
        noviGlumac.put(GLUMAC_BIOGRAFIJA, glumac.getTekstBiografije());
        noviGlumac.put(GLUMAC_RATING, Double.toString(glumac.getRating()));
        noviGlumac.put(GLUMAC_SPOL, glumac.getSpolGlumca());
        noviGlumac.put(GLUMAC_SLIKA, glumac.getSlikaPath());
        noviGlumac.put(GLUMAC_IMBD, glumac.getImbdStranica());

        //db = this.getWritableDatabase();
        db.insert(GlumacDBOpenHelper.DATABASE_TABLE, null, noviGlumac);
        String WHERE = GLUMAC_IMEIPREZIME + " like '" + glumac.getImeGlumca() + "'";
        Cursor cursor = db.query(DATABASE_TABLE, kolone, WHERE, null, null, null, null);
        Integer idGlumca = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Log.d("HEHE", "HEHE");
                idGlumca = cursor.getInt(cursor.getColumnIndex(GLUMAC_ID)); }
        }

        ReziserDBOpenHelper reziserDBOpenHelper = new ReziserDBOpenHelper(Pocetna.pocetna, "string", null, ReziserDBOpenHelper.DATABASE_VERSION);
        reziserDBOpenHelper.dodajRezisere(idGlumca);
        ZanrDBOpenHelper zanrDBOpenHelper = new ZanrDBOpenHelper(Pocetna.pocetna, "string", null, ZanrDBOpenHelper.DATABASE_VERSION);
        zanrDBOpenHelper.dodajZanrove(idGlumca);
    }

    public void brisiGlumca(Glumac glumac) {
        SQLiteDatabase db = this.getWritableDatabase();
        String WHERE = GLUMAC_IMEIPREZIME + " like '" + glumac.getImeGlumca() + "';";
        Cursor cursor = db.query(DATABASE_TABLE, kolone, WHERE, null, null, null, null);
        Integer idGlumca = -1;
        if (cursor != null) {
            if (cursor.moveToFirst())
                idGlumca = cursor.getInt(cursor.getColumnIndex(GLUMAC_ID));
        }
        WHERE = GLUMAC_ID + "=" + idGlumca.toString();
        db.delete(DATABASE_TABLE, WHERE, null);

        ReziserDBOpenHelper reziserDBOpenHelper = new ReziserDBOpenHelper(Pocetna.pocetna, "S", null, ReziserDBOpenHelper.DATABASE_VERSION);
        reziserDBOpenHelper.brisiRezisere(idGlumca);
        ZanrDBOpenHelper zanrDBOpenHelper = new ZanrDBOpenHelper(Pocetna.pocetna, "s", null, ZanrDBOpenHelper.DATABASE_VERSION);
        zanrDBOpenHelper.brisiZanrove(idGlumca);

    }
}
