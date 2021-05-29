package storyline.model;

import storyline.storage.Identifiable;

import java.io.Serializable;

public class Entity implements Serializable, Identifiable {
    private int entityID;
    private String entityName;
    private String entityDescription;

    public Entity(int entityID, String entityName, String entityDescription) {
        this.entityID = entityID;
        this.entityName = entityName;
        this.entityDescription = entityDescription;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityID=" + entityID +
                ", entityName='" + entityName + '\'' +
                ", entityDescription='" + entityDescription + '\'' +
                '}';
    }



    @Override
    public String getIdentifier() {
        return String.valueOf(this.entityID);
    }

}
