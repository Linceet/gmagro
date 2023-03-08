/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ageneste
 */
class MyDialogAddEtablissement extends Dialog<Etablissement> {

    MyDialogAddEtablissement(Model m) {
        this.setTitle("Ajouter");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
        this.setResizable(true);

        Label labelAdr = new Label("Addresse nouveau Etablissement :  ");
        TextField textAdr = new TextField();

        Label labelCp = new Label("Code postal du nouveau Etablissement:  ");
        TextField textCp = new TextField();

        Label labelLib = new Label("Nom du ouveau Etablissement:  ");
        TextField textLib = new TextField();

        Label labelVille = new Label("Ville du nouveau Etablissement:  ");
        TextField textVille = new TextField();

        Label labelLng = new Label("Longitude du nouveau Etablissement:  ");
        TextField textLng = new TextField();

        Label labelLat = new Label("Latitude du nouveau Etablissement:  ");
        TextField textLat = new TextField();

        GridPane grid = new GridPane();
        grid.add(labelAdr, 1, 1);
        grid.add(textAdr, 2, 1);
        grid.add(labelCp, 1, 2);
        grid.add(textCp, 2, 2);
        grid.add(labelLib, 1, 3);
        grid.add(textLib, 2, 3);
        grid.add(labelVille, 1, 4);
        grid.add(textVille, 2, 4);
        grid.add(labelLng, 1, 5);
        grid.add(textLng, 2, 5);
        grid.add(labelLat, 1, 6);
        grid.add(textLat, 2, 6);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            String adr;
            String cp;
            String lib;
            String ville;
            String lng;
            String lat;

            Alert a = new Alert(Alert.AlertType.NONE);
            if (b == buttonTypeOk) {
                adr = textAdr.getText();
                cp = textCp.getText();
                lib = textLib.getText();
                ville = textVille.getText();
                lng = textLng.getText();
                lat = textLat.getText();

                if (!cp.isEmpty() && !lng.isEmpty() && !ville.isEmpty() && !adr.isEmpty() && !lib.isEmpty()) {
                    try {
                        System.out.println("ajouté");
                        m.addEtablissement(adr, cp, lib, ville, lng,  lat);
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("Insertion reussi!");
                        a.setTitle("Etat d'insertion");
                        a.setContentText("vous avez reussi a inserer l'Etablissement " + lib +" "+ ville);
                        a.showAndWait();
                    } catch (IOException ex) {
                        System.out.println("pb d'insertion dans la base de donné");
                    }
                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("Insertion Echec!");
                    a.setTitle("Etat d'insertion;");
                    a.setContentText("Un champ est vide");
                    System.out.println("ajouté pas reussi");
                }
                try {
                    m.allEtabs();
                    
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(MyDialogAddEtablissement.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            return null;
        });

    }

}
