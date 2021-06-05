package storyline.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;


public class ActionPaneController {

    @FXML
    Button createOrLoadButton, saveTimelineButton, exportTimelineButton, createEventButton, createEntityButton;

    @FXML
    public void initialize() {
        createOrLoadButton.setOnMouseClicked(event -> {
            Context.getInstance().activate("welcome");
        });

        saveTimelineButton.setOnMouseClicked(event -> {
            TimelineController.saveCurrentTimeline(LocalStorage.getInstance());
            System.out.println("success");
        });


    }

}