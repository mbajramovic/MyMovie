package com.example.xy.rmazadaca;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 25.5.2017..
 */

public class PretraziZanrove extends AsyncTask<String, Integer, Void> {

    ArrayList<Zanr> listaSvihZanrova = new ArrayList<>();
    ArrayList<Zanr> listaZanrova = new ArrayList<>();

    private onZanrSearchDone pozivatelj;
    public PretraziZanrove(onZanrSearchDone ozsd) {
        pozivatelj = ozsd;
    }

    @Override
    protected Void doInBackground(String... params) {
        String urll = "https://api.themoviedb.org/3/genre/movie/list?api_key=2dcc3372722000b6863888777ec2bde1&language=en-US";
        URL url;
        try {
            url = new URL(urll);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray sviZanrovi = jo.getJSONArray("genres");
            for (int i = 0; i < sviZanrovi.length(); i++)
            {
                JSONObject trenutniZanr = sviZanrovi.getJSONObject(i);
                Zanr noviZanr = new Zanr();
                noviZanr.setID(trenutniZanr.getString("id"));
                noviZanr.setNaziv(trenutniZanr.getString("name"));
                listaSvihZanrova.add(noviZanr);
            }
            dodajSlikuZanra();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urll = "https://api.themoviedb.org/3/discover/movie?api_key=2dcc3372722000b6863888777ec2bde1&language=en-US&sort_by=primary_release_date.desc&include_adult=false&include_video=false&page=1&with_cast=" + query;
        try {
            url = new URL(urll);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray pronadjeniFilmovi = jo.getJSONArray("results");

            for (int i = 0; i < 7; i++) {
                JSONObject obj = pronadjeniFilmovi.getJSONObject(i);
                JSONArray zanrovi = pronadjeniFilmovi.getJSONObject(i).getJSONArray("genre_ids");
                //for (int j = 0; j < zanrovi.length(); j++) {
                Integer id = 0;
                    if (zanrovi.length() > 0) {
                        id = zanrovi.getInt(0);
                        for (int k = 0; k < listaSvihZanrova.size(); k++) {
                            if (listaSvihZanrova.get(k).getID().equals(id.toString())) {
                                Zanr zanr = listaSvihZanrova.get(k);
                                int j = 0;
                                for (j = 0; j < listaZanrova.size(); j++) {
                                    if (listaZanrova.get(j) == zanr)
                                        break;
                                }
                                if (j == listaZanrova.size())
                                    listaZanrova.add(zanr);
                                break;
                            }
                        }
                    }
                //}
            }
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
        pozivatelj.onDone(listaZanrova);
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

    public interface onZanrSearchDone {
        public void onDone(ArrayList<Zanr> listaZanrova);
    }

    private void dodajSlikuZanra()
    {
        for (int i = 0; i < listaSvihZanrova.size(); i++)
        {
            if (listaSvihZanrova.get(i).getNaziv().equals("Horror"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_horor);
            else if (listaSvihZanrova.get(i).getNaziv().equals("Comedy"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_komedija);
            else if (listaSvihZanrova.get(i).getNaziv().equals("Action"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_akcioni);
            else if (listaSvihZanrova.get(i).getNaziv().equals("Drama"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_drama);
            else if (listaSvihZanrova.get(i).getNaziv().equals("Music"))
                 listaSvihZanrova.get(i).setSlika(R.drawable.f_mjuzikl);
            else if (listaSvihZanrova.get(i).getNaziv().equals("History"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_historijski);
            else if (listaSvihZanrova.get(i).getNaziv().equals("Adventure"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_avanturisticki);
            else if (listaSvihZanrova.get(i).getNaziv().equals("War"))
                listaSvihZanrova.get(i).setSlika(R.drawable.f_ratni);
            else
                listaSvihZanrova.get(i).setSlika(R.drawable.zanr_icon);

        }
    }
}