package storyline.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EventCard implements Serializable {

    private String name;
    private int eventID;
    private ArrayList<Entity> EventAttachedEntities = new ArrayList<>();
    private String eventContent;

    public EventCard(String name, int eventID, String eventContent) {
        this.name = name;
        this.eventID = eventID;
        this.eventContent = eventContent;
    }

    @Override
    public String toString() {
        return "EventCard{" +
                "name='" + name + '\'' +
                ", eventID=" + eventID +
                ", EventAttachedEntities=" + EventAttachedEntities +
                ", eventContent='" + eventContent + '\'' +
                '}';
    }
}
