package zmuzik.czechstocks.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import zmuzik.czechstocks.dao.CurrentQuote;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CURRENT_QUOTE.
*/
public class CurrentQuoteDao extends AbstractDao<CurrentQuote, String> {

    public static final String TABLENAME = "CURRENT_QUOTE";

    /**
     * Properties of entity CurrentQuote.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Isin = new Property(0, String.class, "isin", true, "ISIN");
        public final static Property Price = new Property(1, double.class, "price", false, "PRICE");
        public final static Property Delta = new Property(2, double.class, "delta", false, "DELTA");
        public final static Property TimeStr = new Property(3, String.class, "timeStr", false, "TIME_STR");
        public final static Property Stamp = new Property(4, long.class, "stamp", false, "STAMP");
    };


    public CurrentQuoteDao(DaoConfig config) {
        super(config);
    }
    
    public CurrentQuoteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CURRENT_QUOTE' (" + //
                "'ISIN' TEXT PRIMARY KEY NOT NULL ," + // 0: isin
                "'PRICE' REAL NOT NULL ," + // 1: price
                "'DELTA' REAL NOT NULL ," + // 2: delta
                "'TIME_STR' TEXT NOT NULL ," + // 3: timeStr
                "'STAMP' INTEGER NOT NULL );"); // 4: stamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CURRENT_QUOTE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CurrentQuote entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getIsin());
        stmt.bindDouble(2, entity.getPrice());
        stmt.bindDouble(3, entity.getDelta());
        stmt.bindString(4, entity.getTimeStr());
        stmt.bindLong(5, entity.getStamp());
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CurrentQuote readEntity(Cursor cursor, int offset) {
        CurrentQuote entity = new CurrentQuote( //
            cursor.getString(offset + 0), // isin
            cursor.getDouble(offset + 1), // price
            cursor.getDouble(offset + 2), // delta
            cursor.getString(offset + 3), // timeStr
            cursor.getLong(offset + 4) // stamp
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CurrentQuote entity, int offset) {
        entity.setIsin(cursor.getString(offset + 0));
        entity.setPrice(cursor.getDouble(offset + 1));
        entity.setDelta(cursor.getDouble(offset + 2));
        entity.setTimeStr(cursor.getString(offset + 3));
        entity.setStamp(cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(CurrentQuote entity, long rowId) {
        return entity.getIsin();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(CurrentQuote entity) {
        if(entity != null) {
            return entity.getIsin();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
