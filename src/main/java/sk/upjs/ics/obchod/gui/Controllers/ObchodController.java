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
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.dao.ICategoryDao;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.IBrandDao;
import sk.upjs.ics.obchod.managers.ICartManager;
import sk.upjs.ics.obchod.managers.IUserManager;

public class ObchodController implements Initializable {

    private ICategoryDao mysqlKategoriaDao;

    private IBrandDao mysqlZnackaDao;

    private IProductDao mysqlTovarDao;

    private IUserManager pouzivatelManager;

    private ICartManager kosikManager;

    @FXML
    private ComboBox<Category> kategorieComboBox;

    @FXML
    private ComboBox<Brand> znackyComboBox;

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

    private ObservableList<Product> tovar;

    private int pocetStranok;

    private Stage mainStage;

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = EntityManagerFactory.INSTANCE.getUserManager();
        kosikManager = EntityManagerFactory.INSTANCE.getCartManager();
        mysqlKategoriaDao = DaoFactory.INSTANCE.getMysqlCategoryDao();
        mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlBrandDao();

        BooleanProperty jePouzivatelPrihlaseny = pouzivatelManager.isSignedIn();

        jePouzivatelPrihlaseny.addListener(e -> {
            zmenButtony();
        });

        inicializujTovarPagination();

        inicializujKategoriaComboBox();
        inicializujZnackyComboBox();

        incializujProfilComboBox();
    }

    private void inicializujTovarPagination() {
        mysqlTovarDao = DaoFactory.INSTANCE.getMysqlProductDao();
        tovar = FXCollections.observableArrayList(mysqlTovarDao.getAll());
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
                Product kusTovaru = tovar.get(i);

                Image obrazok = new Image("file:" + kusTovaru.getImagePath());
                l.setImage(obrazok);

                Label nazovTovaru = new Label(kusTovaru.getName());

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
        ObservableList<Category> kategorie = FXCollections.observableArrayList(mysqlKategoriaDao.getAll());

        kategorieComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category k) {
                return k.getName();
            }

            @Override
            public Category fromString(String nazovKategorie) {
                return mysqlKategoriaDao.findByName(nazovKategorie);
            }

        });

        kategorieComboBox.setItems(kategorie);

        kategorieComboBox.valueProperty().addListener(event -> {
            Category kategoria = kategorieComboBox.getSelectionModel().getSelectedItem();
            List<Product> tovarPodlaKategorie = mysqlTovarDao.findProductsByCategory(kategoria);
            System.out.println(tovarPodlaKategorie.size());
            obnovTovar(tovarPodlaKategorie);
            zobrazitVsetkoLabel.setVisible(true);
        });
    }

    private void inicializujZnackyComboBox() {
        ObservableList<Brand> znacky = FXCollections.observableArrayList(mysqlZnackaDao.getAll());

        znackyComboBox.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand z) {
                return z.getName();
            }

            @Override
            public Brand fromString(String nazovZnacky) {
                return mysqlZnackaDao.findByName(nazovZnacky);
            }

        });

        znackyComboBox.setItems(znacky);

        znackyComboBox.valueProperty().addListener(event -> {
            Brand znacka = znackyComboBox.getSelectionModel().getSelectedItem();
            List<Product> tovarPodlaZnacky = mysqlTovarDao.findProductsByBrand(znacka);
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
                kosikManager.clearCart();
                pouzivatelManager.logOutUser();
            }

        });
    }

    public void obnovTovar(List<Product> filtovanyTovar) {
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
        List<Product> vsetokTovar = mysqlTovarDao.getAll();
        obnovTovar(vsetokTovar);
    }

    private void zmenButtony() {
        boolean jePouzivatelPrihlaseny = pouzivatelManager.isSignedIn().get();
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
