package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.Password;
import org.ewul.model.db.auth.QPassword;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PasswordDAO extends BaseDAO<Password> {

    private static final QPassword PATH = new QPassword("auth_password");

    @Inject
    public PasswordDAO(EntityDataHandler handler) {
        super(Password.class, handler);
    }

}
