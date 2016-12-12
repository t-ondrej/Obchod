package sk.upjs.ics.obchod.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.stage.WindowEvent;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.services.DefaultKosikManager;
import sk.upjs.ics.obchod.services.DefaultPouzivatelManager;
import sk.upjs.ics.obchod.services.KosikManager;

public class ObchodStage extends Application {

    @Override
    public void start(Stage stage) {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(stage);

        obchodScene.getStylesheets().add("/styles/Obchod.css");

        stage.setTitle("Obchod");
        stage.setScene(obchodScene);

        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
            Pouzivatel aktivnyPouzivatel = DefaultPouzivatelManager.INSTANCE.getAktivnyPouzivatel();
            if (aktivnyPouzivatel != null) {
                KosikManager defaultKosikManager = new DefaultKosikManager();
                defaultKosikManager.vyprazdniKosik();
            }
            stage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
