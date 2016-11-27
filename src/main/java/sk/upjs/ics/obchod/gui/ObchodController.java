package sk.upjs.ics.obchod.gui;

import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlKategoriaDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlZnackaDao;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Znacka;
import sk.upjs.ics.obchod.services.DefaultKosikManager;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.services.KosikManager;
import sk.upjs.ics.obchod.services.PouzivatelManager;

public class ObchodController {

    private MysqlKategoriaDao mysqlKategoriadao;
    
    private MysqlZnackaDao mysqlZnackaDao;
    
    private PouzivatelManager pouzivatelManager;
    
    private KosikManager kosikManager;

    @FXML
    private ComboBox<String> kategorieComboBox;

    @FXML
    private ComboBox<String> znackyComboBox;

    @FXML
    private Button prihlasitButton;

    @FXML
    private Button registrovatButton;

    @FXML
    private Pagination tovarPagination;
    
    @FXML
    private BorderPane obchodBorderPane;
    
    private Pane loginPane;

    public void initialize() {
        pouzivatelManager =  DefaultPouzivatelManager.INSTANCE;
        kosikManager = new DefaultKosikManager();
        mysqlKategoriadao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlZnackaDao();
        
        List<Kategoria> kategorie = mysqlKategoriadao.dajKategorie();
        List<Znacka> znacky = mysqlZnackaDao.dajZnacky();
        
        kategorie.stream().forEach((k) -> {
            kategorieComboBox.getItems().add(k.getNazov());
        });
        
        znacky.stream().forEach((z) -> {
            znackyComboBox.getItems().add(z.getNazov());
        });
    }
    
    @FXML
    public void onPrihlasitButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginFXML.fxml"));
        Parent root = loader.load();
        obchodBorderPane.setCenter(root);      
    }
    

}
