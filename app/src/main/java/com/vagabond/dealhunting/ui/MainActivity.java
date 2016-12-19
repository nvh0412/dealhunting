package com.vagabond.dealhunting.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.sync.DealHuntingSyncAdapter;
import com.vagabond.dealhunting.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = MainActivity.class.getSimpleName();
  private ActionBarDrawerToggle mDrawerToggle;
  private HomeFragment homeFragment;
  private DrawerLayout mDrawerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(LOG_TAG, "onCreate");
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      homeFragment = HomeFragment.getInstance();
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.content_fl, homeFragment)
          .commit();
    }

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_hamburger, R.string.drawer_open, R.string.drawer_close);
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    setupDrawerContent(mNavigationView);

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
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (!homeFragment.backHandler()) {
      super.onBackPressed();
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

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
          case R.id.ic_navview_browse:
            fragment = HomeFragment.getInstance();
            break;
          case R.id.ic_navview_store:
            fragment = StoreFragment.getInstance();
            break;
          case R.id.ic_navview_tutorial:
            Intent intent = new Intent(getApplication(), IntroActivity.class);
            startActivity(intent);
            return true;
          case R.id.about_nav_item:
            break;
        }

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.content_fl, fragment)
            .addToBackStack("menu_back_stack")
            .commit();

        mDrawerLayout.closeDrawers();
        return true;
      }
    });
  }
}
