package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import storyline.model.EventCard;
import storyline.storage.LocalStorage;

import java.io.IOException;

public class CreateEventController {
    @FXML
    Button btnAccept;

    public Button getBtnAccept() {
        return btnAccept;
    }

    @FXML
    TextField fldTitle;

    @FXML
    TextArea areaContent;

    @FXML
    ChoiceBox<String> choiceColor;

    @FXML
    private void initialize() {
        choiceColor.getItems().addAll("Red", "Blue", "Green");

    }

    @FXML
    public void handleAccept() {
        EventCard eventCard = new EventCard(fldTitle.getText(), getColor(choiceColor.getSelectionModel().getSelectedItem()),
                areaContent.getText());
        try {
            CardsEntitiesController cardsEntitiesController = Context.getInstance().projectPageController.cardsEntitiesController;
            cardsEntitiesController.createCard(eventCard);
            cardsEntitiesController.saveCard(LocalStorage.getInstance(), eventCard);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getColor(String choice) {
        switch (choice) {
            case "Red":
                return CardsEntitiesController.RED.toString();
            case "Blue":
                return CardsEntitiesController.BLUE.toString();

            default:
                return CardsEntitiesController.GREEN.toString();
        }
    }
}
