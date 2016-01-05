package fr.pokemonteam.pokemon.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterMonEquipe;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class MonEquipeActivity extends AppCompatActivity {

    ArrayList<PokemonReel> monEquipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_equipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database db = Database.getInstance(this);
        Utilisateur u = db.getUser(0);

        System.out.println(u.getNom());

        monEquipe = u.getEquipe();

        if (monEquipe != null) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AdapterMonEquipe adapter = new AdapterMonEquipe(MonEquipeActivity.this, R.layout.content_mon_equipe, MonEquipeActivity.this.monEquipe);
                    ListView listView = (ListView) findViewById(R.id.monEquipe_listView);
                    listView.setAdapter(adapter);
                }
            });
        } else {
            System.out.println("C'etait null....");
        }
    }

}
