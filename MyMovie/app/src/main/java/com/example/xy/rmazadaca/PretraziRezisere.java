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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 26.5.2017..
 */

public class PretraziRezisere extends AsyncTask<String, Integer, Void> {
    public ArrayList<Reziser> listaRezisera = new ArrayList<>();

    private onReziserSearchDone pozivatelj;
    public PretraziRezisere(onReziserSearchDone orsd) {
        pozivatelj = orsd;
    }
    @Override
    protected Void doInBackground(String... params) {
        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String urll = "https://api.themoviedb.org/3/discover/movie?api_key=2dcc3372722000b6863888777ec2bde1&language=en-US&sort_by=primary_release_date.desc&include_adult=false&include_video=false&page=1&with_cast=" + query;
        URL url;
        try {
            url = new URL(urll);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray pronadjeniFilmovi = jo.getJSONArray("results");
            for (int i = 0; i < 7; i++) {
                Integer filmID = pronadjeniFilmovi.getJSONObject(i).getInt("id");
                urll = "https://api.themoviedb.org/3/movie/" + filmID.toString() + "/credits?api_key=2dcc3372722000b6863888777ec2bde1";
                url = new URL(urll);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                rezultat = convertStreamToString(in);
                jo = new JSONObject(rezultat);
                JSONArray rezultati = jo.getJSONArray("crew");
                for (int j = 0; j < rezultati.length(); j++) {
                    String department = rezultati.getJSONObject(j).getString("department");
                    String job = rezultati.getJSONObject(j).getString("job");
                    if (department.equals("Directing") && job.equals("Director")) {
                        Reziser reziser = new Reziser();
                        reziser.setFirstName(rezultati.getJSONObject(j).getString("name"));
                        reziser.setLastName("");
                        if (listaRezisera.size() == 0)
                            listaRezisera.add(reziser);
                        else {
                            int k = 0;
                            for (k = 0; k < listaRezisera.size(); k++) {
                                if (listaRezisera.get(k).getFirstName().equals(reziser.getFirstName())) {
                                    break;
                                }
                            }
                            if (k == listaRezisera.size()) {
                                listaRezisera.add(reziser);
                            }
                        }
                        break;
                    }
                }
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
        pozivatelj.whenDone(listaRezisera);
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

    public interface onReziserSearchDone {
        public void whenDone(ArrayList<Reziser> listaRezisera);
    }
}
