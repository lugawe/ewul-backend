package org.ewul.core.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.ewul.model.db.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JwtHandlerTest {

    static JwtHandler jwtHandler1;
    static JwtHandler jwtHandler2;

    static JwtHandler jwtHandler3;

    static Account account = new Account();

    @BeforeAll
    static void init() {
        jwtHandler1 = new JwtHandler(Algorithm.HMAC256("foobar"));
        jwtHandler2 = new JwtHandler(Algorithm.HMAC256("foobar"));
        jwtHandler3 = new JwtHandler(Algorithm.HMAC256("secret"));
        account.setId(UUID.randomUUID());
    }

    @Test
    void encode_decode_test() {

        String token = new JwtHandler.Builder()
                .withIssuer("test1")
                .withAccount(account)
                .withRole("role1")
                .withProperty("prop1", "test!!!")
                .withProperty("prop2", "!!!test")
                .withExpiresAt(2, TimeUnit.DAYS)
                .build(jwtHandler1);

        assertNotNull(token);

        assertTrue(jwtHandler1.isValid(token));
        assertTrue(jwtHandler2.isValid(token));
        assertFalse(jwtHandler3.isValid(token));

        JwtHolder holder = jwtHandler1.decode(token);

        assertNotNull(holder);

        assertEquals(account.getId(), holder.getAuthId());

        holder = jwtHandler2.decode(token);

        assertNotNull(holder);

        assertEquals(account.getId(), holder.getAuthId());

        assertThrows(RuntimeException.class, () -> jwtHandler3.decode(token));

    }

}
