package org.ewul.core.modules.auth;

import org.ewul.model.User;

import java.util.Optional;

public interface AuthManager {

    Optional<User> decodeAccessToken(String accessToken);

    boolean hasAccess(User user, String role);

}
