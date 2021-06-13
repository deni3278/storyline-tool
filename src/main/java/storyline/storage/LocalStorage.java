package storyline.storage;

import storyline.model.EventCard;
import storyline.model.Identifiable;
import storyline.model.Timeline;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the local impementation of StorageAdapter. stores files under the home folder and then /StorylineTool.
 */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static LocalStorage getInstance() {
        if (instance == null) {
            instance = new LocalStorage();
        }
        return instance;
    }

    /**
     * Create directory if not exists.
     *
     * @param directoryPath the directory path
     */
    public static void createDirectoryIfNotExists(String directoryPath) {
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
    public boolean deleteEventCard(String ID) {
        return deleteFile("Eventcards", ID);
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

    /**
     * Deletes file specified.
     *
     * @param subDirectory the sub directory
     * @param identifier   the identifier
     * @return true if successfull
     */
    public boolean deleteFile(String subDirectory, String identifier) {
        String filepath = getFilepath(subDirectory) + File.separator + identifier + FILE_FORMAT;
        File timelineFile = new File(filepath);
        if (timelineFile.exists()) {
            return timelineFile.delete();
        }
        return false;
    }

    /**
     * Write object to local file.
     *
     * @param <T>          the type parameter
     * @param subDirectory the sub directory
     * @param object       the object needed to be save to a file
     * @return true if successfull write
     */
    public <T extends Identifiable & Serializable> boolean writeObjectToFile(String subDirectory, T object) {
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

    /**
     * Gets filepath.
     *
     * @param subDirectories the sub directories
     * @return the filepath
     */
    public String getFilepath(String... subDirectories) {
        StringBuilder filepathBuilder = new StringBuilder(this.appDataPath + File.separator);
        Arrays.stream(subDirectories).forEach(path -> filepathBuilder.append(path + File.separator));
        return filepathBuilder.toString();
    }

    /**
     * Gets filepath.
     *
     * @param <T>            the type parameter
     * @param object         the object
     * @param subDirectories the sub directories
     * @return the filepath
     */
    public <T extends Identifiable & Serializable> String getFilepath(T object, String... subDirectories) {
        StringBuilder filepathBuilder = new StringBuilder(getFilepath(subDirectories));
        filepathBuilder.append(object.getIdentifier() + FILE_FORMAT);
        return filepathBuilder.toString();
    }


    /**
     * Read all objects from specified directory.
     *
     * @param <T>          the type parameter
     * @param subDirectory the sub directory
     * @return the array list of all objects from the specified directory
     */
    public <T extends Identifiable & Serializable> ArrayList<T> readAllObjectsFromDirectory(String subDirectory) {
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

    /**
     * Gets file name without extension.
     *
     * @param file the path of the file
     * @return the file name without extension
     */
    public static String getFileNameWithoutExtension(String file) {
        return file.replaceAll("^.*?(([^/\\\\\\.]+))\\.[^\\.]+$", "$1");
    }

    /**
     * Read object from file t.
     *
     * @param <T>        the type parameter, must extend identifiable and implement serializable
     * @param directory  the directory to be read from
     * @param identifier the identifier of the file to be read
     * @return the object read from the file
     */
    public <T extends Identifiable & Serializable> T readObjectFromFile(String directory, String identifier) {

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
