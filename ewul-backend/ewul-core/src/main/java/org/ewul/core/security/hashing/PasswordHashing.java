package org.ewul.core.security.hashing;

public interface PasswordHashing {

    String hash(String plain);

    boolean check(String plain, String hash);

}
