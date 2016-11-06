package sk.upjs.ics.obchod;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ObchodController implements Initializable {
    
    @FXML
    private Label kategorieLabel;
    
    @FXML
    private Label znackyLabel;
    
    @FXML
    private Label vyhladatLabel;
    
    @FXML
    private Button prihlasitButton;
    
    @FXML
    private Button registrovatButton;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
