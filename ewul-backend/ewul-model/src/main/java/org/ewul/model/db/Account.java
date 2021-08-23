package org.ewul.model.db;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class Account implements Comparable<Account>, Principal, DbModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "membership", foreignKey = @ForeignKey(name = "fk_account_membership"))
    private Membership membership;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "password", foreignKey = @ForeignKey(name = "fk_account_password"))
    private Password password;

    @Transient
    private Map<String, String> properties;

    public Account() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public int compareTo(Account account) {
        return name.compareTo(account.name);
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
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
