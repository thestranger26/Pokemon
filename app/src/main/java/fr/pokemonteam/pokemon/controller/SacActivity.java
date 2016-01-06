package fr.pokemonteam.pokemon.controller;

/**
 * Created by melvin on 1/4/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterSac;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class SacActivity extends AppCompatActivity {

    Utilisateur utilisateur = new Utilisateur(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sac_consultation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        remplirList();
    }

    private void remplirList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdapterSac adapter = new AdapterSac(SacActivity.this, R.layout.content_sac_consultation, utilisateur.getSacADos());
                ListView listView = (ListView) findViewById(R.id.list_sac);
                listView.setAdapter(adapter);

            }
        });

    }

}
