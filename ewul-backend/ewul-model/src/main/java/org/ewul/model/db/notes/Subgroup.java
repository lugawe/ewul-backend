package org.ewul.model.db.notes;

import org.ewul.model.db.DbModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.SortedSet;
import java.util.UUID;

@Entity
@Table(name = "notes_subgroup")
public class Subgroup implements Comparable<Subgroup>, DbModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "color")
    private String color = "#000000";

    @ManyToOne
    @JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "fk_notes__subgroup_parent"))
    private Subgroup parent;

    @OrderBy
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_notes__subgroup_children"))
    private SortedSet<Subgroup> children;

    public Subgroup() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subgroup)) return false;
        Subgroup subgroup = (Subgroup) o;
        return Objects.equals(id, subgroup.id) && Objects.equals(name, subgroup.name) &&
                Objects.equals(color, subgroup.color) && Objects.equals(parent, subgroup.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, parent);
    }

    @Override
    public int compareTo(Subgroup other) {
        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison < 0 ? -1 : 1;
        }
        return this.color.compareTo(other.color);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Subgroup getParent() {
        return parent;
    }

    public void setParent(Subgroup parent) {
        this.parent = parent;
    }

    public SortedSet<Subgroup> getChildren() {
        return children;
    }

    public void setChildren(SortedSet<Subgroup> children) {
        this.children = children;
    }

}
