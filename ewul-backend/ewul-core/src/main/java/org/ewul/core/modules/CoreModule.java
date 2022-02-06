package org.ewul.core.modules;

import com.google.inject.AbstractModule;
import org.ewul.core.modules.auth.AuthManager;
import org.ewul.core.modules.auth.JwtAuthManager;
import org.ewul.core.modules.password.BCryptPasswordManager;
import org.ewul.core.modules.password.PasswordManager;

public class CoreModule extends AbstractModule {

    public CoreModule() {
    }

    @Override
    protected void configure() {
        bind(AuthManager.class).to(JwtAuthManager.class);
        bind(PasswordManager.class).to(BCryptPasswordManager.class);
    }

}
