package storyline.storage;

import storyline.model.Timeline;

import java.util.ArrayList;

public interface StorageAdapter {

    Timeline getTimeline(int ID);

    ArrayList<Timeline> getAllTimelines();

    boolean saveTimeline(Timeline timeline);

    boolean updateTimeline(Timeline timeline);

    boolean deleteTimeLine(int ID);
}
