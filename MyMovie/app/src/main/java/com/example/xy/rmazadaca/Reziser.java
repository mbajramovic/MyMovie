package com.example.xy.rmazadaca;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bajramovic Maid on 8.4.2017..
 */

public class Reziser implements Parcelable {
    String firstName;
    String lastName;

    public Reziser() {}
    public Reziser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Reziser(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static final Parcelable.Creator<Reziser> CREATOR = new Parcelable.Creator<Reziser>() {
        public Reziser createFromParcel(Parcel in) {
            return new Reziser(in);
        }

        public Reziser[] newArray(int size) {
            return new Reziser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
    }


}
