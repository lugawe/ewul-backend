package org.ewul.core.modules.auth;

import org.ewul.model.User;

import java.util.Optional;
import java.util.UUID;

public interface AuthManager {

    String generateRefreshToken(UUID id);

    String generateAccessToken(User user);

    Optional<UUID> decodeRefreshToken(String refreshToken);

    Optional<User> decodeAccessToken(String accessToken);

    boolean hasAccess(User user, String role);

}
