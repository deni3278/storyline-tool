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
                EventCard eventCard = new EventCard(title, colorString, eventContent, ID);
                eventCards.add(eventCard);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return eventCards;
    }

    @Override
    public Timeline getTimeline(String timelineID) {

        Timeline timeline;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT fld_TimelineName FROM tbl_Timeline WHERE fld_TimelineID = ?");
            preparedStatement.setString(1, timelineID);
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
            preparedStatement.setString(1, timelineID);
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
                ArrayList<Entity> entities = getTimelineEventcardEntities(ID.toString());
                TimelineEventCard timelineEventCard = new TimelineEventCard(title, colorString, eventContent, x, y, ID);
                timelineEventCard.setEventAttachedEntities(entities);

                timelineEventCards.add(timelineEventCard);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return timelineEventCards;
    }

    private ArrayList<Entity> getTimelineEventcardEntities(String timelineEventCardID) {

        ArrayList<Entity> entities = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM viewTimelineEventCardEntities WHERE fld_TimelineEventCardID = ?");
            preparedStatement.setString(1, timelineEventCardID);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                UUID entitiyID = UUID.fromString(resultSet.getString("fld_EntityID"));
                String initials = resultSet.getString("fld_Initials");
                String entitiyName = resultSet.getString("fld_EntityName");
                String entitiyDescription = resultSet.getString("fld_EntityDescription");
                Entity entity = new Entity(initials, entitiyName, entitiyDescription, entitiyID);
                entities.add(entity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return entities;
    }

    @Override
    public ArrayList<Timeline> getAllTimelines() {

        ArrayList<String> timelineIDs = new ArrayList<>();
        ArrayList<Timeline> timelines = new ArrayList<>();

        PreparedStatement getAllIDsStatement = null;

        ResultSet resultSet = null;
        try {
            getAllIDsStatement = connection.prepareStatement("SELECT fld_TimelineID FROM viewTimeline where fld_UserID = 1");
            getAllIDsStatement.execute();
            resultSet = getAllIDsStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                timelineIDs.add(resultSet.getString("fld_TimelineID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        timelineIDs.forEach(timelineID -> {
            timelines.add(getTimeline(timelineID));
        });
        return timelines;
    }

    @Override
    public boolean saveTimeline(Timeline timeline) {

        CallableStatement preparedStatement = null;

        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareCall("EXEC save_timeline @timelineName = ?, @timelineID = ?, @userID = ?");
            preparedStatement.setString(1, timeline.getName());
            preparedStatement.setString(2, timeline.getIdentifier().toString());
            preparedStatement.setInt(3, 1);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        saveTimelineEventCards(timeline.getEventCards(),timeline.getIdentifier());
        return true;
    }

    private void saveTimelineEventCards(ArrayList<TimelineEventCard> timelineEventCards, UUID timelineID) {
        deleteTimelineEventCards(timelineID);

        String sql = "exec save_timelineEventCard @fld_TimelineEventCardID = ?, @fld_TimelineID = ?, @fld_Title = ?, @fld_EventContent = ?, @fld_ColorString = ?, @fld_X = ?, @fld_Y = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            for (TimelineEventCard timelineEventCard : timelineEventCards) {

                preparedStatement.setString(1, timelineEventCard.getIdentifier().toString());
                preparedStatement.setString(2, timelineID.toString());
                preparedStatement.setString(3, timelineEventCard.getTitle());
                preparedStatement.setString(4, timelineEventCard.getEventContent());
                preparedStatement.setString(5, timelineEventCard.getColorString());
                preparedStatement.setInt(6, timelineEventCard.getX());
                preparedStatement.setInt(7, timelineEventCard.getY());
                preparedStatement.addBatch();
            }
            int[] affectedRecords = preparedStatement.executeBatch();
            System.out.println("affectedRecords.length = " + affectedRecords.length);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveTimelineEventCardEntities(ArrayList<TimelineEventCard> timelineEventCards) {

        deleteTimelineEventCardEntities(timelineEventCards);

        String sql = "";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            for (TimelineEventCard timelineEventCard : timelineEventCards) {
                for (Entity entity: timelineEventCard.getEventAttachedEntities()){

                    preparedStatement.addBatch();
                }


            }
            int[] affectedRecords = preparedStatement.executeBatch();
            System.out.println("affectedRecords.length = " + affectedRecords.length);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void deleteTimelineEventCardEntities(ArrayList<TimelineEventCard> timelineEventCards) {

    }

    private void deleteTimelineEventCards(UUID timelineID) {
        CallableStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareCall("EXEC deleteTimelineEventCards @timelineID = ?");
            preparedStatement.setString(1,timelineID.toString());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean deleteTimeLine(String name) {
        return false;
    }

}
