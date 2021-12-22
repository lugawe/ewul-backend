package org.ewul.core.modules.auth;

public interface PasswordHashing {

    String hash(String plain);

    boolean check(String plain, String hash);

}
