package org.ewul.model.db.notes;

import org.ewul.model.db.DbModel;
import org.ewul.model.db.auth.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.SortedSet;
import java.util.UUID;

@Entity
@Table(name = "notes_category")
public class Category implements DbModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "auth_account", foreignKey = @ForeignKey(name = "fk_notes__category_account"))
    private Account account;

    @OrderBy
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "notes_category", foreignKey = @ForeignKey(name = "fk_notes__category_group"))
    private SortedSet<Subgroup> subgroups;

    public Category() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Category)) return false;
        Category category = (Category) other;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) &&
                Objects.equals(account, category.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, account);
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public SortedSet<Subgroup> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(SortedSet<Subgroup> subgroups) {
        this.subgroups = subgroups;
    }

}
