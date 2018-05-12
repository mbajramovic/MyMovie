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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 19.3.2017.. (custom adapter za glumce)
 */

public class MojAdapter extends BaseAdapter {
    private Activity aktivnost;
    private ArrayList<Glumac> glumci;
    private static LayoutInflater inflater = null;
    public Resources resursi;
    Glumac glumac = null;
    RoundImage roundImage;
    int i = 0;


    public MojAdapter(Activity aktivnost, ArrayList<Glumac> glumci, Resources resursi) {
        this.aktivnost = aktivnost;
        this.glumci = glumci;
        this.resursi = resursi;
        this.inflater = (LayoutInflater)aktivnost.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (glumci.size() < 0)
            return 1;
        return glumci.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder{

        public TextView imePrezime;
        public TextView godinaRodjenja;
        public TextView mjestoRodjenja;
        public TextView rating;
        public ImageView slika;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){
            vi = inflater.inflate(R.layout.element_liste, null);
            holder = new ViewHolder();
            holder.imePrezime = (TextView) vi.findViewById(R.id.textView);
            holder.godinaRodjenja = (TextView)vi.findViewById(R.id.textView7);
            holder.mjestoRodjenja = (TextView)vi.findViewById(R.id.textView2);
            holder.rating = (TextView)vi.findViewById(R.id.textView3);
            holder.slika =(ImageView)vi.findViewById(R.id.imageView);
            vi.setTag(holder);
        }
        else {
            holder = (ViewHolder) vi.getTag();
        }

        if(glumci.size()<=0) {
            holder.imePrezime.setText("Nema podataka");
        }
        else {
            glumac = null;
            glumac = (Glumac)glumci.get(position);
            holder.imePrezime.setText(("    " +  glumac.getImeGlumca() + " " + glumac.getPrezimeGlumca()).toUpperCase());
            holder.godinaRodjenja.setText("    " + glumac.getGodinaRodjenja() + "");
            holder.mjestoRodjenja.setText("    " + glumac.getMjestoRodjenja());
            holder.rating.setText(glumac.getRating() + "       ");
            Picasso.with(Pocetna.context).load("https://image.tmdb.org/t/p/w640" + glumac.getSlikaPath()).into(holder.slika);
        }
        return vi;
    }
}
