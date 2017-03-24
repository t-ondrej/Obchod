package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlCategoryDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlBrandDao;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.utils.StringUtilities;

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
    private TableView<Product> tovarTableView;

    @FXML
    private Label upozornenieLabel;

    /* 
     * Stlpce tabulky tovar 
     */
    @FXML
    private TableColumn<Product, Number> idTableColumn;

    @FXML
    private TableColumn<Product, String> nazovTableColumn;

    @FXML
    private TableColumn<Product, String> kategoriaTableColumn;

    @FXML
    private TableColumn<Product, String> znackaTableColumn;

    @FXML
    private TableColumn<Product, Number> cenaTableColumn;

    @FXML
    private TableColumn<Product, String> urlObrazkaTableColumn;

    @FXML
    private TableColumn<Product, Number> pocetKusovTableColumn;

    @FXML
    private ComboBox<Category> kategorieComboBox;

    @FXML
    private ComboBox<Brand> znackyComboBox;

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

    private ObservableList<Product> tovarModely;

    private MysqlProductDao mysqlProductDao;

    private MysqlCategoryDao mysqlCategoryDao;

    private MysqlBrandDao mysqlBrandDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mysqlProductDao = DaoFactory.INSTANCE.getMysqlProductDao();
        mysqlCategoryDao = DaoFactory.INSTANCE.getMysqlCategoryDao();
        mysqlBrandDao = DaoFactory.INSTANCE.getMysqlBrandDao();

        inicializujTovarTableView();
        inicializujKategorieComboBox();
        inicializujZnackyComboBox();
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

        Product oznacenyTovar = tovarTableView.getSelectionModel().getSelectedItem();

        mysqlProductDao.delete(oznacenyTovar);
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

        Product tovar = tovarTableView.getSelectionModel().getSelectedItem();

        kategorieComboBox.setValue(tovar.getCategory());
        znackyComboBox.setValue(tovar.getBrand());

        nazovTextField.setText(tovar.getName());
        cenaTextField.setText(Integer.toString(tovar.getPrice()));
        obrazokUrlTextField.setText(tovar.getImagePath());
        pocetKusovTextField.setText(Integer.toString(tovar.getQuantity()));
        popisTovaruTextArea.setText(tovar.getDescription());
    }

    @FXML
    public void onPridatButtonClicked() {

        if (kategorieComboBox.getSelectionModel().getSelectedItem() == null) {
            upozornenieLabel.setText("Vyberte kategóriu!");
            ukazUpozornenieLabel();
        } else if (znackyComboBox.getSelectionModel().getSelectedItem() == null) {
            upozornenieLabel.setText("Vyberte značku!");
            ukazUpozornenieLabel();
        } else if (!StringUtilities.isNumber(cenaTextField.getText())) {
            cenaTextField.getStyleClass().add("chyba");
            if (!StringUtilities.isNumber(pocetKusovTextField.getText())) {
                pocetKusovTextField.getStyleClass().add("chyba");
            }
        } else if (!StringUtilities.isNumber(pocetKusovTextField.getText())) {
            pocetKusovTextField.getStyleClass().add("chyba");
        } else {
            Product tovar = new Product();
            tovar.setCategory(kategorieComboBox.getSelectionModel().getSelectedItem());
            tovar.setBrand(znackyComboBox.getSelectionModel().getSelectedItem());
            tovar.setName(nazovTextField.getText());
            tovar.setPrice(Integer.parseInt(cenaTextField.getText()));
            tovar.setImagePath(obrazokUrlTextField.getText());
            tovar.setQuantity(Integer.parseInt(pocetKusovTextField.getText()));
            tovar.setDescription(popisTovaruTextArea.getText());

            tovarModely.add(tovar);
            Long idTovaru = mysqlProductDao.saveOrUpdate(tovar);
            tovar.setId(idTovaru);

            obnovTovarTableView();
            vymazTextFieldy();

            cenaTextField.getStyleClass().remove("chyba");
            pocetKusovTextField.getStyleClass().remove("chyba");
        }
    }

    @FXML
    public void onUpravitButtonClicked() {
        Product tovar = tovarTableView.getSelectionModel().getSelectedItem();

        if (!StringUtilities.isNumber(cenaTextField.getText())) {
            cenaTextField.getStyleClass().add("chyba");
            if (!StringUtilities.isNumber(pocetKusovTextField.getText())) {
                pocetKusovTextField.getStyleClass().add("chyba");
            }
        } else if (!StringUtilities.isNumber(pocetKusovTextField.getText())) {
            pocetKusovTextField.getStyleClass().add("chyba");
        } else {

            tovar.setCategory(kategorieComboBox.getSelectionModel().getSelectedItem());
            tovar.setBrand(znackyComboBox.getSelectionModel().getSelectedItem());
            tovar.setName(nazovTextField.getText());
            tovar.setPrice(Integer.parseInt(cenaTextField.getText()));
            tovar.setImagePath(obrazokUrlTextField.getText());
            tovar.setQuantity(Integer.parseInt(pocetKusovTextField.getText()));
            tovar.setDescription(popisTovaruTextArea.getText());

            mysqlProductDao.saveOrUpdate(tovar);
            
            obnovTovarTableView();
            vymazTextFieldy();
            
            cenaTextField.getStyleClass().remove("chyba");
            pocetKusovTextField.getStyleClass().remove("chyba");
        }
    }

    private void inicializujTovarTableView() {
        naplnTovarModely();

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        kategoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCategory().nameProperty());
        znackaTableColumn.setCellValueFactory(cellData -> cellData.getValue().getBrand().nameProperty());
        cenaTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        urlObrazkaTableColumn.setCellValueFactory(cellData -> cellData.getValue().imagePathProperty());
        pocetKusovTableColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        tovarTableView.setItems(tovarModely);
    }

    private void naplnTovarModely() {
        List<Product> tovar = DaoFactory.INSTANCE.getMysqlProductDao().getAll();
        tovarModely = FXCollections.observableArrayList(tovar);
    }

    private void inicializujKategorieComboBox() {
        ObservableList<Category> kategorie = FXCollections.observableArrayList(mysqlCategoryDao.getAll());

        kategorieComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category k) {
                return k.getName();
            }

            @Override
            public Category fromString(String nazovKategorie) {
                return mysqlCategoryDao.findByName(nazovKategorie);
            }

        });

        kategorieComboBox.getItems().clear();
        kategorieComboBox.getItems().addAll(kategorie);
    }

    private void inicializujZnackyComboBox() {
        ObservableList<Brand> znacky = FXCollections.observableArrayList(mysqlBrandDao.getAll());

        znackyComboBox.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand z) {
                return z.getName();
            }

            @Override
            public Brand fromString(String nazovZnacky) {
                return mysqlBrandDao.findByName(nazovZnacky);
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

    private boolean suVsetkyTextFieldyVyplnene() {
        return !(nazovTextField.getText() == null || nazovTextField.getText().trim().isEmpty()
                || obrazokUrlTextField.getText() == null || obrazokUrlTextField.getText().trim().isEmpty()
                || popisTovaruTextArea.getText() == null || popisTovaruTextArea.getText().trim().isEmpty());
    }

    private void ukazVyberTovarUpozornenie() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText("Vyberte tovar v tabuľke.");
        alert.showAndWait();
    }

    private void ukazUpozornenieLabel() {
        SequentialTransition st = new SequentialTransition();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), upozornenieLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1.0);
        st.getChildren().add(fadeIn);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), upozornenieLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);
        st.getChildren().add(fadeOut);

        st.playFromStart();
    }
}
