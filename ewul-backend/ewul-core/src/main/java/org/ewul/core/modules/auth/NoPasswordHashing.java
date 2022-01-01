package org.ewul.core.modules.auth;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class NoPasswordHashing implements PasswordHashing {

    @Inject
    public NoPasswordHashing() {
    }

    @Override
    public String hash(String plain) {
        return plain;
    }

    @Override
    public boolean check(String plain, String hash) {
        return Objects.equals(plain, hash);
    }

}
