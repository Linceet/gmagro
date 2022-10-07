/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ageneste
 */
public class Model {

    private static WSConnexionHTTPS ws;
    private final JSONParser parser = new JSONParser();
    private ObservableList<Etablissement> lesEtabs = FXCollections.observableArrayList();
    private ObservableList<Intervenant >lesIntervenantParEtab = FXCollections.observableArrayList();
    
    public Model() {
        if (ws == null) {
            ws = WSConnexionHTTPS.getInstance();
        }
    }

    public int login(String id, String pwd) throws IOException {
        String rep = ws.get("uc=log&id=" + id + "&pass=" + pwd);
        int r = Integer.parseInt(rep);
        System.out.println(r);
        return r;
    }

    public void getEtab() throws IOException, ParseException {
        String rep = ws.get("uc=menu");
        JSONArray ar = (JSONArray) parser.parse(rep);
        for (int i = 0; i < ar.size(); i++) {
            JSONObject jsono = (JSONObject) ar.get(i);
            String code = jsono.get("codeEtab").toString();
            String lib = jsono.get("libelle").toString();
            String ad = jsono.get("adrs").toString();
            String cp = jsono.get("cp").toString();
            String ville = jsono.get("ville").toString();
            String lon = jsono.get("lng").toString();
            String lat = jsono.get("lat").toString(); 
            String nb = jsono.get("nbPersonne").toString();
            PointGeo p = new PointGeo(Double.parseDouble(lat), Double.parseDouble(lon));
            Etablissement e = new Etablissement(Integer.parseInt(code), lib ,ad, cp, ville, p ,Integer.parseInt(nb));
            lesEtabs.add(e);
            
        }
    }
    
    public void getIntervenantFromAEtablissement(String codeEtab) throws IOException, ParseException{
        String rep = ws.get("uc=intervenant&codeEtab="+codeEtab);
        JSONArray ar = (JSONArray) parser.parse(rep);
        for (int i = 0; i <ar.size(); i++){
            JSONObject jso = (JSONObject) ar.get(i);
            String nom = jso.get("nom").toString();
            String prenom = jso.get("prenom").toString();
            boolean actif = Boolean.parseBoolean(jso.get("actif").toString());
            String codeE = jso.get("codeEtab").toString();
            String codeR = jso.get("codeRole").toString();
            Intervenant in = new Intervenant(prenom, nom, actif, codeEtab, codeR);
            lesIntervenantParEtab.add(in);
            
        }
    }
    

    public void changer(String cont) throws IOException {
        switch (cont) {
            case "appli":
                App.setRoot(cont);
                break;
            default:
                throw new AssertionError();
        }
    }

    public ObservableList<Etablissement> getLesEtabs() {
        return lesEtabs;
    }

    public ObservableList<Intervenant> getLesIntervenantParEtab() {
        
        return lesIntervenantParEtab;
    }

    
}
