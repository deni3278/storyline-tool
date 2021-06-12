package storyline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

import java.io.IOException;


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

        createEventButton.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/createEvent.fxml"));
            Pane pane = null;
            try {
                pane = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CreateEventController controller = fxmlLoader.getController();
            Popup popup = new Popup();
            popup.getContent().add(pane);
            popup.show(createEventButton.getScene().getWindow());
            controller.getBtnSave().setOnMouseClicked(event1 -> popup.hide());
            controller.getBtnCancel().setOnMouseClicked(event1 -> popup.hide());
        });
    }


}