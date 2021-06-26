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

    public static final QUserAccount Q_USER_ACCOUNT = new QUserAccount("userAccount");

    @Inject
    public UserAccountDAO(TransactionHandler handler) {
        super(UserAccount.class, handler);
    }

    public Optional<UserAccount> getById(UUID id) {
        if (id == null) {
            throw new NullPointerException("param id");
        }
        UserAccount account = factory().selectFrom(Q_USER_ACCOUNT).where(Q_USER_ACCOUNT.id.eq(id)).fetchOne();
        return Optional.ofNullable(account);
    }

    public List<UserAccount> getAll() {
        return factory().selectFrom(Q_USER_ACCOUNT).fetch();
    }

}
