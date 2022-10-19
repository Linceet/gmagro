/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gmagro;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
    private ObservableList<Intervenant> listeIntervenant = m.getLesinters();
    private ObservableList<Etablissement> listeEtabli = FXCollections.observableArrayList();

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
    @FXML
    private Button btnAll;
    @FXML
    private Button btnAddInterv;
    @FXML
    private Button btnImp;
    @FXML
    private MapView mvEtab;
    @FXML
    private TableColumn<Etablissement, String> tablecolumnAdrs;
    @FXML
    private TableColumn<Etablissement, String> tablecolumnCp;
    @FXML
    private TableColumn<Etablissement, String> tablecolumnVille;
    @FXML
    private TableColumn<Etablissement, String> tablecolumnModif;
    @FXML
    private TableColumn<Etablissement, String> tablecolumnSupp;
    @FXML
    private Button btnAjouEtab;
    @FXML
    private TableView<Etablissement> tableViewEtablissement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialiseTvEtablissement();
        initialiseTvIntervenant();
        prepareButtonEtablissement();
        prepareButtonsIntervenant();
        initialiseEtablissement();
        initialiseMenu();
        initMapEtablissement();
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

    private void initialiseTvIntervenant() {
        tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableViewIntervenant.setItems(listeIntervenant);

    }

    private void initialiseTvEtablissement() {
        try {
            m.allEtabs();
            listeEtabli=m.getToutLesEtabs();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableViewEtablissement.setItems(listeEtabli);
        tablecolumnAdrs.setCellValueFactory(new PropertyValueFactory<>("adr"));
        tablecolumnCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tablecolumnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));

    }

    private void initialiseRole() {
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

    private void initMapEtablissement() {
        mvEtab.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapEtablissementIsInitialized();
            }
        });
        mvEtab.initialize();

        for (Etablissement e : listeEt) {
            Marker marInter = Marker.createProvided(Marker.Provided.RED).setVisible(true);
            Coordinate position = new Coordinate(e.getP().getLat(), e.getP().getLng());
            marInter.setPosition(position);
            MapLabel mapLabel = new MapLabel(e.toString());
            marInter.attachLabel(mapLabel);
            hMapEtabPourIntervenant.put(e, marInter);
        }
    }

    private void afterMapEtablissementIsInitialized() {
        mvEtab.setZoom(5);
        for (Marker marInter : hMapEtabPourIntervenant.values()) {
            mvEtab.addMarker(marInter);
        }
        Coordinate position;
        position = new Coordinate(45.46d, 02.57d);
        mvEtab.setCenter(position);
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
        listeIntervenant.clear();
        Etablissement e = lv_etab.getSelectionModel().getSelectedItem();
        System.out.println("on passe : " + e.getLibelle());
        m.getIntervenantFromAEtablissement(String.valueOf(e.getCode()));
        listeIntervenant = m.getLesIntervenantParEtab();

        tableViewIntervenant.setItems(listeIntervenant);

    }

    private void prepareButtonsIntervenant() {
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
                                        m.updateRoleInIntervenant(mail, String.valueOf(showAndWait.get().getCode()));
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

    @FXML
    private void clicAllIntervenant(MouseEvent event) throws IOException, ParseException {
        listeIntervenant.clear();
        m.getAllIntervenant();
        listeIntervenant = m.getLesinters();
        tableViewIntervenant.setItems(listeIntervenant);
        lv_etab.getSelectionModel().clearSelection();
    }

    @FXML
    private void clicAddInterv(MouseEvent event) {
        MyDialogAddinterv mdai = new MyDialogAddinterv(m);
        mdai.showAndWait();
    }

    @FXML
    private void clicImport(MouseEvent event) {
        MyDialogImport mdi = new MyDialogImport(m);
        mdi.showAndWait();
    }

    //*****************************************************ETABLISSEMENT*************************************
    
    private void prepareButtonEtablissement(){
        
        Callback<TableColumn<Etablissement, String>, TableCell<Etablissement, String>> btnSupp
                = //
                new Callback<TableColumn<Etablissement, String>, TableCell<Etablissement, String>>() {
            @Override
            public TableCell call(final TableColumn<Etablissement, String> param) {
                final TableCell<Etablissement, String> cell = new TableCell<Etablissement, String>() {

                    final Button btn = new Button("Supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Etablissement etablissement = getTableView().getItems().get(getIndex());
                                String id = etablissement.getCode();
                                try {
                                    m.suppEtabissementById(id);
                                } catch (IOException ex) {
                                    Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
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
        Callback<TableColumn<Etablissement, String>, TableCell<Etablissement, String>> btnModifier
                = //
                new Callback<TableColumn<Etablissement, String>, TableCell<Etablissement, String>>() {
            @Override
            public TableCell call(final TableColumn<Etablissement, String> param) {
                final TableCell<Etablissement, String> cell = new TableCell<Etablissement, String>() {

                    final Button btn = new Button("modifier");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Etablissement etab = getTableView().getItems().get(getIndex());
                                MyDialogModifEtab mdme = new MyDialogModifEtab(m, etab);
                                mdme.showAndWait();
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        
        tablecolumnModif.setCellFactory(btnModifier);
        tablecolumnSupp.setCellFactory(btnSupp);
    }      
    
    @FXML
    private void clicAddEtab(MouseEvent event) throws IOException, ParseException {
       MyDialogAddEtablissement mdae = new MyDialogAddEtablissement(m);
       mdae.showAndWait(); 
       listeEtabli.clear();
       m.allEtabs();
       listeEtabli= m.getToutLesEtabs();
       
    }

}
