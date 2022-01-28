package org.ewul.core.util;

import org.ewul.model.BasicUser;
import org.ewul.model.User;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.Group;
import org.ewul.model.db.auth.Role;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class AccountUtils {

    private AccountUtils() {
    }

    public static User toUser(Account account) {

        if (account == null) {
            throw new NullPointerException("account");
        }

        BasicUser result = new BasicUser();

        result.setId(account.getId());
        result.setName(account.getName());
        result.setRoles(NullUtils.safeGet(() -> {
            Group group = account.getGroup();
            if (group == null) {
                throw new NullPointerException("group");
            }
            Set<String> roles = group.getRoles()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Role::getName)
                    .collect(Collectors.toSet());
            return Collections.unmodifiableSet(roles);
        }, Collections.emptySet()));
        result.setProperties(Collections.unmodifiableMap(new LinkedHashMap<>(account.getProperties())));

        return result;
    }

}
