package org.ewul.core.security.hashing;

import org.ewul.core.util.Lazy;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordHashing implements PasswordHashing {

    private static final Lazy<BCryptPasswordHashing> instance = Lazy.of(BCryptPasswordHashing::new);

    public static BCryptPasswordHashing getInstance() {
        return instance.get();
    }

    private BCryptPasswordHashing() {
    }

    @Override
    public String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(16));
    }

    @Override
    public boolean check(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }

}
