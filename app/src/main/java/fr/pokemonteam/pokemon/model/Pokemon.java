package fr.pokemonteam.pokemon.model;

/**
 * Created by Tristan on 04/01/16.
 */
public class Pokemon {

    int id;
    String type;
    int numero;
    String nom;
    int attaque;
    int defense;
    int pv;
    String nomImage;
    Boolean vue;
    Boolean capture;

    public Pokemon(int id, String type, int numero, String nom, int attage, int defense, int pv, String nomImage, Boolean vue, Boolean capture) {
        this.id = id;
        this.type = type;
        this.numero = numero;
        this.nom = nom;
        this.attaque = attage;
        this.defense = defense;
        this.pv = pv;
        this.nomImage = nomImage;
        this.vue = vue;
        this.capture = capture;
    }

    public Pokemon() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public Boolean getVue() {
        return vue;
    }

    public void setVue(Boolean vue) {
        this.vue = vue;
    }

    public Boolean getCapture() {
        return capture;
    }

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }
}
