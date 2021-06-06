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

        final Color RED = Color.rgb(254, 0, 78, 0.2);
        final Color GREEN = Color.rgb(44, 204, 112, 0.2);
        final Color BLUE = Color.rgb(0, 193, 254, 0.2);

        EventCard eventCard1 = new EventCard("eventcard1", BLUE.toString(), "eventcard1content");
        EventCard eventCard2 = new EventCard("eventcard2", RED.toString(), "eventcard2content");
        EventCard eventCard3 = new EventCard("eventcard3", GREEN.toString(), "eventcard3content");
        localStorage.saveEventCard(eventCard1);
        localStorage.saveEventCard(eventCard2);
        System.out.println("localStorage.saveEventCard(eventCard3) = " + localStorage.saveEventCard(eventCard3));;

        TimelineEventCard timelineEventCard1 = new TimelineEventCard("test", BLUE.toString(), "testesteteet", 1, 1);
        TimelineEventCard timelineEventCard2 = new TimelineEventCard("test", BLUE.toString(), "testesteteet", 2, 1);
        TimelineEventCard timelineEventCard3 = new TimelineEventCard("test", BLUE.toString(), "testesteteet", 4, 1);
        ArrayList<TimelineEventCard> eventCards = new ArrayList<>(Arrays.asList(timelineEventCard1, timelineEventCard2, timelineEventCard3));
        Timeline timelineOut = new Timeline(eventCards, "Test");
        Timeline timelineOut2 = new Timeline(eventCards, "TestTEST");


        System.out.println("localStorage.saveTimeline(timelineOut) = " + localStorage.saveTimeline(timelineOut));
        System.out.println("localStorage.saveTimeline(timelineOut) = " + localStorage.saveTimeline(timelineOut2));

        localStorage.getAllTimelines().forEach(System.out::println);

    }

}
