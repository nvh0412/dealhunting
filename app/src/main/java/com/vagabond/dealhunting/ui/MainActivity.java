package com.vagabond.dealhunting.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.sync.DealHuntingSyncAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DealFragment.Callback {
  private static final String LOG_TAG = MainActivity.class.getSimpleName();
  private static final int LOADER_ID = 0;
  private static final int COLUMN_CATEGORY_ID_INDEX = 0;
  private static final int COLUMN_CATEGORY_TITLE_INDEX = 1;
  private DrawerLayout mDrawerLayout;
  private ActionBarDrawerToggle mDrawerToggle;
  private ViewPager viewPager;
  private NavigationView mNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(LOG_TAG, "onCreate");
    super.onCreate(savedInstanceState);

    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());

    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    viewPager = (ViewPager) findViewById(R.id.viewpager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    mDrawerToggle = new ActionBarDrawerToggle(
      this,
      mDrawerLayout, R.drawable.ic_hamburger,
      R.string.drawer_open,
      R.string.drawer_close
    ) {
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
      }
    };

    mDrawerLayout.setDrawerListener(mDrawerToggle);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);

    mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    setupDrawerContent(mNavigationView);

    getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    DealHuntingSyncAdapter.initializeSyncAdapter(this);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    Log.d(LOG_TAG, "onBackPressed: Pager position" + viewPager.getCurrentItem());
    if (viewPager.getCurrentItem() == 0) {
      super.onBackPressed();
    } else {
      viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this, DealContract.CategoryEntry.CONTENT_URI, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    // Add Tab to Tablayout
    Log.d(LOG_TAG, "onLoadFinished");
    if (!data.moveToFirst()) {
      return;
    }

    int countFragment = 0;
    if (viewPager.getAdapter() != null) {
      countFragment = viewPager.getAdapter().getCount();
    }

    PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager());

    do {
      adapter.addFragment(
          DealFragment.newInstance(String.valueOf(data.getInt(COLUMN_CATEGORY_ID_INDEX))),
          data.getString(COLUMN_CATEGORY_TITLE_INDEX));
    } while (data.moveToNext());

    if (adapter.getCount() != countFragment) {
      viewPager.setAdapter(adapter);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) { }

  @Override
  public void onItemSelected(Uri dealUri, DealAdapter.DealAdapterViewHolder vh) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.setData(dealUri);
    startActivity(intent);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(LOG_TAG, "Navigation selected");
        return false;
      }
    });
  }
}
