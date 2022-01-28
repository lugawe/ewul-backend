package org.ewul.core.modules.auth;

import org.ewul.model.User;

import java.util.Optional;

public interface AccessHandler {

    String generateAccessToken(User user);

    Optional<User> decodeAccessToken(String token);

}
