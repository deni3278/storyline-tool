package storyline.storage;

public interface Identifiable {
    String getIdentifier();

    default String getName() {
        return getIdentifier();
    }
}
