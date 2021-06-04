package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;


public class ActionPaneController {

    @FXML
    Button createOrLoadButton,saveTimelineButton,exportTimelineButton,createEventButton,createEntityButton;

    @FXML
    public void initialize() {
        StorageAdapter localStorage = LocalStorage.getInstance();
        createOrLoadButton.setOnMouseClicked(event -> {

            TimelineController.loadGridFromSave(localStorage, "Test");
        });


    }

}