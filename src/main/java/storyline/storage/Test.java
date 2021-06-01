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
        Color color2 = Color.valueOf(color.toString());
        System.out.println(color2);

        System.out.println(color.toString());
        TimelineEventCard eventCard1 = new TimelineEventCard("test", color.toString(), "testesteteet",1,1);
        ArrayList<TimelineEventCard> eventCards = new ArrayList<>(Arrays.asList(eventCard1));
        Timeline timelineOut = new Timeline(eventCards, "a");
        Timeline timelineOut2 = new Timeline(eventCards, "b");
        Timeline timelineOut3 = new Timeline(eventCards, "c");
        Timeline timelineOut4 = new Timeline(eventCards, "d");

        localStorage.saveTimeline(timelineOut);
        localStorage.saveTimeline(timelineOut2);
        localStorage.saveTimeline(timelineOut3);
        localStorage.saveTimeline(timelineOut4);

        System.out.println(localStorage.getTimeline("a"));
        localStorage.getAllTimelines().forEach(System.out::println);

        localStorage.deleteTimeLine("a");
        localStorage.deleteTimeLine("b");
        localStorage.deleteTimeLine("c");
        localStorage.deleteTimeLine("d");
        localStorage.getAllTimelines().forEach(System.out::println);


    }

}
