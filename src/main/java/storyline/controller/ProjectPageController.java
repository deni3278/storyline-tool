package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import storyline.storage.DatabaseStorage;
import storyline.storage.LocalStorage;

import java.io.File;
import java.util.Optional;

/**
 * Contains all the controllers from the project page fxml via fx:include
 * meant for interactions across controllers
 */
public class ProjectPageController {
    @FXML
    private ActionPaneController actionPaneController;

    @FXML
    private TimelineController timelineController;

    @FXML
    private CardsEntitiesController cardsEntitiesController;

    @FXML
    public void initialize() {
        actionPaneController.getSaveTimelineButton().setOnMouseClicked(event -> {
            ButtonType database = new ButtonType("Database");
            ButtonType local = new ButtonType("Local");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save current timeline");
            alert.setHeaderText("Chose where to save your current project");
            alert.getButtonTypes().setAll(database, local, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent()) return;

            boolean success = false;

            if (result.get() == database) {
                success = timelineController.saveCurrentTimeline(DatabaseStorage.getInstance());
            } else if (result.get() == local) {
                success = timelineController.saveCurrentTimeline(LocalStorage.getInstance());
            }

            if (success) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setHeaderText("Successfully saved your current project");
                successAlert.showAndWait();
            }
        });

        actionPaneController.getExportTimelineButton().setOnAction(event -> {
            FileChooser saveFile = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            saveFile.getExtensionFilters().add(extFilter);

            File saveTxt = saveFile.showSaveDialog(actionPaneController.getExportTimelineButton().getScene().getWindow());
            timelineController.getTimeline().export(saveTxt);
        });

    }

    /**
     * Gets action pane controller.
     *
     * @return the action pane controller
     */
    public ActionPaneController getActionPaneController() {
        return actionPaneController;
    }

    /**
     * Gets timeline controller.
     *
     * @return the timeline controller
     */
    public TimelineController getTimelineController() {
        return timelineController;
    }

    /**
     * Gets cards entities controller.
     *
     * @return the cards entities controller
     */
    public CardsEntitiesController getCardsEntitiesController() {
        return cardsEntitiesController;
    }
}
