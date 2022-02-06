package org.ewul.core.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.Sets;
import org.ewul.model.BasicUser;
import org.ewul.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtBuilderTest {

    static JwtBuilder jwtBuilder1;
    static JwtBuilder jwtBuilder2;

    @BeforeAll
    static void init() {
        jwtBuilder1 = new JwtBuilder(Algorithm.HMAC256("foo"));
        jwtBuilder2 = new JwtBuilder(Algorithm.HMAC256("bar"));
    }

    @Test
    void encode_test() {

        User user = new BasicUser() {{
            setId(UUID.randomUUID());
            setName("Test-User");
            setRoles(Sets.newHashSet("1", "2"));
            setProperties(Collections.singletonMap("a", "b"));
        }};

        String accessToken1 = jwtBuilder1.buildAccessToken(user);

        assertNotNull(accessToken1);

        String refreshToken1 = jwtBuilder1.buildRefreshToken(user.getId());

        assertNotNull(refreshToken1);

    }

}
