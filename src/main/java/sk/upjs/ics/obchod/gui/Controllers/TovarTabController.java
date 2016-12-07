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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlKategoriaDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlTovarDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlZnackaDao;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class TovarTabController implements Initializable {

    @FXML
    private Button pridatTovarButton;

    @FXML
    private Button pridatButton;

    @FXML
    private Button upravitButton;

    @FXML
    private Button odstranitTovarButton;

    @FXML
    private Button upravitTovarButton;

    @FXML
    private Pane pridatUpravitTovarPane;

    @FXML
    private TableView<Tovar> tovarTableView;

    @FXML
    private TableColumn<Tovar, Number> idTableColumn;

    @FXML
    private TableColumn<Tovar, String> nazovTableColumn;

    @FXML
    private TableColumn<Tovar, String> kategoriaTableColumn;

    @FXML
    private TableColumn<Tovar, String> znackaTableColumn;

    @FXML
    private TableColumn<Tovar, Number> cenaTableColumn;

    @FXML
    private TableColumn<Tovar, String> popisTableColumn;

    @FXML
    private TableColumn<Tovar, String> urlObrazkaTableColumn;

    @FXML
    private TableColumn<Tovar, Number> pocetKusovTableColumn;

    @FXML
    private ComboBox<Kategoria> kategorieComboBox;

    @FXML
    private ComboBox<Znacka> znackyComboBox;

    @FXML
    private TextField nazovTextField;

    @FXML
    private TextField cenaTextField;

    @FXML
    private TextField obrazokUrlTextField;

    @FXML
    private TextField pocetKusovTextField;

    @FXML
    private TextArea popisTovaruTextArea;

    @FXML
    private Label pridatTovarLabel;

    @FXML
    private Label upravitTovarLabel;

    private ObservableList<Tovar> tovarModely;

    private MysqlTovarDao mysqlTovarDao;

    private MysqlKategoriaDao mysqlKategoriaDao;

    private MysqlZnackaDao mysqlZnackaDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mysqlTovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        mysqlKategoriaDao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlZnackaDao();

        inicializujTovarTableView();
    }

    @FXML
    public void onPridatTovarButtonClicked() {
        pridatUpravitTovarPane.setVisible(true);

        inicializujKategorieComboBox();
        inicializujZnackyComboBox();

        upravitButton.setVisible(false);
        pridatButton.setVisible(true);

        upravitTovarLabel.setVisible(false);
        pridatTovarLabel.setVisible(true);
    }

    @FXML
    public void onOdstranitTovarButtonClicked() {
        pridatUpravitTovarPane.setVisible(false);

        if (tovarTableView.getSelectionModel().isEmpty()) {
            ukazVyberTovarUpozornenie();
            return;
        }

        Tovar oznacenyTovar = tovarTableView.getSelectionModel().getSelectedItem();

        mysqlTovarDao.odstranTovar(oznacenyTovar);
        tovarModely.remove(oznacenyTovar);
        obnovTovarTableView();
    }

    @FXML
    public void onUpravitTovarButtonClicked() {
        pridatUpravitTovarPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);

        pridatTovarLabel.setVisible(false);
        upravitTovarLabel.setVisible(true);

        if (tovarTableView.getSelectionModel().isEmpty()) {
            ukazVyberTovarUpozornenie();
            return;
        }

        Tovar tovar = tovarTableView.getSelectionModel().getSelectedItem();

        kategorieComboBox.getSelectionModel().select((tovar.getIdKategoria().intValue()));
        znackyComboBox.getSelectionModel().select(tovar.getIdZnacka().intValue());

        nazovTextField.setText(tovar.getNazov());
        cenaTextField.setText(Integer.toString(tovar.getCena()));
        obrazokUrlTextField.setText(tovar.getObrazokUrl());
        pocetKusovTextField.setText(Integer.toString(tovar.getPocetKusov()));
        popisTovaruTextArea.setText(tovar.getPopis());
    }

    @FXML
    public void onPridatButtonClicked() {
        Tovar tovar = new Tovar();

        tovar.setIdKategoria(kategorieComboBox.getSelectionModel().getSelectedItem().getId());
        tovar.setIdZnacka(znackyComboBox.getSelectionModel().getSelectedItem().getId());
        tovar.setNazov(nazovTextField.getText());
        tovar.setCena(Integer.parseInt(cenaTextField.getText()));
        tovar.setObrazokUrl(obrazokUrlTextField.getText());
        tovar.setPocetKusov(Integer.parseInt(pocetKusovTextField.getText()));
        tovar.setPopis(popisTovaruTextArea.getText());

        tovarModely.add(tovar);
        Long idTovaru = mysqlTovarDao.ulozTovar(tovar);
        tovar.setId(idTovaru);

        obnovTovarTableView();
        vymazTextFieldy();
    }

    @FXML
    public void onUpravitButtonClicked() {
        Tovar Tovar = tovarTableView.getSelectionModel().getSelectedItem();

        Tovar.setIdKategoria(kategorieComboBox.getSelectionModel().getSelectedItem().getId());
        Tovar.setIdZnacka(znackyComboBox.getSelectionModel().getSelectedItem().getId());
        Tovar.setNazov(nazovTextField.getText());
        Tovar.setCena(Integer.parseInt(cenaTextField.getText()));
        Tovar.setObrazokUrl(obrazokUrlTextField.getText());
        Tovar.setPocetKusov(Integer.parseInt(pocetKusovTextField.getText()));
        Tovar.setPopis(popisTovaruTextArea.getText());

        mysqlTovarDao.ulozTovar(Tovar);
        obnovTovarTableView();
        vymazTextFieldy();
    }

    private void inicializujTovarTableView() {
        naplnTovarModely();

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nazovProperty());
        kategoriaTableColumn.setCellValueFactory(cellData -> mysqlKategoriaDao.najdiPodlaId(cellData.getValue().getIdKategoria()).nazovProperty());
        znackaTableColumn.setCellValueFactory(cellData -> mysqlZnackaDao.najdiPodlaId(cellData.getValue().getIdZnacka()).nazovProperty());
        cenaTableColumn.setCellValueFactory(cellData -> cellData.getValue().cenaProperty());
        popisTableColumn.setCellValueFactory(cellData -> cellData.getValue().popisProperty());
        urlObrazkaTableColumn.setCellValueFactory(cellData -> cellData.getValue().obrazokUrl());
        pocetKusovTableColumn.setCellValueFactory(cellData -> cellData.getValue().pocetKusovProperty());

        tovarTableView.setItems(tovarModely);
    }

    private void naplnTovarModely() {
        List<Tovar> tovar = DaoFactory.INSTANCE.getMysqlTovarDao().dajTovary();
        tovarModely = FXCollections.observableArrayList(tovar);
    }

    private void inicializujKategorieComboBox() {
        ObservableList<Kategoria> kategorie = FXCollections.observableArrayList(mysqlKategoriaDao.dajKategorie());

        kategorieComboBox.setConverter(new StringConverter<Kategoria>() {
            @Override
            public String toString(Kategoria k) {
                return k.getNazov();
            }

            @Override
            public Kategoria fromString(String nazovKategorie) {
                return mysqlKategoriaDao.najdiPodlaNazvu(nazovKategorie);
            }

        });

        kategorieComboBox.getItems().clear();
        kategorieComboBox.getItems().addAll(kategorie);
    }

    private void inicializujZnackyComboBox() {
        ObservableList<Znacka> znacky = FXCollections.observableArrayList(mysqlZnackaDao.dajZnacky());

        znackyComboBox.setConverter(new StringConverter<Znacka>() {
            @Override
            public String toString(Znacka z) {
                return z.getNazov();
            }

            @Override
            public Znacka fromString(String nazovZnacky) {
                return mysqlZnackaDao.najdiPodlaNazvu(nazovZnacky);
            }
        });

        znackyComboBox.getItems().clear();
        znackyComboBox.getItems().addAll(znacky);
    }

    private void obnovTovarTableView() {
        tovarTableView.setItems(tovarModely);
    }

    private void vymazTextFieldy() {
        nazovTextField.clear();
        cenaTextField.clear();
        obrazokUrlTextField.clear();
        pocetKusovTextField.clear();
        popisTovaruTextArea.clear();
    }

    private void ukazVyberTovarUpozornenie() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText("Vyberte tovar v tabuľke.");
        alert.showAndWait();
    }
}
