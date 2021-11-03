package org.ewul.core.dao.notes;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.notes.Group;
import org.ewul.model.db.notes.QGroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GroupDAO extends BaseDAO<Group> {

    private static final QGroup PATH = new QGroup("notes_group");

    @Inject
    public GroupDAO(EntityDataHandler handler) {
        super(Group.class, handler);
    }

}
