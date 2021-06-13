package storyline.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * The Event card controller is the controller
 * for each individual eventcard and is instantiated each time a eventcard is made.
 */
public class EventCardController {
    //Below properties are initially not in use, but are used when this controller is assigned to a new eventCard fxml (pls no delet)
    @FXML
    public Label title, description;
    @FXML
    public VBox vLayout;

    public EventCardController(String title, String description, Paint color) {
        Platform.runLater(()->{
            this.title.setText(title);
            this.description.setText(description);
            this.description.setWrapText(true);
            this.vLayout.setBackground(new Background(new BackgroundFill(color, null, null)));
        });

    }
}

