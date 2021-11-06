package org.ewul.model.db.auth;

import org.ewul.model.db.DbModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "auth_account", uniqueConstraints = @UniqueConstraint(name = "uq_auth__account_display_name", columnNames = "display_name"))
public class Account implements Comparable<Account>, Principal, DbModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "display_name")
    private String displayName;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "last_access")
    private LocalDateTime lastAccess;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "auth_group", foreignKey = @ForeignKey(name = "fk_auth__account_group"))
    private Group group;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "auth_password", foreignKey = @ForeignKey(name = "fk_auth__account_password"))
    private Password password;

    @Transient
    private Map<String, String> properties;

    public Account() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Account)) return false;
        Account account = (Account) other;
        return Objects.equals(id, account.id) && Objects.equals(displayName, account.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }

    @Override
    public int compareTo(Account account) {
        return displayName.compareTo(account.displayName);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

}
