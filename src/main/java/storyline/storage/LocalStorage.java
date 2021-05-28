package storyline.storage;

import storyline.model.Timeline;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class LocalStorage implements StorageAdapter {

    private static LocalStorage instance;
    private final String FILE_FORMAT = ".story";
    private final String DIRECTORY_NAME = "StorylineTool";
    private String appDataPath;

    private LocalStorage() {
        String userHomePath = System.getProperty("user.home");
        String appDataPath = userHomePath + File.separator + DIRECTORY_NAME;
        if (Files.notExists(Paths.get(appDataPath))) {
            try {
                Files.createDirectory(Paths.get(appDataPath));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        this.appDataPath = appDataPath;
    }

    public static LocalStorage getInstance() {
        if (instance == null) {
            instance = new LocalStorage();
        }
        return instance;
    }

    @Override
    public Timeline getTimeline(int ID) {
        return readTimelineFromFile(this.appDataPath, ID);
    }

    @Override
    public ArrayList<Timeline> getAllTimelines() {
        return readAllTimelinesFromDirectory(this.appDataPath);
    }

    @Override
    public boolean saveTimeline(Timeline timeline) {

        if (writeTimelineToFile(this.appDataPath, timeline)) return true;
        return false;
    }

    @Override
    public boolean updateTimeline(Timeline timeline) {
        return saveTimeline(timeline);
    }

    @Override
    public boolean deleteTimeLine(int timelineID) {
        if (deleteTimeLineFile(this.appDataPath, timelineID)) return true;
        return false;
    }

    private boolean deleteTimeLineFile(String directory, int timelineID) {
        String filepath = directory + File.separator + timelineID + FILE_FORMAT;
        File timelineFile = new File(filepath);
        if (timelineFile.exists()) {
            if (timelineFile.delete()) return true;
        }
        return false;
    }

    private boolean writeTimelineToFile(String directory, Timeline timeline) {
        String filepath = directory + File.separator + timeline.getTimelineID() + FILE_FORMAT;
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(timeline);
            objectOut.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Timeline readTimelineFromFile(String directory, int ID) {


        String filepath = directory + File.separator + ID + FILE_FORMAT;
        Timeline timeline = null;
        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            if (obj instanceof Timeline) {
                timeline = (Timeline) obj;
            }

            objectIn.close();
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
        return timeline;
    }

    private ArrayList<Timeline> readAllTimelinesFromDirectory(String directoryPath) {
        ArrayList<Timeline> timelines = new ArrayList<>();
        File directory = new File(directoryPath);
        String[] files = directory.list();

        Arrays.stream(files).forEach(file -> {
            String fileNameWithoutExt = file.replaceAll("^.*?(([^/\\\\\\.]+))\\.[^\\.]+$", "$1");
            int timelineID = Integer.parseInt(fileNameWithoutExt);
            Timeline timeline = readTimelineFromFile(directoryPath, timelineID);
            timelines.add(timeline);
        });
        return timelines;

    }
}
