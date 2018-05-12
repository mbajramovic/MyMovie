package com.example.xy.rmazadaca;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Bajramovic Maid on 25.3.2017..
 */

public class Zanr implements Parcelable {
    String naziv;
    int slika;
    String id;

    public Zanr(String naziv, int slika) {
        this.naziv = naziv;
        this.slika = slika;
    }

    public Zanr() {
        this.naziv = "";
        this.id = "";
    }

    public Zanr(Parcel in) {
        naziv = in.readString();
        slika = in.readInt();
        id = in.readString();
    }

    public static final Parcelable.Creator<Zanr> CREATOR = new Parcelable.Creator<Zanr>() {
        public Zanr createFromParcel(Parcel in) {
            return new Zanr(in);
        }

        public Zanr[] newArray(int size) {
            return new Zanr[size];
        }
    };

    public String getNaziv() {
        return naziv;
    }

    public int getSlika() {
        return slika;
    }

    public String getID() { return id; }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setSlika(int slika) {
        this.slika = slika;
    }

    public void setID(String id) { this.id = id; }

    public String dajNaziv(String id) {
        return this.naziv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(naziv);
        dest.writeInt(slika);

    }


}
