package storyline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import storyline.model.Timeline;
import storyline.storage.DatabaseStorage;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Load controller contains the logic for the loading buttons on the bottom part of the welcome page.
 */
public class LoadController {
    @FXML
    private VBox databaseIconButton, localIconButton;

    @FXML
    private IconButtonController databaseIconButtonController, localIconButtonController;

    @FXML
    private void initialize() {
        databaseIconButton.setOnMouseClicked(e -> showTimelines(DatabaseStorage.getInstance()));
        databaseIconButtonController.setImage(new Image(getClass().getResource("../images/database.png").toExternalForm()));
        databaseIconButtonController.setText("From Database");

        localIconButton.setOnMouseClicked(e -> showTimelines(LocalStorage.getInstance()));
        localIconButtonController.setImage(new Image(getClass().getResource("../images/local.png").toExternalForm()));
        localIconButtonController.setText("From Local File");
    }


    private void showTimelines(StorageAdapter storageAdapter) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/listTimelines.fxml"));
        ScrollPane root = null;

        try {
            root = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        TimelineListViewController timelineListViewController = loader.getController();
        ArrayList<Timeline> allTimelines = storageAdapter.getAllTimelines();

        if (allTimelines != null) timelineListViewController.setTimelines(allTimelines);

        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.show(localIconButton.getScene().getWindow());

        timelineListViewController.closeButton.setOnMouseClicked(event -> popup.hide());

        timelineListViewController.loadButton.setOnMouseClicked(event -> {
            Timeline timelineSelected = (Timeline) timelineListViewController.getTimelineListView().getSelectionModel().getSelectedItem();

            loadSelectedTimeline(storageAdapter, timelineSelected);

            popup.hide();
        });
    }

    private void loadSelectedTimeline(StorageAdapter storageAdapter, Timeline timelineSelected) {
        Context instance = Context.getInstance();
        instance.getProjectPageController().getTimelineController().loadGridFromSave(storageAdapter, timelineSelected.getIdentifier().toString());
        //only loads user event cards from local storage at the moment as there is currently no way in the ui to save them to the database
        instance.getProjectPageController().getCardsEntitiesController().loadCardsFromStorage(LocalStorage.getInstance());
        instance.activate("projectPage");
    }
}
