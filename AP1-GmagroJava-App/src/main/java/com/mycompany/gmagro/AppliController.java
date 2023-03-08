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
    private final HashMap<Etablissement, Marker> hMapEtab = new HashMap<>();
    private final ObservableList<Etablissement> listeEt = m.getLesEtabs();
    private ObservableList<Intervenant> listeIntervenant = m.getLesinters();
    private ObservableList<Etablissement> listeEtabli = FXCollections.observableArrayList();
    private ObservableList<TypeMachine> listeMachines = FXCollections.observableArrayList();

    @FXML
    private MapView mapViewMenu;
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
    @FXML
    private ListView<Etablissement> lvEtabMachine;
    @FXML
    private MapView mvEtabMachine;
    @FXML
    private TableView<TypeMachine> tvMachine;
    @FXML
    private TableColumn<TypeMachine, String> TableColumnImage;
    @FXML
    private TableColumn<TypeMachine, String> tableColumnLib;
    @FXML
    private TableColumn<TypeMachine, String> tableColumnAdopter;
    @FXML
    private TableColumn<TypeMachine, String> tableColumnModifierTM;
    @FXML
    private TableColumn<TypeMachine, String> tableColumnSupprimerTM;
    @FXML
    private Button AddMachine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialiseEtablissement();
        initialiseMenu();
        initialiseMachine();
        initialiseIntervenant();
        initialiseRole();

    }

    //**************************************remplissage des liste********************************************
    private void initialiseListeEtablissement() {
        try {
            m.getEtab();
            accordion.setExpandedPane(tpHome);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initialiseListeMachine() {
        try {
            m.allMachine();
        } catch (IOException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listeMachines = m.getToutesLesMachine();
    }

    private void initialiseTvIntervenant() {
        tableColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableViewIntervenant.setItems(listeIntervenant);

    }

    private void initTvMachine() {
        tableColumnLib.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tvMachine.setItems(listeMachines);
    }

    private void initialiseTvEtablissement() {
        try {
            m.allEtabs();
            listeEtabli = m.getToutLesEtabs();
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

    private void initialiseIntervenant() {
        initMapIntervenant();
        loadLvEtab();
        prepareButtonsIntervenant();
        initialiseTvIntervenant();
    }

    private void initialiseEtablissement() {
        initMapEtablissement();
        initialiseTvEtablissement();
        prepareButtonEtablissement();
        initialiseListeEtablissement();
    }

    private void initialiseMachine() {
        initMapMachine();
        loadLvEtabMachine();
        initialiseListeMachine();
        initTvMachine();
        prepareButtonsMachine();
    }

    //***************************fonctions *********************************************************************
    private void initMapMenu() {
        mapViewMenu.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapMenuIsInitialized();
            }
        });
        mapViewMenu.initialize();

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
        mapViewMenu.setZoom(5);
        for (Marker m : hMapEtabPourMenu.values()) {
            mapViewMenu.addMarker(m);
        }
        Coordinate position;
        position = new Coordinate(45.46d, 02.57d);
        mapViewMenu.setCenter(position);
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
            hMapEtab.put(e, marInter);
        }
    }

    private void afterMapIntervenantIsInitialized() {
        mapViewInter.setZoom(5);
        for (Marker marInter : hMapEtab.values()) {
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
            hMapEtab.put(e, marInter);
        }
    }

    private void afterMapEtablissementIsInitialized() {
        mvEtab.setZoom(5);
        for (Marker marInter : hMapEtab.values()) {
            mvEtab.addMarker(marInter);
        }
        Coordinate position;
        position = new Coordinate(45.46d, 02.57d);
        mvEtab.setCenter(position);
    }

    private void initMapMachine() {
        mvEtabMachine.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapMachineIsInitialized();
            }
        });
        mvEtabMachine.initialize();

        for (Etablissement e : listeEt) {
            Marker marInter = Marker.createProvided(Marker.Provided.RED).setVisible(true);
            Coordinate position = new Coordinate(e.getP().getLat(), e.getP().getLng());
            marInter.setPosition(position);
            MapLabel mapLabel = new MapLabel(e.toString());
            marInter.attachLabel(mapLabel);
            hMapEtab.put(e, marInter);
        }
    }

    private void afterMapMachineIsInitialized() {
        mvEtabMachine.setZoom(5);
        for (Marker marInter : hMapEtab.values()) {
            mvEtabMachine.addMarker(marInter);
        }
        Coordinate position;
        position = new Coordinate(45.46d, 02.57d);
        mvEtabMachine.setCenter(position);
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

    private void loadLvEtabMachine() {
        lvEtabMachine.setItems(listeEt);
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
                                    listeIntervenant.clear();
                                    try {
                                        m.getAllIntervenant();
                                    } catch (ParseException ex) {
                                        Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    listeIntervenant = m.getLesinters();
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
                                        listeIntervenant.clear();
                                        try {
                                            m.getAllIntervenant();
                                        } catch (ParseException ex) {
                                            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        listeIntervenant = m.getLesinters();
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
                            listeIntervenant.clear();
                            try {
                                m.getAllIntervenant();
                            } catch (IOException | ParseException ex) {
                                Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            listeIntervenant = m.getLesinters();
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
        try {
            MyDialogAddinterv mdai = new MyDialogAddinterv(m);
            mdai.showAndWait();
            listeIntervenant.clear();
            m.getAllIntervenant();
            listeIntervenant = m.getLesinters();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicImport(MouseEvent event) {
        MyDialogImport mdi = new MyDialogImport(m);
        mdi.showAndWait();
        listeIntervenant.clear();
        try {
            m.getAllIntervenant();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listeIntervenant = m.getLesinters();
    }

    //*****************************************************ETABLISSEMENT*************************************
    private void prepareButtonEtablissement() {

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
                                    listeEtabli.clear();
                                    m.allEtabs();
                                    listeEtabli = m.getToutLesEtabs();
                                } catch (IOException | ParseException ex) {
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
                                try {
                                    Etablissement etab = getTableView().getItems().get(getIndex());
                                    MyDialogModifEtab mdme = new MyDialogModifEtab(m, etab);
                                    mdme.showAndWait();
                                    listeEtabli.clear();
                                    m.allEtabs();
                                    listeEtabli = m.getToutLesEtabs();
                                } catch (IOException | ParseException ex) {
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

        tablecolumnModif.setCellFactory(btnModifier);
        tablecolumnSupp.setCellFactory(btnSupp);
    }

    @FXML
    private void clicAddEtab(MouseEvent event) throws IOException, ParseException {
        MyDialogAddEtablissement mdae = new MyDialogAddEtablissement(m);
        mdae.showAndWait();
        listeEtabli.clear();
        m.allEtabs();
        listeEtabli = m.getToutLesEtabs();

    }

    @FXML
    private void ClicAddMachine(MouseEvent event) {
    }

    private void prepareButtonsMachine() {
        //********************************************tableColumnSupp button de Suppression*********************************************
        Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>> btnSupprimer
                = //
                new Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>>() {
            @Override
            public TableCell call(final TableColumn<TypeMachine, String> param) {
                final TableCell<TypeMachine, String> cell = new TableCell<TypeMachine, String>() {

                    final Button btn = new Button("Supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                TypeMachine machine = getTableView().getItems().get(getIndex());
                                String id = machine.getCode();
                                try {
                                    m.suppMachineById(id);
                                    listeMachines.clear();
                                    try {
                                        m.allMachine();
                                    } catch (ParseException ex) {
                                        Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    listeMachines = m.getToutesLesMachine();
                                } catch (IOException ex) {
                                    System.out.println("suppression Machine: ERROR");
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

        loadBtnAdopter();

        //********************************************tableColumnModifier button de modification*********************************************
        Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>> btnModifier
                = //
                new Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>>() {
            @Override
            public TableCell call(final TableColumn<TypeMachine, String> param) {
                final TableCell<TypeMachine, String> cell = new TableCell<TypeMachine, String>() {

                    final Button btn = new Button("modifier");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                TypeMachine machine = getTableView().getItems().get(getIndex());
                                MyDialogModifTM mdmtm = new MyDialogModifTM(m, machine);
                                mdmtm.showAndWait();
                                m.modifierLibMachine(machine.getLibelle(),machine.getCode());
                                listeMachines.clear(); 
                                try {
                                    m.allMachine();
                                } catch (IOException | ParseException ex) {
                                    Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listeMachines = m.getToutesLesMachine();
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        tableColumnSupprimerTM.setCellFactory(btnSupprimer);
        tableColumnModifierTM.setCellFactory(btnModifier);
    }

    public void loadBtnAdopter() {
        Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>> btnAdopt
                = //
                new Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>>() {
            @Override
            public TableCell call(final TableColumn<TypeMachine, String> param) {
                final TableCell<TypeMachine, String> cell = new TableCell<TypeMachine, String>() {

                    final Button btn = new Button("adopter la machine");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            TypeMachine machine = getTableView().getItems().get(getIndex());
                            btn.setOnAction(event -> {

                                String id = machine.getCode();
                                try {
                                    m.adopterMachineById(id);
                                } catch (IOException ex) {
                                    Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println("Error adoption");

                            });
                            setGraphic(btn);
                            setText(null);
                            if (machine.getCodeEtab() == null) {
                                btn.setDisable(true);
                            } else {
                                btn.setDisable(false);
                            }
                        }

                    }

                };
                return cell;
            }
        };
        tableColumnAdopter.setCellFactory(btnAdopt);
    }
}
