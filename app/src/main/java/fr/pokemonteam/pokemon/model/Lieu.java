package fr.pokemonteam.pokemon.model;

/**
 * Created by Tristan on 04/01/16.
 */
public class Lieu {
    int id;
    String libelle;
    String type;
    double latitude;
    double longitude;

    public Lieu(int id, String libelle, String type, long latitude, long longitude) {
        this.id = id;
        this.libelle = libelle;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Lieu(){}


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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
