package org.ewul.core.dao.auth;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.Group;
import org.ewul.model.db.auth.QGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GroupDAO extends BaseDAO<Group> {

    private static final QGroup PATH = new QGroup("auth_group");

    @Inject
    public GroupDAO(EntityDataHandler handler) {
        super(Group.class, handler);
    }

}
