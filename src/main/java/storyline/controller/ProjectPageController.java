package storyline.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import storyline.model.TimelineEventCard;
import storyline.storage.DatabaseStorage;
import storyline.storage.LocalStorage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
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
        actionPaneController.getExportTimelineButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser saveFile = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                saveFile.getExtensionFilters().add(extFilter);

                File saveTxt = saveFile.showSaveDialog(actionPaneController.getExportTimelineButton().getScene().getWindow());
                try {
                    exportTimelineToFile(saveTxt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    /**
     * Exports timeline to a txt file.
     */

    private void exportTimelineToFile(File saveTxt) throws IOException {

        if (saveTxt != null) {
            ArrayList<TimelineEventCard> expEventCards = new ArrayList<>(timelineController.getTimelineEventCards());
            expEventCards.sort(Comparator.comparingInt(TimelineEventCard::getX).thenComparingInt(TimelineEventCard::getY));
            System.out.println(expEventCards);
            exportFileTextContent(expEventCards, saveTxt);

            System.out.println("File created: " + saveTxt.getName());
        } else {
            System.out.println("File already exists");
        }
    }

    /**
     * Iterates through event card array and formats the text based on their placement
     *
     * @param expEventCards cloned array of timeline event cards.
     * @param saveTxt       file variable for the exported timeline.
     */

    private void exportFileTextContent(ArrayList<TimelineEventCard> expEventCards, File saveTxt) {
        try (PrintWriter printWriter = new PrintWriter(saveTxt)) {

            for (TimelineEventCard expEventCard : expEventCards) {
                String whiteSpace = getSpaces(expEventCard.getY());
                printWriter.write(whiteSpace + expEventCard.getTitle() + "\n" + whiteSpace + expEventCard.getEventContent() + "\n\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSpaces(int y) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < y; i++) {
            spaces.append("    ");
        }
        return spaces.toString();
    }
}
