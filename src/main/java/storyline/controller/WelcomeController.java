package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;

import java.util.Optional;

/**
 * The Welcome controller contains the logic for the buttons present on the welcome page.
 */
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
        StorageAdapter storage = LocalStorage.getInstance();

        blankIconButton.setOnMouseClicked(e -> {
            startFromBlankDialog(storage);
        });
        blankIconButtonController.setImage(new Image(getClass().getResource("../images/blank.png").toExternalForm()));
        blankIconButtonController.setText("Blank");

        fictionIconButton.setOnMouseClicked(e -> {
            System.out.println("Fiction");
        });
        fictionIconButton.setDisable(true);
        fictionIconButtonController.setImage(new Image(getClass().getResource("../images/fiction.png").toExternalForm()));
        fictionIconButtonController.setText("Fiction");

        historicalIconButton.setOnMouseClicked(e -> {
            System.out.println("Historical");
        });
        historicalIconButton.setDisable(true);
        historicalIconButtonController.setImage(new Image(getClass().getResource("../images/historical.png").toExternalForm()));
        historicalIconButtonController.setText("Historical");

        screenplayIconButton.setOnMouseClicked(e -> {
            System.out.println("Screenplay");
        });
        screenplayIconButton.setDisable(true);
        screenplayIconButtonController.setImage(new Image(getClass().getResource("../images/screenplay.png").toExternalForm()));
        screenplayIconButtonController.setText("Screenplay");
    }

    private void startFromBlankDialog(StorageAdapter storage) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText("Input timeline name");
        inputDialog.setTitle("Timeline");
        Optional<String> input = inputDialog.showAndWait();
        if (input.isPresent()) {
            String name = input.get();
            if (name.length() == 0) name = "Timeline";
            Context instance = Context.getInstance();
            instance.getProjectPageController().getCardsEntitiesController().loadCardsFromStorage(storage);
            instance.getProjectPageController().getTimelineController().startFromBlank(name);
            instance.activate("projectPage");
            System.out.println("Blank");
        }
    }
}
