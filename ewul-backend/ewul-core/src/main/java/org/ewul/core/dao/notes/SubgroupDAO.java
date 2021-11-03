package org.ewul.core.dao.notes;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.notes.QSubgroup;
import org.ewul.model.db.notes.Subgroup;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SubgroupDAO extends BaseDAO<Subgroup> {

    private static final QSubgroup PATH = new QSubgroup("notes_subgroup");

    @Inject
    public SubgroupDAO(EntityDataHandler handler) {
        super(Subgroup.class, handler);
    }

}
