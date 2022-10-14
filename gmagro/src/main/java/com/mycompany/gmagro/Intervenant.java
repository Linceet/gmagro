/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

/**
 *
 * @author ageneste
 */
public class Intervenant {
    private String mail;
    private String prenom;
    private String nom;
    private boolean actif;
    private String codeEtab;
    private String codeRole;

    public Intervenant(String mail , String prenom, String nom, boolean actif, String codeEtab, String codeRole) {
        this.mail = mail;
        this.prenom = prenom;
        this.nom = nom;
        this.actif = actif;
        this.codeEtab = codeEtab;
        this.codeRole = codeRole;
    }  

    public String getMail() {
        return mail;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getCodeEtab() {
        return codeEtab;
    }

    public void setCodeEtab(String codeEtab) {
        this.codeEtab = codeEtab;
    }

    public String getCodeRole() {
        return codeRole;
    }

    public void setCodeRole(String codeRole) {
        this.codeRole = codeRole;
    }
    
    
}
