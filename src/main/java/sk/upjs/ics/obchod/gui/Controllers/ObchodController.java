package sk.upjs.ics.obchod.gui.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlKategoriaDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlZnackaDao;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultKosikManager;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.services.KosikManager;

public class ObchodController implements Initializable {

    private MysqlKategoriaDao mysqlKategoriadao;

    private MysqlZnackaDao mysqlZnackaDao;

    private DefaultPouzivatelManager pouzivatelManager;

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
    private Button odhlasitButton;

    @FXML
    private Button kosikButton;

    @FXML
    private Pagination tovarPagination;

    @FXML
    private BorderPane obchodBorderPane;

    private Stage mainStage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = DefaultPouzivatelManager.INSTANCE;
        kosikManager = new DefaultKosikManager();
        mysqlKategoriadao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlZnackaDao();

        List<Kategoria> kategorie = mysqlKategoriadao.dajKategorie();
        List<Znacka> znacky = mysqlZnackaDao.dajZnacky();

        BooleanProperty jePouzivatelPrihlaseny = pouzivatelManager.isPrihlaseny();

        jePouzivatelPrihlaseny.addListener(e -> {
            zmenButtony();
        });

        kategorie.stream().forEach((k) -> {
            kategorieComboBox.getItems().add(k.getNazov());
        });

        znacky.stream().forEach((z) -> {
            znackyComboBox.getItems().add(z.getNazov());
        });
    }
    
    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void onPrihlasitButtonClicked() throws IOException {
        Scene prihlasenieScene = ViewFactory.INSTANCE.getPrihlasenieScene(mainStage);
        mainStage.setScene(prihlasenieScene);
    }

    public void zmenButtony() {
        prihlasitButton.setDisable(true);
        prihlasitButton.setVisible(false);

        registrovatButton.setDisable(true);
        registrovatButton.setVisible(false);

        kosikButton.setDisable(false);
        kosikButton.setVisible(true);

        odhlasitButton.setDisable(false);
        odhlasitButton.setVisible(true);
    }
}
