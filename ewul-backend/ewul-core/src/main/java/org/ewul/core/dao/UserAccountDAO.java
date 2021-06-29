package org.ewul.core.dao;

import org.ewul.core.entity.TransactionHandler;
import org.ewul.model.db.QUserAccount;
import org.ewul.model.db.UserAccount;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class UserAccountDAO extends BaseDAO<UserAccount> {

    private static final QUserAccount PATH = new QUserAccount("userAccount");

    @Inject
    public UserAccountDAO(TransactionHandler handler) {
        super(UserAccount.class, handler);
    }

    public boolean exists(String name) {
        if (name == null) {
            throw new NullPointerException("param name");
        }
        UUID id = factory().select(PATH.id).from(PATH).where(PATH.name.eq(name)).fetchFirst();
        return id != null;
    }

    public Optional<UserAccount> getById(UUID id) {
        if (id == null) {
            throw new NullPointerException("param id");
        }
        UserAccount account = factory().selectFrom(PATH).where(PATH.id.eq(id)).fetchOne();
        return Optional.ofNullable(account);
    }

    public List<UserAccount> getAll() {
        return factory().selectFrom(PATH).fetch();
    }

}
