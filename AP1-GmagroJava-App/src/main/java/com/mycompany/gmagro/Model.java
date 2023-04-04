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
    private ObservableList<Intervenant> lesIntervenantParEtab = FXCollections.observableArrayList();
    private ObservableList<Role> lesRoles = FXCollections.observableArrayList();
    private ObservableList<Intervenant> lesinters = FXCollections.observableArrayList();
    private ObservableList<Etablissement> toutLesEtabs = FXCollections.observableArrayList();
    private ObservableList<TypeMachine> toutesLesMachine = FXCollections.observableArrayList();

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
            Etablissement e = new Etablissement(code, lib, ad, cp, ville, p, Integer.parseInt(nb));
            lesEtabs.add(e);

        }
    }

    public void getRole() throws IOException, ParseException {
        String rep = ws.get("uc=role");
        JSONArray ar = (JSONArray) parser.parse(rep);
        for (int i = 0; i < ar.size(); i++) {
            JSONObject jsono = (JSONObject) ar.get(i);
            String code = jsono.get("codeRole").toString();
            String libelle = jsono.get(("libelle")).toString();
            Role r = new Role(code, libelle);
            lesRoles.add(r);
        }

    }

    public void getAllIntervenant(){
        try {
            String rep = ws.get("uc=intervenant");
            JSONArray ar = (JSONArray) parser.parse(rep);
            for (int i = 0; i < ar.size(); i++) {
                JSONObject jsono = (JSONObject) ar.get(i);
                String mail = jsono.get("mail").toString();
                String nom = jsono.get("nom").toString();
                String prenom = jsono.get("prenom").toString();
                String actif = jsono.get("actif").toString();
                String codeR = jsono.get("codeRole").toString();
                String codeE = jsono.get("codeEtab").toString();
                Intervenant in = new Intervenant(mail, prenom, nom, Boolean.parseBoolean(actif), codeE, codeR);
                lesinters.add(in);
            }
        } catch (ParseException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int addIntervenant(String mail, String mdp, String prenom, String nom, int actif, String codeRole, String codeEtab){
        String rep = ws.get("uc=addInterv&mail=" + mail + "&mdp=" + mdp + "&prenom=" + prenom + "&nom=" + nom + "&actif=" + actif + "&codeR=" + codeRole + "&codeE=" + codeEtab);
        int r = Integer.parseInt(rep);
        System.out.println(r);
        return r;
    }

    public void updateRoleInIntervenant(String mail, String codeRole){
        ws.get("uc=modRoleIntoIntervenant&id=" + mail + "&codeR=" + codeRole);
    }

    public void updateIntervenant(String mail, String prenom, String nom, int actif, String CodeEtab) throws IOException {
        ws.get("uc=modifIntervenant&mail=" + mail + "&prenom=" + prenom + "&nom=" + nom + "&actif=" + actif + "&codeEtab=" + CodeEtab);
    }

    public void getIntervenantFromAEtablissement(String codeEtab) throws IOException, ParseException {
        String rep = ws.get("uc=intervenantByEtab&codeEtab=" + codeEtab);
        JSONArray ar = (JSONArray) parser.parse(rep);
        for (int i = 0; i < ar.size(); i++) {
            JSONObject jso = (JSONObject) ar.get(i);
            String mail = jso.get("mail").toString();
            String nom = jso.get("nom").toString();
            String prenom = jso.get("prenom").toString();
            int valActif = Integer.parseInt(jso.get("actif").toString());
            boolean actif = valActif == 0 ? false : true;
            System.out.println("Actif :" + actif);
            String codeE = jso.get("codeEtab").toString();
            String codeR = jso.get("codeRole").toString();
            Intervenant in = new Intervenant(mail, prenom, nom, actif, codeE, codeR);
            lesIntervenantParEtab.add(in);

        }
    }

    public void suppIntervenantById(String mail){
        ws.get("uc=suppIntervenant&mail=" + mail);
    }

    public void allEtabs() {
        String rep = ws.get("uc=etablissement");
        JSONArray ar;
        try {
            ar = (JSONArray) parser.parse(rep);
            for (int i = 0; i < ar.size(); i++) {
                JSONObject jsono = (JSONObject) ar.get(i);
                String code = jsono.get("codeEtab").toString();
                String lib = jsono.get("libelle").toString();
                String ad = jsono.get("adrs").toString();
                String cp = jsono.get("cp").toString();
                String ville = jsono.get("ville").toString();
                String lon = jsono.get("lng").toString();
                String lat = jsono.get("lat").toString();
                PointGeo p = new PointGeo(Double.parseDouble(lat), Double.parseDouble(lon));
                Etablissement e = new Etablissement(code, lib, ad, cp, ville, p, 0);
                toutLesEtabs.add(e);
            }
        } catch (ParseException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void suppEtablissementById(String id) {
        ws.get("uc=suppEtablissement&id=" + id);
    }

    public void updateEtablissement(String code, String adr, String cp, String lib, String ville) {
        ws.get("uc=modifEtablissement&code=" + code + "&adr=" + adr + "&cp=" + cp + "&lib=" + lib + "&ville=" + ville);
    }

    public void addEtablissement(String adr, String cp, String lib, String ville, String lng, String lat) {
        ws.get("uc=addEtablissement&adr=" + adr + "&cp=" + cp + "&lib=" + lib + "&ville=" + ville + "&lng=" + lng + "&lat=" + lat);
    }

    void allMachine() throws IOException, ParseException {
        String rep = ws.get("uc=getAllMachine");
        System.out.println("rep");
        JSONArray ar = (JSONArray) parser.parse(rep);
        for (int i = 0; i < ar.size(); i++) {
            JSONObject jsono = (JSONObject) ar.get(i);
            String codeM = jsono.get("codeType").toString();
            String libelle = jsono.get("libelle").toString();
            String cheminImg = jsono.get("cheminPhoto") == null ? null : jsono.get("cheminPhoto").toString();
            String codeE = jsono.get("codeEtab") == null ? null : jsono.get("codeEtab").toString();
            TypeMachine TM = new TypeMachine(codeM, libelle, cheminImg, codeE);
            toutesLesMachine.add(TM);

        }

    }

    void addTypeMachine(String code, String libelle, String img) throws IOException {
        ws.post("uc=addTypeMachine&code=" + code + "&lib=" + libelle, img);
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

    public ObservableList<Role> getLesRoles() {
        return lesRoles;
    }

    public void setLesinters(ObservableList<Intervenant> lesinters) {
        this.lesinters = lesinters;
    }

    public ObservableList<Intervenant> getLesinters() {
        return lesinters;
    }

    public ObservableList<Etablissement> getToutLesEtabs() {
        return toutLesEtabs;
    }

    public ObservableList<TypeMachine> getToutesLesMachine() {
        return toutesLesMachine;
    }

    void suppMachineById(String id) throws IOException {
        ws.get("uc=suppMachineById&id=" + id);
    }

    void adopterMachineById(String id) throws IOException {
        ws.get("uc=adopterMachine&id=" + id);
    }

    void modifierTypeMachine(String lib, String code, String img) {
        ws.get("uc=modLibMach&lib=" + lib + "&id=" + code);
    }

}
