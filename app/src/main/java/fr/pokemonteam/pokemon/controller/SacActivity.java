package fr.pokemonteam.pokemon.controller;

/**
 * Created by melvin on 1/4/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterSac;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.ElementSac;
import fr.pokemonteam.pokemon.model.Utilisateur;
/*
public class SacActivity extends AppCompatActivity {

    private ArrayList<ElementSac> monSac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sac_consultation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database db = Database.getInstance(this);
        Utilisateur u = db.getUser(0);

        monSac = u.getSacADos();

        if (monSac != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AdapterSac adapter = new AdapterSac(SacActivity.this, R.layout.content_sac_consultation, SacActivity.this.monSac);
                    ListView listView = (ListView) findViewById(R.id.list_sac);
                    listView.setAdapter(adapter);
                }
            });
        }
    }

    */

public class SacActivity extends Fragment {

    ArrayList<ElementSac> monSac;
    View view = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.activity_sac_consultation, container, false);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mon_equipe);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Toolbar t = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        t.setTitle(R.string.title_sac_activity);

        Database db = Database.getInstance(this.getActivity());
        Utilisateur u = db.getUser(0);

        monSac = u.getSacADos();

        AdapterSac adapter = new AdapterSac(this.getActivity(), R.layout.content_sac_consultation, this.monSac);
        ListView listView = (ListView)  view.findViewById(R.id.list_sac);
        listView.setAdapter(adapter);


        return view;
    }

}
