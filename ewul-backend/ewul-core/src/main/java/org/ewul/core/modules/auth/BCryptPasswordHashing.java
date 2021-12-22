package org.ewul.core.modules.auth;

import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BCryptPasswordHashing implements PasswordHashing {

    @Inject
    public BCryptPasswordHashing() {
    }

    @Override
    public String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(14));
    }

    @Override
    public boolean check(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }

}
