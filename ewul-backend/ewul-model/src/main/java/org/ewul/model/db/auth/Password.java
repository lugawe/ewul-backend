package org.ewul.model.db.auth;

import org.ewul.model.db.DbModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "auth_password")
public class Password implements DbModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "hash")
    private String hash;

    public Password() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Password)) return false;
        Password password = (Password) other;
        return Objects.equals(id, password.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
