package fr.pokemonteam.pokemon.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterMonEquipe;
import fr.pokemonteam.pokemon.adapter.AdapterPokedex;
import fr.pokemonteam.pokemon.adapter.AdapterSac;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class PokedexActivity extends AppCompatActivity {

    ArrayList<Pokemon> pokemons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database db = Database.getInstance(this);
        pokemons = db.getPokedex();

        if (pokemons != null) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AdapterPokedex adapter = new AdapterPokedex(PokedexActivity.this, R.layout.content_pokedex, PokedexActivity.this.pokemons);
                    ListView listView = (ListView) findViewById(R.id.list_pokedex);
                    listView.setAdapter(adapter);
                }
            });
        } else {
            System.out.println("C'etait null....");
        }

    }

}
