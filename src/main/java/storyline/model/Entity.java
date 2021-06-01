package storyline.model;

import storyline.storage.Identifiable;

import java.io.Serializable;

public class Entity implements Serializable, Identifiable {
    private String initials;
    private String name;
    private String description;


    public Entity(String initials, String name, String description) {
        this.initials = initials;
        this.name = name;
        this.description = description;
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

    @Override
    public String getIdentifier() {
        return String.valueOf(this.name);
    }

}
