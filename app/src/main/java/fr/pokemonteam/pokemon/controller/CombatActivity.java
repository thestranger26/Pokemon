package fr.pokemonteam.pokemon.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.ElementSac;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class CombatActivity extends AppCompatActivity {

    PokemonReel pkmnCourant;
    PokemonReel pkmnAdverse;
    Utilisateur u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        Database db = Database.getInstance(this);
        u = db.getUser(0);

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
        textAdverse.setText(pkmnAdverse.getPseudo() + " N." + pkmnAdverse.getNiveau());

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

        Button buttonPokeball = (Button) findViewById(R.id.buttonPokeball);
        int nbrePokeball = 0;
        for (ElementSac e : u.getSacADos()) {
            if(e.getElement().getLibelle().equals("pokeball")){
                nbrePokeball = e.getNombre();}
        }
        buttonPokeball.setText(buttonPokeball.getText() + " ("+String.valueOf(nbrePokeball) +")");
    }

    public void attaque(View view){
        int actionPkmnAdverse = getActionPkmnAdverse();
        // Cas où l'adeversaire est en mode défense
        if(actionPkmnAdverse == 0){
            System.out.println("L'adversaire se défend");
            System.out.println("Attaque de votre pokémon : " + pkmnCourant.getAtk());
            System.out.println("Défense du pokémon adverse : " + pkmnAdverse.getDef());
            int differenceAtkDef = pkmnCourant.getAtk() - pkmnAdverse.getDef();
            if(differenceAtkDef < 0){
                System.out.println("Votre pokémon perd " + differenceAtkDef + " points de vie !");
                pkmnCourant.setVieActuelle(pkmnCourant.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
                progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
                textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());
            }
            if(differenceAtkDef >= 0){
                System.out.println("Le pokémon adverse perd " + differenceAtkDef + " points de vie !");
                pkmnAdverse.setVieActuelle(pkmnAdverse.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
                progressBarAdverse.setProgress(pkmnAdverse.getVieActuelle());
            }
        }
        // Cas où l'adeversaire est en mode attaque
        if(actionPkmnAdverse == 1){
            System.out.println("L'adversaire attaque");
            System.out.println("Attaque de votre pokémon : " + pkmnCourant.getAtk());
            System.out.println("Attaque du pokémon adverse : " + pkmnAdverse.getAtk());
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
            System.out.println("L'adversaire attaque");
            System.out.println("Défense de votre pokémon : " + pkmnCourant.getDef());
            System.out.println("Attaque du pokémon adverse : " + pkmnAdverse.getAtk());
            int differenceAtkDef = pkmnAdverse.getAtk() - pkmnCourant.getDef();
            if(differenceAtkDef >= 0){
                System.out.println("Votre pokémon perd " + differenceAtkDef + " points de vie !");
                pkmnCourant.setVieActuelle(pkmnCourant.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
                progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
                textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());
            }
            if(differenceAtkDef < 0){
                System.out.println("Le pokémon adverse perd " + differenceAtkDef + " points de vie !");
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
        int nbrePokeball = 0;
        for (ElementSac e : u.getSacADos()) {
            if(e.getElement().getLibelle().equals("pokeball")){
                if(e.getNombre() >0) {
                    nbrePokeball = e.getNombre() - 1;
                    e.setNombre(nbrePokeball);
                    if(pokemonEstCapture()){
                        System.out.println("Youpi pokémon capturé !");
                    }
                }
                else{
                    System.out.println("NOOOOOOOOOOPE");
                }
            }
        }
        Button buttonPokeball = (Button) findViewById(R.id.buttonPokeball);
        buttonPokeball.setText(getResources().getString(R.string.button_pkball) + " (" + nbrePokeball + ")");

    }
    public void fuite(View view){

    }

    public int getActionPkmnAdverse(){
        int retour;
        Random randomGenerator = new Random();
        retour = randomGenerator.nextInt(100)%2;
        return retour;
    }

    private boolean pokemonEstCapture(){
        boolean ret = false;
        System.out.println("Etape 1 :" + (3*pkmnAdverse.getMaxVie() - 2*pkmnAdverse.getVieActuelle()));
        System.out.println("Taux de capture : " + pkmnAdverse.getPokemon().getTauxCapture());
        System.out.println("Etape 2 :" + ((3*pkmnAdverse.getMaxVie() - 2*pkmnAdverse.getVieActuelle())*pkmnAdverse.getPokemon().getTauxCapture()));
        int a = ((3*pkmnAdverse.getMaxVie() - 2*pkmnAdverse.getVieActuelle())*pkmnAdverse.getPokemon().getTauxCapture())/(3*pkmnAdverse.getMaxVie());
        System.out.println("chance de capture : " + a);
        if(a>255){ret=true;}
        return ret;
    }


}
