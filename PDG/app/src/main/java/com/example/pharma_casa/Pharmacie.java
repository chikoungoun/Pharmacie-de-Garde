package com.example.pharma_casa;

public class Pharmacie {

    private String nom;
    private String quartier;
    private String adresse;
    private String telephone;


    private String coordonnee;

    public Pharmacie(String nom, String quartier, String adresse, String telephone, String coordonnee) {
        this.nom = nom;
        this.quartier = quartier;
        this.adresse = adresse;
        this.telephone = telephone;
        this.coordonnee = coordonnee;
    }

    public String getNom() {
        return nom;
    }

    public String getQuartier() {
        return quartier;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCoordonnee() {
        return coordonnee;
    }
}
