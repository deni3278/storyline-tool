package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


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
        createOrLoadButton.setOnMouseClicked(event -> Context.getInstance().activate("welcome"));
    }


}