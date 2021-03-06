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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
  private static final int INDEX_COLUMN_SUMMARY = 4;
  private static final int INDEX_COLUMN_STORE_IMAGE = 5;
  private static final int INDEX_COLUMN_STORE_TITLE = 6;
  private static final String LOG_TAG = DealDetailFragment.class.getSimpleName();
  private static final int LOADER_ID = 3;
  private static final String[] DEAIL_PROMOTION_COLUMN = new String[] {
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry._ID,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE_DETAIL,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_IMAGE_URL,
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_SUMMARY,
      DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry.COLUMN_THUMBNAIL_URL,
      DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry.COLUMN_TITLE
  };
  private static final String FORECAST_SHARE_HASHTAG = "#DealHuntingApp";


  private Uri mUri;
  private ImageView backdropImage;
  private ImageView storeIconImage;
  private TextView titleTextView;
  private TextView summaryTextView;
  private String mDeal;
  private Cursor mCursor;

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
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    if ( getActivity() instanceof DetailActivity ){
      inflater.inflate(R.menu.detail_menu, menu);
      finishCreatingMenu(menu);
    }
  }

  private void finishCreatingMenu(Menu menu) {
    // Retrieve the share menu item
    MenuItem menuItem = menu.findItem(R.id.menu_share);
    menuItem.setIntent(createShareDealIntent());
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
    mCursor = data;
    if (mCursor != null && !mCursor.moveToFirst()) {
      Log.e(LOG_TAG, "Error reading item detail cursor");
      mCursor.close();
      mCursor = null;
      return;
    }

    Picasso.with(getActivity()).load(mCursor.getString(INDEX_COLUMN_IMAGE)).into(backdropImage);
    Picasso.with(getActivity()).load(mCursor.getString(INDEX_COLUMN_STORE_IMAGE)).into(storeIconImage);

    String storeName = mCursor.getString(INDEX_COLUMN_STORE_TITLE);
    String dealTitle = mCursor.getString(INDEX_COLUMN_TITLE);
    titleTextView.setText(dealTitle);
    summaryTextView.setText(Html.fromHtml(mCursor.getString(INDEX_COLUMN_SUMMARY)));

    mDeal = String.format("%s - %s ", storeName, dealTitle);
    setHasOptionsMenu(true);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) { mCursor.close(); }

  private Intent createShareDealIntent() {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, mDeal + FORECAST_SHARE_HASHTAG);
    return shareIntent;
  }
}
