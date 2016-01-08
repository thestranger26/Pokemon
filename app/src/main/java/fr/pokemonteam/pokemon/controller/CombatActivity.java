package fr.pokemonteam.pokemon.controller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

    private PokemonReel pkmnCourant;
    private PokemonReel pkmnAdverse;
    private Utilisateur u;
    private Button button;
    private Database db;
    private ProgressBar progressBarAllie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        db = Database.getInstance(this);
        u = db.getUser(0);

        System.out.println(u.getNom());

        Intent i = getIntent();
        int idPokemon = i.getIntExtra("idPokemon", 0);
        double longitude = i.getDoubleExtra("longitude", 0);
        double latitude = i.getDoubleExtra("latitude", 0);

        ArrayList<PokemonReel> monEquipe = u.getEquipe();
        pkmnCourant = monEquipe.get(0);
        pkmnAdverse = genererPokemon(idPokemon, longitude, latitude);

        pkmnAdverse.getPokemon().setVue(true);
        db.updatePokemont(pkmnAdverse.getPokemon());

        // Définition des infos du pokémon adverse
        ImageView imageViewAdverse = (ImageView) findViewById(R.id.imagePokemonAdverse);
        imageViewAdverse.setImageResource(this.getResources().getIdentifier(pkmnAdverse.getPokemon().getNomImage(), "drawable", this.getPackageName()));

        ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
        progressBarAdverse.setMax(100);
        progressBarAdverse.setProgress(100);

        TextView textAdverse = (TextView) findViewById(R.id.textPokemonAdverse);
        textAdverse.setText(pkmnAdverse.getPokemon().getNom() + " N." + pkmnAdverse.getNiveau());

        // Définition des infos de notre pokémon
        progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
        progressBarAllie.setMax(pkmnCourant.getMaxVie());
        progressBarAllie.setProgress(pkmnCourant.getVieActuelle());

        TextView textNomAllie = (TextView) findViewById(R.id.textNomPokemonAllie);
        textNomAllie.setText(pkmnCourant.getPseudo() + " N." + pkmnCourant.getNiveau());

        ImageView imageViewAllie = (ImageView) findViewById(R.id.imagePokemonAllie);
        imageViewAllie.setImageResource(this.getResources().getIdentifier(pkmnCourant.getPokemon().getNomImage(), "drawable", this.getPackageName()));

        TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
        textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());

        button = (Button) findViewById(R.id.buttonShowDialogSac);

        int nbrePokeball = 0;
        for (ElementSac e : u.getSacADos()) {
            if (e.getElement().getLibelle().equals("pokeball")) {
                nbrePokeball = e.getNombre();
            }
        }
    }

    public void attaque(View view) {
        int actionPkmnAdverse = getActionPkmnAdverse();
        // Cas où l'adeversaire est en mode défense
        if (actionPkmnAdverse == 0) {
            System.out.println("L'adversaire se défend");
            System.out.println("Attaque de votre pokémon : " + pkmnCourant.getAtk());
            System.out.println("Défense du pokémon adverse : " + pkmnAdverse.getDef());
            int differenceAtkDef = pkmnCourant.getAtk() - pkmnAdverse.getDef();

            if (differenceAtkDef < 0) {
                System.out.println("Votre pokémon perd " + differenceAtkDef + " points de vie !");
                pkmnCourant.setVieActuelle(pkmnCourant.getVieActuelle() + differenceAtkDef);
                ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
                progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
                textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());
            }
            if (differenceAtkDef >= 0) {
                System.out.println("Le pokémon adverse perd " + differenceAtkDef + " points de vie !");
                pkmnAdverse.setVieActuelle(pkmnAdverse.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
                progressBarAdverse.setProgress(pkmnAdverse.getVieActuelle());
            }
        }
        // Cas où l'adeversaire est en mode attaque
        if (actionPkmnAdverse == 1) {
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

    public void defense(View view) {
        int actionPkmnAdverse = getActionPkmnAdverse();

        // Cas où l'adeversaire est en mode attaque
        if (actionPkmnAdverse == 1) {
            System.out.println("L'adversaire attaque");
            System.out.println("Défense de votre pokémon : " + pkmnCourant.getDef());
            System.out.println("Attaque du pokémon adverse : " + pkmnAdverse.getAtk());
            int differenceAtkDef = pkmnAdverse.getAtk() - pkmnCourant.getDef();
            if (differenceAtkDef >= 0) {
                System.out.println("Votre pokémon perd " + differenceAtkDef + " points de vie !");
                pkmnCourant.setVieActuelle(pkmnCourant.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAllie = (ProgressBar) findViewById(R.id.progressBarPokemonAllie);
                progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                TextView textVieAllie = (TextView) findViewById(R.id.textViePokemonAllie);
                textVieAllie.setText(pkmnCourant.getVieActuelle() + "/" + pkmnCourant.getMaxVie());
            }
            if (differenceAtkDef < 0) {
                System.out.println("Le pokémon adverse perd " + differenceAtkDef + " points de vie !");
                pkmnAdverse.setVieActuelle(pkmnAdverse.getVieActuelle() - differenceAtkDef);
                ProgressBar progressBarAdverse = (ProgressBar) findViewById(R.id.progressBarPokemonAdverse);
                progressBarAdverse.setProgress(pkmnAdverse.getVieActuelle());
            }
        }

        if (pkmnAdverse.getVieActuelle() < 0) {
            System.out.println("GAGNE");
        }
        if (pkmnCourant.getVieActuelle() < 0) {
            System.out.println("PERDU");
        }
    }

    public void sac(View view) {
        // custom dialog
        final Dialog dialog = new Dialog(CombatActivity.this);
        dialog.setContentView(R.layout.activity_dialogue_sac);
        dialog.setTitle("Sac à Dos");

        RelativeLayout boutonSac1 = (RelativeLayout) dialog.findViewById(R.id.bouton_sac1);
        ImageView image1 = (ImageView) dialog.findViewById(R.id.image_element_dialog);
        TextView libelle1 = (TextView) dialog.findViewById(R.id.nom_element_dialog);
        TextView nombre1 = (TextView) dialog.findViewById(R.id.number_element_dialog);
        image1.setImageResource(this.getResources().getIdentifier(u.getSacADos().get(0).getElement().getImage(), "mipmap", this.getPackageName()));
        libelle1.setText(u.getSacADos().get(0).getElement().getLibelle());
        nombre1.setText("(" + Integer.toString(u.getSacADos().get(0).getNombre()) + ")");

        RelativeLayout boutonSac2 = (RelativeLayout)  dialog.findViewById(R.id.bouton_sac2);
        ImageView image2 = (ImageView) dialog.findViewById(R.id.image_element_dialog2);
        TextView libelle2 = (TextView) dialog.findViewById(R.id.nom_element_dialog2);
        TextView nombre2 = (TextView) dialog.findViewById(R.id.number_element_dialog2);
        image2.setImageResource(this.getResources().getIdentifier(u.getSacADos().get(1).getElement().getImage(), "mipmap", this.getPackageName()));
        libelle2.setText(u.getSacADos().get(1).getElement().getLibelle());
        nombre2.setText("(" + Integer.toString(u.getSacADos().get(1).getNombre()) + ")");
        boutonSac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokeball(v);
                dialog.dismiss();
            }
        });

        boutonSac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                potion(v);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void pokeball(View view) {
        int nbrePokeball = 0;
        for (ElementSac e : u.getSacADos()) {
            if (e.getElement().getLibelle().equals("pokeball")) {
                if (e.getNombre() > 0) {
                    nbrePokeball = e.getNombre() - 1;
                    e.setNombre(nbrePokeball);
                    if (pokemonEstCapture()) {
                        capture(pkmnAdverse);
                    }
                } else {
                    System.out.println("NOOOOOOOOOOPE");
                }
            }
        }
    }

    public void fuite(View view) {
        this.finish();
    }

    public int getActionPkmnAdverse() {
        int retour;
        Random randomGenerator = new Random();
        retour = randomGenerator.nextInt(100) % 2;
        return retour;
    }

    private boolean pokemonEstCapture() {
        //TODO si le pokémon est capturé faire :  pkmnAdverse.getPokemon().setCapture(true);
        // db.updatePokemont(pkmnAdverse.getPokemon());
        boolean ret = false;
        System.out.println("Etape 1 :" + (3 * pkmnAdverse.getMaxVie() - 2 * pkmnAdverse.getVieActuelle()));
        System.out.println("Pokemon : " + pkmnAdverse.getPokemon());
        System.out.println("Taux de capture : " + pkmnAdverse.getPokemon().getTauxCapture());
        System.out.println("Etape 2 :" + ((3 * pkmnAdverse.getMaxVie() - 2 * pkmnAdverse.getVieActuelle()) * pkmnAdverse.getPokemon().getTauxCapture()));
        int a = ((3 * pkmnAdverse.getMaxVie() - 2 * pkmnAdverse.getVieActuelle()) * pkmnAdverse.getPokemon().getTauxCapture()) / (3 * pkmnAdverse.getMaxVie());
        System.out.println("chance de capture : " + a);
        if (a > 255) {

            ret = true;
        }
        return ret;
    }

    public void potion(View view) {
        int nbrePotions = 0;
        for (ElementSac e : u.getSacADos()) {
            if (e.getElement().getLibelle().equals("potion")) {
                if (e.getNombre() > 0) {
                    nbrePotions = e.getNombre() - 1;
                    e.setNombre(nbrePotions);
                    pkmnCourant.setVieActuelle(pkmnCourant.getMaxVie());
                    progressBarAllie.setProgress(pkmnCourant.getVieActuelle());
                } else {
                    System.out.println("NOOOOOOOOOOPE");
                }
            }
        }
    }

    public PokemonReel genererPokemon(int idPokemon, double latitude, double longitude) {
        PokemonReel pkmn = new PokemonReel();
        Database db = Database.getInstance(this);
        Random randomGenerator = new Random();
        Pokemon p = db.getPokemon(idPokemon);
        pkmn.setAtk(generateRandomValue(p.getAttaque()));
        pkmn.setDef(generateRandomValue(p.getDefense()));
        pkmn.setMaxVie(generateRandomValue(p.getPv()));
        pkmn.setExp(0);
        pkmn.setVieActuelle(pkmn.getMaxVie());
        pkmn.setNiveau(randomGenerator.nextInt(100));
        pkmn.setLatitude(latitude);
        pkmn.setLongitude(longitude);
        pkmn.setPokemon(p);
        return pkmn;
    }

    public int generateRandomValue(int val) {
        Random randomGenerator = new Random();
        double coeff = randomGenerator.nextDouble() * (1.5 - 0.5) + 0.5;
        double ret = val * coeff;
        return (int) ret;
    }

    public void capture(PokemonReel p){
        if(u.getEquipe().size() < 6){
            u.getEquipe().add(p);
        }
        db.setPokemonReel(p, u.getId());
    }
    
    public void finDeCombat() {
        db.updatePokemonReel(pkmnCourant, u.getId());
        db.updateSacADos(u.getId(), u.getSacADos());
    }


}
