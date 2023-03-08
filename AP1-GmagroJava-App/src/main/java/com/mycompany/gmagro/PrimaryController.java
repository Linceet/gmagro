/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gmagro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ageneste
 */
public class PrimaryController implements Initializable {

    @FXML
    private TextField tf_log;
    @FXML
    private PasswordField pf_pass;
    @FXML
    private Button btn_connect;

    Model m = new Model();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onClic(MouseEvent event) {
        if (!tf_log.getText().isEmpty() && !pf_pass.getText().isEmpty()) {
            String log = tf_log.getText();
            String pwd = pf_pass.getText();
            try {
                int rep = m.login(log, pwd);
                if (rep == 1) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Etat de connexion");
                    a.setHeaderText("Connexion reussi");
                    a.setContentText("bienvenue!");
                    a.showAndWait();
                    m.changer("appli");
                } else if (rep == 2) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Etat de connexion");
                    a.setHeaderText("Connexion impossible");
                    a.setContentText("vous n'avez pas les droits suffisant pour vous connecter");
                    a.showAndWait();
                }
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error de login");
            a.setHeaderText("Error de login");
            a.setContentText("Vous n'avais pas entrer de mots de pass ou de Login!");
            a.showAndWait();
        }
        
    }
    

}
