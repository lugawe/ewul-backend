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

    public Role createRole(String name) {

        if (name == null) {
            throw new NullPointerException("name");
        }

        Role role = new Role();
        role.setName(name);

        if (factory().insert(role) == null) {
            throw new IllegalStateException("cannot insert role entity");
        }

        return role;
    }

}
