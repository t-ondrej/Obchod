package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.time.LocalDate;
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
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.BillManager;
import sk.upjs.ics.obchod.managers.IBillManager;

public class FakturyTabController implements Initializable {

    @FXML
    private TableView<Bill> fakturyTableView;

    @FXML
    private TableView<Product> tovarFakturyTableView;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button zobrazTovarFakturyButton;

    /**
     * * Stlpce tabulky faktur **
     */
    @FXML
    private TableColumn<Bill, Number> idFakturyTableColumn;

    @FXML
    private TableColumn<Bill, Number> pouzivatelTableColumn;

    @FXML
    private TableColumn<Bill, Number> sumaTableColumn;

    @FXML
    private TableColumn<Bill, LocalDate> datumNakupuTableColumn;

    /**
     * * Stlpce tabulky tovaru faktury **
     */

    @FXML
    private TableColumn<Product, String> nazovTovaruTableColumn;

    @FXML
    private TableColumn<Product, String> kategoriaTovaruTableColumn;

    @FXML
    private TableColumn<Product, String> znackaTovaruTableColumn;

    @FXML
    private TableColumn<Product, Number> cenaTovaruTableColumn;

    @FXML
    private TableColumn<Product, Number> pocetKusovTableColumn;

    private ObservableList<Bill> fakturaModely;

    private ObservableList<Product> tovarModely;

    private ObservableList<String> filtre;

    private IBillManager fakturaManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fakturaManager = EntityManagerFactory.INSTANCE.getBillManager();
        inicializujFakturyTableView();
        inicializujTovarFakturyTableView();
        incializujFilterComboBox();
    }

    private void inicializujFakturyTableView() {
        idFakturyTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        pouzivatelTableColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());
        sumaTableColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        datumNakupuTableColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseDateProperty());

        pouzivatelTableColumn.setCellFactory(column -> {
            return new TableCell<Bill, Number>() {
                @Override
                protected void updateItem(Number idPouzivatela, boolean empty) {
                    super.updateItem(idPouzivatela, empty);
                    if (!empty) {
                        User pouzivatel = DaoFactory.INSTANCE.getMysqlUserDao().findById(idPouzivatela.longValue());
                        setText(pouzivatel.getLogin());
                    } else {
                        setText("");
                    }
                }
            };
        });

        obnovFakturyTableView();
    }

    private void inicializujTovarFakturyTableView() {
        nazovTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        kategoriaTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCategory().nameProperty());
        znackaTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().getBrand().nameProperty());
        cenaTovaruTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        pocetKusovTableColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

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
        fakturyTableView.getItems().clear();
        fakturyTableView.setItems(fakturaModely);
    }

    private void obnovFakturyTableView(String obdobie) {
        naplnFaktury(obdobie);
        fakturyTableView.setItems(fakturaModely);
    }

    private void naplnFaktury() {
        List<Bill> faktury = fakturaManager.getBills();
        fakturaModely = FXCollections.observableArrayList(faktury);
    }

    private void naplnFaktury(String obdobie) {
        List<Bill> fakturyZaObdobie = fakturaManager.getBillsForPeriod(obdobie);
        fakturaModely = FXCollections.observableArrayList(fakturyZaObdobie);
    }

    private void obnovTovarFakturyTableView(Bill faktura) {
        naplnTovarFaktury(faktura);
        tovarFakturyTableView.setItems(tovarModely);
    }

    private void naplnTovarFaktury(Bill faktura) {
        List<Product> tovarFaktury = fakturaManager.getBillProducts(faktura);
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

        Bill faktura = fakturyTableView.getSelectionModel().getSelectedItem();
        obnovTovarFakturyTableView(faktura);
    }
}
