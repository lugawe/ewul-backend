package org.ewul.core.modules.password;

public interface PasswordManager {

    String hash(String plain);

    boolean check(String plain, String hash);

}
