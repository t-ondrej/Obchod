package sk.upjs.ics.obchod.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class ObchodStage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ObchodFXML.fxml"));
        Scene scene = new Scene(loader.load());
        
        ObchodController cnt = loader.getController();
        cnt.initialize();

        scene.getStylesheets().add("/styles/ObchodCSS.css");

        stage.setTitle("Obchod");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
