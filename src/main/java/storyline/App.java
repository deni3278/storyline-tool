package storyline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storyline.controller.Context;
import storyline.controller.TimelineController;

import java.io.IOException;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("fxml/welcome.fxml"));
        FXMLLoader projectPageLoader = new FXMLLoader(getClass().getResource("fxml/projectPage.fxml"));


        Parent root = welcomeLoader.load();
        Pane projectPage =  projectPageLoader.load();

        Context context = Context.getInstance();

        context.setWelcomeController(welcomeLoader.getController());
        context.setProjectPageController(projectPageLoader.getController());

        context.addScreen("projectPage", projectPage);
        context.getInstance().addScreen("welcome",(Pane) root);


        stage.setTitle("Storyline Tool");
        Scene main = new Scene(root);
        stage.setScene(main);
        stage.show();
        stage.setMaximized(true);
        Context.getInstance().setMainScene(main);


    }
    public static void main(String[] args) {
        launch(args);
    }
}
