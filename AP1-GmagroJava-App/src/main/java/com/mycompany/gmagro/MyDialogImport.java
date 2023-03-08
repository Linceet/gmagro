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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author ageneste
 */
class MyDialogImport extends Dialog<Intervenant> {

    public MyDialogImport(Model m) {
        HashMap<Intervenant, String> dicoPwd = new HashMap<>();
        Label labelEtablissment = new Label("Etablissement d'import :  ");
        ComboBox<Etablissement> cbEtablissement = new ComboBox<>(m.getLesEtabs());
        ButtonType buttonTypeFileChooser = new ButtonType("selectionner un fichier csv");
        this.getDialogPane().getButtonTypes().add(buttonTypeFileChooser);

        this.setTitle("Import");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
        this.setResizable(true);

        GridPane grid = new GridPane();
        grid.add(labelEtablissment, 1, 1);
        grid.add(cbEtablissement, 2, 1);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeFileChooser) {
                try {
                    FileChooser fc = new FileChooser();
                    File f = fc.showOpenDialog(this.getDialogPane().getScene().getWindow());
                    if (f != null) {
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
                    }
                } catch (IOException ex) {
                    System.out.println("Site indisponible");
                }
            }
            if (b == buttonTypeOk) {
                for (Map.Entry<Intervenant, String> entry : dicoPwd.entrySet()) {
                    Intervenant inter = entry.getKey();
                    String mdp = entry.getValue();
                        
                    
                    try {
                         m.addIntervenant(inter.getMail(), mdp, inter.getPrenom(), inter.getNom(), inter.isActif()?1:0, inter.getCodeRole(), inter.getCodeEtab());
                    } catch (IOException ex) {
                        Logger.getLogger(MyDialogImport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

                   
                    
                }
                alert.setHeaderText("Insertion reussi!");
                alert.setTitle("Etat d'insertion");
                alert.setContentText("vous avez reussi a inserer l'utilisateur ");
                alert.showAndWait();
            }

            return null;
        });

    }

}
