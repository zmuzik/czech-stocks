package zmuzik.czechstocks;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.RestAdapter;
import zmuzik.czechstocks.dao.DaoMaster;
import zmuzik.czechstocks.dao.DaoSession;

public class App extends Application {

    private final String TAG = this.getClass().getSimpleName();
    private final String DB_NAME = "czech-stocks-db";
    private Date mLastUpdated;

    private SQLiteDatabase mDb;
    private DaoSession mDaoSession;
    private MainActivity mMainActivity;
    ApiService mApiService;

    private Locale mLocale;

    @Override
    public void onCreate() {
        Log.i(TAG, "====================Initializing app====================");
        super.onCreate();

        if (isDebuggable()) {
            Log.d(TAG, "Debug build - Crashlytics disabled");
        } else {
            Log.d(TAG, "Release build - starting Crashlytics");
            Crashlytics.start(this);
        }

        initDb(DB_NAME);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConf.SERVER_API_ROOT).build();
        mApiService = restAdapter.create(ApiService.class);

    }

    private void initDb(String dbName) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, dbName, null);

        mDb = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDb);
        mDaoSession = daoMaster.newSession();

        DbUtils dbUtils = DbUtils.getInstance(this);

        if (!dbUtils.isCurrentDbVersion()) {
            dbUtils.fillTableQuoteListItem();
            dbUtils.saveCurrentDbVersion();
        }
    }

    public void setLastUpdatedTime() {
        mLastUpdated = new Date();
    }

    protected String getLastUpdatedTime() {
        try {
            if (mLastUpdated == null) {
                return null;
            }
            SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            return formater.format(mLastUpdated);
        } catch (Exception e) {
            Crashlytics.logException(e);
            return null;
        }
    }

    protected String getDataFromTime() {
        try {
            if (mDb == null || !mDb.isOpen()) {
                return null;
            }
            Cursor cursor = mDb.rawQuery("SELECT STAMP FROM CURRENT_QUOTE;", null);
            if (!cursor.moveToFirst()) {
                return null;
            }
            SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date lastDataTime = new Date(Long.parseLong(cursor.getString(0)));
            return formater.format(lastDataTime);
        } catch (Exception e) {
            Crashlytics.logException(e);
            return null;
        }
    }


    public double getDoubleValue(String s) {
        if (s == null || "".equals(s)) {
            return (double) 0;
        }

        if (mLocale == null) {
            mLocale = getResources().getConfiguration().locale;
        }
        NumberFormat format = NumberFormat.getInstance(mLocale);
        Number number;
        try {
            number = format.parse(s);
        } catch (ParseException e) {
            number = Double.valueOf(s);
        }
        return number.doubleValue();
    }

    public boolean isDebuggable() {
        return 0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
    }

    DaoSession getDaoSession() {
        return mDaoSession;
    }

    void setMainActiviy(MainActivity a) {
        mMainActivity = a;
    }

    MainActivity getMainActivity() {
        return mMainActivity;
    }

    ApiService getApiService() {
        return mApiService;
    }
}