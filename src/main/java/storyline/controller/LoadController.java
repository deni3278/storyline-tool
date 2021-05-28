package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class LoadController {
    @FXML
    private VBox databaseIconButton, localIconButton;

    @FXML
    private IconButtonController databaseIconButtonController, localIconButtonController;

    @FXML
    private void initialize() {
        databaseIconButton.setOnMouseClicked(e -> loadLocal());
        databaseIconButtonController.setImage(new Image(getClass().getResource("../images/database.png").toExternalForm()));
        databaseIconButtonController.setText("From Database");

        localIconButton.setOnMouseClicked(e -> loadDatabase());
        localIconButtonController.setImage(new Image(getClass().getResource("../images/local.png").toExternalForm()));
        localIconButtonController.setText("From Local File");
    }

    private void loadLocal() {
        // Todo: Implement loading from a local file.
    }

    private void loadDatabase() {
        // Todo: Implement loading from a database.
    }
}
