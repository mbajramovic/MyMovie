package com.example.xy.rmazadaca;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

/**
 * Created by Maid Bajramovic on 7.6.2017..
 */

public class GlumacCursorAdapter extends ResourceCursorAdapter {

    public GlumacCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
        Pocetna.bookmarked = true;
        Pocetna.listaGlumaca = new ArrayList<>();
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView imeIPrezime = (TextView)view.findViewById(R.id.textView);
        TextView godinaRodjenja = (TextView)view.findViewById(R.id.textView7);
        TextView mjestoRodjenja = (TextView)view.findViewById(R.id.textView2);
        TextView rating = (TextView)view.findViewById(R.id.textView3);
        ImageView slika = (ImageView)view.findViewById(R.id.imageView);
        Glumac pronadjeniGlumac = new Glumac();


        String _imeIPrezime = cursor.getString(cursor.getColumnIndex(GLUMAC_IMEIPREZIME));
        imeIPrezime.setText(_imeIPrezime);
        pronadjeniGlumac.setImeGlumca(_imeIPrezime);

        String _mjestoRodjenja = cursor.getString(cursor.getColumnIndex(GLUMAC_MJESTORODJENJA));
        mjestoRodjenja.setText(_mjestoRodjenja);
        pronadjeniGlumac.setMjestoRodjenja(_mjestoRodjenja);

        String _godinaRodjenja = cursor.getString(cursor.getColumnIndex(GLUMAC_GODINARODJENJA));
        godinaRodjenja.setText(_godinaRodjenja);
        pronadjeniGlumac.setGodinaRodjenja(Integer.parseInt(_godinaRodjenja));

        String _rating = cursor.getString(cursor.getColumnIndex(GLUMAC_RATING));
        rating.setText(_rating);
        pronadjeniGlumac.setRating(Double.parseDouble(_rating));

        String _slika = cursor.getString(cursor.getColumnIndex(GLUMAC_SLIKA));
        Picasso.with(Pocetna.context).load("https://image.tmdb.org/t/p/w640" + _slika).into(slika);
        pronadjeniGlumac.setSlikaPath(_slika);

        String _godinaSmrti = cursor.getString(cursor.getColumnIndex(GLUMAC_GODINASMRTI));
        pronadjeniGlumac.setGodinaSmrti(Integer.parseInt(_godinaSmrti));

        String biografija = cursor.getString(cursor.getColumnIndex(GLUMAC_BIOGRAFIJA));
        pronadjeniGlumac.setTekstBiografije(biografija);

        String spol = cursor.getString(cursor.getColumnIndex(GLUMAC_SPOL));
        pronadjeniGlumac.setSpolGlumca(spol);

        String imbd = cursor.getString(cursor.getColumnIndex(GLUMAC_IMBD));
        pronadjeniGlumac.setImbdStranica(imbd);

        Pocetna.listaGlumaca.add(pronadjeniGlumac);
    }
}
