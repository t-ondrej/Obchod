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
import sk.upjs.ics.obchod.dao.mysql.MysqlKategoriaDao;
import sk.upjs.ics.obchod.entity.Kategoria;

public class KategorieTabController implements Initializable {
    
    @FXML
    private Button pridatKategoriuButton;
    
    @FXML
    private Button odstranitKategoriuButton;
    
    @FXML
    private Button upravitKategoriuButton;
    
    @FXML
    private Button pridatButton;

    @FXML
    private Button upravitButton;
    
    @FXML
    private Pane pridatUpravitKategoriuPane; 
    
    @FXML
    private TextField nazovTextField;
    
    @FXML
    private TableView<Kategoria> kategorieTableView;
    
    @FXML
    private TableColumn<Kategoria, Number> idTableColumn;
    
    @FXML
    private TableColumn<Kategoria, String> nazovTableColumn;
    
    private ObservableList<Kategoria> kategoriaModely;
    
    private MysqlKategoriaDao mysqlKategoriaDao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mysqlKategoriaDao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        
        inicializujKategorieTableView();
    }
    
   private void inicializujKategorieTableView() {
        naplnKategoriaModely();

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nazovProperty());

        obnovKategorieTableView();
    }

    private void obnovKategorieTableView() {
        kategorieTableView.setItems(kategoriaModely);
    }

    private void naplnKategoriaModely() {
        List<Kategoria> znacky = mysqlKategoriaDao.dajKategorie();
        kategoriaModely = FXCollections.observableArrayList(znacky);
    }

    @FXML
    public void onPridatKategoriuButtonClicked() {
        pridatUpravitKategoriuPane.setVisible(true);
        upravitButton.setVisible(false);
        pridatButton.setVisible(true);
    }

    @FXML
    public void onOdstranitKategoriuButtonClicked() {
        pridatUpravitKategoriuPane.setVisible(false);
        
        if (kategorieTableView.getSelectionModel().isEmpty()) {
            ukazVyberKategoriuUpozornenie();
            return;
        }

        Kategoria oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();
        System.out.println(oznacenaKategoria.getNazov());
        
        // mysqlKategoriaDao.odstranKategoriu(oznacenaKategoria);
        kategoriaModely.remove(oznacenaKategoria);
        obnovKategorieTableView();
    }

    @FXML
    public void onUpravitKategoriuButtonClicked() {
        pridatUpravitKategoriuPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);

        if (kategorieTableView.getSelectionModel().isEmpty()) {
            ukazVyberKategoriuUpozornenie();
            return;
        }

        Kategoria oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();

        nazovTextField.setText(oznacenaKategoria.getNazov());
    }

    @FXML
    public void onUpravitButtonClicked() {
        Kategoria oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();
        String novyNazov = nazovTextField.getText();
        
        oznacenaKategoria.setNazov(novyNazov);
        mysqlKategoriaDao.uloz(oznacenaKategoria);
        obnovKategorieTableView();
        
        nazovTextField.clear();
    }

    @FXML
    public void onPridatButtonClicked() {
        Kategoria novaKategoria = new Kategoria();
        String nazov = nazovTextField.getText();
        novaKategoria.setNazov(nazov);
        
        Long idZnacky = mysqlKategoriaDao.uloz(novaKategoria);
        novaKategoria.setId(idZnacky);
        
        kategoriaModely.add(novaKategoria);     
        obnovKategorieTableView();
        
        nazovTextField.clear();
    }

    private void ukazVyberKategoriuUpozornenie() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText("Vyberte znacku v tabuľke.");
        alert.showAndWait();
    }

}
