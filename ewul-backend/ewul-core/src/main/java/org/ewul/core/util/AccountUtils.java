package org.ewul.core.util;

import org.ewul.model.db.auth.Account;
import org.ewul.model.db.auth.Group;
import org.ewul.model.db.auth.Role;

import java.util.*;
import java.util.stream.Collectors;

public final class AccountUtils {

    private AccountUtils() {
    }

    public static Set<String> getRolesAsString(Account account) {

        if (account == null) {
            throw new NullPointerException("account");
        }

        Group group = account.getGroup();
        if (group != null) {
            Set<Role> roles = group.getRoles();
            if (roles != null) {
                Set<String> result = roles.stream()
                        .filter(Objects::nonNull)
                        .map(Role::getName)
                        .collect(Collectors.toSet());
                return Collections.unmodifiableSet(result);
            }
        }

        return Collections.emptySet();
    }

}
