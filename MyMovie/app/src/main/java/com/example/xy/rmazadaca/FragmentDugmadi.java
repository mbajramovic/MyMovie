package com.example.xy.rmazadaca;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Bajramovic Maid on 8.4.2017..
 */

public class FragmentDugmadi extends Fragment {
    onButtonClick obc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.dugmad_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            obc = (onButtonClick) getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
        final Button zanrButton = (Button)getView().findViewById(R.id.button3);
        final Button reziserButon = (Button)getView().findViewById(R.id.button2);
        final Button glumciButton = (Button)getView().findViewById(R.id.button);
        final Button filmoviButton = (Button)getView().findViewById(R.id.filmoviButton);
        final Button glumciButtonSiri = (Button)getView().findViewById(R.id.glumciButton);
        final Button ostaloButton = (Button)getView().findViewById(R.id.ostaloButton);
        final Button filmButtonSiri = (Button)getView().findViewById(R.id.filmButton);
        final ImageView langIcon = (ImageView)getView().findViewById(R.id.lang_icon);

        if (zanrButton != null) {
            zanrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (reziserButon != null) {
            reziserButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (glumciButton != null) {
            glumciButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (filmoviButton != null) {
            filmoviButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (glumciButtonSiri != null) {
            glumciButtonSiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (ostaloButton != null) {
            ostaloButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (filmButtonSiri != null) {
            filmButtonSiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obc.onClick(v);
                }
            });
        }

        if (langIcon != null) {
            langIcon.setImageResource(Pocetna.getResourceForLangIcon());
        }

    }



    public interface onButtonClick {
        public void onClick(View v);
    }

}
