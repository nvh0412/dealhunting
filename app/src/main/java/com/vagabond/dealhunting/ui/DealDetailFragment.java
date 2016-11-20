package com.vagabond.dealhunting.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;

public class DealDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String DETAIL_URI = "detail_uri";
  private Uri mUri;

  public DealDetailFragment() {
  }

  public static DealDetailFragment newInstance(Bundle arguments) {
    DealDetailFragment fragment = new DealDetailFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_deal_detail, container, false);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUri = getArguments().getParcelable(DETAIL_URI);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), mUri, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) { }
}
