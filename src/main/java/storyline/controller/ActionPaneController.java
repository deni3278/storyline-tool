package storyline.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;


public class ActionPaneController {

    @FXML
    Button createOrLoadButton, saveTimelineButton, exportTimelineButton, createEventButton, createEntityButton;

    public Button getCreateOrLoadButton() {
        return createOrLoadButton;
    }

    public Button getSaveTimelineButton() {
        return saveTimelineButton;
    }

    public Button getExportTimelineButton() {
        return exportTimelineButton;
    }

    public Button getCreateEventButton() {
        return createEventButton;
    }

    public Button getCreateEntityButton() {
        return createEntityButton;
    }

    @FXML
    public void initialize() {

    }


}