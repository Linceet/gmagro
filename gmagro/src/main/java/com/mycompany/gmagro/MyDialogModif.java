/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import java.io.IOException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ageneste
 */
class MyDialogModif extends Dialog<Intervenant> {

    public MyDialogModif(Model m, Intervenant inter) {
        this.setTitle("Modifier");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel). \n"
                + "please fill EVERYTHING!");
        this.setResizable(true);

        Label labelMail = new Label(inter.getMail() + ": ");
        Label labelNom = new Label("nouveau nom :  ");
        TextField textNom = new TextField(inter.getNom());

        Label labelPrenom = new Label("nouveau prenom : ");
        TextField textPrenom = new TextField(inter.getPrenom());

        Label labelActif = new Label("Activité de l'Intervenant (cauché si actif) : ");
        CheckBox cbActif = new CheckBox("actif");
        
        cbActif.setSelected(inter.isActif());

        Label labelEtab = new Label("Selectionner le nouvel établissement: ");
        ComboBox<Etablissement> cbEtab = new ComboBox<>(m.getLesEtabs());
        for( Etablissement e : m.getLesEtabs()){
            if( inter.getCodeEtab().equals(e.getCode())){
                cbEtab.getSelectionModel().select(e);
            }
        }
        

        GridPane grid = new GridPane();
        grid.add(labelMail, 1, 1);
        grid.add(labelNom, 1, 2);
        grid.add(textNom, 2, 2);
        grid.add(labelPrenom, 1, 3);
        grid.add(textPrenom, 2, 3);
        grid.add(labelActif, 1, 4);
        grid.add(cbActif, 2, 4);
        grid.add(labelEtab, 1, 5);
        grid.add(cbEtab, 2, 5);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            String nom;
            String prenom;
            int ac;
            String cEtab;
            if (b == buttonTypeOk) {
                if (textNom.getText().isEmpty()) {
                    nom = inter.getNom();
                } else {
                    nom = textNom.getText();
                    inter.setNom(nom);
                }
                if (textPrenom.getText().isEmpty()) {
                    prenom = inter.getPrenom();
                } else {
                    prenom = textPrenom.getText();
                }
                if (cbActif.isSelected()) {
                    ac = 1;
                } else {
                    ac = 0;
                }
                if (cbEtab.getSelectionModel().isEmpty()) {
                    cEtab = inter.getCodeEtab();
                } else {
                    cEtab = String.valueOf(cbEtab.getSelectionModel().getSelectedItem().getCode());
                }
                try {
                    m.updateIntervenant(inter.getMail(), prenom, nom, ac, cEtab);
                } catch (IOException ex) {
                    Logger.getLogger(MyDialogModif.class.getName()).log(Level.SEVERE, null, ex);
                }
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Modif reussi!");
                a.setTitle("Etat Modification");
                a.setContentText("vous avez reussi a Modifier l'utilisateur " + inter.getMail());
            }

            return null;
        });
    }

}
