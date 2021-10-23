package org.ewul.core.util;

import org.ewul.model.db.Account;
import org.ewul.model.db.Membership;
import org.ewul.model.db.Role;

import java.util.*;
import java.util.stream.Collectors;

public final class AccountUtils {

    private AccountUtils() {
    }

    public static Set<String> getRoles(Account account) {

        if (account == null) {
            throw new NullPointerException("account");
        }

        Membership membership = account.getMembership();
        if (membership != null) {
            List<Role> roles = membership.getRoles();
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
