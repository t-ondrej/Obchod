package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.managers.DefaultFakturaManager;
import sk.upjs.ics.obchod.managers.FakturaManager;

public class FakturyTabController implements Initializable {

    @FXML
    private TableView<Faktura> fakturyTableView;

    @FXML
    private TableView<Tovar> tovarFakturyTableView;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button zobrazTovarFakturyButton;

    /**
     * * Stlpce tabulky faktur **
     */
    @FXML
    private TableColumn<Faktura, Number> idFakturyTableColumn;

    @FXML
    private TableColumn<Faktura, Number> pouzivatelTableColumn;

    @FXML
    private TableColumn<Faktura, Number> sumaTableColumn;

    @FXML
    private TableColumn<Faktura, LocalDate> datumNakupuTableColumn;

    /**
     * * Stlpce tabulky tovaru faktury **
     */

    @FXML
    private TableColumn<Tovar, String> nazovTovaruTableColumn;

    @FXML
    private TableColumn<Tovar, String> kategoriaTovaruTableColumn;

    @FXML
    private TableColumn<Tovar, String> znackaTovaruTableColumn;

    @FXML
    private TableColumn<Tovar, Number> cenaTovaruTableColumn;

    @FXML
    private TableColumn<Tovar, Number> pocetKusovTableColumn;

    private ObservableList<Faktura> fakturaModely;

    private ObservableList<Tovar> tovarModely;

    private ObservableList<String> filtre;

    private FakturaManager defaultFakturaManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultFakturaManager = new DefaultFakturaManager(false);
        inicializujFakturyTableView();
        inicializujTovarFakturyTableView();
        incializujFilterComboBox();
    }

    private void inicializujFakturyTableView() {
        idFakturyTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        pouzivatelTableColumn.setCellValueFactory(cellData -> cellData.getValue().idPouzivatelProperty());
        sumaTableColumn.setCellValueFactory(cellData -> cellData.getValue().sumaProperty());
        datumNakupuTableColumn.setCellValueFactory(cellData -> cellData.getValue().datumNakupuProperty());

        pouzivatelTableColumn.setCellFactory(column -> {
            return new TableCell<Faktura, Number>() {
                @Override
                protected void updateItem(Number idPouzivatela, boolean empty) {
                    super.updateItem(idPouzivatela, empty);
                    if (!empty) {
                        Pouzivatel pouzivatel = DaoFactory.INSTANCE.getMysqlPouzivatelDao().dajPouzivatela(idPouzivatela.longValue());
                        setText(pouzivatel.getPrihlasovacieMeno());
                    }
                }
            };
        });

        obnovFakturyTableView();
    }

    private void inicializujTovarFakturyTableView() {
        nazovTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().nazovProperty());
        kategoriaTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().getKategoria().nazovProperty());
        znackaTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().getZnacka().nazovProperty());
        cenaTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().cenaProperty());
        pocetKusovTableColumn.setCellValueFactory(cellData -> cellData.getValue().pocetKusovProperty());

    }

    private void incializujFilterComboBox() {
        List<String> filtreList = Arrays.asList("všetky", "posledný deň", "posledný týždeň", "posledný mesiac", "posledný rok");
        filtre = FXCollections.observableArrayList(filtreList);

        filterComboBox.setItems(filtre);

        filterComboBox.valueProperty().addListener(event -> {
            if (filterComboBox.getSelectionModel().getSelectedItem().equals("všetky")) {
                obnovFakturyTableView();
            } else {
                obnovFakturyTableView(filterComboBox.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void obnovFakturyTableView() {
        naplnFaktury();
        fakturyTableView.setItems(fakturaModely);
    }

    private void obnovFakturyTableView(String obdobie) {
        naplnFaktury(obdobie);
        fakturyTableView.setItems(fakturaModely);
    }

    private void naplnFaktury() {
        List<Faktura> faktury = defaultFakturaManager.dajFaktury();
        fakturaModely = FXCollections.observableArrayList(faktury);
    }

    private void naplnFaktury(String obdobie) {
        List<Faktura> fakturyZaObdobie = defaultFakturaManager.dajFakturyZaObdobie(obdobie);
        fakturaModely = FXCollections.observableArrayList(fakturyZaObdobie);
    }

    private void obnovTovarFakturyTableView(Faktura faktura) {
        naplnTovarFaktury(faktura);
        tovarFakturyTableView.setItems(tovarModely);
    }

    private void naplnTovarFaktury(Faktura faktura) {
        List<Tovar> tovarFaktury = defaultFakturaManager.dajTovarFaktury(faktura);
        tovarModely = FXCollections.observableArrayList(tovarFaktury);
    }

    private void ukazVyberFakturuUpozornenie() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText("Vyberte fakturu v tabuľke.");
        alert.showAndWait();
    }

    @FXML
    public void onZobrazTovarFakturyButtonClicked() {
        if (fakturyTableView.getSelectionModel().isEmpty()) {
            ukazVyberFakturuUpozornenie();
            return;
        }

        Faktura faktura = fakturyTableView.getSelectionModel().getSelectedItem();
        obnovTovarFakturyTableView(faktura);
    }
}
