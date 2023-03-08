/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

/**
 *
 * @author ageneste
 */
public class TypeMachine {
    private String code;
    private String libelle;
    private String adr;
    private String codeEtab;
    private String img;

    public TypeMachine(String code, String libelle, String adr, String codeEtab) {
        this.code = code;
        this.libelle = libelle;
        this.adr = adr;
        this.codeEtab = codeEtab;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getAdr() {
        return adr;
    }

    public String getCodeEtab() {
        return codeEtab;
    }

    @Override
    public String toString() {
        return code + libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    
    
}
