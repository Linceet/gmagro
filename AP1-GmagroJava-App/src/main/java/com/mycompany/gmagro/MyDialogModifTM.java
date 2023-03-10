/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    public MyDialogModifTM(Model m, TypeMachine machine) {
        this.setTitle("Modifier");
        this.setHeaderText("Modification des types machines");
        this.setResizable(true);

        ImageView iv = new ImageView();
        iv.setFitHeight(200);
        iv.setFitWidth(200);

        Label labelLib = new Label("Nouveau nom de la machine:  ");
        TextField textLib = new TextField(machine.getLibelle());
        Button btnFileImage = new Button("selectionner une image");
        GridPane grid = new GridPane();
        grid.add(labelLib, 1, 1);
        grid.add(textLib, 2, 1);
        grid.add(iv, 1, 2);
        grid.add(btnFileImage, 1, 3);

        this.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        System.err.println("On affecte resultconverter");
        btnFileImage.setOnMouseClicked(mc -> {
            FileChooser fc = new FileChooser();
            File fi = fc.showOpenDialog(this.getDialogPane().getScene().getWindow());
            if (fi != null) {
                try {
                    String imgB64;
                    try (FileInputStream fin = new FileInputStream(fi)) {
                        iv.setImage(new Image(fin));
                        byte[] FileB64 = new byte[(int)fi.length()];
                        fin.read(FileB64);
                        fin.read(FileB64);
                        imgB64 = Base64.getEncoder().encodeToString(FileB64);
                    }
                    machine.setImg(imgB64);
                    System.out.println("HEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEE");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MyDialogModifTM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MyDialogModifTM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.setResultConverter((buttonClique) -> {
            if (buttonClique == buttonTypeOk) {

                String lib = textLib.getText();
                machine.setLibelle(lib);

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Modif reussi!");
                a.setTitle("Etat Modification");
                a.setContentText("vous avez reussi a Modifier l'Etablissement ");
                a.showAndWait();
                return machine;
            }

            return null;
        });
    }
}
