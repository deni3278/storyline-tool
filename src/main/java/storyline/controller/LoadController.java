package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;

public class LoadController {
    @FXML
    private VBox databaseIconButton, localIconButton;

    @FXML
    private IconButtonController databaseIconButtonController, localIconButtonController;

    @FXML
    private void initialize() {
        databaseIconButton.setOnMouseClicked(e -> loadDatabase());
        databaseIconButtonController.setImage(new Image(getClass().getResource("../images/database.png").toExternalForm()));
        databaseIconButtonController.setText("From Database");

        localIconButton.setOnMouseClicked(e -> {
            loadLocal();
            Context.getInstance().activate("projectPage");
        });
        localIconButtonController.setImage(new Image(getClass().getResource("../images/local.png").toExternalForm()));
        localIconButtonController.setText("From Local File");
    }

    private void loadLocal() {
        StorageAdapter localStorage = LocalStorage.getInstance();
        TimelineController.loadGridFromSave(localStorage,"test");
    }

    private void loadDatabase() {
        // Todo: Implement loading from a database.
    }
}
