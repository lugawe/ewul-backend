package org.ewul.core.dao.notes;

import org.ewul.core.dao.BaseDAO;
import org.ewul.core.entity.EntityDataHandler;
import org.ewul.model.db.auth.Account;
import org.ewul.model.db.notes.Category;
import org.ewul.model.db.notes.QCategory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CategoryDAO extends BaseDAO<Category> {

    private static final QCategory PATH = new QCategory("notes_category");

    @Inject
    public CategoryDAO(EntityDataHandler handler) {
        super(Category.class, handler);
    }

    public List<Category> getAll() {
        return factory().selectFrom(PATH).fetch();
    }

    public List<Category> getAllByAccount(Account account) {

        if (account == null) {
            throw new NullPointerException("account");
        }

        return factory().selectFrom(PATH).where(PATH.account.eq(account)).fetch();
    }

}
