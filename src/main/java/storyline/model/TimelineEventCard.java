package storyline.model;

public class TimelineEventCard extends EventCard {

    private double posX;
    private double posY;


    public TimelineEventCard(String name, String colorString, String eventContent, double posX, double posY) {
        super(name, colorString, eventContent);
        this.posX = posX;
        this.posY = posY;
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
