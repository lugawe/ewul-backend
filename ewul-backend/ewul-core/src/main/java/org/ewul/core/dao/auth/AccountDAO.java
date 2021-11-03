package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.QAccount;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class AccountDAO extends BaseDAO<Account> {

    private static final QAccount PATH = new QAccount("auth_account");

    @Inject
    public AccountDAO(EntityDataHandler handler) {
        super(Account.class, handler);
    }

    public Optional<Account> getByEmail(String email) {

        if (email == null) {
            throw new NullPointerException("email");
        }

        return Optional.ofNullable(factory().selectFrom(PATH).where(PATH.email.eq(email)).fetchFirst());
    }

}
