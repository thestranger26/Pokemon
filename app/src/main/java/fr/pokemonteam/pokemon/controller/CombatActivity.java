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

import fr.pokemonteam.pokemon.R;

public class CombatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        ImageView imageViewAdverse = (ImageView) findViewById(R.id.imagePokemonAdverse);
        imageViewAdverse.setImageResource(R.mipmap.magikarp);

        ImageView imageViewAllie = (ImageView) findViewById(R.id.imagePokemonAllie);
        imageViewAllie.setImageResource(R.mipmap.chrysacier);

        ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
        progressBarAdverse.setMax(100);
        progressBarAdverse.setProgress(100);

        ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
        progressBarAllie.setMax(100);
        progressBarAllie.setProgress(50);

        TextView textAdverse = (TextView) findViewById(R.id.textPokemonAdverse);
        textAdverse.setText("Magikarp");

        TextView textNomAllie = (TextView) findViewById(R.id.textNomPokemonAllie);
        textNomAllie.setText("Chrysacier");

        TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
        textVieAllie.setText("30/30");

    }

}
