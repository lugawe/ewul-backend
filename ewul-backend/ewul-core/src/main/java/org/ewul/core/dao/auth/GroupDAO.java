package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.Group;
import org.ewul.model.db.auth.QGroup;
import org.ewul.model.db.auth.Role;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class GroupDAO extends BaseDAO<Group> {

    private static final QGroup PATH = new QGroup("auth_group");

    @Inject
    public GroupDAO(EntityDataHandler handler) {
        super(Group.class, handler);
    }

    public Group createGroup(String name, Set<Role> roles) {

        if (name == null) {
            throw new NullPointerException("name");
        }
        if (roles == null) {
            throw new NullPointerException("roles");
        }

        Group group = new Group();
        group.setName(name);
        group.setRoles(roles);

        if (factory().insert(group) == null) {
            throw new IllegalStateException("cannot insert group entity");
        }

        return group;
    }

}
