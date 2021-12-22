package org.ewul.core.modules;

import com.google.inject.AbstractModule;
import org.ewul.core.modules.auth.BCryptPasswordHashing;
import org.ewul.core.modules.auth.PasswordHashing;

public class CoreModule extends AbstractModule {

    public CoreModule() {
    }

    @Override
    public void configure() {
        bind(PasswordHashing.class).to(BCryptPasswordHashing.class);
    }

}
