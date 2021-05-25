package org.ewul.core.dao;

import org.ewul.core.entity.EntityManagerHandler;
import org.ewul.model.db.QUserAccount;
import org.ewul.model.db.UserAccount;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserAccountDAO extends BaseDAO<UserAccount> {

    public static final QUserAccount Q_USER_ACCOUNT = new QUserAccount("userAccount");

    @Inject
    public UserAccountDAO(EntityManagerHandler handler) {
        super(UserAccount.class, handler);
    }

    public List<UserAccount> getAll() {
        return factory().selectFrom(Q_USER_ACCOUNT).fetch();
    }

}
