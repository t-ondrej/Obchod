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
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.gui.ViewFactory;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.IAccountManager;
import sk.upjs.ics.obchod.managers.IBillManager;
import sk.upjs.ics.obchod.utils.GuiUtils;
import sk.upjs.ics.obchod.managers.IPersonManager;

public class KosikController extends Controller implements Initializable {

    @FXML
    private Button kupitButton;

    @FXML
    private Label celkovaCenaLabel;

    @FXML
    private TableView<Product> tovarTableView;

    @FXML
    private TableColumn<Product, String> nazovTableColumn;

    @FXML
    private TableColumn<Product, Number> mnozstvoTableColumn;

    @FXML
    private TableColumn<Product, Number> cenaTableColumn;

    @FXML
    private TableColumn odobratTovarTableColumn;

    protected ObservableList<Product> tovarKosika;

    private IPersonManager pouzivatelManager;
    
    private IAccountManager accountManager;

    private IBillManager fakturaManager;
    
    private Cart activeAccountCart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pouzivatelManager = EntityManagerFactory.INSTANCE.getPersonManager();
        accountManager = EntityManagerFactory.INSTANCE.getAccountManager();
        fakturaManager = EntityManagerFactory.INSTANCE.getBillManager();
        
        activeAccountCart = accountManager.getActiveAccount().getCart();

        celkovaCenaLabel.textProperty().bind(Bindings.convert(activeAccountCart.totalPriceProperty()));

        nazovTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mnozstvoTableColumn.setCellValueFactory(cellData -> activeAccountCart.cartProductQuantityProperty(cellData.getValue()));
        cenaTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

        odobratTovarTableColumn.setCellFactory(col -> {

            Button odstranitButton = new Button("Odstranit");
            ImageView obrazok = new ImageView(new Image("file:src/main/resources/img/square-minus-small.jpg"));
            odstranitButton.setGraphic(obrazok);

            TableCell<Product, Product> cell = new TableCell<Product, Product>() {
                @Override
                public void updateItem(Product tovar, boolean empty) {
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
                Product vybranyTovar = tovarTableView.getItems().get(cell.getIndex());

                List<Product> tovar = activeAccountCart.getCartProducts();

                Product prvyTovar = tovar.stream().filter(t -> t.getName().equals(vybranyTovar.getName())).collect(Collectors.toList()).get(0);

                activeAccountCart.removeProductFromCart(prvyTovar);
            });
            return cell;
        });

        activeAccountCart.getProducts().addListener(new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
                tovarTableView.getItems().clear();
                tovarTableView.setItems(activeAccountCart.cartProductsObservableList());
            }
        });

        tovarTableView.setItems(activeAccountCart.cartProductsObservableList());
    }

    @FXML
    public void onKupitButtonClicked() {
        if (activeAccountCart.getCartProducts().isEmpty()) {
            return;
        }
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
            if (!pouzivatelManager.hasFilledBillingAddress(accountManager.getActiveAccount().getPerson())) {
                GuiUtils.showWarning("Vyplňte prosím fakturačné údaje");
                alert.close();
            } else {
                fakturaManager.createBill(accountManager.getActiveAccount());
            }
        } else {
            alert.close();
        }
    }
}
