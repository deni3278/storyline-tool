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
 * for each individual event card and is instantiated each time a event card is made.
 */
public class EventCardController {
    @FXML
    private Label title, description;

    @FXML
    private VBox vLayout;

    public EventCardController(String title, String description, Paint color) {
        Platform.runLater(()->{
            this.title.setText(title);
            this.description.setText(description);
            this.description.setWrapText(true);
            this.vLayout.setBackground(new Background(new BackgroundFill(color, null, null)));
        });
    }
}

