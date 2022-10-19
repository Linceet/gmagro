/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

/**
 *
 * @author ageneste
 */
public class Etablissement {
    private String code;
    private String libelle;
    private String adr;
    private String cp;
    private String ville;
    private PointGeo p ;
    private int nbPers;

    public Etablissement(String code, String libelle, String adr, String cp, String ville, PointGeo p, int nbPers) {
        this.code = code;
        this.libelle = libelle;
        this.adr = adr;
        this.cp = cp;
        this.ville = ville;
        this.p = p;
        this.nbPers = nbPers;
    }
    
    
    
   
    @Override
    public String toString() {
        return libelle + " ("+ ville+ ") ";
    }

    public PointGeo getP() {
        return p;
    }

    public String getCode() {
        return code;
    }

    public String getAdr() {
        return adr;
    }

    public String getCp() {
        return cp;
    }

    public String getVille() {
        return ville;
    }

    public int getNbPers() {
        return nbPers;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setP(PointGeo p) {
        this.p = p;
    }

    public void setNbPers(int nbPers) {
        this.nbPers = nbPers;
    }
    
    
    
    
    
}
