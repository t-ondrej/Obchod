package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.BrandManager;
import sk.upjs.ics.obchod.managers.IBrandManager;

public class ZnackyTabController implements Initializable {

    @FXML
    private Button pridatZnackuButton;

    @FXML
    private Button odstranitZnackuButton;

    @FXML
    private Button upravitZnackuButton;

    @FXML
    private Button pridatButton;

    @FXML
    private Button upravitButton;

    @FXML
    private Pane pridatUpravitZnackuPane;

    @FXML
    private TextField nazovTextField;

    @FXML
    private TableView<Brand> znackyTableView;

    @FXML
    private TableColumn<Brand, Number> idTableColumn;

    @FXML
    private TableColumn<Brand, String> nazovTableColumn;

    private ObservableList<Brand> znackaModely;

    private IBrandManager defaultZnackaManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultZnackaManager = EntityManagerFactory.INSTANCE.getBrandManager();

        inicializujZnackyTableView();
    }

    private void inicializujZnackyTableView() {
        naplnZnackaModely();

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        obnovZnackyTableView();
    }

    private void obnovZnackyTableView() {
        znackyTableView.setItems(znackaModely);
    }

    private void naplnZnackaModely() {
        List<Brand> znacky = defaultZnackaManager.getBrands();
        znackaModely = FXCollections.observableArrayList(znacky);
    }

    @FXML
    public void onPridatZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(true);
        upravitButton.setVisible(false);
        pridatButton.setVisible(true);
    }

    @FXML
    public void onOdstranitZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(false);

        if (znackyTableView.getSelectionModel().isEmpty()) {
            ukazUpozornenie("Vyberte značku v tabuľke!");
            return;
        }

        Brand oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();

        if (defaultZnackaManager.productOfBrandExists(oznacenaZnacka)) {
            ukazUpozornenie("Značku nie je možné odstrániť. Existuje tovar s danou značkou.");
            return;
        }

        defaultZnackaManager.remove(oznacenaZnacka);
        znackaModely.remove(oznacenaZnacka);
        obnovZnackyTableView();
    }

    @FXML
    public void onUpravitZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);

        if (znackyTableView.getSelectionModel().isEmpty()) {
            ukazUpozornenie("Vyberte značku v tabuľke!");
            return;
        }

        Brand oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();

        nazovTextField.setText(oznacenaZnacka.getName());
    }

    @FXML
    public void onUpravitButtonClicked() {
        Brand oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();
        String novyNazov = nazovTextField.getText();

        if (defaultZnackaManager.brandExists(novyNazov)) {
            ukazUpozornenie("Značka s daným názvom už existuje!");
        } else if (novyNazov == null || novyNazov.trim().isEmpty()) {
            ukazUpozornenie("Zadajte názov značky");
        } else {
            defaultZnackaManager.update(oznacenaZnacka, novyNazov);
        }
    }

    @FXML
    public void onPridatButtonClicked() {
        String nazov = nazovTextField.getText();

        if (defaultZnackaManager.brandExists(nazov)) {
            ukazUpozornenie("Značka s daným názvom už existuje!");
        } else if (nazov == null || nazov.trim().isEmpty()) {
            ukazUpozornenie("Zadajte názov značky");
        } else {
            Brand novaZnacka = new Brand();
            novaZnacka.setName(nazov);

            Long idZnacky = defaultZnackaManager.save(novaZnacka);
            novaZnacka.setId(idZnacky);

            znackaModely.add(novaZnacka);
            obnovZnackyTableView();
        }

        nazovTextField.clear();
    }

    private void ukazUpozornenie(String hlavicka) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText(hlavicka);
        alert.showAndWait();
    }
}
