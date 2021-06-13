package storyline.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


/**
 * The Timeline model class contains the name of the Timeline plus the current timelineeventcards in it.
 */
public class Timeline extends Identifiable implements Serializable {
    private String name;
    private ArrayList<TimelineEventCard> eventCards;
    private static final long serialVersionUID = 355062240;


    public Timeline(ArrayList<TimelineEventCard> eventCards, String name) {
        this.eventCards = eventCards;
        this.name = name;
        generateID();
    }

    public Timeline(ArrayList<TimelineEventCard> eventCards, String name, UUID ID) {
        this.eventCards = eventCards;
        this.name = name;
        setID(ID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeline timeline = (Timeline) o;
        return Objects.equals(getName(), timeline.getName()) && Objects.equals(getEventCards(), timeline.getEventCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEventCards());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TimelineEventCard> getEventCards() {
        return eventCards;
    }

    public void setEventCards(ArrayList<TimelineEventCard> eventCards) {
        this.eventCards = eventCards;
    }


    @Override
    public String toString() {
        return this.getName();
    }
}
