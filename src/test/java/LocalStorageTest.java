import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storyline.model.EventCard;
import storyline.storage.LocalStorage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LocalStorageTest {

    final Color RED = Color.rgb(254, 0, 78, 0.2);
    final Color GREEN = Color.rgb(44, 204, 112, 0.2);
    final Color BLUE = Color.rgb(0, 193, 254, 0.2);
    LocalStorage localStorage = LocalStorage.getInstance();


    @Test
    public void saveEventCard() {
        EventCard eventCardOut = new EventCard("eventcard1", BLUE.toString(), "eventcard1content");
        localStorage.saveEventCard(eventCardOut);
        EventCard eventCardIn = localStorage.readObjectFromFile("Eventcards", eventCardOut.getIdentifier().toString());
        assertEquals(eventCardOut, eventCardIn);
    }

    @Test
    public void deleteEventCard() {
        EventCard eventCardOut = new EventCard("eventcard1", BLUE.toString(), "eventcard1content");
        localStorage.saveEventCard(eventCardOut);
        localStorage.deleteFile("Eventcards", eventCardOut.getIdentifier().toString());
        EventCard eventCardIn = localStorage.readObjectFromFile("Eventcards", eventCardOut.getIdentifier().toString());
        assertNull(eventCardIn);
    }

    @Test
    public void getAllEventCards() {
        EventCard eventCard1 = new EventCard("eventcard1", BLUE.toString(), "eventcard1content");
        EventCard eventCard2 = new EventCard("eventcard2", RED.toString(), "eventcard2content");
        EventCard eventCard3 = new EventCard("eventcard3", GREEN.toString(), "eventcard3content");
        localStorage.saveEventCard(eventCard1);
        localStorage.saveEventCard(eventCard2);
        localStorage.saveEventCard(eventCard3);
        ArrayList<EventCard> eventCardsOut = new ArrayList<>(Arrays.asList(eventCard1, eventCard2, eventCard3));
        ArrayList<EventCard> eventCardsIn = localStorage.getAllEventCards();
        assertTrue(eventCardsIn.containsAll(eventCardsOut));
    }

    @Test
    public void getTimeline() {
    }

    @Test
    public void getAllTimelines() {
    }

    @Test
    public void saveTimeline() {
    }

    @Test
    public void deleteTimeLine() {
    }
}