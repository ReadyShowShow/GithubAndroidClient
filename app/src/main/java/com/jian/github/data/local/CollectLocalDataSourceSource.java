package com.jian.github.data.local;

import com.jian.github.bean.localbean.CollectedRepo;
import com.jian.github.dao.CollectedRepoDao;
import com.jian.github.dao.DaoSession;
import com.jian.github.data.ICollectDataSource;
import com.jian.github.db.DaoHelper;
import com.jian.github.utils.Log;

import java.util.List;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * Description：收藏的库数据源
 */
public class CollectLocalDataSourceSource implements ICollectDataSource {

    private final static String TAG = CollectLocalDataSourceSource.class.getSimpleName();

    private CollectedRepoDao mRepoDao;

    public CollectLocalDataSourceSource() {
        DaoSession daoSession = DaoHelper.getDaoSession();
        mRepoDao = daoSession.getCollectedRepoDao();
    }

    @Override
    public List<CollectedRepo> queryCollectRepo(int page, int limit) {
        return mRepoDao.queryBuilder()
                .limit(limit)
                .offset(page * limit)
                .orderDesc(CollectedRepoDao.Properties.CollectTime)
                .build()
                .list();
    }

    @Override
    public List<CollectedRepo> queryByKeyword(String keyword, int page, int limit) {
        WhereCondition fullName = CollectedRepoDao.Properties.FullName.like("%" + keyword + "%");
        WhereCondition desc = CollectedRepoDao.Properties.Description.like("%" + keyword + "%");
        QueryBuilder<CollectedRepo> qb = mRepoDao.queryBuilder();
        return qb.where(qb.or(fullName, desc)).limit(limit).offset(page * limit)
                .orderDesc(CollectedRepoDao.Properties.CollectTime)
                .list();
    }

    @Override
    public boolean collectRepo(CollectedRepo repo) {
        long rawId = mRepoDao.insertOrReplace(repo);
        return -1 != rawId;
    }

    @Override
    public boolean cancelRepo(CollectedRepo repo) {
        return cancelRepo(repo.getId());
    }

    @Override
    public boolean cancelRepo(Long id) {
        if (id == null) {
            Log.w(TAG, "cancelRepo failure, id == null");
            return false;
        }
        mRepoDao.deleteByKey(id);
        return true;
    }
}
