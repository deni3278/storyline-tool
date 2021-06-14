package storyline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import storyline.controller.Context;

import java.io.IOException;

/**
 * The App class is the main class where our main function "start" is located that loads the program
 * and delegates the different pages and its controllers to the Context class.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("fxml/welcome.fxml"));
        FXMLLoader projectPageLoader = new FXMLLoader(getClass().getResource("fxml/projectPage.fxml"));

        Parent root = welcomeLoader.load();
        Pane projectPage =  projectPageLoader.load();


        //Saves both the welcome and project page's controllers and panes to the Context class for
        //later access between the 2 pages.
        Context context = Context.getInstance();

        context.setWelcomeController(welcomeLoader.getController());
        context.setProjectPageController(projectPageLoader.getController());

        context.addScreen("projectPage", projectPage);
        context.addScreen("welcome",(Pane) root);

        //starts the program
        stage.setTitle("Storyline Tool");
        Scene main = new Scene(root);
        stage.setScene(main);
        stage.show();
        stage.setMaximized(true);
        context.setMainScene(main);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
