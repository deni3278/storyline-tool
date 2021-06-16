package storyline.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import storyline.model.EventCard;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Cards entities controller contains the logic for the list of event cards
 * shown on the right side of the project page.
 */
public class CardsEntitiesController {

//Preset colors
    public static final Color RED = Color.rgb(254, 0, 78, 0.2);

    public static final Color GREEN = Color.rgb(44, 204, 112, 0.2);

    public static final Color BLUE = Color.rgb(0, 193, 254, 0.2);

    private final ObservableList<EventCard> eventCards = FXCollections.observableArrayList();

    @FXML
    private VBox vLayout;

    @FXML
    private TextField fldSearch;

    @FXML
    private Label lblCounter;

    @FXML
    private void initialize() {
        lblCounter.textProperty().bind(Bindings.size(eventCards).asString());
    }

    /**
     * Iterate through the vbox vLayout and check if the text on the eventCard contains any of the text in the searchbar textfield
     */
    @FXML
    private void search() {
        for (int i = 0; i < vLayout.getChildren().size(); i++) {
            if (((EventCard)vLayout.getChildren().get(i).getUserData()).getTitle().toLowerCase().contains(fldSearch.getText().toLowerCase())){
                vLayout.getChildren().get(i).setVisible(true);
                vLayout.getChildren().get(i).setManaged(true);
            }
            else if (((EventCard)vLayout.getChildren().get(i).getUserData()).getEventContent().toLowerCase().contains(fldSearch.getText().toLowerCase())){
                vLayout.getChildren().get(i).setVisible(true);
                vLayout.getChildren().get(i).setManaged(true);
            }
            else{
                vLayout.getChildren().get(i).setVisible(false);
                vLayout.getChildren().get(i).setManaged(false);
            }

        }

    }

    /**
     * Load cards from storage.
     *
     * @param storageAdapter the storage adapter
     */
    public void loadCardsFromStorage(StorageAdapter storageAdapter) {

        vLayout.getChildren().clear();
        eventCards.clear();

        ArrayList<EventCard> eventCardsFromSave = getEventCards(storageAdapter);
        eventCardsFromSave.forEach(eventCard -> {
            try {
                createCard(eventCard);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    /**
     * Remove event card from the list.
     *
     * @param eventCard the event card
     */
    public void removeEventCard(HBox eventCard) {
        this.eventCards.remove(eventCard.getUserData());
        this.vLayout.getChildren().remove(eventCard);
    }

    /**
     * Save current cards.
     *
     * @param storageAdapter the storage adapter
     */
    public void saveCurrentCards(StorageAdapter storageAdapter) {
        eventCards.forEach(storageAdapter::saveEventCard);
    }

    /**
     * Save card to storage.
     *
     * @param storageAdapter the storage adapter
     * @param eventCard      the event card
     */
    public void saveCard(StorageAdapter storageAdapter, EventCard eventCard) {
        storageAdapter.saveEventCard(eventCard);
    }

    /**
     * Delete card from storage.
     *
     * @param storageAdapter the storage adapter
     * @param eventCard      the event card
     */
    public void deleteCard(StorageAdapter storageAdapter, EventCard eventCard) {
        storageAdapter.deleteEventCard(eventCard.getIdentifier().toString());
    }

    private ArrayList<EventCard> getEventCards(StorageAdapter storageAdapter) {
        return storageAdapter.getAllEventCards();
    }

    /**
     * Create card and add it to the list.
     *
     * @param eventCardParam the event card param
     * @throws IOException the io exception
     */
    public void createCard(EventCard eventCardParam) throws IOException {
        //Load the fxml of the event card
        FXMLLoader card = new FXMLLoader(getClass().getResource("../fxml/eventCard.fxml"));
        //Assign a controller to the newly loaded card, and pass the variables for the card
        card.setController(new EventCardController(eventCardParam.getTitle(), eventCardParam.getEventContent(), eventCardParam.getColor()));

        HBox eventCard = card.load();
        eventCard.setUserData(eventCardParam);

        eventCard.setOnContextMenuRequested(event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Delete");
            menuItem.setOnAction(event1 -> {
                removeEventCard(eventCard);
                deleteCard(LocalStorage.getInstance(),eventCardParam);
            });
            contextMenu.getItems().add(menuItem);
            contextMenu.show(eventCard, event.getScreenX(), event.getScreenY());
        });
        eventCard.setOnDragDetected(event -> {
            ImageView preview = new ImageView(eventCard.snapshot(null, null));
            Dragboard db = eventCard.startDragAndDrop(TransferMode.ANY);
            db.setDragView(preview.getImage());
            ClipboardContent content = new ClipboardContent();
            content.putString("EventCard");
            db.setContent(content);
        });
        eventCard.setOnMouseDragged(event -> event.setDragDetect(true));

        //Add the new card to the vbox
        vLayout.getChildren().add(eventCard);
        eventCards.add(eventCardParam);
    }

}
