package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import storyline.model.EventCard;

import java.io.IOException;

public class CreateEventController {
    @FXML
    Button btnSave, btnCancel;

    public Button getBtnSave() {
        return btnSave;
    }

    public Button getBtnCancel(){
        return btnCancel;
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
    public void handleSave() {
        EventCard eventCard = new EventCard(fldTitle.getText(), getColor(choiceColor.getSelectionModel().getSelectedItem()),
                areaContent.getText());
        try {
            Context.getInstance().projectPageController.cardsEntitiesController.createCard(eventCard);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getColor(String choice) {
        switch (choice) {
            case "Red":
                return CardsEntitiesController.RED.toString();
            case "Blue":
                return CardsEntitiesController.GREEN.toString();
            case "Green":
                return CardsEntitiesController.BLUE.toString();
        }
        return CardsEntitiesController.GREEN.toString();
    }


    }

