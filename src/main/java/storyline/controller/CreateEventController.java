package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import storyline.model.EventCard;
import storyline.storage.LocalStorage;

import java.io.IOException;

/**
 * The Create event controller contains the logic for the popup that is shown when creating a new event card.
 */
public class CreateEventController {
    @FXML
    private Button btnSave, btnCancel;

    @FXML
    private TextField fldTitle;

    @FXML
    private TextArea areaContent;

    @FXML
    private ChoiceBox<String> choiceColor;

    @FXML
    private void initialize() {
        choiceColor.getItems().addAll("Red", "Blue", "Green");
    }

    @FXML
    private void handleSave() {
        EventCard eventCard = new EventCard(fldTitle.getText(), getColor(choiceColor.getSelectionModel().getSelectedItem()), areaContent.getText());

        try {
            CardsEntitiesController cardsEntitiesController = Context.getInstance().projectPageController.getCardsEntitiesController();
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

    public Button getBtnSave() {
        return btnSave;
    }

    public Button getBtnCancel(){
        return btnCancel;
    }
}