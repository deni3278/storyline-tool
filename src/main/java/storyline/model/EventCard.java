package storyline.model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * The model entity EventCard contains the information of a user's eventcard upon being dragged to the timeline,
 * the eventcard gets converted into the subclass TimelineEventCard.
 */
public class EventCard extends Identifiable implements Serializable {
    private String title;
    private String colorString;
    private String eventContent;
    private ArrayList<Entity> eventAttachedEntities = new ArrayList<>();

    private static final long serialVersionUID = 355062238;

    public EventCard(String title, String colorString, String eventContent) {
        this.title = title;
        this.colorString = colorString;
        this.eventContent = eventContent;
        generateID();
    }
    public EventCard(String title, String colorString, String eventContent, UUID ID) {
        this.title = title;
        this.colorString = colorString;
        this.eventContent = eventContent;
        setID(ID);
    }

    public Color getColor() {
        return Color.valueOf(this.getColorString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public ArrayList<Entity> getEventAttachedEntities() {
        return eventAttachedEntities;
    }

    public void setEventAttachedEntities(ArrayList<Entity> eventAttachedEntities) {
        this.eventAttachedEntities = eventAttachedEntities;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    @Override
    public String toString() {
        return "EventCard{" +
                "title='" + title + '\'' +
                ", color='" + getColor() + '\'' +
                ", EventAttachedEntities=" + eventAttachedEntities +
                ", eventContent='" + eventContent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventCard eventCard = (EventCard) o;
        return Objects.equals(getTitle(), eventCard.getTitle()) && Objects.equals(getColorString(), eventCard.getColorString()) && Objects.equals(getEventAttachedEntities(), eventCard.getEventAttachedEntities()) && Objects.equals(getEventContent(), eventCard.getEventContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getColorString(), getEventAttachedEntities(), getEventContent());
    }
}
