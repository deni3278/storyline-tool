package storyline.controller;

import javafx.fxml.FXML;
import storyline.storage.DatabaseStorage;

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
            timelineController.saveCurrentTimeline(DatabaseStorage.getInstance());
            System.out.println("success");
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
