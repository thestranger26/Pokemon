package fr.pokemonteam.pokemon.model;

/**
 * Created by Tristan on 04/01/16.
 */
public class PokemonReel {
    Pokemon pokemon;
    int id;
    String pseudo;
    int atk;
    int def;
    int niveau;
    int exp;
    long latitude;
    long longitude;
    int maxVie;
    int vieActuelle;


    public PokemonReel() {
    }

    public PokemonReel(Pokemon pokemon, String pseudo, int atk, int def, int niveau, int exp, long latitude, long longitude) {
        this.pokemon = pokemon;
        this.pseudo = pseudo;
        this.atk = atk;
        this.def = def;
        this.niveau = niveau;
        this.exp = exp;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getMaxVie() {
        return maxVie;
    }

    public void setMaxVie(int maxVie) {
        this.maxVie = maxVie;
    }

    public int getVieActuelle() {
        return vieActuelle;
    }

    public void setVieActuelle(int vieActuelle) {
        this.vieActuelle = vieActuelle;
    }
}
