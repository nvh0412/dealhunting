package com.vagabond.dealhunting.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.vagabond.dealhunting.R;

public class DetailActivity extends AppCompatActivity {

  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    final Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    viewPager = (ViewPager) findViewById(R.id.detail_viewpager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.detail_tabs);
    tabLayout.setupWithViewPager(viewPager);

    Bundle arguments = new Bundle();
    arguments.putParcelable(DealDetailFragment.DETAIL_URI, getIntent().getData());

    setUpTabs(arguments);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.detail_menu, menu);
    return true;
  }

  private void setUpTabs(Bundle arguments) {
    PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager());
    adapter.addFragment(DealDetailFragment.newInstance(arguments), "Detail");
    adapter.addFragment(DetailLocationFragment.newInstance(arguments), "Locations");
    viewPager.setAdapter(adapter);
  }
}
