package org.ewul.core.cache;

import com.google.common.cache.CacheLoader;
import org.ewul.core.dao.auth.AccountDAO;
import org.ewul.core.util.AccountUtils;
import org.ewul.model.User;
import org.ewul.model.db.auth.Account;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserCacheLoader extends CacheLoader<UUID, User> {

    protected final AccountDAO accountDAO;

    @Inject
    public UserCacheLoader(AccountDAO accountDAO) {
        this.accountDAO = Objects.requireNonNull(accountDAO);
    }

    @Nonnull
    @Override
    public User load(@Nonnull UUID key) {
        Optional<Account> account = accountDAO.getById(key);
        if (!account.isPresent()) {
            throw new NullPointerException(String.format("no account found for id: %s", key));
        }
        return AccountUtils.toUser(account.get());
    }

}
