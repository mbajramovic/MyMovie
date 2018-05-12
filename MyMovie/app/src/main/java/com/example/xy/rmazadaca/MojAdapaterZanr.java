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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bajramovic Maid on 19.3.2017.. (custom adapter za zanrove)
 */

public class MojAdapaterZanr extends BaseAdapter {
    private Activity aktivnost;
    private ArrayList<Zanr> zanrovi;
    private static LayoutInflater inflater = null;
    Zanr zanr = null;
    Resources resursi = null;
    RoundImage roundImage;

    public MojAdapaterZanr(Activity aktivnost, ArrayList<Zanr> zanrovi, Resources resursi) {
        this.aktivnost = aktivnost;
        this.zanrovi = zanrovi;
        this.resursi = resursi;
        this.inflater = (LayoutInflater)aktivnost.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (zanrovi.size() < 0)
            return 1;
        return zanrovi.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public class ViewHolderRez {
        public TextView tekst;
        public ImageView ikona;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolderRez holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.element_liste_zanrovi, null);
            holder = new ViewHolderRez();
            holder.tekst = (TextView)view.findViewById(R.id.textView5);
            holder.ikona = (ImageView)view.findViewById(R.id.imageView2);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolderRez)view.getTag();
        }

        if (zanrovi.size() <= 0) {
            holder.tekst.setText("Nema podataka");
        }
        else {
            zanr = null;
            zanr = (Zanr)zanrovi.get(position);

            holder.tekst.setText(("\n" + zanr.getNaziv()).toUpperCase());

            /*Bitmap bitmap = BitmapFactory.decodeResource(resursi, zanr.getSlika());
            roundImage = new RoundImage(bitmap);
            holder.ikona.setImageDrawable(roundImage);*/

        }
        return view;
    }
}
