package org.ewul.model.db.notes;

import org.ewul.model.db.DbModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$", message = "invalid hex color format")
    @Column(name = "color")
    private String color = "#000000";

    @ManyToOne
    @JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "fk_notes__subgroup_parent"))
    private Subgroup parent;

    @OrderBy
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "fk_notes__subgroup_children"))
    private SortedSet<Subgroup> children;

    @OrderBy
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subgroup", foreignKey = @ForeignKey(name = "fk_notes__subgroup_documents"))
    private SortedSet<Document> documents;

    public Subgroup() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Subgroup)) return false;
        Subgroup subgroup = (Subgroup) other;
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

        int colorComparison = this.color.compareTo(other.color);
        if (colorComparison != 0) {
            return colorComparison < 0 ? -1 : 1;
        }

        if (this.parent == null && other.parent == null) {
            return 0;
        } else if (this.parent == null) {
            return -1;
        } else if (other.parent == null) {
            return 1;
        } else {
            return this.parent.compareTo(other.parent);
        }
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

    public SortedSet<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(SortedSet<Document> documents) {
        this.documents = documents;
    }

}
