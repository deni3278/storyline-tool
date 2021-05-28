package storyline.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Timeline implements Serializable {
    private String name;
    private ArrayList<EventCard> eventCards;
    private int timelineID;

    public Timeline(ArrayList<EventCard> eventCards, int timelineID,String name) {
        this.eventCards = eventCards;
        this.name = name;
        this.timelineID = timelineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EventCard> getEventCards() {
        return eventCards;
    }

    public void setEventCards(ArrayList<EventCard> eventCards) {
        this.eventCards = eventCards;
    }

    public int getTimelineID() {
        return timelineID;
    }

    public void setTimelineID(int timelineID) {
        this.timelineID = timelineID;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "name='" + name + '\'' +
                ", eventCards=" + eventCards +
                ", timelineID=" + timelineID +
                '}';
    }
}
