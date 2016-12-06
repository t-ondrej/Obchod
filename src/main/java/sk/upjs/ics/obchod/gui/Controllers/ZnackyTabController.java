package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlZnackaDao;
import sk.upjs.ics.obchod.entity.Tovar;
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
        
        znackyTableView.setItems(znackaModely);
    }
    
    private void obnovZnackyTableView() {
        znackyTableView.getItems().clear();
        znackyTableView.getItems().addAll(znackaModely);
    }
    
    private void naplnZnackaModely() {
        List<Znacka> znacky = mysqlZnackaDao.dajZnacky();
        znackaModely = FXCollections.observableArrayList(znacky);
    }
    
    @FXML
    public void onPridatZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(true);
    }
    
    @FXML
    public void onOdstranitZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(false);
        upravitButton.setVisible(false);
        pridatButton.setVisible(true);        
    }
    
    @FXML
    public void onUpravitZnackuButtonClicked() {
        pridatUpravitZnackuPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);
    }
    
    @FXML
    public void onOdstranitButtonClicked() {
        
    }
    
    @FXML
    public void onUpravitButtonClicked() {
        
    }
    
    @FXML
    public void onPridatButtonClicked() {
        
    }

    
}
