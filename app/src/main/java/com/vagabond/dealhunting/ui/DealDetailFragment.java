package com.vagabond.dealhunting.ui;

import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;

public class DealDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String DETAIL_URI = "detail_uri";
  private static final int INDEX_COLUMN_TITLE = 1;
  private static final int INDEX_COLUMN_IMAGE = 3;
  private static final String LOG_TAG = DealDetailFragment.class.getSimpleName();
  private static final int LOADER_ID = 3;
  private static final String[] DEAIL_PROMOTION_COLUMN = new String[] {
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry._ID,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE_DETAIL,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_IMAGE_URL
  };
  private Uri mUri;
  private ImageView backdropImage;

  public DealDetailFragment() {
  }

  public static DealDetailFragment newInstance(Bundle arguments) {
    DealDetailFragment fragment = new DealDetailFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_deal_detail, container, false);
    backdropImage = (ImageView)rootView.findViewById(R.id.detail_image_backdrop);
    return rootView;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUri = getArguments().getParcelable(DETAIL_URI);
    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), mUri, DEAIL_PROMOTION_COLUMN, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    Log.d(LOG_TAG, "onLoadFinished ");
    if (!data.moveToFirst()) {
      Log.e(LOG_TAG, "onLoadFinished error data: " + data);
      return;
    }
    Picasso.with(getActivity()).load(data.getString(INDEX_COLUMN_IMAGE)).into(backdropImage);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) { }
}
