/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro.callbacks;

import com.mycompany.gmagro.AppliController;
import com.mycompany.gmagro.Etablissement;
import com.mycompany.gmagro.Model;
import com.mycompany.gmagro.MyDialogModifEtab;
import com.mycompany.gmagro.TypeCB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ageneste
 */
public class CBEtablissement implements Callback<TableColumn<Etablissement, String>, TableCell<Etablissement, String>> {

    private final Model m;
    private List<Etablissement> listeEtabli;
    private final TypeCB typeCB;

    public CBEtablissement(Model m, List<Etablissement> listeEtabli, TypeCB typeCB) {
        this.m = m;
        this.listeEtabli = listeEtabli;
        this.typeCB = typeCB;
    }

    @Override
    public TableCell<Etablissement, String> call(TableColumn<Etablissement, String> p) {
        TableCell<Etablissement, String> tbSelEtablissement = new TableCell<>() {
            @Override
            protected void updateItem(String t, boolean empty) {
                System.out.println("Surcharge de UpdateItem Etab");
                super.updateItem(t, empty);
                Button btn = new Button(typeCB.name());
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    traiterBouton(btn, typeCB);
                    setGraphic(btn);
                    setText(null);
                }
            }

            private void traiterBouton(Button btn, TypeCB typeCB) {
                System.out.println("Traitements des boutons du TB : " + typeCB.name());
                switch (typeCB) {
                    case SUPPRIMER:
                        btn.setOnAction(event -> {
                            Etablissement etablissement = getTableView().getItems().get(getIndex());
                            String id = etablissement.getCode();
                            m.suppEtablissementById(id);
                            refresh();
                        });
                        break;
                    case MODIFIER:
                        btn.setOnAction(event -> {
                            Etablissement etab = getTableView().getItems().get(getIndex());
                            MyDialogModifEtab mdme = new MyDialogModifEtab(m, etab);
                            mdme.showAndWait();
                            refresh() ;
                        });
                        break;
                    default:
                        System.err.println("Erreur de typeCB");
                }
            }

            private void refresh() {
                listeEtabli.clear();
                m.allEtabs();
                listeEtabli = m.getToutLesEtabs();
            }

        };
        return tbSelEtablissement;
    }

}
