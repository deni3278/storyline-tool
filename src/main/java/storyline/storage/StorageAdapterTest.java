package storyline.storage;

import javafx.scene.paint.Color;
import org.junit.Before;
import storyline.model.EventCard;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StorageAdapterTest {

    final Color RED = Color.rgb(254, 0, 78, 0.2);
    final Color GREEN = Color.rgb(44, 204, 112, 0.2);
    final Color BLUE = Color.rgb(0, 193, 254, 0.2);
    StorageAdapter localStorage = LocalStorage.getInstance();
    StorageAdapter databaseStorage = DatabaseStorage.getInstance();

    @org.junit.Test
    public void saveEventCard() {
    }

    @org.junit.Test
    public void deleteEventCard() {
    }

    @org.junit.Test
    public void getAllEventCards() {
    }

    @org.junit.Test
    public void getTimeline() {
    }

    @org.junit.Test
    public void getAllTimelines() {
    }

    @org.junit.Test
    public void saveTimeline() {
    }

    @org.junit.Test
    public void deleteTimeLine() {
    }
}