package storyline.storage;

import storyline.model.Timeline;

import java.util.ArrayList;

public class DatabaseStorage implements StorageAdapter{

    @Override
    public Timeline getTimeline(int ID) {
        return null;
    }

    @Override
    public ArrayList<Timeline> getAllTimelines() {
        return null;
    }

    @Override
    public boolean saveTimeline(Timeline timeline) {
        return false;
    }

    @Override
    public boolean updateTimeline(Timeline timeline) {
        return false;
    }

    @Override
    public boolean deleteTimeLine(int ID) {
        return false;
    }
}