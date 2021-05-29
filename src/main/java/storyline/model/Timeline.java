package storyline.model;

import storyline.storage.Identifiable;

import java.io.Serializable;
import java.util.ArrayList;

public class Timeline implements Serializable, Identifiable {
    private String name;
    private ArrayList<EventCard> eventCards;

    public Timeline(ArrayList<EventCard> eventCards, String name) {
        this.eventCards = eventCards;
        this.name = name;
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



    @Override
    public String toString() {
        return "Timeline{" +
                "name='" + name + '\'' +
                ", eventCards=" + eventCards +
                '}';
    }
    @Override
    public String getIdentifier() {
        return String.valueOf(this.name);
    }


}
