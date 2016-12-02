package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.gui.KosikModel;
import sk.upjs.ics.obchod.gui.TovarModel;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.services.DefaultKosikManager;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;

public class PokladnaController implements Initializable {

    @FXML
    private Button kupitButton;

    @FXML
    private TableView<TovarModel> tovarTableView;

    @FXML
    private TableColumn<TovarModel, String> nazovTableColumn;

    @FXML
    private TableColumn<TovarModel, Number> mnozstvoTableColumn;

    @FXML
    private TableColumn<TovarModel, Number> cenaTableColumn;

    @FXML
    private TableColumn odobratTovarTableColumn;

    private Stage mainStage;

    protected ObservableList<TovarModel> tovarKosika;

    private DefaultKosikManager defaultKosikManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultKosikManager = new DefaultKosikManager();

        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().getNazov());
        mnozstvoTableColumn.setCellValueFactory(cellData -> cellData.getValue().getPocetKusovVKosiku());
        cenaTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCena());

        odobratTovarTableColumn.setCellFactory(col -> {
            
            Button odstranitButton = new Button("Odstranit");
            odstranitButton.setGraphic(new ImageView(new Image("file:src/main/resources/img/square-minus-small.jpg")));
            
            
            TableCell<TovarModel, TovarModel> cell = new TableCell<TovarModel, TovarModel>() {
                @Override
                public void updateItem(TovarModel person, boolean empty) {
                    super.updateItem(person, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(odstranitButton);
                    }
                }
            };

            odstranitButton.setOnAction(event -> {
                TovarModel vybranyTovar = tovarTableView.getItems().get(cell.getIndex());

                List<Tovar> tovary = defaultKosikManager.dajTovarKosika(DefaultPouzivatelManager.INSTANCE.getAktivnyPouzivatel().getKosik());

                Tovar tovar = tovary.stream().filter(t -> t.getNazov().equals(vybranyTovar.getNazov().getValue())).collect(Collectors.toList()).get(0);

                KosikModel.INSTANCE.odoberTovarKosika(tovar);
            });
            return cell;
        });


        tovarTableView.setItems(KosikModel.INSTANCE.getTovarKosika());
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
