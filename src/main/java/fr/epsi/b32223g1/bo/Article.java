package fr.epsi.b32223g1.bo;

import fr.epsi.b32223g1.bo.Fournisseur;

public class Article {
    private String nom;
    private double prix;
    private Fournisseur fournisseur;

    public Article(String nom, double prix, Fournisseur fournisseur) {
        this.nom = nom;
        this.prix = prix;
        this.fournisseur = fournisseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
}
