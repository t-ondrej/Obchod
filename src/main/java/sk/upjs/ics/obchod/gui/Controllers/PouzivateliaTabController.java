package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.IPersonDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlPersonDao;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.IPersonManager;

public class PouzivateliaTabController implements Initializable {

    @FXML
    private TableView<Person> pouzivateliaTableView;

    @FXML
    private TableColumn<Person, Number> idTableColumn;

    @FXML
    private TableColumn<Person, String> prihlasovacieMenoTableColumn;

    @FXML
    private TableColumn<Person, String> menoTableColumn;

    @FXML
    private TableColumn<Person, String> priezviskoTableColumn;

    @FXML
    private TableColumn<Person, String> mestoTableColumn;

    @FXML
    private TableColumn<Person, String> ulicaTableColumn;

    @FXML
    private TableColumn<Person, Number> pscTableColumn;

    @FXML
    private TableColumn<Person, String> emailTableColumn;

    @FXML
    private TableColumn<Person, LocalDateTime> poslednePrihlasenieTableColumn;

    @FXML
    private TableColumn<Person, Boolean> pravomociTableColumn;

    private ObservableList<Person> pouzivatelModely;

    private IPersonManager personManager;
    
    private IPersonDao personDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personManager = EntityManagerFactory.INSTANCE.getPersonManager();
        personDao = DaoFactory.INSTANCE.getMysqlPersonDao();
        pouzivatelModely = FXCollections.observableArrayList(personDao.getAll());

        inicializujPouzivatelTableView();
        obnovPouzivateliaTableView();
    }

    // TODO change tableView 
    private void inicializujPouzivatelTableView() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        menoTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priezviskoTableColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        mestoTableColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        ulicaTableColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        pscTableColumn.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
        emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        pscTableColumn.setCellFactory(column -> {
            return new TableCell<Person, Number>() {
                @Override
                protected void updateItem(Number psc, boolean empty) {
                    super.updateItem(psc, empty);

                    if (psc == null || psc.intValue() == 0 || empty) {
                        setText("");
                    } else {
                        setText(Integer.toString(psc.intValue()));
                    }
                }
            };
        });

        pravomociTableColumn.setCellFactory(column -> {
            return new TableCell<Person, Boolean>() {
                @Override
                protected void updateItem(Boolean jeAdmin, boolean empty) {
                    super.updateItem(jeAdmin, empty);

                    if (jeAdmin == null || empty) {
                        setText("");
                    } else if (jeAdmin) {
                        setText("administrátor");
                    } else {
                        setText("bežný použivateľ");
                    }
                }
            };
        });
    }

    public void obnovPouzivateliaTableView() {
        pouzivateliaTableView.getItems().clear();
        pouzivatelModely = FXCollections.observableArrayList(personDao.getAll());
        pouzivateliaTableView.getItems().addAll(pouzivatelModely);
    }
}
