package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Timeline;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseStorage implements StorageAdapter {

    private static DatabaseStorage instance;

    private Connection connection;

    private DatabaseStorage() {
        Properties properties = new Properties();
        FileInputStream propertiesFile;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            propertiesFile = new FileInputStream("src/main/resources/storyline/connection.prop");
            properties.load(propertiesFile);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DB_StorylineTool",username,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DatabaseStorage getInstance() {
        if (instance == null) {
            instance = new DatabaseStorage();
        }
        return instance;
    }


    @Override
    public boolean saveEventCard(EventCard eventCard) {
        return false;
    }

    @Override
    public ArrayList<EventCard> getAllEventCards() {
        return null;
    }

    @Override
    public Timeline getTimeline(String ID) {
        return null;
    }

    @Override
    public ArrayList<Timeline> getAllTimelines() {
        return null;
    }

    @Override
    public boolean saveTimeline(Timeline timeline) {
        return false;
    }

    @Override
    public boolean updateTimeline(Timeline timeline) {
        return false;
    }

    @Override
    public boolean deleteTimeLine(String name) {
        return false;
    }

}
