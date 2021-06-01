package storyline.model;

import javafx.scene.paint.Color;

public class TimelineEventCard extends EventCard {

    private double posX;
    private double posY;


    public TimelineEventCard(String name, String colorString, String eventContent) {
        super(name, colorString, eventContent);
    }


    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
