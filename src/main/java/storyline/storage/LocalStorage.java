package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Identifiable;
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
        createDirectoryIfNotExists(appDataPath);
        this.appDataPath = appDataPath;
    }

    public static LocalStorage getInstance() {
        if (instance == null) {
            instance = new LocalStorage();
        }
        return instance;
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        if (Files.notExists(Paths.get(directoryPath))) {
            try {
                Files.createDirectory(Paths.get(directoryPath));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    @Override
    public boolean saveEventCard(EventCard eventCard) {
        return writeObjectToFile("Eventcards", eventCard);
    }


    @Override
    public boolean deleteEventCard(EventCard eventCard) {
        return false;
    }

    @Override
    public ArrayList<EventCard> getAllEventCards() {
        return readAllObjectsFromDirectory("Eventcards");
    }

    @Override
    public Timeline getTimeline(String ID) {
        return readObjectFromFile("Timelines", ID);
    }

    @Override
    public ArrayList<Timeline> getAllTimelines() {
        return readAllObjectsFromDirectory("Timelines");
    }

    @Override
    public boolean saveTimeline(Timeline timeline) {
        return writeObjectToFile("Timelines", timeline);
    }

    @Override
    public boolean deleteTimeLine(String ID) {
        return deleteFile("Timelines", ID);
    }

    private boolean deleteFile(String subDirectory, String identifier) {
        String filepath = getFilepath(subDirectory) + File.separator + identifier + FILE_FORMAT;
        File timelineFile = new File(filepath);
        if (timelineFile.exists()) {
            return timelineFile.delete();
        }
        return false;
    }

    private <T extends Identifiable & Serializable> boolean writeObjectToFile(String subDirectory, T object) {
        String directoryPath = getFilepath(subDirectory);
        createDirectoryIfNotExists(directoryPath);
        String filepath = getFilepath(object, subDirectory);
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private String getFilepath(String... subDirectories) {
        StringBuilder filepathBuilder = new StringBuilder(this.appDataPath + File.separator);
        Arrays.stream(subDirectories).forEach(path -> filepathBuilder.append(path + File.separator));
        return filepathBuilder.toString();
    }

    private <T extends Identifiable & Serializable> String getFilepath(T object, String... subDirectories) {
        StringBuilder filepathBuilder = new StringBuilder(getFilepath(subDirectories));
        filepathBuilder.append(object.getIdentifier() + FILE_FORMAT);
        return filepathBuilder.toString();
    }


    private <T extends Identifiable & Serializable> ArrayList<T> readAllObjectsFromDirectory(String subDirectory) {
        ArrayList<T> objects = new ArrayList<>();

        String directoryPath = getFilepath(subDirectory);
        File directory = new File(directoryPath);
        //get all file names of the directoryPath
        String[] files = directory.list();

        for (String file : files) {
            String objectID = getFileNameWithoutExtension(file);
            T object = readObjectFromFile(subDirectory, objectID);
            objects.add(object);
        }

        return objects;

    }

    public static String getFileNameWithoutExtension(String file) {
        return file.replaceAll("^.*?(([^/\\\\\\.]+))\\.[^\\.]+$", "$1");
    }

    private <T extends Identifiable & Serializable> T readObjectFromFile(String directory, String identifier) {

        String filepath = this.appDataPath + File.separator + directory + File.separator + identifier + FILE_FORMAT;
        T object = null;
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object readObject = objectIn.readObject();
            if (readObject instanceof Identifiable) {
                object = (T) readObject;
            }
            objectIn.close();
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
        return object;
    }
}
