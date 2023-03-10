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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ageneste
 */
class MyDialogAddTM extends Dialog<TypeMachine> {

    public MyDialogAddTM(Model m) {
        this.setTitle("Ajouter");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
        this.setResizable(true);

        Label labelCode = new Label("Code du TypeMachine : ");
        TextField textCode = new TextField("Code");

        Label labelLib = new Label("Libelle de TypeMachine : ");
        TextField TextLib = new TextField("libelle");
        

        GridPane grid = new GridPane();
        grid.add(labelCode, 1, 1);
        grid.add(textCode, 2, 1);
        grid.add(labelLib, 1, 2);
        grid.add(TextLib, 2, 2);
        
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            String code;
            String lib;
            
            Alert a = new Alert(Alert.AlertType.NONE);
            if (b == buttonTypeOk) {
                code = textCode.getText();
                lib = TextLib.getText();
                if (!code.isEmpty() && !lib.isEmpty()) {
                    try {
                        System.out.println("ajouté");
                        m.addTypeMachine(code, lib,lib);
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("Insertion reussi!");
                        a.setTitle("Etat d'insertion");
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
            }

            return null;
        });

    }
}
