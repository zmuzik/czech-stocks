package zmuzik.czechstocks.dao;

import zmuzik.czechstocks.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table QUOTE_LIST_ITEM.
 */
public class QuoteListItem {

    /** Not-null value. */
    private String isin;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient QuoteListItemDao myDao;

    private CurrentQuote currentQuote;
    private String currentQuote__resolvedKey;


    public QuoteListItem() {
    }

    public QuoteListItem(String isin) {
        this.isin = isin;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getQuoteListItemDao() : null;
    }

    /** Not-null value. */
    public String getIsin() {
        return isin;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /** To-one relationship, resolved on first access. */
    public CurrentQuote getCurrentQuote() {
        String __key = this.isin;
        if (currentQuote__resolvedKey == null || currentQuote__resolvedKey != __key) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CurrentQuoteDao targetDao = daoSession.getCurrentQuoteDao();
            CurrentQuote currentQuoteNew = targetDao.load(__key);
            synchronized (this) {
                currentQuote = currentQuoteNew;
            	currentQuote__resolvedKey = __key;
            }
        }
        return currentQuote;
    }

    public void setCurrentQuote(CurrentQuote currentQuote) {
        if (currentQuote == null) {
            throw new DaoException("To-one property 'isin' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.currentQuote = currentQuote;
            isin = currentQuote.getIsin();
            currentQuote__resolvedKey = isin;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}