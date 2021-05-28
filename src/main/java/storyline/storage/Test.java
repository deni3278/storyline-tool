package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Timeline;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        StorageAdapter localStorage =  LocalStorage.getInstance();


        EventCard eventCard1 = new EventCard("test", 1, "testesteteet");
        ArrayList<EventCard> eventCards = new ArrayList<>(Arrays.asList(eventCard1));
        Timeline timelineOut = new Timeline(eventCards, 1, "testtimeline");
        Timeline timelineOut2 = new Timeline(eventCards, 2, "testtimeline");
        Timeline timelineOut3 = new Timeline(eventCards, 3, "testtimeline");
        Timeline timelineOut4 = new Timeline(eventCards, 4, "testtimeline");

        localStorage.saveTimeline(timelineOut);
        localStorage.saveTimeline(timelineOut2);
        localStorage.saveTimeline(timelineOut3);
        localStorage.saveTimeline(timelineOut4);


        localStorage.getAllTimelines().forEach(System.out::println);

        localStorage.deleteTimeLine(1);
        localStorage.deleteTimeLine(2);
        localStorage.deleteTimeLine(3);
        localStorage.deleteTimeLine(4);
        localStorage.getAllTimelines().forEach(System.out::println);
    }
}
