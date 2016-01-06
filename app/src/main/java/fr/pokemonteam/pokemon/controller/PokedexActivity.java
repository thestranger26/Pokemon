package fr.pokemonteam.pokemon.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterPokedex;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Pokemon;

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
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Pokemon pokemon = (Pokemon) parent.getItemAtPosition(position);
                            Intent monIntent = new Intent(getBaseContext(), PokedexDetailActivity.class);
                            monIntent.putExtra("pokemon", pokemon);
                            startActivity(monIntent);
                        }
                    });
                }
            });
        } else {
            System.out.println("C'etait null....");
        }

    }

}
