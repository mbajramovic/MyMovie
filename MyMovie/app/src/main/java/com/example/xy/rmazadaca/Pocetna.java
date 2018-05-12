package com.example.xy.rmazadaca;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.Application;



import java.util.ArrayList;
import java.util.Locale;
import com.facebook.stetho.Stetho;

import static com.example.xy.rmazadaca.ReziserDBOpenHelper.DATABASE_VERSION;
import static com.example.xy.rmazadaca.ReziserDBOpenHelper.REZISER_ID;

public class Pocetna extends AppCompatActivity implements FragmentDugmadi.onButtonClick, FragmentGlumaca.onItemClick, PretraziZanrove.onZanrSearchDone, PretraziRezisere.onReziserSearchDone, FragmentFilmovi.onItemClick {

    public static ArrayList<Glumac> listaGlumaca = new ArrayList<Glumac>();
    public static ArrayList<Zanr> listaZanrova = new ArrayList<>();
    public static ArrayList<Reziser> listaRezisera = new ArrayList<>();
    public static ArrayList<String> listaFilmova = new ArrayList<>();
    ListView listView;
    public Bundle argumenti;
    static Boolean siriL = false;
    public static Context context;
    public static String ID;
    public static Pocetna pocetna = null;
    public static Boolean bookmarked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_pocetna);

        siriL = false;
        context = getApplicationContext();
        pocetna = this;

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_F3);

        ReziserDBOpenHelper r = new ReziserDBOpenHelper(this, "string", null, 2);
        SQLiteDatabase db = r.getWritableDatabase();
        Cursor cursor = db.query(ReziserDBOpenHelper.DATABASE_TABLE, new String[]{REZISER_ID}, null, null, null, null, null);
        if (cursor != null)
        {
            Integer i = 0;
            while (cursor.moveToNext())
                i = cursor.getInt(cursor.getColumnIndex(REZISER_ID));
                Log.d("TAG", i.toString());
        }

        if (frameLayout != null) {
            Fragment ffragmentRezisera = getFragmentManager().findFragmentByTag("reziseri");
            if (ffragmentRezisera != null) {
                getFragmentManager().beginTransaction().remove(ffragmentRezisera).commit();
            }
            siriL = true;
            if (listaGlumaca.size() > 0) {
                Bundle argumenti = new Bundle();
                argumenti.putParcelable("glumac", listaGlumaca.get(0));
                FragmentDetalji fragmentDetalji = new FragmentDetalji();
                fragmentDetalji.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F3, fragmentDetalji).commit();
                new PretraziRezisere((PretraziRezisere.onReziserSearchDone)Pocetna.this).execute(listaGlumaca.get(0).id.toString());
                new PretraziZanrove((PretraziZanrove.onZanrSearchDone)Pocetna.this).execute(listaGlumaca.get(0).id.toString());
            }
        }


        if (frameLayout == null) {
            Fragment fragment = getFragmentManager().findFragmentByTag("reziseri");
            if(fragment != null)
                getFragmentManager().beginTransaction().remove(fragment).commit();
        }
        FragmentDugmadi fragmentDugmadi = (FragmentDugmadi)getFragmentManager().findFragmentById(R.id.frame_F1);
        if (fragmentDugmadi == null) {
            fragmentDugmadi = new FragmentDugmadi();
            getFragmentManager().beginTransaction().replace(R.id.frame_F1, fragmentDugmadi).commit();
        }
        FragmentGlumaca fragGlum = new FragmentGlumaca();
        Bundle argumenti = new Bundle();
        argumenti.putParcelableArrayList("Alista", listaGlumaca);
        fragGlum.setArguments(argumenti);
        getFragmentManager().beginTransaction().replace(R.id.frame_F2, fragGlum, "glumci").addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {

            case R.id.button:
                FragmentGlumaca  _fragmentGlumaca = new FragmentGlumaca();
                argumenti = new Bundle();
                argumenti.putParcelableArrayList("Alista", listaGlumaca);
                _fragmentGlumaca.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, _fragmentGlumaca, "glumci").addToBackStack(null).commit();

                break;

            case R.id.button3:
                FragmentZanrova _fragmentZanrova = new FragmentZanrova();
                argumenti = new Bundle();
                argumenti.putParcelableArrayList("Zlista", listaZanrova);
                _fragmentZanrova.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, _fragmentZanrova, "zanrovi").addToBackStack(null).commit();

                break;

            case R.id.button2:
                FragmentRezisera _fragmentRezisera = new FragmentRezisera();
                argumenti = new Bundle();
                argumenti.putParcelableArrayList("Rlista", listaRezisera);
                _fragmentRezisera.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, _fragmentRezisera, "reziseri").addToBackStack(null).commit();

                break;

            case R.id.filmoviButton:
                FragmentFilmovi fragmentFilmovi =  new FragmentFilmovi();
                argumenti = new Bundle();
                argumenti.putStringArrayList("Flista", listaFilmova);
                fragmentFilmovi.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, fragmentFilmovi, "filmovi").addToBackStack(null).commit();
                break;

            case R.id.glumciButton:

                FragmentGlumaca fragmentGlumaca = new FragmentGlumaca();
                fragmentGlumaca = new FragmentGlumaca();
                argumenti= new Bundle();
                argumenti.putParcelableArrayList("Alista", listaGlumaca);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, fragmentGlumaca, "glumci").addToBackStack(null).commit();

                argumenti = new Bundle();
                if (listaGlumaca.size() > 0) {
                    argumenti.putParcelable("glumac", listaGlumaca.get(0));
                    FragmentDetalji fragmentDetalji = new FragmentDetalji();
                    fragmentDetalji.setArguments(argumenti);
                    getFragmentManager().beginTransaction().replace(R.id.frame_F3, fragmentDetalji).addToBackStack(null).commit();
                }
                break;

            case R.id.ostaloButton:

                FragmentZanrova fragmentZanrova = new FragmentZanrova();
                argumenti = new Bundle();
                argumenti.putParcelableArrayList("Zlista", listaZanrova);
                fragmentZanrova.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, fragmentZanrova, "zanrovi").addToBackStack(null).commit();

                FragmentRezisera fragmentRezisera = new FragmentRezisera();
                argumenti = new Bundle();
                argumenti.putParcelableArrayList("Rlista", listaRezisera);
                fragmentRezisera.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F3, fragmentRezisera, "reziseri").addToBackStack(null).commit();

               break;

            case R.id.filmButton:
                FragmentFilmovi _fragmentFilmovi = new FragmentFilmovi();
                argumenti = new Bundle();
                argumenti.putStringArrayList("Flista", listaFilmova);
                _fragmentFilmovi.setArguments(argumenti);
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, _fragmentFilmovi, "filmovi").addToBackStack(null).commit();

                FragmentDetaljiFilm fragmentDetaljiFilm = new FragmentDetaljiFilm();
                if (listaFilmova.size() > 0) {
                    argumenti = new Bundle();
                    argumenti.putString("film", listaFilmova.get(0));
                    fragmentDetaljiFilm.setArguments(argumenti);
                    getFragmentManager().beginTransaction().replace(R.id.frame_F3, fragmentDetaljiFilm).addToBackStack(null).commit();
                }

        }
    }

    @Override
    public void onItemClicked(int pos) {
        if (listaGlumaca.size() > 0) {
            Bundle argumenti = new Bundle();
            ID = listaGlumaca.get(pos).id;
            Log.d("MYTAG", listaGlumaca.get(pos).getImeGlumca());
            argumenti.putParcelable("glumac", listaGlumaca.get(pos));
            FragmentDetalji fragmentDetalji = new FragmentDetalji();
            fragmentDetalji.setArguments(argumenti);
            if (!siriL)
                getFragmentManager().beginTransaction().replace(R.id.frame_F2, fragmentDetalji).addToBackStack(null).commit();
            else
                getFragmentManager().beginTransaction().replace(R.id.frame_F3, fragmentDetalji).addToBackStack(null).commit();

            if (!Pocetna.bookmarked) {

                new PretraziZanrove((PretraziZanrove.onZanrSearchDone) Pocetna.this).execute(ID);
                new PretraziRezisere((PretraziRezisere.onReziserSearchDone) Pocetna.this).execute(ID);
            } else {

                GlumacDBOpenHelper glumacDBOpenHelper = new GlumacDBOpenHelper(this, "s", null, GlumacDBOpenHelper.DATABASE_VERSION);
                SQLiteDatabase db = glumacDBOpenHelper.getWritableDatabase();
                String WHERE = GlumacDBOpenHelper.GLUMAC_IMEIPREZIME + " like '" + listaGlumaca.get(pos).getImeGlumca() + "';";
                Cursor cursor = db.query(GlumacDBOpenHelper.DATABASE_TABLE, new String[]{GlumacDBOpenHelper.GLUMAC_ID}, WHERE, null, null, null, null);
                Integer id = -1;
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        id = cursor.getInt(cursor.getColumnIndex(GlumacDBOpenHelper.GLUMAC_ID));
                        ReziserDBOpenHelper reziserDBOpenHelper = new ReziserDBOpenHelper(this, "s", null, DATABASE_VERSION);
                        reziserDBOpenHelper.ucitajRezisere(id);
                        ZanrDBOpenHelper zanrDBOpenHelper = new ZanrDBOpenHelper(this, "s", null, ZanrDBOpenHelper.DATABASE_VERSION);
                        zanrDBOpenHelper.ucitajZanrove(id);
                    }
                }
            }
        }

    }


    private void popuniListu() {
        String biografija = "Jennifer Aniston (Jennifer Joanne Linn Anastasakis), kćer je poznatog američkog glumca Johna Anistona i spisateljice Nancy Aniston. Majka joj je talijansko-škotskog porijekla, a otac grčkog. Kratak dio svoga djetinjstva provodi u Grčkoj. Ima dva polubrata Melicka i Alexa Anistona. Školuje se u New Yorku u \"Fiorello H. LaGuardia High School of Music i Art and Performing Arts\". Potom odlazi u Los Angeles jer joj otac dobija ulogu u poznatoj američkoj daytime sapunici \"Days of our Lives\". Anistonina želja za glumom jača kad počinje s radom na broadwayskim produkcijama kao npr. \"For Dear Life and Dancing on Checker's Grave\". Osim posla u kazalištu, Jennifer je radila i kao poštar na biciklu.";
        Glumac g = new Glumac("Jennifer", "Aniston", 1969, 0, biografija, R.drawable.a_jennfier_aniston, "Sherman Oaks, Kalifornija", "zenski", "http://www.imdb.com/name/nm0000098/", 5);
        listaGlumaca.add(g);

        biografija = "George Clooney (Lexington, Kentucky, 6. svibnja 1961.), američki filmski glumac, redatelj, scenarist i producent, isprva poznat po ulozi dr. Douga Rossa u Hitnoj službi, a kasnije po nastupima u prvorazrednim filmovima. Dobitnik Oscara i dva Zlatna globusa, Clooney je balansirao između nastupa u visokobudžetnim blockbusterima i producentskog i redateljskog rada iza komercijalno riskantnijih projekata, kao i društvenog i političkog aktivizma. 31. siječnja 2008. je postao mirovni veleposlanik Ujedinjenih naroda.[1][2] Poznat je po suradnji s većinom filmske elite, kao što su Brad Pitt, Don Cheadle, Matt Damon, Julia Roberts, Casey Affleck, braća Coen i Steven Soderbergh.";
        g = new Glumac("George", "Clooney", 1961, 0, biografija, R.drawable.a_george_clooney, "Lexington, Kentucky", "muski", "http://www.imdb.com/name/nm0000123/", 5);
        listaGlumaca.add(g);

        biografija = "Angelina Jolie, rođena Angelina Jolie Voight (Los Angeles, Kalifornija, 4. lipnja 1975.), američka filmska glumica. Dobitnica je tri Zlatna globusa i Oskara za najbolju sporednu ulogu. Godine 2006. izabrana je za \"najzgodnije slavno tijelo\" u izboru E! Television i najljepšu ženu u izboru časopisa \"People\".[1] Redovito je visoko rangirana na takvim izborima. UNHCR-ova je veleposlanica dobre volje. Trenutno živi s Brad Pittom s kojim ima troje biološke i troje usvojene djece.";
        g = new Glumac("Angelina", "Jolie", 1975, 0, biografija, R.drawable.a_angelina_jolie, "Los Angeles, Kalifornija", "zenski", "http://www.imdb.com/name/nm0001401/", 5);
        listaGlumaca.add(g);

        biografija = "Bryan Lee Cranston (7. ožujka, 1956. Canoga Park, Kalifornija) je američki filmski i televizijski glumac i producent, najpoznatiji po ulogama zbunjenog oca Hala, sina petoro tinejdžera u seriji \"Malcolm u sredini\" i Waltera Whitea u AMC-ovoj hit seriji \"Breaking Bad\".";
        g = new Glumac("Bryan", "Cranston", 1956, 0, biografija, R.drawable.a_bryan_cranston, "Canoga Park, Kalifornija", "muski", "http://www.imdb.com/name/nm0186505/", 5);
        listaGlumaca.add(g);

        biografija = "James Hugh Calum Laurie (11. lipnja 1959.) engleski je glumac, komičar, pisac i glazbenik. Dobitnik je Reda Britanskog Carstva. Britanskoj je publici poznat po ulozi u humorističnoj seriji \"Crna Guja\", dok je svjetsku slavu doživio tumačeći lik Gregoryja Housea u američkoj dramskoj seriji \"Dr. House\". Laurie je rođen u Oxfordu. Ima brata i dvije sestre. Njegova majka Patricia umrla je od bolesti motornih neurona kada je Laurie imao 29 godina. Svoga oca, W.G.R.M. \"Ran\" Laurieja, koji je osvojio zlatnu olimpijsku medalju u veslanju 1948., Hugh naziva \"najsimpatičnijim čovjekom na svijetu\". Laurie je odgojen u škotskoj Prezbiterijanskoj crkvi. U Oxfordu je pohađao Dragon School, prestižnu osnovnu školu. Kasnije je otišao na Eton, a zatim na Selwyn College u Cambridgeu, gdje je studirao arheologiju i antropologiju.";
        g = new Glumac("Hugh", "Laurie", 1959, 0, biografija, R.drawable.a_hugh_laurie, "Oxford, England", "muski", "http://www.imdb.com/name/nm0491402/", 5);
        listaGlumaca.add(g);

        biografija = "Emily Jean \"Emma\" Stone (born November 6, 1988) is an American actress. One of the world's highest-paid actresses in 2015, Stone has received such accolades as an Academy Award, a BAFTA Award, a Golden Globe Award and three Screen Actors Guild Awards. She appeared in Forbes Celebrity 100 in 2013, and is often described by the media as one of the most talented actresses of her generation. Born and raised in Scottsdale, Arizona, Stone began acting as a child, in a theater production of The Wind in the Willows in 2000. As a teenager, she relocated to Los Angeles with her mother, and made her television debut in VH1's In Search of the New Partridge Family (2004), a reality show that produced only an unsold pilot. After small television roles, she won a Young Hollywood Award for her film debut in Superbad (2007), and received positive media attention for her role in Zombieland (2009).";
        g = new Glumac("Emma", "Stone", 1988, 0, biografija, R.drawable.a_emma_stone, "Scottsdale, Arizona", "zenski", "http://www.imdb.com/name/nm1297015/", 5);
        listaGlumaca.add(g);
    }

    private void popuniListuZanrovi() {
        listaZanrova.add(new Zanr("akcioni", R.drawable.f_akcioni));
        listaZanrova.add(new Zanr("avanturisticki", R.drawable.f_avanturisticki));
        listaZanrova.add(new Zanr("drama", R.drawable.f_drama));
        listaZanrova.add(new Zanr("historijski", R.drawable.f_historijski));
        listaZanrova.add(new Zanr("horor", R.drawable.f_horor));
        listaZanrova.add(new Zanr("komedija", R.drawable.f_komedija));
        listaZanrova.add(new Zanr("kriminalisticki", R.drawable.f_kriminalisticki));
        listaZanrova.add(new Zanr("mjuzikl", R.drawable.f_mjuzikl));
        listaZanrova.add(new Zanr("naucni", R.drawable.f_naucni));
        listaZanrova.add(new Zanr("ratni",R.drawable.f_ratni));
        listaZanrova.add(new Zanr("romanticni", R.drawable.f_romanticni));
        listaZanrova.add(new Zanr("western", R.drawable.f_western));
    }

    private void popuniListuReziseri() {
        listaRezisera.add(new Reziser("Steven", "Speilberg"));
        listaRezisera.add(new Reziser("Woody", "Allen"));
        listaRezisera.add(new Reziser("Denis", "Villeneuve"));
        listaRezisera.add(new Reziser("Kenneth", "Lonergan"));
        listaRezisera.add(new Reziser("George",  "Lucas"));

    }

    public static int getResourceForLangIcon() {
        if (Locale.getDefault().getLanguage().equals("en"))
            return R.drawable.uk_flag;
        else
            return R.drawable.bosnian_flag;
    }

    @Override
    public void onDone(ArrayList<Zanr> listaZanrova) {
        this.listaZanrova = listaZanrova;
    }


    @Override
    public void whenDone(ArrayList<Reziser> listaRezisera) {
        this.listaRezisera = listaRezisera;
    }

    @Override
    public void onFilmItemClicked(int pos) {
        Bundle argumenti = new Bundle();
        argumenti.putString("film", listaFilmova.get(pos));
        FragmentDetaljiFilm fragmentDetaljiFilm = new FragmentDetaljiFilm();
        fragmentDetaljiFilm.setArguments(argumenti);
        if (!siriL) getFragmentManager().beginTransaction().replace(R.id.frame_F2, fragmentDetaljiFilm).addToBackStack(null).commit();
        else getFragmentManager().beginTransaction().replace(R.id.frame_F3, fragmentDetaljiFilm).addToBackStack(null).commit();
    }
}
