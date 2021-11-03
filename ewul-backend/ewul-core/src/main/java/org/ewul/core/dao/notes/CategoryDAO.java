package org.ewul.core.dao.notes;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.notes.Category;
import org.ewul.model.db.notes.QCategory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CategoryDAO extends BaseDAO<Category> {

    private static final QCategory PATH = new QCategory("notes_category");

    @Inject
    public CategoryDAO(EntityDataHandler handler) {
        super(Category.class, handler);
    }

}
