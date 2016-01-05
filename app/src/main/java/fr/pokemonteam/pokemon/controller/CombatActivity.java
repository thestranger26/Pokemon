package fr.pokemonteam.pokemon.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class CombatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        Database db = Database.getInstance(this);
        Utilisateur u = db.getUser(0);

        System.out.println(u.getNom());

        ArrayList<PokemonReel> monEquipe = u.getEquipe();
        PokemonReel pkmnCourant = monEquipe.get(0);

        // Définition des infos du pokémon adverse
        ImageView imageViewAdverse = (ImageView) findViewById(R.id.imagePokemonAdverse);
        imageViewAdverse.setImageResource(R.mipmap.magikarp);

        ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
        progressBarAdverse.setMax(100);
        progressBarAdverse.setProgress(100);

        TextView textAdverse = (TextView) findViewById(R.id.textPokemonAdverse);
        textAdverse.setText("Magikarp");

        // Définition des infos de notre pokémon
        ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
        progressBarAllie.setMax(pkmnCourant.getMaxVie());
        progressBarAllie.setProgress(pkmnCourant.getVieActuelle());

        TextView textNomAllie = (TextView) findViewById(R.id.textNomPokemonAllie);
        textNomAllie.setText(pkmnCourant.getPseudo());

        ImageView imageViewAllie = (ImageView) findViewById(R.id.imagePokemonAllie);
        imageViewAllie.setImageResource(R.mipmap.chrysacier);

        TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
        textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());

    }

}
