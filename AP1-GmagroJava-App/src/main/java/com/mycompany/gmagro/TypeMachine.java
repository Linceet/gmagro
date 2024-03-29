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
    private String ext;

    public TypeMachine(String code, String libelle, String adr, String codeEtab) {
        this.code = code;
        this.libelle = libelle;
        this.adr = adr;
        this.codeEtab = codeEtab;
    }
    
    public TypeMachine(String code, String libelle, String adr, String codeEtab,String img) {
        this.code = code;
        this.libelle = libelle;
        this.adr = adr;
        this.codeEtab = codeEtab;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    public void setExt(String ext) {
        this.ext = ext;
    }
    
    public String getExt() {
        return ext;
    }
    
    
    
    
}
