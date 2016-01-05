package fr.pokemonteam.pokemon.model;

/**
 * Created by Tristan on 04/01/16.
 */
public class Lieu {
    int id;
    String libelle;
    String type;
    long latitude;
    long longitude;

    public Lieu(int id, String libelle, String type, long latitude, long longitude) {
        this.id = id;
        this.libelle = libelle;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
