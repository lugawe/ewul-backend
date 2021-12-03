package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.core.security.hashing.PasswordHashing;
import org.ewul.model.db.auth.Password;
import org.ewul.model.db.auth.QPassword;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class PasswordDAO extends BaseDAO<Password> {

    private static final QPassword PATH = new QPassword("auth_password");

    protected final PasswordHashing passwordHashing;

    @Inject
    public PasswordDAO(EntityDataHandler handler, PasswordHashing passwordHashing) {
        super(Password.class, handler);
        this.passwordHashing = Objects.requireNonNull(passwordHashing);
    }

    public Password createPassword(String plainPassword) {

        if (plainPassword == null) {
            throw new NullPointerException("plainPassword");
        }

        Password password = new Password();
        password.setHash(passwordHashing.hash(plainPassword));

        if (factory().insert(password) == null) {
            throw new IllegalStateException("cannot insert password entity");
        }

        return password;
    }

    public boolean checkPassword(String plainPassword, Password password) {

        if (plainPassword == null) {
            throw new NullPointerException("plainPassword");
        }
        if (password == null) {
            throw new NullPointerException("password");
        }

        return passwordHashing.check(plainPassword, password.getHash());
    }

}
