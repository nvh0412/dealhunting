package com.vagabond.dealhunting.ui;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.vagabond.dealhunting.R;

public class DetailActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener, DetailLocationFragment.Callback, OnMapReadyCallback {

  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private ViewPager viewPager;
  private GoogleMap mMap;
  private boolean mPermissionDenied;
  private DetailLocationFragment.LocationMapHandler mHandler;

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

  private void setUpTabs(Bundle arguments) {
    PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager());
    adapter.addFragment(DealDetailFragment.newInstance(arguments), "Detail");
    adapter.addFragment(DetailLocationFragment.newInstance(arguments), "Locations");
    viewPager.setAdapter(adapter);
  }

  @Override
  public void syncMap(MapView mapView, DetailLocationFragment.LocationMapHandler handler) {
    mapView.getMapAsync(this);
    mHandler = handler;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMyLocationButtonClickListener(this);
    enableMyLocation();
    mHandler.handler(mMap);
  }

  /**
   * Enables the My Location layer if the fine location permission has been granted.
   */
  private void enableMyLocation() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
          Manifest.permission.ACCESS_FINE_LOCATION, true);
    } else if (mMap != null) {
      mMap.setMyLocationEnabled(true);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
      return;
    }

    if (PermissionUtils.isPermissionGranted(permissions, grantResults,
        Manifest.permission.ACCESS_FINE_LOCATION)) {
      enableMyLocation();
    } else {
      mPermissionDenied = true;
    }
  }

  @Override
  protected void onResumeFragments() {
    super.onResumeFragments();
    if (mPermissionDenied) {
      showMissingPermissionError();
      mPermissionDenied = false;
    }
  }

  private void showMissingPermissionError() {
    PermissionUtils.PermissionDeniedDialog
        .newInstance(true).show(getSupportFragmentManager(), "dialog");
  }

  @Override
  public boolean onMyLocationButtonClick() {
    Toast.makeText(this, getString(R.string.mylocation_clicked), Toast.LENGTH_SHORT).show();
    return false;
  }
}
