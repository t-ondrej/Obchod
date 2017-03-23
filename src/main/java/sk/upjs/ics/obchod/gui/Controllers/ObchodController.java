package sk.upjs.ics.obchod.gui.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.KosikManager;
import sk.upjs.ics.obchod.managers.PouzivatelManager;
import sk.upjs.ics.obchod.managers.IKosikManager;
import sk.upjs.ics.obchod.managers.IPouzivatelManager;
import sk.upjs.ics.obchod.dao.IZnackaDao;
import sk.upjs.ics.obchod.dao.ITovarDao;
import sk.upjs.ics.obchod.dao.IKategoriaDao;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;

public class ObchodController implements Initializable {

    private IKategoriaDao mysqlKategoriaDao;

    private IZnackaDao mysqlZnackaDao;

    private ITovarDao mysqlTovarDao;

    private IPouzivatelManager pouzivatelManager;

    private IKosikManager kosikManager;

    @FXML
    private ComboBox<Kategoria> kategorieComboBox;

    @FXML
    private ComboBox<Znacka> znackyComboBox;

    @FXML
    private ComboBox<String> profilComboBox;

    @FXML
    private Button prihlasitButton;

    @FXML
    private Button registrovatButton;

    @FXML
    private Button odhlasitButton;

    @FXML
    private Label zobrazitVsetkoLabel;
    
    @FXML
    private ImageView kosikImageView;

    @FXML
    private Pagination tovarPagination;

    private ObservableList<Tovar> tovar;

    private int pocetStranok;

    private Stage mainStage;

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = EntityManagerFactory.INSTANCE.getPouzivatelManager();
        kosikManager = EntityManagerFactory.INSTANCE.getKosikManager();
        mysqlKategoriaDao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlZnackaDao();

        BooleanProperty jePouzivatelPrihlaseny = pouzivatelManager.isPrihlaseny();

        jePouzivatelPrihlaseny.addListener(e -> {
            zmenButtony();
        });

        inicializujTovarPagination();

        inicializujKategoriaComboBox();
        inicializujZnackyComboBox();

        incializujProfilComboBox();
    }

    private void inicializujTovarPagination() {
        mysqlTovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        tovar = FXCollections.observableArrayList(mysqlTovarDao.dajTovar());
        pocetStranok = (tovar.size() / 8);
        tovarPagination.setPageCount(pocetStranok + 1);
        tovarPagination.setPageFactory((Integer pageIndex) -> vytvorStranku(pageIndex));
    }

    private GridPane vytvorStranku(int idxStrany) {
        GridPane grid = new GridPane();
        int startIdx = idxStrany * 8, pom = 0;

        for (int i = startIdx; i < startIdx + 8; i++) {
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setAlignment(Pos.CENTER);

            ImageView l = new ImageView();
            l.setPreserveRatio(true);
            l.setFitHeight(270);
            l.setFitWidth(270);

            vBox.getChildren().add(l);

            if (tovar.size() > i) {
                Tovar kusTovaru = tovar.get(i);

                Image obrazok = new Image("file:" + kusTovaru.getObrazokUrl());
                l.setImage(obrazok);

                Label nazovTovaru = new Label(kusTovaru.getNazov());

                l.setOnMouseClicked((event) -> {
                    prejdiNaSpecifikaciuTovaru(nazovTovaru.getText());
                });

                l.setCursor(Cursor.HAND);

                vBox.getChildren().add(nazovTovaru);
            }

            GridPane.setHgrow(vBox, Priority.ALWAYS);
            GridPane.setVgrow(vBox, Priority.ALWAYS);
            GridPane.setHalignment(vBox, HPos.CENTER);

            GridPane.setConstraints(vBox, pom % 4, pom / 4);
            grid.getChildren().add(vBox);

            pom++;
        }

        return grid;
    }

    private void inicializujKategoriaComboBox() {
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

        kategorieComboBox.setItems(kategorie);

        kategorieComboBox.valueProperty().addListener(event -> {
            Kategoria kategoria = kategorieComboBox.getSelectionModel().getSelectedItem();
            List<Tovar> tovarPodlaKategorie = mysqlTovarDao.dajTovarPodlaKategorie(kategoria);
            System.out.println(tovarPodlaKategorie.size());
            obnovTovar(tovarPodlaKategorie);
            zobrazitVsetkoLabel.setVisible(true);
        });
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

        znackyComboBox.setItems(znacky);

        znackyComboBox.valueProperty().addListener(event -> {
            Znacka znacka = znackyComboBox.getSelectionModel().getSelectedItem();
            List<Tovar> tovarPodlaZnacky = mysqlTovarDao.dajTovarPodlaZnacky(znacka);
            obnovTovar(tovarPodlaZnacky);
            zobrazitVsetkoLabel.setVisible(true);
        });
    }

    private void incializujProfilComboBox() {
        ObservableList<String> profil = FXCollections.observableArrayList(Arrays.asList("Upraviť", "Odhlásiť"));
        profilComboBox.setItems(profil);

        profilComboBox.valueProperty().addListener(event -> {
            int vybranyIdx = profilComboBox.getSelectionModel().getSelectedIndex();

            profilComboBox.getSelectionModel().clearSelection();

            if (vybranyIdx == 0) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfilPouzivatela.fxml"));
                    Scene profilPouzivatelaScene = new Scene(loader.load());
                    ProfilPouzivatelaController profilPouzivatelaController = loader.getController();
                    profilPouzivatelaController.setStage(mainStage);
                    mainStage.setScene(profilPouzivatelaScene);
                } catch (IOException ex) {
                    Logger.getLogger(ObchodController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (vybranyIdx == 1) {
                kosikManager.vyprazdniKosik();
                pouzivatelManager.odhlasPouzivatela();
            }

        });
    }

    public void obnovTovar(List<Tovar> filtovanyTovar) {
        tovar.removeAll(tovar);
        tovar.addAll(FXCollections.observableArrayList(filtovanyTovar));

        pocetStranok = (tovar.size() / 8);
        tovarPagination.setPageCount(pocetStranok + 1);
        tovarPagination.setPageFactory((Integer pageIndex) -> vytvorStranku(pageIndex));

        kategorieComboBox.getSelectionModel().clearSelection();
        znackyComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void onPrihlasitButtonClicked() throws IOException {
        Scene prihlasenieScene = ViewFactory.INSTANCE.getPrihlasenieScene(mainStage);
        mainStage.setScene(prihlasenieScene);
    }

    @FXML
    public void onRegistrovatButtonClicked() {
        Scene registraciaScene = ViewFactory.INSTANCE.getRegistraciaScene(mainStage);
        mainStage.setScene(registraciaScene);
    }

    @FXML
    public void onKosikImageViewClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Kosik.fxml"));
            Scene pokladnaScene = new Scene(loader.load());
            KosikController pokladnaController = loader.getController();
            pokladnaController.setStage(mainStage);
            mainStage.setScene(pokladnaScene);
        } catch (IOException ex) {
            Logger.getLogger(ObchodController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void onZobrazitVsetkoLabelClicked() {
        zobrazitVsetkoLabel.setVisible(false);
        List<Tovar> vsetokTovar = mysqlTovarDao.dajTovar();
        obnovTovar(vsetokTovar);
    }

    private void zmenButtony() {
        boolean jePouzivatelPrihlaseny = pouzivatelManager.isPrihlaseny().get();
        prihlasitButton.setVisible(!jePouzivatelPrihlaseny);
        registrovatButton.setVisible(!jePouzivatelPrihlaseny);

        kosikImageView.setVisible(jePouzivatelPrihlaseny);
        profilComboBox.setVisible(jePouzivatelPrihlaseny);
    }

    private void prejdiNaSpecifikaciuTovaru(String nazovTovaru) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SpecifikaciaTovaru.fxml"));
            Scene specifikaciaTovaruScene = new Scene(loader.load());
            SpecifikaciaTovaruController specifikaciaTovaruController = loader.getController();

            specifikaciaTovaruController.inicializuj(nazovTovaru);
            specifikaciaTovaruController.setStage(mainStage);

            mainStage.setScene(specifikaciaTovaruScene);
        } catch (IOException ex) {
           System.out.println("Nepodarilo sa načítať súbor SpecifikaciaTovaru.fxml");
        }
    }
}
