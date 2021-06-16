package storyline.model;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;


/**
 * The Timeline model class contains the name of the Timeline plus the current timeline event cards in it.
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

    /**
     * Iterates through event card array and formats the text based on their placement
     */
    public void export(File file) {
        if (file != null) {
            ArrayList<TimelineEventCard> exportCards = new ArrayList<>(eventCards);
            exportCards.sort(Comparator.comparingInt(TimelineEventCard::getX).thenComparingInt(TimelineEventCard::getY));

            try (PrintWriter printWriter = new PrintWriter(file)) {

                for (TimelineEventCard eventCard : exportCards) {
                    StringBuilder spaces = new StringBuilder();

                    for (int i = 0; i < eventCard.getY(); i++) {
                        spaces.append("    ");
                    }

                    String whiteSpace = spaces.toString();
                    printWriter.write(whiteSpace + eventCard.getTitle() + "\n" + whiteSpace + eventCard.getEventContent() + "\n\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEventCards());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Timeline timeline = (Timeline) o;

        return Objects.equals(getName(), timeline.getName()) && Objects.equals(getEventCards(), timeline.getEventCards());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
