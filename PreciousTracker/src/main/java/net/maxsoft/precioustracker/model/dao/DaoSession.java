package net.maxsoft.precioustracker.model.dao;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig preciousCategoryDaoConfig;
    private final DaoConfig preciousItemDaoConfig;
    private final DaoConfig preciousMoveDaoConfig;
    private final DaoConfig preciousQuickAddDaoConfig;

    private final PreciousCategoryDao preciousCategoryDao;
    private final PreciousItemDao preciousItemDao;
    private final PreciousMoveDao preciousMoveDao;
    private final PreciousQuickAddDao preciousQuickAddDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        preciousCategoryDaoConfig = daoConfigMap.get(PreciousCategoryDao.class).clone();
        preciousCategoryDaoConfig.initIdentityScope(type);

        preciousItemDaoConfig = daoConfigMap.get(PreciousItemDao.class).clone();
        preciousItemDaoConfig.initIdentityScope(type);

        preciousMoveDaoConfig = daoConfigMap.get(PreciousMoveDao.class).clone();
        preciousMoveDaoConfig.initIdentityScope(type);

        preciousQuickAddDaoConfig = daoConfigMap.get(PreciousQuickAddDao.class).clone();
        preciousQuickAddDaoConfig.initIdentityScope(type);

        preciousCategoryDao = new PreciousCategoryDao(preciousCategoryDaoConfig, this);
        preciousItemDao = new PreciousItemDao(preciousItemDaoConfig, this);
        preciousMoveDao = new PreciousMoveDao(preciousMoveDaoConfig, this);
        preciousQuickAddDao = new PreciousQuickAddDao(preciousQuickAddDaoConfig, this);

        registerDao(PreciousCategory.class, preciousCategoryDao);
        registerDao(PreciousItem.class, preciousItemDao);
        registerDao(PreciousMove.class, preciousMoveDao);
        registerDao(PreciousQuickAdd.class, preciousQuickAddDao);
    }
    
    public void clear() {
        preciousCategoryDaoConfig.getIdentityScope().clear();
        preciousItemDaoConfig.getIdentityScope().clear();
        preciousMoveDaoConfig.getIdentityScope().clear();
        preciousQuickAddDaoConfig.getIdentityScope().clear();
    }

    public PreciousCategoryDao getPreciousCategoryDao() {
        return preciousCategoryDao;
    }

    public PreciousItemDao getPreciousItemDao() {
        return preciousItemDao;
    }

    public PreciousMoveDao getPreciousMoveDao() {
        return preciousMoveDao;
    }

    public PreciousQuickAddDao getPreciousQuickAddDao() {
        return preciousQuickAddDao;
    }

}
