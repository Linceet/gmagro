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

/**
 *
 * @author ageneste
 */
public class MyDialogModifEtab extends Dialog<Etablissement> {

    public MyDialogModifEtab(Model m, Etablissement etab) {
        this.setTitle("Modifier");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel). \n"
                + "please fill EVERYTHING!");
        this.setResizable(true);

        Label labelAdr = new Label("nouvelle Addresse :  ");
        TextField textAdr = new TextField(etab.getAdr());

        Label labelCp = new Label("nouveau code postal:  ");
        TextField textCp = new TextField(etab.getCp());

        Label labelLib = new Label("Nouveau nom d'Etablissement:  ");
        TextField textLib = new TextField(etab.getLibelle());

        Label labelVille = new Label("nouvelle ville de l'etablissement:  ");
        TextField textVille = new TextField(etab.getVille());

        GridPane grid = new GridPane();
        grid.add(labelAdr, 1, 1);
        grid.add(textAdr, 2, 1);
        grid.add(labelCp, 1, 2);
        grid.add(textCp, 2, 2);
        grid.add(labelLib, 1, 3);
        grid.add(textLib, 2, 3);
        grid.add(labelVille, 1, 4);
        grid.add(textVille, 2, 4);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            String adr;
            String cp;
            String lib;
            String ville;
            if (b == buttonTypeOk) {

                adr = textAdr.getText();
                etab.setAdr(adr);

                cp = textCp.getText();
                etab.setCp(cp);

                lib = textLib.getText();
                etab.setLibelle(lib);

                ville = textVille.getText();
                etab.setVille(ville);
                m.updateEtablissement(etab.getCode(), adr, cp, lib, ville);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Modif reussi!");
                a.setTitle("Etat Modification");
                a.setContentText("vous avez reussi a Modifier l'Etablissement " + etab.toString());
            }

            return null;
        });
    }

}
