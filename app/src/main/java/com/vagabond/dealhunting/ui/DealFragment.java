package com.vagabond.dealhunting.ui;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;

public class DealFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
  private static final String LOG_TAG = DealFragment.class.getSimpleName();
  private static final int LOADER_ID = 1;
  private String categoryId;
  private RecyclerView recycleView;
  private DealAdapter dealAdapter;
  private View emptyView;

  public DealFragment() {
  }

  public interface Callback {
    void onItemSelected(Uri dealUri, DealAdapter.DealAdapterViewHolder vh);
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
    View root = inflater.inflate(R.layout.fragment_deal, container, false);
    recycleView = (RecyclerView) root.findViewById(R.id.deal_recycler_view);

    dealAdapter = new DealAdapter(getActivity(), new DealAdapter.ForecastAdapterOnClickHandler() {
      @Override
      public void onClick(DealAdapter.DealAdapterViewHolder vh) {
        ((Callback)getActivity()).onItemSelected(null, vh);
      }
    }, emptyView);
    dealAdapter.setHasStableIds(true);

    recycleView.setAdapter(dealAdapter);
    int columnCount = getResources().getInteger(R.integer.list_column_count);
    StaggeredGridLayoutManager sglm =
        new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
    recycleView.setLayoutManager(sglm);

    return root;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(),
        DealContract.PromotionEntry.buildPromotionUriByCategory(categoryId), null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    Log.d(LOG_TAG, "onLoadFinished");
    if (!data.moveToFirst()) {
      Log.d(LOG_TAG, "onLoadFinished: empty promotion");
      return;
    }

    dealAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    recycleView.setAdapter(null);
  }
}
