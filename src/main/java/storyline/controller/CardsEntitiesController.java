package storyline.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Arrays;


public class CardsEntitiesController {

    @FXML
    VBox vLayout;

    @FXML
    private void initialize() throws IOException {
        FXMLLoader test = new FXMLLoader(getClass().getResource("../fxml/eventCard.fxml"));
        test.setController(new EventCardController("yeptitle", "yepdesc", Color.BLUE));
        vLayout.getChildren().add(test.load());
        System.out.println("BENIS");


    }
}
