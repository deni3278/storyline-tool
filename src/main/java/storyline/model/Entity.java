package storyline.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


/**
 * The entity model class represents an entity that can be referred to upon creating an eventcard.
 */
public class Entity extends Identifiable implements Serializable {
    private String initials;
    private String name;
    private String description;

    private static final long serialVersionUID = 355062237;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(getInitials(), entity.getInitials()) && Objects.equals(getName(), entity.getName()) && Objects.equals(getDescription(), entity.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInitials(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "initials='" + initials + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
