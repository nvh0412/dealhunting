package com.vagabond.dealhunting.ui;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vagabond.dealhunting.Constant;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;


/**
 * A simple {@link Fragment} subclass. Use the {@link StoreDetailFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class StoreDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  private static final String[] STORE_COLUMN = {
      DealContract.StoreEntry._ID,
      DealContract.StoreEntry.COLUMN_TITLE,
      DealContract.StoreEntry.COLUMN_THUMBNAIL_URL,
  };
  private static final String LOG_TAG = StoreDetailFragment.class.getSimpleName();
  private static final int INDEX_STORE_COLUMN_IMAGE = 2;
  private Cursor mCursor;
  private ImageView mStoreDetailLogo;

  public StoreDetailFragment() {
  }

  public static StoreDetailFragment newInstance(int storeId) {
    StoreDetailFragment fragment = new StoreDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Constant.BUNDLE_STORE_KEY, storeId);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(Constant.STORE_DETAIL_LOADER_ID, null, this);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivity().getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.store_promotion_fl, DealFragment.newInstance(getArguments().getInt(Constant.BUNDLE_STORE_KEY)))
        .commit();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_store_detail, container, false);
    mStoreDetailLogo = (ImageView) root.findViewById(R.id.store_detail_banner);
    return root;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {

    return new CursorLoader(
        getActivity(),
        DealContract.StoreEntry.buildStoreUri(getArguments().getInt(Constant.BUNDLE_STORE_KEY)),
        STORE_COLUMN,
        null, null, null
    );
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mCursor = data;
    if (mCursor != null && !mCursor.moveToFirst()) {
      mCursor.close();
      mCursor = null;
    }

    Picasso.with(getActivity()).load(mCursor.getString(INDEX_STORE_COLUMN_IMAGE)).into(new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mStoreDetailLogo.setImageBitmap(bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
          @Override
          public void onGenerated(Palette palette) {
            Palette.Swatch brandSwatch = palette.getDominantSwatch();
            if (brandSwatch != null) {
              mStoreDetailLogo.setBackgroundColor(brandSwatch.getRgb());
            }
          }
        });
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {

      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {

      }
    });
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }
}
