package com.example.xy.rmazadaca;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

public class FragmentDetalji extends Fragment {
    Glumac glumac;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.detalji_o_glumcu_fragment, container, false);
        Button bookmarkButton = (Button)vi.findViewById(R.id.bookmarkButton);
        if (Pocetna.bookmarked)
            bookmarkButton.setText("REMOVE BOOKMARK");
        if (getArguments()!= null && getArguments().containsKey("glumac")) {
            glumac = getArguments().getParcelable("glumac");
            final TextView imeIPrezimeGlumca = (TextView)vi.findViewById(R.id.textView11);
            imeIPrezimeGlumca.setText((glumac.getImeGlumca() + " " + glumac.getPrezimeGlumca()).toUpperCase());
            TextView godinaRodjenja = (TextView)vi.findViewById(R.id.godinaRodjenja);
            godinaRodjenja.setText(" " +  glumac.getGodinaRodjenja() + ".");
            TextView godinaSmrti = (TextView)vi.findViewById(R.id.godinaSmrti);
            if (glumac.getGodinaSmrti() != 0)
                godinaSmrti.setText(" " + glumac.getGodinaSmrti() + ".");
            else
                godinaSmrti.setText("/");
            final TextView tekstBiografije = (TextView)vi.findViewById(R.id.biografija);
            tekstBiografije.setText(glumac.getTekstBiografije());
            tekstBiografije.setMovementMethod(new ScrollingMovementMethod());
            final TextView imbdStranica = (TextView)vi.findViewById(R.id.imbdStranica);
            imbdStranica.setText(glumac.getImbdStranica() + "\n");
            ImageView slika = (ImageView)vi.findViewById(R.id.slikaGlumca);
            //slika.setImageResource(glumac.getSlikaGlumca());
            Picasso.with(Pocetna.context).load("https://image.tmdb.org/t/p/w640" + glumac.getSlikaPath()).into(slika);
            String spol = glumac.getSpolGlumca();
            //ImageView spolIk = (ImageView)findViewById(R.id.spolIkona);
            TextView spolGlumca = (TextView)vi.findViewById(R.id.spol);
            LinearLayout linearLayout1 = (LinearLayout)vi.findViewById(R.id.linearLayout1);
            Button podijeliButton = (Button)vi.findViewById(R.id.podijeliButton);

            if (spol.equals("muski")) {
                //linearLayout1.setBackgroundResource(R.drawable.gradient);
                //spolIk.setImageResource(R.drawable.spol_muski);
                spolGlumca.setText(R.string.muskiSpol);
               /* podijeliButton.setBackgroundResource(R.color.dodatna_muska);
                bookmarkButton.setBackgroundResource(R.color.dodatna_muska);*/
            }
            else {
                //linearLayout1.setBackgroundResource(R.drawable.gradient_zenski_spol);
                spolGlumca.setText(R.string.zenskiSpol);
                /*podijeliButton.setBackgroundResource(R.color.dodatna_zenska);
                bookmarkButton.setBackgroundResource(R.color.dodatna_zenska);*/
            }

            TextView mjestoRodjenja = (TextView)vi.findViewById(R.id.mjestoRodjenja);
            if (mjestoRodjenja != null)
                mjestoRodjenja.setText("" + glumac.getMjestoRodjenja());
            TextView rating = (TextView)vi.findViewById(R.id.rating);
            rating.setText(glumac.rating + "    ");

            imbdStranica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = imbdStranica.getText().toString();
                    //url = url.substring(0, url.length() - 1);
                    Intent myIntent = new Intent(Intent.ACTION_VIEW);
                    myIntent.setData(Uri.parse(url));
                    startActivity(myIntent);
                }
            });

            podijeliButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    String poruka = imeIPrezimeGlumca.getText().toString() + "\n" + tekstBiografije.getText().toString();
                    myIntent.putExtra(Intent.EXTRA_TEXT, poruka);
                    myIntent.setType("text/plain");
                    if (myIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(myIntent);
                    }
                }
            });

            if (bookmarkButton != null) {
                bookmarkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (!Pocetna.bookmarked) {
                            GlumacDBOpenHelper glumacDBOpenHelper = new GlumacDBOpenHelper(Pocetna.pocetna, "string", null, DATABASE_VERSION);
                            glumacDBOpenHelper.dodajGlumca(glumac);
                        } else {
                            GlumacDBOpenHelper glumacDBOpenHelper = new GlumacDBOpenHelper(Pocetna.pocetna, "s", null, DATABASE_VERSION);
                            glumacDBOpenHelper.brisiGlumca(glumac);
                        }
                    }
                });
            }
        }

        return vi;
    }

    private void brisiAllesZusammen()
    {
        ZanrDBOpenHelper z = new ZanrDBOpenHelper(Pocetna.pocetna, "s", null, ZanrDBOpenHelper.DATABASE_VERSION);
        SQLiteDatabase S = z.getWritableDatabase();
        S.delete(ZanrDBOpenHelper.DATABASE_TABLE, null, null);
        ReziserDBOpenHelper r = new ReziserDBOpenHelper(Pocetna.pocetna, "s", null, ReziserDBOpenHelper.DATABASE_VERSION);
        S = r.getWritableDatabase();
        S.delete(ReziserDBOpenHelper.DATABASE_TABLE, null, null);
        GlumacDBOpenHelper glumacDBOpenHelper = new GlumacDBOpenHelper(Pocetna.pocetna, "string", null, DATABASE_VERSION);
        S = glumacDBOpenHelper.getWritableDatabase();
        S.delete(GlumacDBOpenHelper.DATABASE_TABLE, null, null);
    }
}
