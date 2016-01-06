package fr.pokemonteam.pokemon.controller;

/**
 * Created by melvin on 1/4/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterSac;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Element;
import fr.pokemonteam.pokemon.model.ElementSac;

public class SacActivity extends AppCompatActivity {

    ArrayList<ElementSac> sac = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sac_consultation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database db = Database.getInstance(this);
        sac = db.getSacADos(0);

        if (sac != null) {
            remplirList();
        }
    }

    private void remplirList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdapterSac adapter = new AdapterSac(SacActivity.this, R.layout.content_sac_consultation, SacActivity.this.sac);
                ListView listView = (ListView) findViewById(R.id.list_sac);
                listView.setAdapter(adapter);

            }
        });

    }

}
