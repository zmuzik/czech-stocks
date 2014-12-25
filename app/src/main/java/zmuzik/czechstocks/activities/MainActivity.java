package zmuzik.czechstocks.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import zmuzik.czechstocks.App;
import zmuzik.czechstocks.R;
import zmuzik.czechstocks.UpdateListener;
import zmuzik.czechstocks.adapters.SectionsPagerAdapter;
import zmuzik.czechstocks.fragments.PortfolioListFragment;
import zmuzik.czechstocks.fragments.QuoteListFragment;
import zmuzik.czechstocks.helpers.PrefsHelper;
import zmuzik.czechstocks.tasks.FillDbTablesTask;
import zmuzik.czechstocks.tasks.UpdateCurrentDataTask;
import zmuzik.czechstocks.utils.DbUtils;
import zmuzik.czechstocks.utils.TimeUtils;

public class MainActivity extends Activity implements ActionBar.TabListener, UpdateListener {

    private final String TAG = this.getClass().getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MenuItem mRefreshMenuItem;

    boolean updateInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(App.get(), getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }

        // fill tables with default data if there's new db version
        if (!DbUtils.getInstance().isCurrentDbVersion()) {
            FillDbTablesTask fillDbTablesTask = new FillDbTablesTask(this);
            fillDbTablesTask.execute();
        }
    }

    @Override protected void onResume() {
        super.onResume();
        if (!updateInProgress && isTimeToUpdate()) {
            actionDataRefresh();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mRefreshMenuItem = menu.findItem(R.id.action_refresh);
        if (!updateInProgress && isTimeToUpdate()) {
            actionDataRefresh();
        }
        return true;
    }

    boolean isTimeToUpdate() {
        return PrefsHelper.get().getCurrentQuotesLut() + TimeUtils.ONE_MINUTE < TimeUtils.getNow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                if (mViewPager.getCurrentItem() == 0) {
                    actionAddStock();
                } else {
                    actionAddPortfolioItem();
                }
                break;
            case R.id.action_refresh:
                mRefreshMenuItem = item;
                actionDataRefresh();
                break;
            default:
                break;
        }
        return true;
    }

    void actionAddStock() {
        Intent intent = new Intent(this, AddStockActivity.class);
        startActivity(intent);
    }

    void actionAddPortfolioItem() {
        Intent intent = new Intent(this, AddPortfolioItemActivity.class);
        startActivity(intent);
    }

    void actionDataRefresh() {
        updateInProgress = true;
        setMovingRefreshIcon();
        new UpdateCurrentDataTask(this).execute();
    }

    void setMovingRefreshIcon() {
        if (mRefreshMenuItem == null) return;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.icon_action_refresh, null);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_refresh);
        rotation.setRepeatCount(Animation.INFINITE);
        iv.startAnimation(rotation);
        mRefreshMenuItem.setActionView(iv);
    }

    public void setStaticRefreshIcon() {
        if (mRefreshMenuItem == null || mRefreshMenuItem.getActionView() == null) return;
        mRefreshMenuItem.getActionView().clearAnimation();
        mRefreshMenuItem.setActionView(null);
    }

    public void refreshFragments() {
        if (mSectionsPagerAdapter == null) return;
        if (mSectionsPagerAdapter.getItem(0) != null) {
            ((QuoteListFragment) mSectionsPagerAdapter.getItem(0)).refreshData();
        }
        if (mSectionsPagerAdapter.getItem(1) != null) {
            ((PortfolioListFragment) mSectionsPagerAdapter.getItem(1)).refreshData();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }

    @Override public void loadData() {
        refreshFragments();
        setStaticRefreshIcon();
        updateInProgress = false;
    }
}
