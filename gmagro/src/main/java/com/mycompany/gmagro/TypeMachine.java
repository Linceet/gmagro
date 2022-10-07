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
    private int code;
    private String libelle;
    private String adr;
    private int codeEtab;

    public TypeMachine(int code, String libelle, String adr, int codeEtab) {
        this.code = code;
        this.libelle = libelle;
        this.adr = adr;
        this.codeEtab = codeEtab;
    }

    public int getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getAdr() {
        return adr;
    }

    public int getCodeEtab() {
        return codeEtab;
    }

    @Override
    public String toString() {
        return code + libelle;
    }
    
    
    
}
