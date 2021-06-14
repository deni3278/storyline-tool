import javafx.scene.paint.Color;
import org.junit.Test;
import storyline.model.Timeline;
import storyline.model.TimelineEventCard;
import storyline.storage.DatabaseStorage;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DatabaseStorageTest {


    final Color RED = Color.rgb(254, 0, 78, 0.2);
    final Color GREEN = Color.rgb(44, 204, 112, 0.2);
    final Color BLUE = Color.rgb(0, 193, 254, 0.2);
    DatabaseStorage databaseStorage = DatabaseStorage.getInstance();



    @Test
    public void saveTimeline() {
        TimelineEventCard timelineEventCard1 = new TimelineEventCard("test", BLUE.toString(), "testesteteet", 1, 1);
        TimelineEventCard timelineEventCard2 = new TimelineEventCard("test", BLUE.toString(), "testesteteet", 2, 1);
        TimelineEventCard timelineEventCard3 = new TimelineEventCard("test", BLUE.toString(), "testesteteet", 4, 1);
        ArrayList<TimelineEventCard> eventCards = new ArrayList<>(Arrays.asList(timelineEventCard1, timelineEventCard2, timelineEventCard3));
        Timeline timelineOut = new Timeline(eventCards, "Test");
        databaseStorage.saveTimeline(timelineOut);
        Timeline timelineIn = databaseStorage.getTimeline(timelineOut.getIdentifier().toString());
        assertEquals(timelineIn.getIdentifier(),timelineOut.getIdentifier());
    }

    @Test
    public void deleteTimeLine() {
    }
}