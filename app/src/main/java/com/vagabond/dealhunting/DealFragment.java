package com.vagabond.dealhunting;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.data.DealContract;

public class DealFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
  private static final String LOG_TAG = DealFragment.class.getSimpleName();
  private static final int LOADER_ID = 1;
  private String categoryId;

  public DealFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    if (args != null) {
      categoryId = args.getString("CATEGORY_ID");
      Log.d(LOG_TAG, "onCreate: CATEGORY_ID:" + categoryId);
    }

    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_deal, container, false);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(),
        DealContract.PromotionEntry.buildMovieUriByCategory(categoryId), null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    Log.d(LOG_TAG, "onLoadFinished");
    if (!data.moveToFirst()) {
      Log.d(LOG_TAG, "onLoadFinished: empty promotion");
      return;
    }

    do {
      Log.d(LOG_TAG, "onLoadFinished: " + data.getInt(0));
    } while (data.moveToNext());
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }
}
