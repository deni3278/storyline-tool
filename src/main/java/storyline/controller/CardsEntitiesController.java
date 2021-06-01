package storyline.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Arrays;


public class CardsEntitiesController {

    //Preset colors
    final Color RED = Color.rgb(254,0,78,0.2);
    final Color GREEN = Color.rgb(44,204,112,0.2);
    final Color BLUE = Color.rgb(0,193,254,0.2);


    @FXML
    VBox vLayout;
    @FXML
    private void initialize() throws IOException {


        createCard("title1","desc1",BLUE);
        createCard("title2","desc2",RED);
        createCard("title3","desc3",GREEN);
    }


    private void createCard(String title, String description, Color color) throws IOException {
        //Load the fxml of the event card
        FXMLLoader card = new FXMLLoader(getClass().getResource("../fxml/eventCard.fxml"));
        //Assign a controller to the newly loaded card, and pass the variables for the card
        card.setController(new EventCardController(title, description, color));

        HBox eventCard = card.load();
        System.out.println(card.toString());

        //eventCard.setUserData(new storyline.model.EventCard(title,description));

        //new storyline.model.EventCard();

        //Add the new card to the vbox
        vLayout.getChildren().add(eventCard);
    }
}
