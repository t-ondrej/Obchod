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
import sk.upjs.ics.obchod.entity.Znacka;
import sk.upjs.ics.obchod.services.DefaultZnackaManager;
import sk.upjs.ics.obchod.services.ZnackaManager;

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
    private TableView<Znacka> znackyTableView;

    @FXML
    private TableColumn<Znacka, Number> idTableColumn;

    @FXML
    private TableColumn<Znacka, String> nazovTableColumn;

    private ObservableList<Znacka> znackaModely;

    private ZnackaManager defaultZnackaManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultZnackaManager = new DefaultZnackaManager(false);

        inicializujZnackyTableView();
    }

    private void inicializujZnackyTableView() {
        naplnZnackaModely();

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nazovProperty());

        obnovZnackyTableView();
    }

    private void obnovZnackyTableView() {
        znackyTableView.setItems(znackaModely);
    }

    private void naplnZnackaModely() {
        List<Znacka> znacky = defaultZnackaManager.dajZnacky();
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
        
        Znacka oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();
        
        if (defaultZnackaManager.existujeTovarSoZnackou(oznacenaZnacka)) {
            ukazUpozornenie("Značku nie je možné odstrániť. Existuje tovar s danou značkou.");
            return;
        }
   
        defaultZnackaManager.odstranZnacku(oznacenaZnacka);
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

        Znacka oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();

        nazovTextField.setText(oznacenaZnacka.getNazov());
    }

    @FXML
    public void onUpravitButtonClicked() {
        Znacka oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();
        String novyNazov = nazovTextField.getText();

        if (defaultZnackaManager.existujeZnackaSNazvom(novyNazov)) {
            ukazUpozornenie("Značka s daným názvom už existuje!");
        } else {
            defaultZnackaManager.upravZnacku(oznacenaZnacka, novyNazov);
        }
    }

    @FXML
    public void onPridatButtonClicked() {
        String nazov = nazovTextField.getText();

        if (defaultZnackaManager.existujeZnackaSNazvom(nazov)) {
            ukazUpozornenie("Značka s daným názvom už existuje!");
        } else {
            Znacka novaZnacka = new Znacka();
            novaZnacka.setNazov(nazov);
            
            Long idZnacky = defaultZnackaManager.pridajZnacku(novaZnacka);
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
