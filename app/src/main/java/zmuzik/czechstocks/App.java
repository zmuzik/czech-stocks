package zmuzik.czechstocks;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;
import zmuzik.czechstocks.dao.DaoMaster;
import zmuzik.czechstocks.dao.DaoSession;
import zmuzik.czechstocks.events.MainThreadBus;
import zmuzik.czechstocks.widgets.PortfolioWidgetProvider;
import zmuzik.czechstocks.widgets.QuoteListWidgetProvider;

public class App extends Application {

    private final String TAG = this.getClass().getSimpleName();
    private static final MainThreadBus BUS = new MainThreadBus();
    private static App mApp;
    private static DaoSession mDaoSession;
    private static ServerApi mServerApi;

    private SQLiteDatabase mDb;

    public static App get() {
        return mApp;
    }

    public static DaoSession getDaoSsn() {
        return mDaoSession;
    }

    public static ServerApi getServerApi() {
        return mServerApi;
    }

    public static MainThreadBus getBus() {
        return BUS;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "====================Initializing app====================");
        mApp = this;
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initServerApi();
        initDb();
    }

    private void initServerApi() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AppConf.SERVER_API_ROOT).build();
        mServerApi = restAdapter.create(ServerApi.class);
    }

    private void initDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, AppConf.DB_NAME, null);
        mDb = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(mDb);
        mDaoSession = daoMaster.newSession();
    }

    public boolean isDebuggable() {
        return 0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public int getAppVersionCode() {
        try {
            return App.get().getPackageManager().getPackageInfo(App.get().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public void refreshPortfolioWidgets() {
        Intent intent = new Intent(this, PortfolioWidgetProvider.class);
        intent.setAction(PortfolioWidgetProvider.ACTION_PORTFOLIO_WIDGET_REFRESH);
        int[] ids = AppWidgetManager.getInstance(this).
                getAppWidgetIds(new ComponentName(this, PortfolioWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        Log.d(TAG, "sending intent to refresh portfolio widgets");
        sendBroadcast(intent);
    }

    public void refreshQuoteListWidgets() {
        Intent intent = new Intent(this, QuoteListWidgetProvider.class);
        intent.setAction(PortfolioWidgetProvider.ACTION_WIDGETS_REFRESH);
        int[] ids = AppWidgetManager.getInstance(this).
                getAppWidgetIds(new ComponentName(this, QuoteListWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        Log.d(TAG, "sending intent to refresh quote list widgets");
        sendBroadcast(intent);
    }

    public void refreshAllWidgets() {
        refreshPortfolioWidgets();
        refreshQuoteListWidgets();
    }
}
