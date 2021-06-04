package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import storyline.App;

public class WelcomeController {
    @FXML
    private VBox blankIconButton, fictionIconButton, historicalIconButton, screenplayIconButton;

    @FXML
    private IconButtonController blankIconButtonController, fictionIconButtonController, historicalIconButtonController, screenplayIconButtonController, browseIconButtonController;

    @FXML
    private void initialize() {
        initButtons();

    }

    private void initButtons() {
        blankIconButton.setOnMouseClicked(e -> {
            TimelineController.startFromBlank("test");
            Context.getInstance().activate("projectPage");
            System.out.println("Blank");
        });
        blankIconButtonController.setImage(new Image(getClass().getResource("../images/blank.png").toExternalForm()));
        blankIconButtonController.setText("Blank");

        fictionIconButton.setOnMouseClicked(e -> {
            System.out.println("Fiction");
        });
        fictionIconButtonController.setImage(new Image(getClass().getResource("../images/fiction.png").toExternalForm()));
        fictionIconButtonController.setText("Fiction");

        historicalIconButton.setOnMouseClicked(e -> {
            System.out.println("Historical");
        });
        historicalIconButtonController.setImage(new Image(getClass().getResource("../images/historical.png").toExternalForm()));
        historicalIconButtonController.setText("Historical");

        screenplayIconButton.setOnMouseClicked(e -> {
            System.out.println("Screenplay");
        });
        screenplayIconButtonController.setImage(new Image(getClass().getResource("../images/screenplay.png").toExternalForm()));
        screenplayIconButtonController.setText("Screenplay");
    }
}
