package com.vagabond.dealhunting.ui;

import com.google.android.gms.maps.MapView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;

public class DetailLocationFragment extends Fragment {

  private MapView mMapView;
  private RecyclerView recycleView;
  private LocationAdapter locationAdapter;

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
    if (getArguments() != null) {
    }
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

  public interface Callback {
    void syncMap(MapView mapView);
  }

}