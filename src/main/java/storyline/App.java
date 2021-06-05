package storyline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storyline.controller.Context;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/welcome.fxml"));
        Parent root = loader.load();

        stage.setTitle("Storyline Tool");
        Scene main = new Scene(root);
        stage.setScene(main);
        stage.show();
        stage.setMaximized(true);
        Context.getInstance().setMainScene(main);
        Context.getInstance().addScreen("projectPage",new FXMLLoader(getClass().getResource("fxml/projectPage.fxml")).load());
        Context.getInstance().addScreen("welcome",new FXMLLoader(getClass().getResource("fxml/welcome.fxml")).load());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
