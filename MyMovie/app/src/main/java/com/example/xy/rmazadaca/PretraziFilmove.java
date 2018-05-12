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
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by xy on 12.6.2017..
 */

public class PretraziFilmove extends AsyncTask<String, Integer, Void> {
    ArrayList<String> listaFilmova = new ArrayList<>();

    private onFilmSearchDone pozivatelj;
    public PretraziFilmove(onFilmSearchDone ofsd) {
        pozivatelj = ofsd;
    }

    @Override
    protected Void doInBackground(String... params) {
        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urll = "https://api.themoviedb.org/3/search/movie?api_key=2dcc3372722000b6863888777ec2bde1&language=en-US&query=" + query + "&page=1&include_adult=false";
        URL url;
        try {
            url = new URL(urll);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray pronadjeniFilmovi = jo.getJSONArray("results");
            for (int i = 0; i < pronadjeniFilmovi.length(); i++) {
                String title = pronadjeniFilmovi.getJSONObject(i).getString("title");
                listaFilmova.add(title);

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
        pozivatelj.onDoneFilm(listaFilmova);
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

    public interface onFilmSearchDone {
        public void onDoneFilm(ArrayList<String> listaFilmova);
    }
}
