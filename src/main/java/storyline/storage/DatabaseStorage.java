package storyline.storage;

import storyline.model.Entity;
import storyline.model.EventCard;
import storyline.model.Timeline;
import storyline.model.TimelineEventCard;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class DatabaseStorage implements StorageAdapter {

    private static DatabaseStorage instance;

    private Connection connection;

    private DatabaseStorage() {
        FileInputStream propertiesFile;
        Properties properties = new Properties();

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
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DB_StorylineTool", username, password);
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
    public boolean deleteEventCard(EventCard eventCard) {
        return false;
    }

    @Override
    public ArrayList<EventCard> getAllEventCards() {
        ArrayList<EventCard> eventCards = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM viewUserEventCards WHERE fld_UserID = 1");
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                String title = resultSet.getString("fld_Title");
                String colorString = resultSet.getString("fld_ColorString");
                String eventContent = resultSet.getString("fld_EventContent");
                UUID ID = UUID.fromString(resultSet.getString("fld_UserEventCardID"));
                EventCard eventCard = new EventCard(title,colorString,eventContent,ID);
                eventCards.add(eventCard);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return eventCards;
    }

    @Override
    public Timeline getTimeline(String timelineID) {

        Timeline timeline = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT fld_TimelineName FROM tbl_Timeline WHERE fld_TimelineID = ?");
            preparedStatement.setString(1,timelineID);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String timelineName = null;
        try {
            if (resultSet.next()) {
                timelineName = resultSet.getString("fld_TimelineName");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ArrayList<TimelineEventCard> timelineEventCards = getTimelineEventCards(timelineID);
        timeline = new Timeline(timelineEventCards, timelineName, UUID.fromString(timelineID));
        return timeline;
        
    }

    public ArrayList<TimelineEventCard> getTimelineEventCards(String timelineID) {
        ArrayList<TimelineEventCard> timelineEventCards = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM viewTimelineEventCards WHERE fld_TimelineID = ?");
            preparedStatement.setString(1,timelineID);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                String title = resultSet.getString("fld_Title");
                String colorString = resultSet.getString("fld_ColorString");
                String eventContent = resultSet.getString("fld_EventContent");
                int x = resultSet.getInt("fld_X");
                int y = resultSet.getInt("fld_Y");
                UUID ID = UUID.fromString(resultSet.getString("fld_TimelineEventCardID"));
                TimelineEventCard timelineEventCard = new TimelineEventCard(title,colorString,eventContent,x,y,ID);

                timelineEventCards.add(timelineEventCard);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return timelineEventCards;
    }

    private ArrayList<Entity> getTimelineEventcardEntities() {
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
    public boolean deleteTimeLine(String name) {
        return false;
    }

}
