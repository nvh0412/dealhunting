package com.vagabond.dealhunting.ui;


import android.content.Intent;
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
  private static final int COLUMN_PROMOTION_ID_INDEX = 0;
  private static final String CATEGORY_ID_KEY = "CATEGORY_ID";
  private String categoryId;
  private RecyclerView recycleView;
  private DealAdapter dealAdapter;
  private View emptyView;

  public static DealFragment newInstance(String categoryId) {
    DealFragment fragment = new DealFragment();
    Bundle args = new Bundle();
    args.putString(CATEGORY_ID_KEY, categoryId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Bundle args = getArguments();
    if (args != null) {
      categoryId = args.getString(CATEGORY_ID_KEY);
    }
    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_deal, container, false);
    recycleView = (RecyclerView) root.findViewById(R.id.deal_recycler_view);

    dealAdapter = new DealAdapter(getActivity(), new DealAdapter.ForecastAdapterOnClickHandler() {
      @Override
      public void onClick(Cursor cursor, DealAdapter.DealAdapterViewHolder vh) {
        if (null != cursor) {
          cursor.moveToPosition(vh.getAdapterPosition());
          Uri dealUri = DealContract.PromotionEntry.buildPromotionUri(cursor.getInt(COLUMN_PROMOTION_ID_INDEX));

          Intent intent = new Intent(getActivity(), DetailActivity.class);
          intent.setData(dealUri);
          startActivity(intent);
        }
      }
    }, emptyView);

    dealAdapter.setHasStableIds(true);
    recycleView.setAdapter(dealAdapter);
    int columnCount = getResources().getInteger(R.integer.list_column_count);
    recycleView.setLayoutManager(new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));
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
      data.close();
      return;
    }

    dealAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    recycleView.setAdapter(null);
  }
}
