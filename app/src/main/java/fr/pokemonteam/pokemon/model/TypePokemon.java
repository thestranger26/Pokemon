package fr.pokemonteam.pokemon.model;

/**
 * Created by Tristan on 04/01/16.
 */
public class TypePokemon {
    int id;
    String libelle;

    public TypePokemon(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
