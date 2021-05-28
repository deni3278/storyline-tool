package storyline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RootController {
    @FXML
    private BorderPane root;

    @FXML
    private void initialize() throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("../fxml/welcome.fxml")));
    }
}
