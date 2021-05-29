package storyline.storage;

import storyline.model.Timeline;

import java.util.ArrayList;

public interface StorageAdapter {

    Timeline getTimeline(String name);

    ArrayList<Timeline> getAllTimelines();

    boolean saveTimeline(Timeline timeline);

    boolean updateTimeline(Timeline timeline);

    boolean deleteTimeLine(String name);
}
