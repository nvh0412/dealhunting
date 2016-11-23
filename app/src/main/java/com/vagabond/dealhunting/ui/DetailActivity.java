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
import android.view.Menu;

import com.vagabond.dealhunting.R;

public class DetailActivity extends AppCompatActivity implements DetailLocationFragment.Callback, OnMapReadyCallback {

  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  private ViewPager viewPager;
  private GoogleMap mMap;
  private boolean mPermissionDenied;

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

  @Override
  public void syncMap(MapView mapView) {
    mapView.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    enableMyLocation();
  }

  /**
   * Enables the My Location layer if the fine location permission has been granted.
   */
  private void enableMyLocation() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // Permission to access the location is missing.
      PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
          Manifest.permission.ACCESS_FINE_LOCATION, true);
    } else if (mMap != null) {
      // Access to the location has been granted to the app.
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
      // Enable the my location layer if the permission has been granted.
      enableMyLocation();
    } else {
      // Display the missing permission error dialog when the fragments resume.
      mPermissionDenied = true;
    }
  }

  @Override
  protected void onResumeFragments() {
    super.onResumeFragments();
    if (mPermissionDenied) {
      // Permission was not granted, display error dialog.
      showMissingPermissionError();
      mPermissionDenied = false;
    }
  }

  /**
   * Displays a dialog with error message explaining that the location permission is missing.
   */
  private void showMissingPermissionError() {
    PermissionUtils.PermissionDeniedDialog
        .newInstance(true).show(getSupportFragmentManager(), "dialog");
  }
}
