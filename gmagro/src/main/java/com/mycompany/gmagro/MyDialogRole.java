/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author ageneste
 */
public class MyDialogRole extends Dialog<Role> {

    public MyDialogRole(String mail, Model m) {

        this.setTitle("Upgrade/Downgrade");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
        this.setResizable(true);

        Label label1 = new Label(mail + ": ");
        Label label2 = new Label("nouveau role(1= superAdmin / 2 = admin / 3 = utilisateur) :  ");
        ComboBox<Role> cbRoles = new ComboBox<>(m.getLesRoles());

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(cbRoles, 2, 2);
        grid.add(label2, 1, 2);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                return cbRoles.getSelectionModel().getSelectedItem();
            }

            return null;
        });
    }

}
