/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gmagro;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author ageneste
 */
public class AppliController implements Initializable {

    private Model m = new Model();
    private final HashMap<Etablissement, Marker> hMapEtabPourMenu = new HashMap<>();
    private final HashMap<Etablissement, Marker> hMapEtabPourIntervenant = new HashMap<>();
    private final ObservableList<Etablissement> listeEt = m.getLesEtabs();
    private ObservableList<Intervenant> listeIntervenantParEtablissement = FXCollections.observableArrayList();

    @FXML
    private MapView mapView;
    @FXML
    private Accordion accordion;
    @FXML
    private TitledPane tpHome;
    @FXML
    private ListView<Etablissement> lv_etab;
    @FXML
    private MapView mapViewInter;
    @FXML
    private TableView<Intervenant> tableViewIntervenant;
    @FXML
    private PieChart pc_Pers;
    @FXML
    private TableColumn<Intervenant, String> tableColumnNom;
    @FXML
    private TableColumn<Intervenant, String> tableColumnSupprimer;
    @FXML
    private TableColumn<Intervenant, String> tableColumnGrade;
    @FXML
    private TableColumn<Intervenant, String> tableColumnModifier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialiseEtablissement();
        initialiseMenu();
        initialiseMapIntervenant();
        initialiseRole();

    }

    //**************************************remplissage des liste********************************************
    private void initialiseEtablissement() {
        try {
            m.getEtab();
            accordion.setExpandedPane(tpHome);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initialiseLvIntervenant() {
        //m.get;
    }
    private void initialiseRole(){
        try {
            m.getRole();
        } catch (IOException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ******************fonction de différente partie a l'initialisation*****************************
    private void initialiseMenu() {
        initMapMenu();
        loadPiechart();
    }

    private void initialiseMapIntervenant() {
        initMapIntervenant();
        loadLvEtab();
    }

    //***************************fonctions *********************************************************************
    private void initMapMenu() {
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapMenuIsInitialized();
            }
        });
        mapView.initialize();

        for (Etablissement e : listeEt) {
            Marker mar = Marker.createProvided(Marker.Provided.RED).setVisible(true);
            Coordinate position = new Coordinate(e.getP().getLat(), e.getP().getLng());
            mar.setPosition(position);
            MapLabel mapLabel = new MapLabel(e.toString());
            mar.attachLabel(mapLabel);
            hMapEtabPourMenu.put(e, mar);
        }
        //refreshVisibility(listeEt);
    }

    private void afterMapMenuIsInitialized() {
        mapView.setZoom(5);
        for (Marker m : hMapEtabPourMenu.values()) {
            mapView.addMarker(m);
        }
        Coordinate position;
        position = new Coordinate(45.46d, 02.57d);
        mapView.setCenter(position);
    }

    private void initMapIntervenant() {
        mapViewInter.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIntervenantIsInitialized();
            }
        });
        mapViewInter.initialize();

        for (Etablissement e : listeEt) {
            Marker marInter = Marker.createProvided(Marker.Provided.RED).setVisible(true);
            Coordinate position = new Coordinate(e.getP().getLat(), e.getP().getLng());
            marInter.setPosition(position);
            MapLabel mapLabel = new MapLabel(e.toString());
            marInter.attachLabel(mapLabel);
            hMapEtabPourIntervenant.put(e, marInter);
        }
    }

    private void afterMapIntervenantIsInitialized() {
        mapViewInter.setZoom(5);
        for (Marker marInter : hMapEtabPourIntervenant.values()) {
            mapViewInter.addMarker(marInter);
        }
        Coordinate position;
        position = new Coordinate(45.46d, 02.57d);
        mapViewInter.setCenter(position);
    }

    private void loadPiechart() {
        for (Etablissement e : listeEt) {
            String key = e.toString();
            int value = e.getNbPers();
            pc_Pers.getData().add(new PieChart.Data(key, value));
            pc_Pers.setLabelsVisible(true);
        }
    }

    private void loadLvEtab() {
        lv_etab.setItems(listeEt);
    }

    @FXML
    private void onClicEtablissmentSelected(MouseEvent event) throws IOException, ParseException {
        listeIntervenantParEtablissement.clear();
        Etablissement e = lv_etab.getSelectionModel().getSelectedItem();
        System.out.println("on passe : " + e.getLibelle());
        m.getIntervenantFromAEtablissement(String.valueOf(e.getCode()));
        listeIntervenantParEtablissement = m.getLesIntervenantParEtab();
        tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        for (Intervenant i : listeIntervenantParEtablissement) {
            
             //********************************************tableColumnSupp button de Suppression*********************************************
            Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>> btnSupp
                    = //
                    new Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>>() {
                @Override
                public TableCell call(final TableColumn<Intervenant, String> param) {
                    final TableCell<Intervenant, String> cell = new TableCell<Intervenant, String>() {

                        final Button btn = new Button("Supprimer");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    Intervenant person = getTableView().getItems().get(getIndex());
                                    String id = person.getMail();
                                    try {
                                        m.suppIntervenantById(id);
                                    } catch (IOException ex) {
                                        System.out.println("suppression Intervenant: ERROR");
                                    }
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
             //********************************************tableColumnGrade button de grade*********************************************
            
            Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>> btnUpgrade
                    = //
                    new Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>>() {
                @Override
                public TableCell call(final TableColumn<Intervenant, String> param) {
                    final TableCell<Intervenant, String> cell = new TableCell<Intervenant, String>() {

                        final Button btn = new Button("grade");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    Intervenant person = getTableView().getItems().get(getIndex());
                                    String mail = person.getMail();
                                    MyDialogRole mdr = new MyDialogRole(mail, m);
                                    Optional<Role> showAndWait = mdr.showAndWait();
                                    if (showAndWait.isPresent()) {
                                        try {
                                            m.updateRoleInIntervenant(mail,String.valueOf(showAndWait.get().getCode()));
                                        } catch (IOException ex) {
                                            System.out.println("Error modif de role d'un Intervenant");
                                        }
                                    }

                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
            //********************************************tableColumnModifier button de modification*********************************************
            Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>> btnModifier
                    = //
                    new Callback<TableColumn<Intervenant, String>, TableCell<Intervenant, String>>() {
                @Override
                public TableCell call(final TableColumn<Intervenant, String> param) {
                    final TableCell<Intervenant, String> cell = new TableCell<Intervenant, String>() {

                        final Button btn = new Button("modifier");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    Intervenant person = getTableView().getItems().get(getIndex());
                                    MyDialogModif mdm = new MyDialogModif(m, person);
                                    mdm.showAndWait();
                                    });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

            tableColumnSupprimer.setCellFactory(btnSupp);
            tableColumnGrade.setCellFactory(btnUpgrade);
            tableColumnModifier.setCellFactory(btnModifier);

        }

        tableViewIntervenant.setItems(listeIntervenantParEtablissement);

    }

}
