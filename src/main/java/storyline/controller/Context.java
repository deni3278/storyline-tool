package storyline.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;


/**
 * this class contains global state such as the different controllers and panes.
 * this makes it possible to switch pages and makes it possible for different controllers to access each others instances
 * implemented as singleton
 */
public class Context {
    private final static Context instance = new Context();

    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ProjectPageController projectPageController;

    public WelcomeController welcomeController;

    /**
     * Gets the singleton instance.
     *
     * @return the instance
     */
    public static Context getInstance() {
        return instance;
    }

    /**
     * Gets project page controller.
     *
     * @return the project page controller
     */
    public ProjectPageController getProjectPageController() {
        return projectPageController;
    }

    /**
     * Sets project page controller.
     *
     * @param projectPageController the project page controller
     */
    public void setProjectPageController(ProjectPageController projectPageController) {
        this.projectPageController = projectPageController;
    }

    /**
     * Gets welcome controller.
     *
     * @return the welcome controller
     */
    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    /**
     * Sets welcome controller.
     *
     * @param welcomeController the welcome controller
     */
    public void setWelcomeController(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
    }

    /**
     * Sets main scene.
     *
     * @param main the main scene
     */
    public void setMainScene(Scene main) {
        this.main = main;
    }

    /**
     * Add screen.
     *
     * @param name the name of the page
     * @param pane the pane
     */
    public void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    /**
     * Remove screen.
     *
     * @param name the name of the page
     */
    public void removeScreen(String name) {
        screenMap.remove(name);
    }


    /**
     * switches the current visible page
     *
     * @param name the name of the page
     */
    public void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}