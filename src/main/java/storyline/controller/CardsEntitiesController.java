package storyline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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


public class CardsEntitiesController {
    //Preset colors
    public static final Color RED = Color.rgb(254, 0, 78, 0.2);
    public static final Color GREEN = Color.rgb(44, 204, 112, 0.2);
    public static final Color BLUE = Color.rgb(0, 193, 254, 0.2);

    private ArrayList<EventCard> eventCards = new ArrayList<>();

    @FXML
    VBox vLayout;

    @FXML
    private void initialize() throws IOException {


    }

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

    public void removeEventCard(HBox eventCard) {
        this.eventCards.remove(eventCard.getUserData());
        this.vLayout.getChildren().remove(eventCard);
    }

    public void saveCurrentCards(StorageAdapter storageAdapter) {
        eventCards.forEach(storageAdapter::saveEventCard);
    }

    public void saveCard(StorageAdapter storageAdapter, EventCard eventCard) {
        storageAdapter.saveEventCard(eventCard);
    }
    public void deleteCard(StorageAdapter storageAdapter, EventCard eventCard) {
        storageAdapter.deleteEventCard(eventCard.getIdentifier().toString());
    }

    private ArrayList<EventCard> getEventCards(StorageAdapter storageAdapter) {
        return storageAdapter.getAllEventCards();
    }

    public void createCard(EventCard eventCardParam) throws IOException {
        //Load the fxml of the event card
        FXMLLoader card = new FXMLLoader(getClass().getResource("../fxml/eventCard.fxml"));
        //Assign a controller to the newly loaded card, and pass the variables for the card
        card.setController(new EventCardController(eventCardParam.getTitle(), eventCardParam.getEventContent(), eventCardParam.getColor()));

        HBox eventCard = card.load();
        eventCard.setUserData(eventCardParam);

        eventCard.setOnContextMenuRequested(event -> {
            System.out.println("contextmenu");
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("delete");
            menuItem.setOnAction(event1 -> {
                removeEventCard(eventCard);
                deleteCard(LocalStorage.getInstance(),eventCardParam);
            });
            contextMenu.getItems().add(menuItem);
            contextMenu.show(vLayout, event.getScreenX(), event.getScreenY());
        });
        eventCard.setOnDragDetected(event -> {

            System.out.println(eventCard + " drag detected");

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
