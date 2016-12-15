package com.vagabond.dealhunting.ui;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.model.Location;
import com.vagabond.dealhunting.services.WebService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.vagabond.dealhunting.ui.DealDetailFragment.DETAIL_URI;

public class DetailLocationFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnMapReadyCallback {

  private static final int LOADER_ID = 5;
  private static final String[] DEAIL_PROMOTION_COLUMN = new String[] {
    DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry._ID,
    DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry.COLUMN_TITLE,
  };
  private static final String LOG_TAG = DetailLocationFragment.class.getSimpleName();
  private MapView mMapView;
  private RecyclerView recycleView;
  private LocationAdapter locationAdapter;
  private Uri mUri;
  private Cursor mCursor;
  private GoogleMap mGoogleMap;

  public DetailLocationFragment() {
  }

  public static DetailLocationFragment newInstance(Bundle args) {
    DetailLocationFragment fragment = new DetailLocationFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUri = getArguments().getParcelable(DETAIL_URI);
    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_detail_location, container, false);

    mMapView = (MapView) root.findViewById(R.id.mapView);

    mMapView.onCreate(savedInstanceState);
    mMapView.onResume();

    ((Callback)getActivity()).syncMap(mMapView);

    recycleView = (RecyclerView) root.findViewById(R.id.location_recycler_view);

    locationAdapter = new LocationAdapter(getActivity());
    recycleView.setAdapter(locationAdapter);
    recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

    return root;
  }

  private void fetchLocationData(final int storeId) {
    WebService.getDealHuntingervice().getLocationData(storeId)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Action1<List<Location>>() {
              @Override
              public void call(List<Location> locationList) {
                Log.d(LOG_TAG, "Sync list of all locations by Store: " + storeId);
                locationListHanlder(locationList);
              }
            },
            new Action1<Throwable>() {
              @Override
              public void call(Throwable e) {
                Log.e(LOG_TAG, "Error: Can't sync data from API", e);
              }
            }
        );
  }

  private void locationListHanlder(List<Location> locationList) {
    locationAdapter.setLocations(locationList);

    if (mGoogleMap != null) {
      for (Location location : locationList) {
        mGoogleMap.addMarker(new MarkerOptions()
            .position(new LatLng(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLon())))
            .title(location.getName()));
      }
    }
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), mUri, DEAIL_PROMOTION_COLUMN, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mCursor = data;
    if (mCursor != null && !mCursor.moveToFirst()) {
      Log.e(LOG_TAG, "Error reading item detail cursor");
      mCursor.close();
      mCursor = null;
      return;
    }

    fetchLocationData(mCursor.getInt(0));
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mGoogleMap = googleMap;
  }

  public interface Callback {
    void syncMap(MapView mapView);
  }

}