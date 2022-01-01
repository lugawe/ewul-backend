package org.ewul.core.modules.auth;

import com.google.common.hash.Hashing;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Singleton
public class SHA256PasswordHashing implements PasswordHashing {

    @Inject
    public SHA256PasswordHashing() {
    }

    @Override
    public String hash(String plain) {
        return Hashing.sha256().hashString(plain, StandardCharsets.UTF_8).toString();
    }

    @Override
    public boolean check(String plain, String hash) {
        return Objects.equals(plain, hash(hash));
    }

}
