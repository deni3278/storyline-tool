package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Icon button controller contains the logic for all the buttons on the welcome page.
 */
public class IconButtonController {
    @FXML
    private ImageView image;

    @FXML
    private Label text;

    public void setImage(Image image) {
        this.image.setImage(image);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
