package storyline.model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class EventCard extends Identifiable implements Serializable {
    private String title;
    private String colorString;
    private ArrayList<Entity> EventAttachedEntities = new ArrayList<>();
    private String eventContent;


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
        return EventAttachedEntities;
    }

    public void setEventAttachedEntities(ArrayList<Entity> eventAttachedEntities) {
        EventAttachedEntities = eventAttachedEntities;
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
                ", EventAttachedEntities=" + EventAttachedEntities +
                ", eventContent='" + eventContent + '\'' +
                '}';
    }


}
