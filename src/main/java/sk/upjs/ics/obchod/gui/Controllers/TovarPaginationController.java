package sk.upjs.ics.obchod.gui.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.entity.Tovar;

public class TovarPaginationController implements Initializable {

    @FXML
    private Pagination tovarPagination;
    
    private List<Tovar> tovary = new ArrayList<>();
    
    private int pocetStranok;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tovary = DaoFactory.INSTANCE.getMysqlTovarDao().dajTovar();
        pocetStranok = (tovary.size() / 8);
        tovarPagination.setPageCount(1);
        tovarPagination.setPageFactory((Integer pageIndex) -> vytvorStranku(pageIndex));
    }
    
    // TODO: zmenit pocet stran
    public GridPane vytvorStranku(int idxStrany) {
        GridPane grid = new GridPane();
        int startIdx = idxStrany * 8, pom = 0;
        grid.setGridLinesVisible(true);
        
        System.err.println("Test tovar paginationnnn");
        
        for (int i = 0; i < 8; i++) {
            Image obrazok = new Image("file:src/main/resources/img/1.JPG");
            ImageView l = new ImageView(obrazok);

            GridPane.setHgrow(l, Priority.ALWAYS);
            GridPane.setVgrow(l, Priority.ALWAYS);
            
            l.fitHeightProperty().bind(tovarPagination.prefHeightProperty().divide(2));
            l.fitWidthProperty().bind(tovarPagination.prefWidthProperty().divide(4));

            GridPane.setHalignment(l, HPos.CENTER);
            
            GridPane.setConstraints(l, pom % 4, pom / 4);
            grid.getChildren().add(l);

            pom++;
        }

        return grid;
    } 
}
