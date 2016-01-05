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
import java.util.Random;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class CombatActivity extends AppCompatActivity {

    PokemonReel pkmnCourant;
    PokemonReel pkmnAdverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        Database db = Database.getInstance(this);
        Utilisateur u = db.getUser(0);

        System.out.println(u.getNom());

        ArrayList<PokemonReel> monEquipe = u.getEquipe();
        pkmnCourant = monEquipe.get(0);
        pkmnAdverse = monEquipe.get(1);

        // Définition des infos du pokémon adverse
        ImageView imageViewAdverse = (ImageView) findViewById(R.id.imagePokemonAdverse);
        imageViewAdverse.setImageResource(this.getResources().getIdentifier("magikarp", "mipmap", this.getPackageName()));

        ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
        progressBarAdverse.setMax(100);
        progressBarAdverse.setProgress(100);

        TextView textAdverse = (TextView) findViewById(R.id.textPokemonAdverse);
        textAdverse.setText(pkmnAdverse.getPseudo()  + " N." + pkmnAdverse.getNiveau());

        // Définition des infos de notre pokémon
        ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
        progressBarAllie.setMax(pkmnCourant.getMaxVie());
        progressBarAllie.setProgress(pkmnCourant.getVieActuelle());

        TextView textNomAllie = (TextView) findViewById(R.id.textNomPokemonAllie);
        textNomAllie.setText(pkmnCourant.getPseudo() + " N." + pkmnCourant.getNiveau());

        ImageView imageViewAllie = (ImageView) findViewById(R.id.imagePokemonAllie);
        imageViewAllie.setImageResource(R.mipmap.chrysacier);

        TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
        textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());

    }

    public void attaque(View view){
        int actionPkmnAdverse = getActionPkmnAdverse();
        // Cas où l'adeversaire est en mode défense
        if(actionPkmnAdverse == 0){
            int differenceAtkDef = pkmnCourant.getAtk() - pkmnAdverse.getDef();
            if(differenceAtkDef < 0){
                pkmnCourant.setVieActuelle(pkmnCourant.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
                progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
                textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());
            }
            if(differenceAtkDef >= 0){
                pkmnAdverse.setVieActuelle(pkmnAdverse.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
                progressBarAdverse.setProgress(pkmnAdverse.getVieActuelle());
            }
        }
        // Cas où l'adeversaire est en mode attaque
        if(actionPkmnAdverse == 1){
            int viePkmnAdverse = pkmnAdverse.getVieActuelle() - pkmnAdverse.getAtk();
            int viePkmnAllie = pkmnCourant.getVieActuelle() - pkmnCourant.getAtk();

            pkmnAdverse.setVieActuelle(viePkmnAdverse);
            pkmnCourant.setVieActuelle(viePkmnAllie);

            ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
            progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
            TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
            textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());

            ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
            progressBarAdverse.setProgress(pkmnAdverse.getVieActuelle());
        }
    }

    public void defense(View view){
        int actionPkmnAdverse = getActionPkmnAdverse();

        // Cas où l'adeversaire est en mode attaque
        if(actionPkmnAdverse == 1){
            int differenceAtkDef = pkmnAdverse.getAtk() - pkmnCourant.getDef();
            if(differenceAtkDef >= 0){
                pkmnCourant.setVieActuelle(pkmnCourant.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
                progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
                textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());
            }
            if(differenceAtkDef < 0){
                pkmnAdverse.setVieActuelle(pkmnAdverse.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
                progressBarAdverse.setProgress(pkmnAdverse.getVieActuelle());
            }
        }

        if(pkmnAdverse.getVieActuelle()<0){
            System.out.println("GAGNE");
        }
        if(pkmnCourant.getVieActuelle()<0){
            System.out.println("PERDU");
        }

    }
    public void pokeball(View view){

    }
    public void fuite(View view){

    }

    public int getActionPkmnAdverse(){
        int retour;
        Random randomGenerator = new Random();
        retour = randomGenerator.nextInt(100)%2;
        return retour;
    }


}
