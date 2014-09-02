package zmuzik.czechstocks.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import zmuzik.czechstocks.dao.CurrentQuote;
import zmuzik.czechstocks.dao.PortfolioItem;
import zmuzik.czechstocks.dao.QuoteListItem;

import zmuzik.czechstocks.dao.CurrentQuoteDao;
import zmuzik.czechstocks.dao.PortfolioItemDao;
import zmuzik.czechstocks.dao.QuoteListItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig currentQuoteDaoConfig;
    private final DaoConfig portfolioItemDaoConfig;
    private final DaoConfig quoteListItemDaoConfig;

    private final CurrentQuoteDao currentQuoteDao;
    private final PortfolioItemDao portfolioItemDao;
    private final QuoteListItemDao quoteListItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        currentQuoteDaoConfig = daoConfigMap.get(CurrentQuoteDao.class).clone();
        currentQuoteDaoConfig.initIdentityScope(type);

        portfolioItemDaoConfig = daoConfigMap.get(PortfolioItemDao.class).clone();
        portfolioItemDaoConfig.initIdentityScope(type);

        quoteListItemDaoConfig = daoConfigMap.get(QuoteListItemDao.class).clone();
        quoteListItemDaoConfig.initIdentityScope(type);

        currentQuoteDao = new CurrentQuoteDao(currentQuoteDaoConfig, this);
        portfolioItemDao = new PortfolioItemDao(portfolioItemDaoConfig, this);
        quoteListItemDao = new QuoteListItemDao(quoteListItemDaoConfig, this);

        registerDao(CurrentQuote.class, currentQuoteDao);
        registerDao(PortfolioItem.class, portfolioItemDao);
        registerDao(QuoteListItem.class, quoteListItemDao);
    }
    
    public void clear() {
        currentQuoteDaoConfig.getIdentityScope().clear();
        portfolioItemDaoConfig.getIdentityScope().clear();
        quoteListItemDaoConfig.getIdentityScope().clear();
    }

    public CurrentQuoteDao getCurrentQuoteDao() {
        return currentQuoteDao;
    }

    public PortfolioItemDao getPortfolioItemDao() {
        return portfolioItemDao;
    }

    public QuoteListItemDao getQuoteListItemDao() {
        return quoteListItemDao;
    }

}
