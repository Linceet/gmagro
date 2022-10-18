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
class MyDialogAddinterv extends Dialog<Intervenant> {

    public MyDialogAddinterv(Model m) {
        this.setTitle("Import");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
        this.setResizable(true);

        Label labelMail = new Label("mail : ");
        TextField textMail = new TextField("mail");

        Label labelMdp = new Label("mots de passe : ");
        PasswordField pfMdp = new PasswordField();
        Label labelMdpConf = new Label("Confirmé mots de passe : ");
        PasswordField pfMdpConf = new PasswordField();

        Label labelPrenom = new Label("nouveau prenom : ");
        TextField textPrenom = new TextField("prenom");

        Label labelNom = new Label("nom :  ");
        TextField textNom = new TextField("nom");

        Label labelActif = new Label("Activité de l'Intervenant (cauché si actif) : ");
        CheckBox cbActif = new CheckBox("actif");
        cbActif.setSelected(true);

        Label labelEtab = new Label("Selectionner un établissement: ");
        ComboBox<Etablissement> cbEtab = new ComboBox<>(m.getLesEtabs());

        Label labelRole = new Label("Selectionner un role: ");
        ComboBox<Role> cbRole = new ComboBox<>(m.getLesRoles());

        GridPane grid = new GridPane();
        grid.add(labelMail, 1, 1);
        grid.add(textMail, 2, 1);
        grid.add(labelMdp, 1, 2);
        grid.add(pfMdp, 2, 2);
        grid.add(labelMdpConf, 1, 3);
        grid.add(pfMdpConf, 2, 3);
        grid.add(labelPrenom, 1, 4);
        grid.add(textPrenom, 2, 4);
        grid.add(labelNom, 1, 5);
        grid.add(textNom, 2, 5);
        grid.add(labelActif, 1, 6);
        grid.add(cbActif, 2, 6);
        grid.add(labelRole, 1, 7);
        grid.add(cbRole, 2, 7);
        grid.add(labelEtab, 1, 8);
        grid.add(cbEtab, 2, 8);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            String nom;
            String mail;
            String mdp;
            String mdpC;
            String prenom;
            int ac;
            String cEtab;
            String cRole;
            Alert a = new Alert(Alert.AlertType.NONE);
            if (b == buttonTypeOk) {
                mail = textMail.getText();
                mdp = pfMdp.getText();
                mdpC = pfMdpConf.getText();
                prenom = textPrenom.getText();
                nom = textNom.getText();
                ac = cbActif.isSelected()?1:0;
                System.out.println(ac);
                cRole = String.valueOf(cbEtab.getSelectionModel().getSelectedItem().getCode());
                cEtab = String.valueOf(cbEtab.getSelectionModel().getSelectedItem().getCode());

                if (mdp.equals(mdpC)) {
                    if (!mail.isEmpty() && !prenom.isEmpty() && !mdp.isEmpty() && !nom.isEmpty() && !cRole.isEmpty() && !cRole.isEmpty()) {
                        try {
                            System.out.println("ajouté");
                            m.addIntervenant(mail, mdp, prenom, nom, ac, cRole, cEtab);
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("Insertion reussi!");
                            a.setTitle("Etat d'insertion");
                            a.setContentText("vous avez reussi a inserer l'utilisateur " + prenom + " " + nom);
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

                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("Insertion Echec!");
                    a.setTitle("Etat d'insertion;");
                    a.setContentText("Mots de passe et mots de passe de confirmation ne sont pas parreil");
                    a.showAndWait();
                    System.out.println("ajouté pas reussi due au mdp");
                }

                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setHeaderText("Insertion reussi!");
                a.setTitle("Etat d'insertion");
                a.setContentText("vous avez reussi a inserer l'utilisateur " + prenom + " " + nom);
                a.showAndWait();
            }

            return null;
        });

    }
}
