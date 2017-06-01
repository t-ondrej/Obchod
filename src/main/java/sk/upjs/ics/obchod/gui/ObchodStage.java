package sk.upjs.ics.obchod.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sk.upjs.ics.obchod.entity.Account;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;

public class ObchodStage extends Application {

    @Override
    public void start(Stage stage) {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(stage);

        obchodScene.getStylesheets().add("/styles/Obchod.css");

        stage.setTitle("Obchod");
        stage.setScene(obchodScene);

        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
            Account activeAccount = EntityManagerFactory.INSTANCE.getAccountManager().getActiveAccount();
            Cart cart = activeAccount.getCart();
            if (activeAccount != null) {
                cart.clearCart();
            }
            stage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}