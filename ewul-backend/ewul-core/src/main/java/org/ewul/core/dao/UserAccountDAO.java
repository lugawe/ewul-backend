package org.ewul.core.dao;

import com.querydsl.core.QueryResults;
import org.ewul.core.entity.EntityDataHandler;
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
    public UserAccountDAO(EntityDataHandler handler) {
        super(UserAccount.class, handler);
    }

    public boolean exists(String name) {
        if (name == null) {
            throw new NullPointerException("param name");
        }
        UUID id = query(0, 1).select(PATH.id).from(PATH).where(PATH.name.equalsIgnoreCase(name)).fetchFirst();
        return id != null;
    }

    public Optional<UserAccount> getById(UUID id) {
        if (id == null) {
            throw new NullPointerException("param id");
        }
        UserAccount account = query(0, 1).select(PATH).from(PATH).where(PATH.id.eq(id)).fetchOne();
        return Optional.ofNullable(account);
    }

    public QueryResults<UserAccount> getAll(long offset, long limit) {
        return query(offset, limit).select(PATH).from(PATH).fetchResults();
    }

    public List<UserAccount> getAll() {
        return query().select(PATH).from(PATH).fetch();
    }

}
