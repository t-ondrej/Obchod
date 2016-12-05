package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlPouzivatelDao;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class PouzivateliaTabController implements Initializable {

    @FXML
    private TableView<Pouzivatel> pouzivateliaTableView;

    @FXML
    private TableColumn<Pouzivatel, Number> idTableColumn;

    @FXML
    private TableColumn<Pouzivatel, String> prihlasovacieMenoTableColumn;

    @FXML
    private TableColumn<Pouzivatel, String> emailTableColumn;

    @FXML
    private TableColumn<Pouzivatel, LocalDate> poslednePrihlasenieTableColumn;

    @FXML
    private TableColumn<Pouzivatel, Boolean> pravomociTableColumn;

    private ObservableList<Pouzivatel> pouzivatelModely;

    private MysqlPouzivatelDao mysqlPouzivatelDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mysqlPouzivatelDao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
        pouzivatelModely = FXCollections.observableArrayList(mysqlPouzivatelDao.dajPouzivatelov());

        inicializujPouzivatelTableView();
        obnovPouzivateliaTableView();
    }

    private void inicializujPouzivatelTableView() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        prihlasovacieMenoTableColumn.setCellValueFactory(cellData -> cellData.getValue().prihlasovacieMenoProperty());
        emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        poslednePrihlasenieTableColumn.setCellValueFactory(cellData -> cellData.getValue().poslednePrihlasenieProperty());
        pravomociTableColumn.setCellValueFactory(cellData -> cellData.getValue().jeAdministratorProperty());

        pravomociTableColumn.setCellFactory(column -> {
            return new TableCell<Pouzivatel, Boolean>() {
                @Override
                protected void updateItem(Boolean jeAdmin, boolean empty) {
                    super.updateItem(jeAdmin, empty);

                    if (jeAdmin == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {                 
                        if (jeAdmin) {
                            setText("administrátor");
                        } else {
                            setText("bežný použivateľ");
                        }   
                    }
                }
            };
        });
    }

    public void obnovPouzivateliaTableView() {
        pouzivateliaTableView.getItems().clear();
        pouzivatelModely = FXCollections.observableArrayList(mysqlPouzivatelDao.dajPouzivatelov());
        pouzivateliaTableView.getItems().addAll(pouzivatelModely);
    }
}
