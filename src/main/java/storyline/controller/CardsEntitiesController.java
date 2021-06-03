package storyline.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import storyline.model.EventCard;

import java.io.IOException;


public class CardsEntitiesController {
    //Preset colors
    final Color RED = Color.rgb(254, 0, 78, 0.2);
    final Color GREEN = Color.rgb(44, 204, 112, 0.2);
    final Color BLUE = Color.rgb(0, 193, 254, 0.2);

    @FXML
    VBox vLayout;

    @FXML
    private void initialize() throws IOException {

        EventCard eventCard1 = new EventCard("test", RED.toString(), "testtesttest");
        EventCard eventCard2 = new EventCard("test2", BLUE.toString(), "testtesttest");
        EventCard eventCard3 = new EventCard("test3", GREEN.toString(), "testtesttest");
        createCard(eventCard1);
        createCard(eventCard2);
        createCard(eventCard3);
    }

    private void createCard(EventCard eventCardParam) throws IOException {
        //Load the fxml of the event card
        FXMLLoader card = new FXMLLoader(getClass().getResource("../fxml/eventCard.fxml"));
        //Assign a controller to the newly loaded card, and pass the variables for the card
        card.setController(new EventCardController(eventCardParam.getTitle(), eventCardParam.getEventContent(), eventCardParam.getColor()));

        HBox eventCard = card.load();
        eventCard.setUserData(eventCardParam);

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
    }

}
