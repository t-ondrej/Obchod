package sk.upjs.ics.obchod.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.managers.EntityManagerFactory;
import sk.upjs.ics.obchod.managers.ICartManager;


public class ObchodStage extends Application {

    @Override
    public void start(Stage stage) {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(stage);

        obchodScene.getStylesheets().add("/styles/Obchod.css");

        stage.setTitle("Obchod");
        stage.setScene(obchodScene);

        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
            User aktivnyPouzivatel = EntityManagerFactory.INSTANCE.getUserManager().getSignedInUser();
            if (aktivnyPouzivatel != null) {
                ICartManager defaultKosikManager = EntityManagerFactory.INSTANCE.getCartManager();
                defaultKosikManager.clearCart();
            }
            stage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}