package storyline.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Identifiable implements Serializable {
    private UUID ID;

    public void setID(UUID ID) {
        this.ID = ID;

    }
    public UUID getIdentifier() {
        return ID;
    }
    public void generateID() {
        this.ID = UUID.randomUUID();
    }


}
