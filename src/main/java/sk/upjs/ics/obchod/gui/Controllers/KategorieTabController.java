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
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.managers.DefaultKategoriaManager;
import sk.upjs.ics.obchod.managers.KategoriaManager;

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

    private KategoriaManager defaultKategoriaManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultKategoriaManager = new DefaultKategoriaManager(false);

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
        List<Kategoria> znacky = defaultKategoriaManager.dajKategorie();
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
            ukazUpozornenie("Vyberte kategóriu v tabuľke!");
            return;
        }

        Kategoria oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();

        if (defaultKategoriaManager.existujeTovarVKategorii(oznacenaKategoria)) {
            ukazUpozornenie("Kategóriu nie je možné odstrániť. Existuje tovar v danej kategórii.");
            return;
        }

        defaultKategoriaManager.odstranKategoriu(oznacenaKategoria);
        kategoriaModely.remove(oznacenaKategoria);
        obnovKategorieTableView();
    }

    @FXML
    public void onUpravitKategoriuButtonClicked() {
        pridatUpravitKategoriuPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);

        if (kategorieTableView.getSelectionModel().isEmpty()) {
            ukazUpozornenie("Vyberte kategóriu v tabuľke!");
            return;
        }

        Kategoria oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();

        nazovTextField.setText(oznacenaKategoria.getNazov());
    }

    @FXML
    public void onUpravitButtonClicked() {
        Kategoria oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();
        String novyNazov = nazovTextField.getText();

        if (defaultKategoriaManager.existujeKategoriaSNazvom(novyNazov)) {
            ukazUpozornenie("Kategória s daným názvom už existuje!");
        } else if (novyNazov == null || novyNazov.trim().isEmpty()) {
            ukazUpozornenie("Zadajte názov kategórie");
        } else {
            defaultKategoriaManager.upravKategoriu(oznacenaKategoria, novyNazov);
        }
    }

    @FXML
    public void onPridatButtonClicked() {
        String nazovKategorie = nazovTextField.getText();

        if (defaultKategoriaManager.existujeKategoriaSNazvom(nazovKategorie)) {
            ukazUpozornenie("Kategória s daným názvom už existuje!");
        } else if (nazovKategorie == null || nazovKategorie.trim().isEmpty()) {
            ukazUpozornenie("Zadajte názov kategórie");
        } else {
            Kategoria novaKategoria = new Kategoria();
            novaKategoria.setNazov(nazovKategorie);

            Long idKategorie = defaultKategoriaManager.pridajKategoriu(novaKategoria);
            novaKategoria.setId(idKategorie);

            kategoriaModely.add(novaKategoria);
            obnovKategorieTableView();
        }

        nazovTextField.clear();
    }

    private void ukazUpozornenie(String hlavička) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText(hlavička);
        alert.showAndWait();
    }
}
