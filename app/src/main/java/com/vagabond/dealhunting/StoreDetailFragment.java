package com.vagabond.dealhunting;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.ui.DealFragment;


/**
 * A simple {@link Fragment} subclass. Use the {@link StoreDetailFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class StoreDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  public StoreDetailFragment() {
  }

  public static StoreDetailFragment newInstance() {
    StoreDetailFragment fragment = new StoreDetailFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivity().getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.store_promotion_fl, DealFragment.newInstance("9"))
        .commit();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_store_detail, container, false);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return null;
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }
}
