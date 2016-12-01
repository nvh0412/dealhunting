package com.vagabond.dealhunting.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.StoreDetailFragment;

public class StoreDetail extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store_detail);

    final Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    getSupportFragmentManager().beginTransaction().
    replace(R.id.store_content_fl, StoreDetailFragment.newInstance()).commit();
  }
}
