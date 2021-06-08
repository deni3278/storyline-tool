package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import storyline.storage.DatabaseStorage;
import storyline.storage.LocalStorage;
import storyline.storage.StorageAdapter;

import java.io.File;

public class LoadController {
    @FXML
    private VBox databaseIconButton, localIconButton;

    @FXML
    private IconButtonController databaseIconButtonController, localIconButtonController;


    @FXML
    private void initialize() {
        databaseIconButton.setOnMouseClicked(e -> {
            if (loadDatabase()) {
                Context.getInstance().activate("projectPage");
            }
        });
        databaseIconButtonController.setImage(new Image(getClass().getResource("../images/database.png").toExternalForm()));
        databaseIconButtonController.setText("From Database");

        localIconButton.setOnMouseClicked(e -> {

            if (loadLocal()) {
                Context.getInstance().activate("projectPage");
            }
        });
        localIconButtonController.setImage(new Image(getClass().getResource("../images/local.png").toExternalForm()));
        localIconButtonController.setText("From Local File");
    }

    private boolean loadLocal() {
        StorageAdapter localStorage = LocalStorage.getInstance();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Story files", "*.story"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "StorylineTool" + File.separator + "Timelines"));

        File fileChosen = fileChooser.showOpenDialog(new Stage());
        if (fileChosen != null) {
            String fileName = LocalStorage.getFileNameWithoutExtension(fileChosen.getName());
            System.out.println(fileName);

            Context context = Context.getInstance();
            context.getProjectPageController().getTimelineController().loadGridFromSave(localStorage, fileName);
            return true;
        }
        return false;
    }

    private boolean loadDatabase() {

        StorageAdapter databaseStorage = DatabaseStorage.getInstance();
        Context context = Context.getInstance();
        context.getProjectPageController().getTimelineController().loadGridFromSave(databaseStorage,"44EA6116-B5EC-4304-A82F-2E5FA8037E77");




        return false;
    }
}
