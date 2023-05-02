/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gmagro;

import com.mycompany.gmagro.callbacks.CBEtablissement;
import com.mycompany.gmagro.callbacks.CBIntervenant;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.io.ByteArrayInputStream;
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
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author ageneste
 */
public class AppliController implements Initializable {

    private final Model m = new Model();
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
        } catch (IOException | ParseException ex) {
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

        m.allEtabs();
        listeEtabli = m.getToutLesEtabs();

        tableViewEtablissement.setItems(listeEtabli);
        tablecolumnAdrs.setCellValueFactory(new PropertyValueFactory<>("adr"));
        tablecolumnCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tablecolumnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));

    }

    private void initialiseRole() {
        try {
            m.getRole();
        } catch (IOException | ParseException ex) {
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
        initialiseTvIntervenant();
        prepareButtonsIntervenant();
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
        for (Marker mM : hMapEtabPourMenu.values()) {
            mapViewMenu.addMarker(mM);
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

        tableColumnSupprimer.setCellFactory(new CBIntervenant(m, listeIntervenant, TypeCB.SUPPRIMER));
        tableColumnGrade.setCellFactory(new CBIntervenant(m, listeIntervenant, TypeCB.GRADE));
        tableColumnModifier.setCellFactory(new CBIntervenant(m, listeIntervenant, TypeCB.MODIFIER));
    }

    @FXML
    private void clicAllIntervenant(MouseEvent event){
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
        listeIntervenant.clear();
        m.getAllIntervenant();
        listeIntervenant = m.getLesinters();
    }

    @FXML
    private void clicImport(MouseEvent event) {
        MyDialogImport mdi = new MyDialogImport(m);
        mdi.showAndWait();
        listeIntervenant.clear();
        m.getAllIntervenant();
        listeIntervenant = m.getLesinters();
    }

    //*****************************************************ETABLISSEMENT*************************************
    private void prepareButtonEtablissement() {

        tablecolumnSupp.setCellFactory(new CBEtablissement(m, listeEtabli, TypeCB.SUPPRIMER));
        tablecolumnModif.setCellFactory(new CBEtablissement(m, listeEtabli, TypeCB.MODIFIER));
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
        MyDialogAddTM mdam = new MyDialogAddTM(m);
        mdam.showAndWait();
        listeMachines.clear();
        try {
            m.allMachine();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(AppliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listeMachines = m.getToutesLesMachine();
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
                                System.out.println(machine.getImg());
                                System.out.println("cete limage");
                                if(mdmtm!=null){
                                m.modifierTypeMachine(machine.getLibelle(), machine.getCode(),machine.getExt(), machine.getImg());
                                }
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
        ///////////////////////
        Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>> image
                = //
                new Callback<TableColumn<TypeMachine, String>, TableCell<TypeMachine, String>>() {
            @Override
            public TableCell call(final TableColumn<TypeMachine, String> param) {
                final TableCell<TypeMachine, String> cell = new TableCell<TypeMachine, String>() {

                    final ImageView iv = new ImageView();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Image img = new Image("http://sio.jbdelasalle.com/~ageneste/gmagrowsjava/img/"+getTableView().getItems().get(getIndex()).getImg());
                            iv.setImage(img);
                                    
                           
                            setGraphic(iv);
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
