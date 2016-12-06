package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultKosikManager;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;

public class PokladnaController implements Initializable {

    @FXML
    private Button kupitButton;

    @FXML
    private Label celkovaCenaLabel;

    @FXML
    private TableView<Tovar> tovarTableView;

    @FXML
    private TableColumn<Tovar, String> nazovTableColumn;

    @FXML
    private TableColumn<Tovar, Number> mnozstvoTableColumn;

    @FXML
    private TableColumn<Tovar, Number> cenaTableColumn;

    @FXML
    private TableColumn odobratTovarTableColumn;

    private Stage mainStage;

    protected ObservableList<Tovar> tovarKosika;

    private DefaultKosikManager defaultKosikManager;

    private DefaultPouzivatelManager defaultPouzivatelManager = DefaultPouzivatelManager.INSTANCE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultKosikManager = new DefaultKosikManager();
        Kosik kosikAktivnehoPouzivatela = defaultPouzivatelManager.getAktivnyPouzivatel().getKosik();

        celkovaCenaLabel.textProperty().bind(Bindings.convert(kosikAktivnehoPouzivatela.celkovaCenaProperty()));

        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nazovProperty());
        mnozstvoTableColumn.setCellValueFactory(cellData -> defaultKosikManager.pocetTovaruVKosikuProperty(cellData.getValue(), kosikAktivnehoPouzivatela));
        cenaTableColumn.setCellValueFactory(cellData -> cellData.getValue().cenaProperty());

        odobratTovarTableColumn.setCellFactory(col -> {

            Button odstranitButton = new Button("Odstranit");
            odstranitButton.setGraphic(new ImageView(new Image("file:src/main/resources/img/square-minus-small.jpg")));

            TableCell<Tovar, Tovar> cell = new TableCell<Tovar, Tovar>() {
                @Override
                public void updateItem(Tovar tovar, boolean empty) {
                    super.updateItem(tovar, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(odstranitButton);
                    }
                }
            };

            odstranitButton.setOnAction(event -> {
                Tovar vybranyTovar = tovarTableView.getItems().get(cell.getIndex());

                Kosik kosik = DefaultPouzivatelManager.INSTANCE.getAktivnyPouzivatel().getKosik();

                List<Tovar> tovary = defaultKosikManager.dajTovaryKosika(kosik);

                Tovar tovar = tovary.stream().filter(t -> t.getNazov().equals(vybranyTovar.getNazov())).collect(Collectors.toList()).get(0);

                defaultKosikManager.odoberTovarZKosika(tovar, kosik);
            });
            return cell;
        });

        kosikAktivnehoPouzivatela.getTovary().addListener(new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
                tovarTableView.getItems().clear();
                tovarTableView.setItems(defaultKosikManager.tovarKosikaObservableList(kosikAktivnehoPouzivatela));
            }
        });

        tovarTableView.setItems(defaultKosikManager.tovarKosikaObservableList(kosikAktivnehoPouzivatela));
    }

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void onKupitButtonClicked() {

    }

    @FXML
    public void onSpatLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }

}
