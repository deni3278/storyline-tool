package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import storyline.storage.DatabaseStorage;
import storyline.storage.LocalStorage;

import java.util.Optional;

/**
 * Contains all the controllers from the projectpage fxml via fx:include
 * meant for interactions across controllers
 */
public class ProjectPageController {


    @FXML
    ActionPaneController actionPaneController;

    @FXML
    TimelineController timelineController;

    @FXML
    CardsEntitiesController cardsEntitiesController;

    @FXML
    public void initialize() {


        actionPaneController.getSaveTimelineButton().setOnMouseClicked(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save current timeline");
            alert.setHeaderText("Chose where to save your current project");

            ButtonType database = new ButtonType("Database");
            ButtonType local = new ButtonType("Local");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(database, local, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            boolean success = false;
            if (result.get() == database) {
                success = timelineController.saveCurrentTimeline(DatabaseStorage.getInstance());
                System.out.println("saved in database");
            } else if (result.get() == local) {
                success = timelineController.saveCurrentTimeline(LocalStorage.getInstance());
                System.out.println("saved locally");
            }
            if (success){
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setHeaderText("Successfully saved your current project");
                successAlert.showAndWait();
            }


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
