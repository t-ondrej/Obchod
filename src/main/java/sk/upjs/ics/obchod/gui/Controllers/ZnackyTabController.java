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
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlZnackaDao;
import sk.upjs.ics.obchod.entity.Znacka;

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

    private MysqlZnackaDao mysqlZnackaDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlZnackaDao();
            
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
        List<Znacka> znacky = mysqlZnackaDao.dajZnacky();
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
            ukazVyberZnackuUpozornenie();
            return;
        }

        Znacka oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();
        System.out.println(oznacenaZnacka.getNazov());
        
        //  mysqlZnackaDao.odstranZnacku(oznacenaZnacka);
        znackaModely.remove(oznacenaZnacka);
        obnovZnackyTableView();
    }

    @FXML
    public void onUpravitZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);

        if (znackyTableView.getSelectionModel().isEmpty()) {
            ukazVyberZnackuUpozornenie();
            return;
        }

        Znacka oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();

        nazovTextField.setText(oznacenaZnacka.getNazov());
    }

    @FXML
    public void onUpravitButtonClicked() {
        Znacka oznacenaZnacka = znackyTableView.getSelectionModel().getSelectedItem();
        String novyNazov = nazovTextField.getText();
        
        oznacenaZnacka.setNazov(novyNazov);
        mysqlZnackaDao.uloz(oznacenaZnacka);
        obnovZnackyTableView();
        
        nazovTextField.clear();
    }

    @FXML
    public void onPridatButtonClicked() {
        Znacka novaZnacka = new Znacka();
        String nazov = nazovTextField.getText();
        novaZnacka.setNazov(nazov);
        
        Long idZnacky = mysqlZnackaDao.uloz(novaZnacka);
        novaZnacka.setId(idZnacky);
        
        znackaModely.add(novaZnacka);     
        obnovZnackyTableView();
        
        nazovTextField.clear();
    }

    private void ukazVyberZnackuUpozornenie() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText("Vyberte znacku v tabuľke.");
        alert.showAndWait();
    }

}
