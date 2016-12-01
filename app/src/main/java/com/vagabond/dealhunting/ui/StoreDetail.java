package com.vagabond.dealhunting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vagabond.dealhunting.Constant;
import com.vagabond.dealhunting.R;

public class StoreDetail extends AppCompatActivity {

  private static final String LOG_TAG = StoreDetail.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store_detail);

    Intent intent = getIntent();
    int storeId = intent.getIntExtra(Constant.BUNDLE_STORE_KEY, 0);

    final Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    getSupportFragmentManager().beginTransaction().
        replace(R.id.store_content_fl, StoreDetailFragment.newInstance(storeId)).commit();
  }
}
