/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        Label labelUrl = new Label("Address d'import :  ");
        TextField textUrl = new TextField("URL");

        this.setTitle("Upgrade/Downgrade");
        this.setHeaderText("This is a custom dialog. Enter info and \n"
                + "press Okay (or click title bar 'X' for cancel).");
        this.setResizable(true);

        GridPane grid = new GridPane();
        grid.add(labelUrl, 1, 1);
        grid.add(textUrl, 2, 1);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);

        this.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                String url = textUrl.getText();
                try {
                    String s = run(url);
                    Scanner sc = new Scanner(s);
                    sc.nextLine();
                    while (sc.hasNext()) {
                        String ligne = sc.nextLine();
                        String infoInterv[] = ligne.split(";");
                        Intervenant i = new Intervenant(infoInterv[2], infoInterv[1], infoInterv[0], true, "0", infoInterv[4]);
                        dicoPwd.put(i, infoInterv[3]);
                    }
                } catch (IOException ex) {
                    System.out.println("Site indisponible");
                }
                
                    
                

            }

            return null;
        });
    }

    private String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try ( Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
