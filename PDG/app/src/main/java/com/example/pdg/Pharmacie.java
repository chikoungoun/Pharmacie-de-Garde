package com.example.pdg;

public class Pharmacie {

    private String nom;
    private String quartier;
    private String adresse;
    private String telephone;

    public Pharmacie(String nom, String quartier, String adresse, String telephone) {
        this.nom = nom;
        this.quartier = quartier;
        this.adresse = adresse;
        this.telephone = telephone;
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
}
