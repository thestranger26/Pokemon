package fr.pokemonteam.pokemon.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.model.Pokemon;

public class PokedexDetailActivity extends AppCompatActivity {

    Pokemon pokemon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Pokemon pokemon = (Pokemon) i.getSerializableExtra("pokemon");

        if (pokemon != null) {
            TextView numero = (TextView) findViewById(R.id.numero_pokemon_detail);
            TextView nom = (TextView) findViewById(R.id.nom_pokemon_detail);
            ImageView image = (ImageView) findViewById(R.id.image_pokemon_detail);
            TextView type = (TextView) findViewById(R.id.type_pokemon_detail);
            TextView attaque = (TextView) findViewById(R.id.attaque_valeur_pokemon_detail);
            TextView defense = (TextView) findViewById(R.id.defense_valeur_pokemon_detail);
            TextView pv = (TextView) findViewById(R.id.pv_valeur_pokemon_detail);

            numero.setText(Integer.toString(pokemon.getNumero()));
            nom.setText(pokemon.getNom());
            image.setImageResource(getResources().getIdentifier(pokemon.getNomImage(), "drawable", getPackageName()));
            type.setText(pokemon.getType());
            attaque.setText(Integer.toString(pokemon.getAttaque()));
            defense.setText(Integer.toString(pokemon.getDefense()));
            pv.setText(Integer.toString(pokemon.getPv()));
        } else {
            System.out.println("C'etait null....");
        }

    }
}
