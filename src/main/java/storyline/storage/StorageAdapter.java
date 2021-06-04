package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Timeline;

import java.util.ArrayList;

public interface StorageAdapter {

    boolean saveEventCard(EventCard eventCard);

    ArrayList<EventCard> getAllEventCards();

    Timeline getTimeline(String name);

    ArrayList<Timeline> getAllTimelines();

    boolean saveTimeline(Timeline timeline);

    boolean updateTimeline(Timeline timeline);

    boolean deleteTimeLine(String name);
}
