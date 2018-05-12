package com.example.xy.rmazadaca;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by xy on 12.6.2017..
 */

public class FragmentDetaljiFilm extends Fragment {
    String film = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.film_spasi_fragment, container, false);
        final TextView nazivFilma = (TextView) vi.findViewById(R.id.nazivFilma);
        Button zapamtiFilm = (Button) vi.findViewById(R.id.zapamtiButton);
        final DatePicker datum = (DatePicker) vi.findViewById(R.id.datePicker);
        final EditText detaljiOFilmu = (EditText) vi.findViewById(R.id.unosDetalja);
        if (getArguments() != null && getArguments().containsKey("film")) {
            film = getArguments().getString("film");
            nazivFilma.setText(film);

            zapamtiFilm.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    try {
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(datum.getYear(), datum.getMonth(), datum.getDayOfMonth());
                        ContentValues l_event = new ContentValues();
                        l_event.put("calendar_id", 1);
                        l_event.put("title", nazivFilma.getText().toString());
                        l_event.put("description",  detaljiOFilmu.getText().toString());
                        l_event.put("dtstart", beginTime.getTimeInMillis());
                        l_event.put("dtend", beginTime.getTimeInMillis());
                        l_event.put("allDay", 1);
                        l_event.put("rrule", "FREQ=YEARLY");
                        l_event.put("eventTimezone", "Europe");
                        Uri l_eventUri;
                        //if (Build.VERSION.SDK_INT >= 8) {
                        l_eventUri = Uri.parse("content://com.android.calendar/events");
                        //} else {
                           // l_eventUri = Uri.parse("content://calendar/events");
                        //}
                        Uri l_uri = Pocetna.pocetna.getContentResolver().insert(l_eventUri, l_event);
                }
                catch(SecurityException se) {
                    throw se;
                }
                }
            });
        }

        return vi;
    }
}
