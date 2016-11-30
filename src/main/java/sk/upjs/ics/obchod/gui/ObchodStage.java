package sk.upjs.ics.obchod.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class ObchodStage extends Application {

    @Override
    public void start(Stage stage) {
        Scene obchodScene = ViewFactory.INSTANCE.getObchodScene(stage);

        obchodScene.getStylesheets().add("/styles/Obchod.css");

        stage.setTitle("Obchod");
        stage.setScene(obchodScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
