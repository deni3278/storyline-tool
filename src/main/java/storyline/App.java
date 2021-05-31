package storyline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/projectPage.fxml"));
        Parent root = loader.load();

        stage.setTitle("Storyline Tool");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
