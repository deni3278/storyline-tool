package storyline.model;

import storyline.storage.Identifiable;

import java.io.Serializable;
import java.util.UUID;

public class Entity extends Identifiable implements Serializable {
    private String initials;
    private String name;
    private String description;


    public Entity(String initials, String name, String description) {
        this.initials = initials;
        this.name = name;
        this.description = description;
        generateID();
    }

    public Entity(String initials, String name, String description, UUID ID) {
        this.initials = initials;
        this.name = name;
        this.description = description;
        setID(ID);
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
