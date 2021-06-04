package storyline.controller;

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

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class TimelineController {

    public static ArrayList<TimelineEventCard> timelineEventCards;
    public static Timeline timeline;

    @FXML
    public ScrollPane timelineScrollPane;

    @FXML
    GridPane timelineGridPane;

    @FXML
    VBox root;

    @FXML
    private void initialize() {


        LocalStorage localStorage = LocalStorage.getInstance();
        timeline = localStorage.getTimeline("test");
        timelineEventCards = timeline.getEventCards();
        timelineEventCards.forEach(timelineEventCard -> {
            try {
                addTimelineEventCard(timelineEventCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

            boolean overlap = false;
            for (TimelineEventCard eventCard : timelineEventCards) {
                System.out.println("x =" + eventCard.getX() + " y = " + eventCard.getY());
                if (eventCard.getX() == columnIndex && eventCard.getY() == rowIndex) {
                    System.out.println("overlap!");
                    overlap = true;
                }
            }
            if (overlap == false) {
                timelineGridPane.getChildren().remove(source);

                TimelineEventCard timelineEventCard;
                if (source.getParent() instanceof VBox) {
                    timelineEventCard = new TimelineEventCard(sourceEventCard.getTitle(),
                            sourceEventCard.getColorString(), sourceEventCard.getEventContent(), columnIndex, rowIndex);
                    System.out.println("from eventcard vbox");
                    try {
                        addTimelineEventCard(timelineEventCard);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    timelineEventCard = (TimelineEventCard) sourceEventCard;
                    timelineEventCard.setX(columnIndex);
                    timelineEventCard.setY(rowIndex);
                    timelineGridPane.add(source, columnIndex, rowIndex);
                }

            }

            event.consume();
        });


    }

    private void addTimelineEventCard(TimelineEventCard timelineEventCard) throws IOException {

        HBox interactableEventCard = createTimelineEventCard(timelineEventCard);
        timelineGridPane.add(interactableEventCard, timelineEventCard.getX(), timelineEventCard.getY());
        if (!timelineEventCards.contains(timelineEventCard)) timelineEventCards.add(timelineEventCard);
    }

    private HBox createTimelineEventCard(TimelineEventCard timelineEventCard) throws IOException {
        FXMLLoader card = new FXMLLoader(getClass().getResource("../fxml/eventCard.fxml"));
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

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void populateGrid(GridPane gridPane) {

        int numCols = gridPane.getColumnConstraints().size();
        int numRows = gridPane.getRowConstraints().size();

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(gridPane, i, j);
            }
        }
    }

    private void addPane(GridPane gridPane, int colIndex, int rowIndex) {
        Pane pane = new Pane();

        gridPane.add(pane, colIndex, rowIndex);
    }
}


