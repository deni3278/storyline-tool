package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import storyline.model.Timeline;

import java.util.ArrayList;

/**
 * The Timeline list view controller contains the logic for the popup that shows
 * a list of timelines the user can chose to load from when pressing one of the load buttons on the welcome page.
 */
public class TimelineListViewController {
    @FXML
    private ListView timelineListView;

    @FXML
    Button closeButton, loadButton;

    public ListView getTimelineListView() {
        return timelineListView;
    }

    public ArrayList<Timeline> getTimelines() {
        return new ArrayList<>(timelineListView.getItems());
    }

    public void setTimelines(ArrayList<Timeline> timelines) {
        this.timelineListView.getItems().setAll(timelines);
    }
}
