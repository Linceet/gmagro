/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;


/**
 *
 * @author ageneste
 */
class MyDialogModifTM extends Dialog<TypeMachine> {

    MyDialogModifTM(Model m, TypeMachine machine) {
        this.setTitle("Modifier");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel). \n"
                + "please fill EVERYTHING!");
        this.setResizable(true);
        
        ImageView iv = new ImageView();
        Label labelLib = new Label("Nouveau nom de la machine:  ");
        TextField textLib = new TextField(machine.getLibelle());
        ButtonType btnFileImage = new ButtonType("selectionner une image");
        this.getDialogPane().getButtonTypes().add(btnFileImage);
        this.setResultConverter((ButtonType f) -> {
            if (f == btnFileImage) {
                FileChooser fc = new FileChooser();
                File fi = fc.showOpenDialog(this.getDialogPane().getScene().getWindow());
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Photos de machines", "*.jpg" )) ;
                if (fi != null) {
                    try {
                        iv.setImage(new javafx.scene.image.Image(new FileInputStream(fi)));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MyDialogModifTM.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        });

        GridPane grid = new GridPane();
        grid.add(labelLib, 1, 1);
        grid.add(textLib, 2, 1);
        
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            String adr;
            String cp;
            String lib;
            String ville;
            if (b == buttonTypeOk) {

               /* adr = textAdr.getText();
                etab.setAdr(adr);

                cp = textCp.getText();
                etab.setCp(cp);

                lib = textLib.getText();
                etab.setLibelle(lib);

                ville = textVille.getText();
                etab.setVille(ville);

                try {
                    m.updateEtablissement(etab.getCode(), adr, cp, lib, ville);
                } catch (IOException ex) {
                    Logger.getLogger(MyDialogModifEtab.class.getName()).log(Level.SEVERE, null, ex);
                }
*/
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Modif reussi!");
                a.setTitle("Etat Modification");
                a.setContentText("vous avez reussi a Modifier l'Etablissement " );
            }

            return null;
        });
    }
}
