package storyline.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import storyline.model.TimelineEventCard;
import storyline.storage.DatabaseStorage;
import storyline.storage.LocalStorage;

import java.io.*;

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
        actionPaneController.getExportTimelineButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exportTimelineToFile();
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

    private void exportTimelineToFile (){

        try {
            File timelineExport = new File(timelineController.getTimeline().getName()+".txt");
            if (timelineExport.createNewFile()) {
                ArrayList<TimelineEventCard> expEventCards = new ArrayList<>(timelineController.getTimelineEventCards());
                expEventCards.sort(Comparator.comparing(timelineEventCard -> timelineEventCard.getX()));
                System.out.println(expEventCards);
                exportFileTextContent(expEventCards, timelineExport);

                System.out.println("File created: "+ timelineExport.getName());
            }
            else {
                System.out.println("File already exists");
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private void exportFileTextContent(ArrayList<TimelineEventCard> expEventCards, File timelineExport) throws IOException {
        FileOutputStream fos = new FileOutputStream(timelineExport);
        BufferedWriter writeEventCard = new BufferedWriter(new OutputStreamWriter(fos));
        int smallestY = -1;

        for (int i =0;i<expEventCards.size();i++){
            if(smallestY<expEventCards.get(i).getY()){
              smallestY=expEventCards.get(i).getY();
            }
            else
                continue;
        }
        for (int i=0;i<expEventCards.size();i++){

            if(expEventCards.get(i).getY()>smallestY) {
                writeEventCard.write("\t");
                writeEventCard.write(expEventCards.get(i).getTitle()+"\n"+expEventCards.get(i).getEventContent());
            }else{
                writeEventCard.write(expEventCards.get(i).getTitle()+"\n"+expEventCards.get(i).getEventContent());
                }
            writeEventCard.newLine();
            }
        writeEventCard.close();
    }
}
