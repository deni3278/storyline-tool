package storyline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import storyline.model.EventCard;
import storyline.model.Timeline;
import storyline.model.TimelineEventCard;
import storyline.storage.StorageAdapter;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Timeline controller.
 */
public class TimelineController {

    private Timeline timeline;
    private ArrayList<TimelineEventCard> timelineEventCards;

    @FXML
    public ScrollPane timelineScrollPane;

    @FXML
    GridPane timelineGridPane;

    @FXML
    VBox root;

    @FXML
    Label timelineLabel;

    @FXML
    public void initialize() {

        this.timelineEventCards = new ArrayList<>();

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
            if (overlap) return;

            TimelineEventCard timelineEventCard;
            if (!(sourceEventCard instanceof TimelineEventCard)) {
                System.out.println("from eventcard vbox");
                timelineEventCard = new TimelineEventCard(sourceEventCard.getTitle(),
                        sourceEventCard.getColorString(), sourceEventCard.getEventContent(), columnIndex, rowIndex);
                try {
                    addTimelineEventCard(timelineEventCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                source.setVisible(true);
                source.setManaged(true);
            } else {
                System.out.println("from within gridpane");
                timelineGridPane.getChildren().remove(source);

                timelineEventCard = (TimelineEventCard) sourceEventCard;
                timelineEventCard.setX(columnIndex);
                timelineEventCard.setY(rowIndex);
                timelineGridPane.add(source, columnIndex, rowIndex);
            }
            event.consume();
        });
    }

    public Timeline getTimeline(){
        return this.timeline;
    }

    public ArrayList<TimelineEventCard> getTimelineEventCards() {
        return this.timelineEventCards;
    }

    private boolean checkOverlap(int columnIndex, int rowIndex) {
        boolean overlap = false;
        for (TimelineEventCard eventCard : this.timelineEventCards) {
            System.out.println("x =" + eventCard.getX() + " y = " + eventCard.getY());
            if (eventCard.getX() == columnIndex && eventCard.getY() == rowIndex) {
                System.out.println("overlap!");
                overlap = true;
            }
        }
        return overlap;
    }

    /**
     * method for starting a blank timeline
     *
     * @param timelineName the timeline name
     */
    public void startFromBlank(String timelineName) {
        //initializes variables
        timelineLabel.setText(timelineName);

        this.timeline = new Timeline(new ArrayList<TimelineEventCard>(), timelineName);
        timelineEventCards = timeline.getEventCards();

        //clears the current grid nodes.
        timelineGridPane.getChildren().removeIf(node -> node instanceof HBox);
    }

    /**
     * Load a timeline from save and adds it to the grid.
     *
     * @param storageAdapter the storage adapter
     * @param ID             the id
     */
    public void loadGridFromSave(StorageAdapter storageAdapter, String ID) {


        //initializes variables
        timeline = storageAdapter.getTimeline(ID);
        timelineLabel.setText(timeline.getName());
        timelineEventCards = timeline.getEventCards();
        System.out.println("timelineEventCards = " + timelineEventCards);

        //clears the current grid nodes.
        timelineGridPane.getChildren().removeIf(node -> node instanceof HBox);

        //converts the model timeline eventcards into interactable hboxes and adds them to the gridpane
        timelineEventCards.forEach(timelineEventCard -> {
            try {
                addTimelineEventCard(timelineEventCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Saves current timeline.
     *
     * @param storageAdapter the storage adapter
     */
    public boolean saveCurrentTimeline(StorageAdapter storageAdapter) {
        return storageAdapter.saveTimeline(timeline);
    }

    private void addTimelineEventCard(TimelineEventCard timelineEventCard) throws IOException {

        //wrap the model timeline eventcard with an interactable hbox
        HBox interactableEventCard = createTimelineEventCard(timelineEventCard);

        //adds the hbox to the grid with the coordinates of the event card
        timelineGridPane.add(interactableEventCard, timelineEventCard.getX(), timelineEventCard.getY());

        //if the list of timeline eventcards doesn't contain the newly added eventcard, add it to the list.
        if (!timelineEventCards.contains(timelineEventCard)) timelineEventCards.add(timelineEventCard);
    }

    private HBox createTimelineEventCard(TimelineEventCard timelineEventCard) throws IOException {
        FXMLLoader card = new FXMLLoader(TimelineController.class.getResource("../fxml/eventCard.fxml"));
        //Assign a controller to the newly loaded card, and pass the variables for the card
        EventCardController controller = new EventCardController(timelineEventCard.getTitle(), timelineEventCard.getEventContent(), timelineEventCard.getColor());
        card.setController(controller);

        HBox eventCard = card.load();
        eventCard.setUserData(timelineEventCard);

        eventCard.setOnContextMenuRequested(event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Delete");
            menuItem.setOnAction(event1 -> removeEventCard(eventCard));
            contextMenu.getItems().add(menuItem);
            contextMenu.show(timelineGridPane, event.getScreenX(), event.getScreenY());
        });
        eventCard.setOnDragDetected(event -> {

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

    private void removeEventCard(HBox eventCard) {
        this.timelineGridPane.getChildren().remove(eventCard);
        this.timelineEventCards.remove(eventCard.getUserData());

    }

    //gets a node from the grid pane from a specific coordinate
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}