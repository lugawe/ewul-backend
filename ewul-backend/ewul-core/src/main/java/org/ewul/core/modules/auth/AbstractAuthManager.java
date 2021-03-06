package org.ewul.core.modules.auth;

import org.ewul.core.util.CollectionUtils;
import org.ewul.model.User;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractAuthManager implements AuthManager {

    protected AbstractAuthManager() {
    }

    @Override
    public abstract String generateRefreshToken(UUID id);

    @Override
    public abstract String generateAccessToken(User user);

    @Override
    public abstract Optional<UUID> decodeRefreshToken(String refreshToken);

    @Override
    public abstract Optional<User> decodeAccessToken(String accessToken);

    @Override
    public boolean hasAccess(User user, String role) {
        if (user == null || role == null) {
            return false;
        }
        Collection<String> roles = CollectionUtils.emptyIfNull(user.getRoles());
        return roles.stream().filter(Objects::nonNull).anyMatch(r -> r.equals(role));
    }

}
