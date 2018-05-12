package com.example.xy.rmazadaca;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Bajramovic Maid on 19.3.2017..
 */

public class Glumac implements Parcelable {
    String imeGlumca;
    String prezimeGlumca;
    int godinaRodjenja;
    int godinaSmrti = 0;
    String tekstBiografije;
    int slikaGlumca;
    String mjestoRodjenja;
    String spolGlumca;
    String imbdStranica;
    double rating;
    String slikaPath;
    String id;

    public Glumac() {
        this.imeGlumca = "";
        this.prezimeGlumca = "";
        this.godinaRodjenja = 0;
        this.godinaSmrti = 0;
        this.tekstBiografije = "";
        this.slikaGlumca = 0;
        this.mjestoRodjenja = "";
        this.spolGlumca = "";
        this.imbdStranica = "";
        this.rating = 0.0;
        this.slikaPath = "";
        this.id = "";
    }

    public Glumac(String imeGlumca, String prezimeGlumca, int godinaRodjenja, int godinaSmrti, String tekstBiografije, int slikaGlumca, String mjestoRodjenja, String spolGlumca, String imbdStranica, double rating) {
        this.imeGlumca = imeGlumca;
        this.prezimeGlumca = prezimeGlumca;
        this.godinaRodjenja = godinaRodjenja;
        this.godinaSmrti = godinaSmrti;
        this.tekstBiografije = tekstBiografije;
        this.slikaGlumca = slikaGlumca;
        this.mjestoRodjenja = mjestoRodjenja;
        this.spolGlumca = spolGlumca;
        this.imbdStranica = imbdStranica;
        this.rating = rating;
    }

    public Glumac(Parcel in) {
        imeGlumca = in.readString();
        prezimeGlumca = in.readString();
        godinaRodjenja = in.readInt();
        godinaSmrti = in.readInt();
        tekstBiografije = in.readString();
        slikaGlumca = in.readInt();
        mjestoRodjenja = in.readString();
        spolGlumca = in.readString();
        imbdStranica = in.readString();
        rating = in.readDouble();
        slikaPath = in.readString();
        id = in.readString();
    }

    public static final Parcelable.Creator<Glumac> CREATOR = new Parcelable.Creator<Glumac>() {
        public Glumac createFromParcel(Parcel in) {
            return new Glumac(in);
        }

        public Glumac[] newArray(int size) {
            return new Glumac[size];
        }
    };


    public void setImeGlumca(String imeGlumca) {
        this.imeGlumca = imeGlumca;
    }

    public void setPrezimeGlumca(String prezimeGlumca) {
        this.prezimeGlumca = prezimeGlumca;
    }

    public void setGodinaRodjenja(int godinaRodjenja) {
        this.godinaRodjenja = godinaRodjenja;
    }

    public void setGodinaSmrti(int godinaSmrti) {
        this.godinaSmrti = godinaSmrti;
    }

    public void setTekstBiografije(String tekstBiografije) {
        this.tekstBiografije = tekstBiografije;
    }

    public void setSlikaGlumca(int slikaGlumca) {
        this.slikaGlumca = slikaGlumca;
    }

    public void setMjestoRodjenja(String mjestoRodjenja) {
        this.mjestoRodjenja = mjestoRodjenja;
    }

    public void setSpolGlumca(String spolGlumca) {
        this.spolGlumca = spolGlumca;
    }

    public void setImbdStranica(String imbdStranica) {
        this.imbdStranica = imbdStranica;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setSlikaPath(String slikaPath) {
        this.slikaPath = slikaPath;
    }

    public String getImeGlumca() {
        return imeGlumca;
    }

    public String getPrezimeGlumca() {
        return prezimeGlumca;
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja;
    }

    public int getGodinaSmrti() {
        return godinaSmrti;
    }

    public String getTekstBiografije() {
        return tekstBiografije;
    }

    public int getSlikaGlumca() {
        return slikaGlumca;
    }

    public String getMjestoRodjenja() {
        return mjestoRodjenja;
    }

    public String getSpolGlumca() {
        return spolGlumca;
    }

    public String getImbdStranica() {
        return imbdStranica;
    }

    public double getRating() {
        return rating;
    }

    public String getSlikaPath(){
        return slikaPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imeGlumca);
        dest.writeString(prezimeGlumca);
        dest.writeInt(godinaRodjenja);
        dest.writeInt(godinaSmrti);
        dest.writeString(tekstBiografije);
        dest.writeInt(slikaGlumca);
        dest.writeString(mjestoRodjenja);
        dest.writeString(spolGlumca);
        dest.writeString(imbdStranica);
        dest.writeDouble(rating);
        dest.writeString(slikaPath);
        dest.writeString(id);
    }

}
