package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Timeline;

import java.util.ArrayList;

/**
 * The interface StorageAdapter follows the adapter pattern and specifies methods that need to be implemented by a
 * storage system like a database or a local disk.
 */
public interface StorageAdapter {

    /**
     * Saves eventcard. is meant for both updating and creating a new eventcard.
     *
     * @param eventCard the event card
     * @return true if successfull
     */
    boolean saveEventCard(EventCard eventCard);

    /**
     * Delete event card boolean.
     *
     * @param ID the id
     * @return true if successfull
     */
    boolean deleteEventCard(String ID);

    /**
     * Gets all event cards.
     *
     * @return all event cards
     */
    ArrayList<EventCard> getAllEventCards();

    /**
     * Gets timeline from specified ID.
     *
     * @param ID the ID
     * @return the timeline
     */
    Timeline getTimeline(String ID);

    /**
     * Gets all timelines.
     *
     * @return all timelines
     */
    ArrayList<Timeline> getAllTimelines();

    /**
     * Saves specified timeline. is meant for both updating and creating a new timeline
     *
     * @param timeline the timeline
     * @return true if successfull
     */
    boolean saveTimeline(Timeline timeline);

    /**
     * Deletes timeline from specified ID.
     *
     * @param ID the ID
     * @return true if successfull
     */
    boolean deleteTimeLine(String ID);
}
