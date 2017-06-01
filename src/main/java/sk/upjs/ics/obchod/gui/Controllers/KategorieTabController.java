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
import sk.upjs.ics.obchod.dao.ICategoryDao;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.ICategoryManager;
import sk.upjs.ics.obchod.utils.GuiUtils;

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
    private TableView<Category> kategorieTableView;

    @FXML
    private TableColumn<Category, Number> idTableColumn;

    @FXML
    private TableColumn<Category, String> nazovTableColumn;

    private ObservableList<Category> kategoriaModely;

    private ICategoryManager categoryManager;
    
    private ICategoryDao categoryDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryManager = EntityManagerFactory.INSTANCE.getCategoryManager();
        categoryDao = DaoFactory.INSTANCE.getMysqlCategoryDao();

        inicializujKategorieTableView();
    }

    private void inicializujKategorieTableView() {
        naplnKategoriaModely();

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        obnovKategorieTableView();
    }

    private void obnovKategorieTableView() {
        kategorieTableView.setItems(kategoriaModely);
    }

    private void naplnKategoriaModely() {
        List<Category> znacky = categoryDao.getAll();
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
            GuiUtils.showWarning("Vyberte kategóriu v tabuľke!");
            return;
        }

        Category oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();

        if (categoryManager.productsOfCategoryExists(oznacenaKategoria)) {
            GuiUtils.showWarning("Kategóriu nie je možné odstrániť. Existuje tovar v danej kategórii.");
            return;
        }

        categoryDao.delete(oznacenaKategoria);
        kategoriaModely.remove(oznacenaKategoria);
        obnovKategorieTableView();
    }

    @FXML
    public void onUpravitKategoriuButtonClicked() {
        pridatUpravitKategoriuPane.setVisible(true);
        pridatButton.setVisible(false);
        upravitButton.setVisible(true);

        if (kategorieTableView.getSelectionModel().isEmpty()) {
            GuiUtils.showWarning("Vyberte kategóriu v tabuľke!");
            return;
        }

        Category oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();

        nazovTextField.setText(oznacenaKategoria.getName());
    }

    @FXML
    public void onUpravitButtonClicked() {
        Category oznacenaKategoria = kategorieTableView.getSelectionModel().getSelectedItem();
        String novyNazov = nazovTextField.getText();

        if (categoryManager.categoryExists(novyNazov)) {
            GuiUtils.showWarning("Kategória s daným názvom už existuje!");
        } else if (novyNazov == null || novyNazov.trim().isEmpty()) {
            GuiUtils.showWarning("Zadajte názov kategórie");
        } else {
            categoryManager.update(oznacenaKategoria, novyNazov);
        }
    }

    @FXML
    public void onPridatButtonClicked() {
        String nazovKategorie = nazovTextField.getText();

        if (categoryManager.categoryExists(nazovKategorie)) {
            GuiUtils.showWarning("Kategória s daným názvom už existuje!");
        } else if (nazovKategorie == null || nazovKategorie.trim().isEmpty()) {
            GuiUtils.showWarning("Zadajte názov kategórie");
        } else {
            Category novaKategoria = new Category();
            novaKategoria.setName(nazovKategorie);

            categoryDao.saveOrUpdate(novaKategoria);

            kategoriaModely.add(novaKategoria);
            obnovKategorieTableView();
        }

        nazovTextField.clear();
    }
}
