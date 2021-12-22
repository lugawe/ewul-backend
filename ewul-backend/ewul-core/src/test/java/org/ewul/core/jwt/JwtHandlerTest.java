package org.ewul.core.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.core.modules.auth.JwtTokenHandler;
import org.ewul.model.BasicUser;
import org.ewul.model.User;
import org.ewul.model.config.JwtConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JwtHandlerTest {

    static final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    static JwtTokenHandler jwtHandler1;
    static JwtTokenHandler jwtHandler2;

    static JwtTokenHandler jwtHandler3;

    @BeforeAll
    static void init() {
        jwtHandler1 = new JwtTokenHandler(jwtConfiguration, Algorithm.HMAC256("foobar"));
        jwtHandler2 = new JwtTokenHandler(jwtConfiguration, Algorithm.HMAC256("foobar"));
        jwtHandler3 = new JwtTokenHandler(jwtConfiguration, Algorithm.HMAC256("secret"));
    }

    @Test
    void encode_decode_test() {

        String token = new JwtTokenHandler.Builder()
                .withUser(new BasicUser() {{
                    setName("Test-User");
                    setId(UUID.randomUUID());
                }})
                .withIssuer("test1")
                .withProperty("prop1", "test!!!")
                .withRole("role1")
                .withProperty("prop2", "!!!test")
                .withExpiresAt(2, TimeUnit.DAYS)
                .build(jwtHandler1);

        assertNotNull(token);

        Optional<User> user = jwtHandler1.decodeAccessToken(token);
        assertTrue(user.isPresent());

        assertNotNull(user.get().getId());
        assertEquals("Test-User", user.get().getName());
        assertEquals(1, user.get().getRoles().size());
        assertEquals(2, user.get().getProperties().size());
        assertEquals("!!!test", user.get().getProperties().get("prop2"));

        Optional<User> user2 = jwtHandler2.decodeAccessToken(token);
        assertTrue(user2.isPresent());

        Optional<User> user3 = jwtHandler3.decodeAccessToken(token);
        assertFalse(user3.isPresent());
    }

}
