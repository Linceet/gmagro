/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 *
 * @author ageneste
 */
class MyDialogImport extends Dialog<Intervenant> {

    public MyDialogImport(Model m) {
        HashMap<Intervenant, String> dicoPwd = new HashMap<>();
        Label labelEtablissment = new Label("Etablissement d'import :  ");
        ComboBox<Etablissement> cbEtablissement = new ComboBox<>(m.getLesEtabs());
        Button buttonFileChooser = new Button("selectionner un fichier csv");

        this.setTitle("Import");
        this.setHeaderText("Import d'intervenant");
        this.setResizable(true);

        GridPane grid = new GridPane();
        grid.add(labelEtablissment, 1, 1);
        grid.add(cbEtablissement, 2, 1);
        grid.add(buttonFileChooser, 1, 2);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.getDialogPane().getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        buttonFileChooser.setOnMouseClicked(mc -> {
            FileChooser fc = new FileChooser();
            File f = fc.showOpenDialog(this.getDialogPane().getScene().getWindow());
            if (f != null) {
                try {
                    Scanner sc = new Scanner(new FileInputStream(f));
                    sc.nextLine();
                    while (sc.hasNext()) {
                        String ligne = sc.nextLine();
                        String infoIntervenant[] = ligne.split(";");
                        Etablissement e = cbEtablissement.getSelectionModel().getSelectedItem();
                        String codeE = e.getCode();
                        Intervenant i = new Intervenant(infoIntervenant[2], infoIntervenant[1], infoIntervenant[0], true, codeE, infoIntervenant[4]);
                        dicoPwd.put(i, infoIntervenant[3]);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MyDialogImport.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {

                for (Map.Entry<Intervenant, String> entry : dicoPwd.entrySet()) {
                    Intervenant inter = entry.getKey();
                    String mdp = entry.getValue();
                    m.addIntervenant(inter.getMail(), mdp, inter.getPrenom(), inter.getNom(), inter.isActif() ? 1 : 0, inter.getCodeRole(), inter.getCodeEtab());
                }
                alert.setHeaderText("Insertion reussi!");
                alert.setTitle("Etat d'insertion");
                alert.setContentText("vous avez reussi a inserer l'utilisateur ");
                alert.showAndWait();
                return null;

            }
            return null;
        });

    }
}
