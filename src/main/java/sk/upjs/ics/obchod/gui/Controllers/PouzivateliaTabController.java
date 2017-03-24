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
import sk.upjs.ics.obchod.dao.mysql.MysqlUserDao;
import sk.upjs.ics.obchod.entity.User;

public class PouzivateliaTabController implements Initializable {

    @FXML
    private TableView<User> pouzivateliaTableView;

    @FXML
    private TableColumn<User, Number> idTableColumn;

    @FXML
    private TableColumn<User, String> prihlasovacieMenoTableColumn;

    @FXML
    private TableColumn<User, String> menoTableColumn;

    @FXML
    private TableColumn<User, String> priezviskoTableColumn;

    @FXML
    private TableColumn<User, String> mestoTableColumn;

    @FXML
    private TableColumn<User, String> ulicaTableColumn;

    @FXML
    private TableColumn<User, Number> pscTableColumn;

    @FXML
    private TableColumn<User, String> emailTableColumn;

    @FXML
    private TableColumn<User, LocalDateTime> poslednePrihlasenieTableColumn;

    @FXML
    private TableColumn<User, Boolean> pravomociTableColumn;

    private ObservableList<User> pouzivatelModely;

    private MysqlUserDao mysqlPouzivatelDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mysqlPouzivatelDao = DaoFactory.INSTANCE.getMysqlUserDao();
        pouzivatelModely = FXCollections.observableArrayList(mysqlPouzivatelDao.getAll());

        inicializujPouzivatelTableView();
        obnovPouzivateliaTableView();
    }

    private void inicializujPouzivatelTableView() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        prihlasovacieMenoTableColumn.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
        menoTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priezviskoTableColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        mestoTableColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        ulicaTableColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        pscTableColumn.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
        emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        poslednePrihlasenieTableColumn.setCellValueFactory(cellData -> cellData.getValue().lastLoginProperty());
        pravomociTableColumn.setCellValueFactory(cellData -> cellData.getValue().isAdminProperty());

        pscTableColumn.setCellFactory(column -> {
            return new TableCell<User, Number>() {
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
            return new TableCell<User, Boolean>() {
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
        pouzivatelModely = FXCollections.observableArrayList(mysqlPouzivatelDao.getAll());
        pouzivateliaTableView.getItems().addAll(pouzivatelModely);
    }
}
