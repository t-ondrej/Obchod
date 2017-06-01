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
import sk.upjs.ics.obchod.dao.IBillDao;
import sk.upjs.ics.obchod.entity.Account;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.BillManager;
import sk.upjs.ics.obchod.managers.IBillManager;
import sk.upjs.ics.obchod.utils.GuiUtils;

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
    
    private IBillDao billDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fakturaManager = EntityManagerFactory.INSTANCE.getBillManager();
        billDao = DaoFactory.INSTANCE.getMysqlBillDao();
        inicializujFakturyTableView();
        inicializujTovarFakturyTableView();
        incializujFilterComboBox();
    }

    private void inicializujFakturyTableView() {
        idFakturyTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        pouzivatelTableColumn.setCellValueFactory(cellData -> cellData.getValue().accountIdProperty());
        sumaTableColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        datumNakupuTableColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseDateProperty());

        pouzivatelTableColumn.setCellFactory(column -> {
            return new TableCell<Bill, Number>() {
                @Override
                protected void updateItem(Number accountId, boolean empty) {
                    super.updateItem(accountId, empty);
                    if (!empty) {
                        Account account = EntityManagerFactory.INSTANCE.getAccountManager().findById(accountId.longValue());
                        setText(account.getUsername());
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
                // TODO
                obnovFakturyTableView(1);
            }
        });
    }

    private void obnovFakturyTableView() {
        naplnFaktury();
        fakturyTableView.getItems().clear();
        fakturyTableView.setItems(fakturaModely);
    }

    private void obnovFakturyTableView(int daysCount) {
        naplnFaktury(daysCount);
        fakturyTableView.setItems(fakturaModely);
    }

    private void naplnFaktury() {
        List<Bill> faktury = billDao.getAll();
        fakturaModely = FXCollections.observableArrayList(faktury);
    }

    private void naplnFaktury(int obdobie) {
        List<Bill> fakturyZaObdobie = billDao.getBillsForLastDays(obdobie);
        fakturaModely = FXCollections.observableArrayList(fakturyZaObdobie);
    }

    private void obnovTovarFakturyTableView(Bill faktura) {
        naplnTovarFaktury(faktura);
        tovarFakturyTableView.setItems(tovarModely);
    }

    private void naplnTovarFaktury(Bill faktura) {
        List<Product> tovarFaktury = billDao.getBillProducts(faktura);
        tovarModely = FXCollections.observableArrayList(tovarFaktury);
    }


    @FXML
    public void onZobrazTovarFakturyButtonClicked() {
        if (fakturyTableView.getSelectionModel().isEmpty()) {
            GuiUtils.showWarning("Vyberte faktúru v tabuľke.");
            return;
        }

        Bill faktura = fakturyTableView.getSelectionModel().getSelectedItem();
        obnovTovarFakturyTableView(faktura);
    }
}
