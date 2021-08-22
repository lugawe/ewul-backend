package org.ewul.core.dao;

import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.Password;
import org.ewul.model.db.QPassword;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PasswordDAO extends BaseDAO<Password> {

    private static final QPassword PATH = new QPassword("password");

    @Inject
    public PasswordDAO(EntityDataHandler handler) {
        super(Password.class, handler);
    }

}
