package storyline.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import storyline.model.EventCard;
import storyline.model.Timeline;
import storyline.model.TimelineEventCard;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;

import java.io.IOException;
import java.util.ArrayList;


public class TimelineController {


    public static ArrayList<TimelineEventCard> timelineEventCards = new ArrayList<>();

    public static Timeline timeline;

    public static GridPane timelineGridPaneStatic;


    @FXML
    public ScrollPane timelineScrollPane;

    @FXML
    GridPane timelineGridPane;

    @FXML
    VBox root;

    @FXML
    private void initialize() {

        timelineGridPaneStatic = timelineGridPane;


        StorageAdapter localStorage = LocalStorage.getInstance();


//        Platform.runLater(() -> loadGridFromSave(localStorage, "Test"));

        timelineGridPane.setOnDragOver(event -> {
            if (event.getGestureSource() != timelineGridPane) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        timelineGridPane.setOnDragDropped(event -> {


            HBox source = (HBox) event.getGestureSource();

            System.out.println("source.getUserData() = " + source.getUserData());
            double mouseX = event.getX();
            double mouseY = event.getY();

            Bounds cellBounds = timelineGridPane.impl_getCellBounds(0, 0);

            int columnIndex = (int) Math.floor(mouseX / cellBounds.getWidth());
            int rowIndex = (int) Math.floor(mouseY / cellBounds.getHeight());
            System.out.println("rowIndex = " + rowIndex);
            System.out.println("columnIndex = " + columnIndex);

            EventCard sourceEventCard = (EventCard) source.getUserData();


            boolean overlap = checkOverlap(columnIndex, rowIndex);

            if (overlap == false) {
                timelineGridPane.getChildren().remove(source);

                TimelineEventCard timelineEventCard;
                if (!(sourceEventCard instanceof TimelineEventCard)) {
                    timelineEventCard = new TimelineEventCard(sourceEventCard.getTitle(),
                            sourceEventCard.getColorString(), sourceEventCard.getEventContent(), columnIndex, rowIndex);
                    System.out.println("from eventcard vbox");
                    try {
                        addTimelineEventCard(timelineEventCard);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    source.setVisible(false);
                    source.setManaged(false);
                } else {
                    System.out.println("from within gridpane");
                    timelineEventCard = (TimelineEventCard) sourceEventCard;
                    timelineEventCard.setX(columnIndex);
                    timelineEventCard.setY(rowIndex);
                    timelineGridPane.add(source, columnIndex, rowIndex);
                }

            }

            event.consume();
        });


    }

    private boolean checkOverlap(int columnIndex, int rowIndex) {
        boolean overlap = false;
        for (TimelineEventCard eventCard : timelineEventCards) {
            System.out.println("x =" + eventCard.getX() + " y = " + eventCard.getY());
            if (eventCard.getX() == columnIndex && eventCard.getY() == rowIndex) {
                System.out.println("overlap!");
                overlap = true;
            }
        }
        return overlap;
    }

    /**
     * Start from blank.
     *
     * @param timelineName the timeline name
     */
//method for starting a blank timeline
    public static void startFromBlank(String timelineName) {

        //initializes public static variables
        timeline = new Timeline(new ArrayList<TimelineEventCard>(), timelineName);
        timelineEventCards = timeline.getEventCards();
        //clears the current grid nodes.
        //need to keep the first node of the gridpane as it holds the layout information like the gridlines...
        Platform.runLater(() -> timelineGridPaneStatic.getChildren().retainAll(timelineGridPaneStatic.getChildren().get(0)));
    }

    /**
     * Load a timeline from save and add it to the grid.
     *
     * @param storageAdapter the storage adapter
     * @param ID             the id
     */
    public static void loadGridFromSave(StorageAdapter storageAdapter, String ID) {
        //initializes public static variables
        timeline = storageAdapter.getTimeline(ID);
        timelineEventCards = timeline.getEventCards();

        //clears the current grid nodes.
        //need to keep the first node of the gridpane as it holds the layout information like the gridlines...
        Platform.runLater(() -> timelineGridPaneStatic.getChildren().retainAll(timelineGridPaneStatic.getChildren().get(0)));

        //convers the model timeline eventcards into interactable hboxes and adds them to the gridpane
        timelineEventCards.forEach(timelineEventCard -> {
            try {
                addTimelineEventCard(timelineEventCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void addTimelineEventCard(TimelineEventCard timelineEventCard) throws IOException {

        //wrap the model timeline eventcard with an interactable hbox
        HBox interactableEventCard = createTimelineEventCard(timelineEventCard);

        //adds the hbox to the grid with the coordinates of the eventcard
        timelineGridPaneStatic.add(interactableEventCard, timelineEventCard.getX(), timelineEventCard.getY());

        //if the list of timeline eventcards doesn't contain the newly added eventcard, add it to the list.
        if (!timelineEventCards.contains(timelineEventCard)) timelineEventCards.add(timelineEventCard);
    }


    private static HBox createTimelineEventCard(TimelineEventCard timelineEventCard) throws IOException {
        FXMLLoader card = new FXMLLoader(TimelineController.class.getResource("../fxml/eventCard.fxml"));
        //Assign a controller to the newly loaded card, and pass the variables for the card
        card.setController(new EventCardController(timelineEventCard.getTitle(), timelineEventCard.getEventContent(), timelineEventCard.getColor()));

        HBox eventCard = card.load();
        eventCard.setUserData(timelineEventCard);

        eventCard.setOnDragDetected(event -> {

            System.out.println(eventCard + " drag detected");

            ImageView preview = new ImageView(eventCard.snapshot(null, null));

            Dragboard db = eventCard.startDragAndDrop(TransferMode.ANY);
            db.setDragView(preview.getImage());

            ClipboardContent content = new ClipboardContent();
            content.putString("TimelineEventCard");
            db.setContent(content);

        });
        eventCard.setOnMouseDragged(event -> event.setDragDetect(true));
        return eventCard;
    }

    //gets a node from the gridpane from a specific coordinate
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}