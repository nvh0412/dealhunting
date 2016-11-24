package com.vagabond.dealhunting.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;

public class DealDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  public static final String DETAIL_URI = "detail_uri";
  private static final int INDEX_COLUMN_TITLE = 1;
  private static final int INDEX_COLUMN_IMAGE = 3;
  private static final int INDEX_COLUMN_STORE_IMAGE = 5;
  private static final int INDEX_COLUMN_SUMMARY = 4;
  private static final String LOG_TAG = DealDetailFragment.class.getSimpleName();
  private static final int LOADER_ID = 3;
  private static final String[] DEAIL_PROMOTION_COLUMN = new String[] {
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry._ID,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE_DETAIL,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_IMAGE_URL,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_SUMMARY,
      DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry.COLUMN_THUMBNAIL_URL
  };

  private Uri mUri;
  private ImageView backdropImage;
  private ImageView storeIconImage;
  private TextView titleTextView;
  private TextView summaryTextView;

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
    backdropImage = (ImageView) rootView.findViewById(R.id.detail_image_backdrop);
    storeIconImage = (ImageView) rootView.findViewById(R.id.detail_store_logo);
    titleTextView = (TextView) rootView.findViewById(R.id.detail_title);
    summaryTextView = (TextView) rootView.findViewById(R.id.detail_summary);
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
      return;
    }
    Picasso.with(getActivity()).load(data.getString(INDEX_COLUMN_IMAGE)).into(backdropImage);
    Picasso.with(getActivity()).load(data.getString(INDEX_COLUMN_STORE_IMAGE)).into(storeIconImage);
    titleTextView.setText(data.getString(INDEX_COLUMN_TITLE));
    Log.d(LOG_TAG, data.getString(INDEX_COLUMN_SUMMARY));
    summaryTextView.setText(Html.fromHtml(data.getString(INDEX_COLUMN_SUMMARY)));
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) { }
}
