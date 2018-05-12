package com.example.xy.rmazadaca;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.example.xy.rmazadaca.R.id.imageView;

/**
 * Created by Maid Bajramovic on 17.5.2017..
 */

public class PretraziGlumce extends AsyncTask<String, Integer, Void> {
    ArrayList<Glumac> listaGlumaca = new ArrayList<>();

    private onGlumacSearchDone pozivatelj;
    public PretraziGlumce(onGlumacSearchDone ogsd) {
        pozivatelj = ogsd;
    }

    @Override
    protected Void doInBackground(String... params) {
        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urll = "https://api.themoviedb.org/3/search/person?api_key=2dcc3372722000b6863888777ec2bde1&language=en-US&query=" + query + "&page=1&include_adult=false";
        URL url;
        try {
            url = new URL(urll);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray pronadjeniGlumci = jo.getJSONArray("results");
            for (int i = 0; i < pronadjeniGlumci.length(); i++) {
                String id = pronadjeniGlumci.getJSONObject(i).getString("id");
                urll = "https://api.themoviedb.org/3/person/" + id + "?api_key=2dcc3372722000b6863888777ec2bde1&language=en-US";
                url = new URL(urll);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                rezultat = convertStreamToString(in);
                jo = new JSONObject(rezultat);
                Glumac noviGlumac = new Glumac();

                String ime = jo.getString("name");
                noviGlumac.setImeGlumca(ime);
                noviGlumac.id = id;

                String datumRodjenja = jo.getString("birthday");
                if (datumRodjenja.length() > 4) {
                    datumRodjenja = datumRodjenja.substring(0, 4);
                    int j;
                    for (j = 0; j < datumRodjenja.length(); j++) {
                        if (datumRodjenja.charAt(j) < '0' || datumRodjenja.charAt(j) > '9')
                            break;
                    }
                    if (j == datumRodjenja.length()) {
                        int godinaRodjenja = Integer.parseInt(datumRodjenja);
                        noviGlumac.setGodinaRodjenja(godinaRodjenja);
                    }

                }

                String datumSmrti = jo.getString("deathday");
                if (datumSmrti.length() > 4) {
                    datumSmrti = datumSmrti.substring(0, 4);
                    int godinaSmrti = Integer.parseInt(datumSmrti);
                    noviGlumac.setGodinaSmrti(godinaSmrti);
                }

                String mjestoRodjenja = jo.getString("place_of_birth");
                noviGlumac.setMjestoRodjenja(mjestoRodjenja);

                String spol = jo.getString("gender");
                if (!spol.equals("1"))
                    noviGlumac.setSpolGlumca("muski");
                else
                    noviGlumac.setSpolGlumca("zenski");

                String tekstBiografije = jo.getString("biography");
                noviGlumac.setTekstBiografije(tekstBiografije);

                String rating = jo.getString("popularity");
                if (rating == null)
                    rating = "0";
                double _rating = Double.parseDouble(rating);
                _rating = ((double)((int)(_rating*100)))/100; // na dvije decimale
                noviGlumac.setRating(_rating);

                String imdb_id = jo.getString("imdb_id");
                noviGlumac.setImbdStranica("http://www.imdb.com/name/" + imdb_id + "/");
                String slika = jo.getString("profile_path");
                noviGlumac.setSlikaPath(slika);
                listaGlumaca.add(noviGlumac);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected  void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        pozivatelj.onDone(listaGlumaca);
    }

    public String convertStreamToString(InputStream in)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try
            {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public interface onGlumacSearchDone {
        public void onDone(ArrayList<Glumac> listaGlumaca);
    }
}
