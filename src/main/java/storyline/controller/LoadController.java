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
        timelineListViewController.setTimelines(storageAdapter.getAllTimelines());

        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.show(localIconButton.getScene().getWindow());

        timelineListViewController.closeButton.setOnMouseClicked(event -> popup.hide());

        timelineListViewController.loadButton.setOnMouseClicked(event -> {
            Timeline timelineSelected = (Timeline) timelineListViewController.timelineListView.getSelectionModel().getSelectedItem();
            loadSelectedTimeline(storageAdapter, timelineSelected);
            popup.hide();
        });


    }

    private void loadSelectedTimeline(StorageAdapter storageAdapter, Timeline timelineSelected) {
        Context instance = Context.getInstance();
        instance.getProjectPageController().getTimelineController().loadGridFromSave(storageAdapter, timelineSelected.getIdentifier().toString());
        instance.getProjectPageController().getCardsEntitiesController().loadCardsFromStorage(storageAdapter);
        instance.activate("projectPage");
    }
}
