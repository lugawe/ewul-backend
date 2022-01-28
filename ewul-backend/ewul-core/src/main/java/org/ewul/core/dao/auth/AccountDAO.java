package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.QAccount;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class AccountDAO extends BaseDAO<Account> {

    private static final QAccount PATH = new QAccount("auth_account");

    @Inject
    public AccountDAO(EntityDataHandler handler) {
        super(Account.class, handler);
    }

    public boolean checkNameExists(String name) {

        if (name == null) {
            throw new NullPointerException("name");
        }

        return query().select(PATH.id).from(PATH).where(PATH.name.eq(name)).fetchCount() > 0;
    }

    public boolean checkEmailExists(String email) {

        if (email == null) {
            throw new NullPointerException("email");
        }

        return query().select(PATH.id).from(PATH).where(PATH.email.eq(email)).fetchCount() > 0;
    }

    public Optional<Account> getById(UUID id) {

        if (id == null) {
            throw new NullPointerException("id");
        }

        return Optional.ofNullable(factory().selectFrom(PATH).where(PATH.id.eq(id)).fetchFirst());
    }

    public Optional<Account> getByName(String name) {

        if (name == null) {
            throw new NullPointerException("name");
        }

        return Optional.ofNullable(factory().selectFrom(PATH).where(PATH.name.eq(name)).fetchFirst());
    }

    public Optional<Account> getByEmail(String email) {

        if (email == null) {
            throw new NullPointerException("email");
        }

        return Optional.ofNullable(factory().selectFrom(PATH).where(PATH.email.eq(email)).fetchFirst());
    }

}
