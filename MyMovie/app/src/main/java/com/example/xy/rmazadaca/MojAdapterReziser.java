package com.example.xy.rmazadaca;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 8.4.2017..
 */

public class MojAdapterReziser extends BaseAdapter {
    private Activity aktivnost;
    private ArrayList<Reziser> reziseri;
    private static LayoutInflater inflater = null;
    Reziser reziser = null;
    Resources resursi = null;
    RoundImage roundImage;

    public MojAdapterReziser(Activity aktivnost, ArrayList<Reziser> reziseri, Resources resursi) {
        this.aktivnost = aktivnost;
        this.reziseri = reziseri;
        this.resursi = resursi;
        this.inflater = (LayoutInflater)aktivnost.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (reziseri.size() < 0)
            return 1;
        return reziseri.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public class ViewHolderRez {
        public TextView tekst;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MojAdapterReziser.ViewHolderRez holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.element_liste_reziser, null);
            holder = new MojAdapterReziser.ViewHolderRez();
            holder.tekst = (TextView)view.findViewById(R.id.textView4);
            view.setTag(holder);
        }
        else {
            holder = (MojAdapterReziser.ViewHolderRez)view.getTag();
        }

        if (reziseri.size() <= 0) {
            holder.tekst.setText("Nema podataka");
        }
        else {
            reziser = null;
            reziser = (Reziser)reziseri.get(position);

            holder.tekst.setText(("\n" + reziser.getFirstName() + " " + reziser.getLastName()));


        }
        return view;
    }
}
