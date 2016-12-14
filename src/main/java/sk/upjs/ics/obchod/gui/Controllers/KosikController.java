package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import sk.upjs.ics.obchod.managers.DefaultFakturaManager;
import sk.upjs.ics.obchod.managers.DefaultKosikManager;
import sk.upjs.ics.obchod.managers.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.managers.FakturaManager;
import sk.upjs.ics.obchod.managers.KosikManager;
import sk.upjs.ics.obchod.managers.PouzivatelManager;

public class KosikController implements Initializable {

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

    private KosikManager defaultKosikManager;

    private PouzivatelManager defaultPouzivatelManager = DefaultPouzivatelManager.INSTANCE;

    private FakturaManager defaultFakturaManager = new DefaultFakturaManager(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultKosikManager = new DefaultKosikManager();
        Kosik kosikAktivnehoPouzivatela = defaultPouzivatelManager.getAktivnyPouzivatel().getKosik();

        celkovaCenaLabel.textProperty().bind(Bindings.convert(kosikAktivnehoPouzivatela.celkovaCenaProperty()));

        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nazovProperty());
        mnozstvoTableColumn.setCellValueFactory(cellData -> defaultKosikManager.pocetTovaruVKosikuProperty(cellData.getValue()));
        cenaTableColumn.setCellValueFactory(cellData -> cellData.getValue().cenaProperty());

        odobratTovarTableColumn.setCellFactory(col -> {

            Button odstranitButton = new Button("Odstranit");
            ImageView obrazok = new ImageView(new Image("file:src/main/resources/img/square-minus-small.jpg"));
            odstranitButton.setGraphic(obrazok);

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

            odstranitButton.getStyleClass().clear();
            odstranitButton.getStyleClass().add("vyber-tovar-kosika-button");
            odstranitButton.setCursor(Cursor.HAND);
            cell.setAlignment(Pos.CENTER);

            odstranitButton.setOnAction(event -> {
                Tovar vybranyTovar = tovarTableView.getItems().get(cell.getIndex());

                List<Tovar> tovar = defaultKosikManager.dajTovarKosika();

                Tovar prvyTovar = tovar.stream().filter(t -> t.getNazov().equals(vybranyTovar.getNazov())).collect(Collectors.toList()).get(0);

                defaultKosikManager.odoberTovarZKosika(prvyTovar);
            });
            return cell;
        });

        kosikAktivnehoPouzivatela.getTovar().addListener(new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
                tovarTableView.getItems().clear();
                tovarTableView.setItems(defaultKosikManager.tovarKosikaObservableList());
            }
        });

        tovarTableView.setItems(defaultKosikManager.tovarKosikaObservableList());
    }

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void onKupitButtonClicked() {
        ukazChceteKupitTovarDialog();
    }

    @FXML
    public void onSpatLabelClicked() {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(mainStage);
        mainStage.setScene(obchodScene);
    }

    private void ukazChceteKupitTovarDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Chcete kúpiť tovar?");

        ButtonType ano = new ButtonType("Áno");
        ButtonType nie = new ButtonType("Nie");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().setAll(ano, nie);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ano) {
            if (!defaultPouzivatelManager.maVyplneneFakturacneUdaje()) {
                ukazUpozornenie("Vyplňte prosím fakturačné údaje");
                alert.close();
            } else {
                defaultFakturaManager.vytvorFakturu(defaultPouzivatelManager.getAktivnyPouzivatel());
            }
        } else {
            alert.close();
        }
    }

    private void ukazUpozornenie(String hlavička) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozornenie");
        alert.setHeaderText(hlavička);
        alert.showAndWait();
    }
}
