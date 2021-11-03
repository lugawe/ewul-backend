package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.QRole;
import org.ewul.model.db.auth.Role;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoleDAO extends BaseDAO<Role> {

    private static final QRole PATH = new QRole("auth_role");

    @Inject
    public RoleDAO(EntityDataHandler handler) {
        super(Role.class, handler);
    }

}
