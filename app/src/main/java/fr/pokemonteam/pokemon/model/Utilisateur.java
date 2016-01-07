package fr.pokemonteam.pokemon.model;

import java.util.ArrayList;

/**
 * Created by Tristan on 04/01/16.
 */
public class Utilisateur {
    int id;
    String pseudo;
    String nom;
    String prenom;
    String mail;
    ArrayList<PokemonReel> equipe = new ArrayList<>();
    ArrayList<ElementSac> sacADos = new ArrayList<>();
    ArrayList<Lieu> lieuxFavoris = new ArrayList<>();

    private Utilisateur(int id, String pseudo, String nom, String prenom, ArrayList<PokemonReel> equipe, ArrayList<ElementSac> sacADos, ArrayList<Lieu> lieuxFavoris) {
        this.id = id;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.equipe = equipe;
        this.sacADos = sacADos;
        this.lieuxFavoris = lieuxFavoris;
    }

    public Utilisateur(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public ArrayList<PokemonReel> getEquipe() {
        return equipe;
    }

    public void setEquipe(ArrayList<PokemonReel> equipe) {
        this.equipe = equipe;
    }

    public ArrayList<ElementSac> getSacADos() {
        return sacADos;
    }

    public void setSacADos(ArrayList<ElementSac> sacADos) {
        this.sacADos = sacADos;
    }

    public ArrayList<Lieu> getLieuxFavoris() {
        return lieuxFavoris;
    }

    public void setLieuxFavoris(ArrayList<Lieu> lieuxFavoris) {
        this.lieuxFavoris = lieuxFavoris;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
