package storyline.storage;

import javafx.scene.paint.Color;
import storyline.model.EventCard;
import storyline.model.Timeline;
import storyline.model.TimelineEventCard;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        StorageAdapter localStorage = LocalStorage.getInstance();

        Color color = Color.BLUE;

        TimelineEventCard eventCard1 = new TimelineEventCard("test", color.toString(), "testesteteet", 1, 1);
        TimelineEventCard eventCard2 = new TimelineEventCard("test", color.toString(), "testesteteet", 2, 1);
        TimelineEventCard eventCard3 = new TimelineEventCard("test", color.toString(), "testesteteet", 4, 1);
        ArrayList<TimelineEventCard> eventCards = new ArrayList<>(Arrays.asList(eventCard1, eventCard2, eventCard3));
        Timeline timelineOut = new Timeline(eventCards, "Test");


        System.out.println("localStorage.saveTimeline(timelineOut) = " + localStorage.saveTimeline(timelineOut));

        localStorage.getAllTimelines().forEach(System.out::println);

    }

}
