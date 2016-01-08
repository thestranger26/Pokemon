package fr.pokemonteam.pokemon.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class DetailPokemonMonEquipeActivity extends AppCompatActivity {

    PokemonReel p;
    Utilisateur u;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon_mon_equipe);


        Intent i = getIntent();
        position = i.getIntExtra("idPkmnReel", 0);

        Database db = Database.getInstance(this);
        u = db.getUser(0);

        p = u.getEquipe().get(position);

        if (p != null) {
            TextView numero = (TextView) findViewById(R.id.numero_pokemon_detail);
            TextView nom = (TextView) findViewById(R.id.nom_pokemon_detail);
            ImageView image = (ImageView) findViewById(R.id.image_pokemon_detail);
            TextView type = (TextView) findViewById(R.id.type_pokemon_detail);
            TextView attaque = (TextView) findViewById(R.id.attaque_valeur_pokemon_detail);
            TextView defense = (TextView) findViewById(R.id.defense_valeur_pokemon_detail);
            TextView pv = (TextView) findViewById(R.id.pv_valeur_pokemon_detail);
            TextView niv = (TextView) findViewById(R.id.niveau_valeur_pokemon_detail);

            numero.setText(Integer.toString(p.getPokemon().getNumero()));
            nom.setText(p.getPseudo());
            image.setImageResource(getResources().getIdentifier(p.getPokemon().getNomImage(), "drawable", getPackageName()));
            type.setText(p.getPokemon().getType());
            attaque.setText(Integer.toString(p.getAtk()));
            defense.setText(Integer.toString(p.getDef()));
            pv.setText(Integer.toString(p.getExp()));
            niv.setText(Integer.toString(p.getNiveau()));

            ProgressBar pg = (ProgressBar) findViewById(R.id.monEquipe_progressBar);
            pg.setMax(p.getMaxVie());
            pg.setProgress(p.getVieActuelle());

        } else {
            System.out.println("C'etait null....");
        }
    }

    public void suppPokemon(View v) {
        System.out.println("BlaBliBlo");
         Database db = Database.getInstance(this);
        db.suppPokemonEquipe(p.getId());
        u.getEquipe().remove(position);
        this.finish();
    }

}
