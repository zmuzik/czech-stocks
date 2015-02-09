package zmuzik.czechstocks.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import zmuzik.czechstocks.App;
import zmuzik.czechstocks.R;
import zmuzik.czechstocks.adapters.SectionsPagerAdapter;
import zmuzik.czechstocks.tasks.FillDbTablesTask;
import zmuzik.czechstocks.utils.DbUtils;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private final String TAG = this.getClass().getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(App.get(), getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
//        }o
        getSupportActionBar().setDisplayShowHomeEnabled(false);
       // getSupportActionBar().setIcon(R.drawable.ic_launcher);

        //fill the default data if necessary
        if (!DbUtils.getInstance().isDataFilled()) new FillDbTablesTask(this).execute();
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
