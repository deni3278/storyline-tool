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
 * The Timeline controller contains the logic of the gridpane(timeline) shown on the project page.
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

        //checks if the drag event source is an eventcard
        timelineGridPane.setOnDragOver(event -> {
            Node source = (Node) event.getGestureSource();
            if (source.getUserData() instanceof EventCard) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        timelineGridPane.setOnDragDropped(event -> {

            //gets the row and column index according to the event's mouse coordinate
            Bounds cellBounds = timelineGridPane.impl_getCellBounds(0, 0);
            int columnIndex = (int) Math.floor(event.getX() / cellBounds.getWidth());
            int rowIndex = (int) Math.floor(event.getY() / cellBounds.getHeight());

            //checks if the newly dropped eventcard overlaps the current timeline event cards
            if (checkOverlap(columnIndex, rowIndex)) return;

            //gets the source object of the event, which is an HBox containing an eventcard in its userdata
            HBox source = (HBox) event.getGestureSource();
            EventCard sourceEventCard = (EventCard) source.getUserData();

            /*
            if the source is not an instance of TimelineEventCard it means that it comes from outside the timeline
            and should be added to the timeline as a new TimelineEventCard. Otherwise the source should be treated
            as a TimelineEventCard from within the timeline and be moved to the corresponding coordinate
            */
            if (!(sourceEventCard instanceof TimelineEventCard)) {
                addNewTimelineEventCard(columnIndex, rowIndex, sourceEventCard);
            } else {
                moveTimelineEventCard(source, columnIndex, rowIndex, (TimelineEventCard) sourceEventCard);
            }
            event.consume();
        });
    }

    private void moveTimelineEventCard(HBox source, int columnIndex, int rowIndex, TimelineEventCard sourceEventCard) {
        TimelineEventCard timelineEventCard;
        timelineGridPane.getChildren().remove(source);
        timelineEventCard = sourceEventCard;
        timelineEventCard.setX(columnIndex);
        timelineEventCard.setY(rowIndex);
        timelineGridPane.add(source, columnIndex, rowIndex);
    }

    private void addNewTimelineEventCard(int columnIndex, int rowIndex, EventCard sourceEventCard) {
        TimelineEventCard timelineEventCard;
        timelineEventCard = new TimelineEventCard(sourceEventCard.getTitle(),
                sourceEventCard.getColorString(), sourceEventCard.getEventContent(), columnIndex, rowIndex);
        addTimelineEventCard(timelineEventCard);
    }

    public Timeline getTimeline() {
        return this.timeline;
    }

    public ArrayList<TimelineEventCard> getTimelineEventCards() {
        return this.timelineEventCards;
    }


    private boolean checkOverlap(int columnIndex, int rowIndex) {
        boolean overlap = false;
        for (TimelineEventCard eventCard : this.timelineEventCards) {
            if (eventCard.getX() == columnIndex && eventCard.getY() == rowIndex) {
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

        //clears the current grid nodes.
        timelineGridPane.getChildren().removeIf(node -> node instanceof HBox);

        //converts the model timeline eventcards into interactable hboxes and adds them to the gridpane
        timelineEventCards.forEach(timelineEventCard -> {
            addTimelineEventCard(timelineEventCard);
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

    private void addTimelineEventCard(TimelineEventCard timelineEventCard) {

        //wrap the model timeline eventcard with an interactable hbox
        HBox interactableEventCard = null;
        try {
            interactableEventCard = createTimelineEventCard(timelineEventCard);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

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
            contextMenu.show(eventCard, event.getScreenX(), event.getScreenY());
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

}