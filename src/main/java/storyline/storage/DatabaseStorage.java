package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Timeline;

import java.util.ArrayList;

public class DatabaseStorage implements StorageAdapter{

    @Override
    public boolean saveEventCard(EventCard eventCard) {
        return false;
    }

    @Override
    public ArrayList<EventCard> getAllEventCards() {
        return null;
    }

    @Override
    public Timeline getTimeline(String ID) {
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
    public boolean deleteTimeLine(String name) {
        return false;
    }

}
