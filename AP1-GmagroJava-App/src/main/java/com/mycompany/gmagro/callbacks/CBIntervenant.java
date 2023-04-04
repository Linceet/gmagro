package com.mycompany.gmagro.callbacks;

import com.mycompany.gmagro.Intervenant;
import com.mycompany.gmagro.Model;
import com.mycompany.gmagro.MyDialogModif;
import com.mycompany.gmagro.MyDialogRole;
import com.mycompany.gmagro.Role;
import com.mycompany.gmagro.TypeCB;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author ageneste
 */
public class CBIntervenant implements Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>> {

    private final Model m;
    private List<Intervenant> listeIntervenant;
    private final TypeCB typeCB;

    public CBIntervenant(Model m, List<Intervenant> listeIntervenant, TypeCB typeCB) {
        this.m = m;
        this.listeIntervenant = listeIntervenant;
        this.typeCB = typeCB;
    }

    @Override
    public TableCell<Intervenant, String> call(TableColumn<Intervenant, String> p) {
        TableCell<Intervenant, String> tbSelEtablissement = new TableCell<>() {
            @Override
            protected void updateItem(String t, boolean empty) {
                System.out.println("Surcharge de UpdateItem Interv");
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
                            Intervenant interv = getTableView().getItems().get(getIndex());
                            String mail = interv.getMail();
                            m.suppIntervenantById(mail);
                            refresh();
                        });
                        break;
                    case MODIFIER:
                        btn.setOnAction(event -> {
                            Intervenant interv = getTableView().getItems().get(getIndex());
                            MyDialogModif mdm = new MyDialogModif(m, interv);
                            mdm.showAndWait();
                            refresh();
                        });
                        break;
                    case GRADE:
                        btn.setOnAction(event -> {
                            Intervenant interv = getTableView().getItems().get(getIndex());
                            String mail = interv.getMail();
                            MyDialogRole mdr = new MyDialogRole(mail, m);
                            Optional<Role> showAndWait = mdr.showAndWait();
                            if (showAndWait.isPresent()) {
                                m.updateRoleInIntervenant(mail, String.valueOf(showAndWait.get().getCode()));
                            }

                            refresh();
                        });
                        break;
                    default:
                        System.err.println("Erreur de typeCB");
                }
            }

            private void refresh() {
                listeIntervenant.clear();
                m.getAllIntervenant();
                listeIntervenant = m.getLesinters();
            }

        };
        return tbSelEtablissement;
    }

}
