package org.ewul.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.ewul.model.BasicUser;
import org.ewul.model.User;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class JwtDecoder {

    protected final Algorithm algorithm;

    public JwtDecoder(Algorithm algorithm) {
        this.algorithm = Objects.requireNonNull(algorithm, "algorithm");
    }

    private Optional<DecodedJWT> decode(String token) {
        try {
            Verification verification = JWT.require(algorithm);
            verification.withClaimPresence(Jwt.CLAIM_TOKEN_TYPE);

            return Optional.of(verification.build().verify(token));
        } catch (Exception e) {
            // invalid token
            return Optional.empty();
        }
    }

    public Optional<UUID> decodeRefreshToken(String refreshToken) {
        return decode(refreshToken).map(jwt -> UUID.fromString(jwt.getClaim(Jwt.CLAIM_AUTH_ID).asString()));
    }

    public Optional<User> decodeAccessToken(String accessToken) {
        return decode(accessToken).map(jwt -> jwt.getClaim(Jwt.CLAIM_USER).as(BasicUser.class));
    }

}
