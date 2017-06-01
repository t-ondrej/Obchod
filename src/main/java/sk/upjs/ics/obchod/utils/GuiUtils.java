package sk.upjs.ics.obchod.utils;

import javafx.scene.control.Alert;


public class GuiUtils {
    
    public static void showWarning(String headerText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
