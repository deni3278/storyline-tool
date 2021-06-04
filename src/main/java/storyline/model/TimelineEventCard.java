package storyline.model;

import storyline.storage.Identifiable;

import java.io.Serializable;

public class TimelineEventCard extends EventCard implements Serializable, Identifiable {

    private int x;
    private int y;


    public TimelineEventCard(String name, String colorString, String eventContent, int posX, int posY) {
        super(name, colorString, eventContent);
        this.x = posX;
        this.y = posY;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "TimelineEventCard{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
