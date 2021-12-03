package org.ewul.core.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.model.User;
import org.ewul.model.config.JwtConfiguration;
import org.ewul.model.db.auth.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JwtHandlerTest {

    static final JwtConfiguration jwtConfiguration = new JwtConfiguration();

    static AccountJwtHandler jwtHandler1;
    static AccountJwtHandler jwtHandler2;

    static AccountJwtHandler jwtHandler3;

    static Account account = new Account();

    @BeforeAll
    static void init() {
        jwtHandler1 = new AccountJwtHandler(Algorithm.HMAC256("foobar"), jwtConfiguration);
        jwtHandler2 = new AccountJwtHandler(Algorithm.HMAC256("foobar"), jwtConfiguration);
        jwtHandler3 = new AccountJwtHandler(Algorithm.HMAC256("secret"), jwtConfiguration);
        account.setId(UUID.randomUUID());
        account.setName("foobar");
        account.setProperties(new HashMap<>());
        account.getProperties().put("foo", "bar");
    }

    @Test
    void encode_decode_test() {

        String token = new AccountJwtHandler.Builder()
                .withAccount(account)
                .withIssuer("test1")
                .withProperty("prop1", "test!!!")
                .withRole("role1")
                .withProperty("prop2", "!!!test")
                .withExpiresAt(2, TimeUnit.DAYS)
                .build(jwtHandler1);

        assertNotNull(token);

        assertTrue(jwtHandler1.isValid(token));
        assertTrue(jwtHandler2.isValid(token));
        assertFalse(jwtHandler3.isValid(token));

        User user = jwtHandler1.decode(token);

        assertNotNull(user);

        assertEquals(account.getId(), user.getId());

        user = jwtHandler2.decode(token);

        assertNotNull(user);

        assertEquals(account.getId(), user.getId());

        assertThrows(RuntimeException.class, () -> jwtHandler3.decode(token));

    }

}
