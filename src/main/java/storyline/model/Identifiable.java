package storyline.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * This abstract class contains a unique identifier(UUID). classes meant to be stored need to extend
 * this class to insure that they contain a ID for storage. contains method for generating the id.
 */
public abstract class Identifiable implements Serializable {
    private UUID ID;

    private static final long SERIAL_VERSION_UID = 355062239;

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public UUID getIdentifier() {
        return ID;
    }

    public void generateID() {
        this.ID = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifiable that = (Identifiable) o;
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
