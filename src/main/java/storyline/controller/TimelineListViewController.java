package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import storyline.model.Timeline;

import java.util.ArrayList;

public class TimelineListViewController {

    @FXML
    ListView timelineListView;

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

    @FXML
    public void initialize() {

    }
}