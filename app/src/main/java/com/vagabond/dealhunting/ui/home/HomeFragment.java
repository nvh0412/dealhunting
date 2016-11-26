package com.vagabond.dealhunting.ui.home;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.ui.DealFragment;
import com.vagabond.dealhunting.ui.PagerFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

  private static final String LOG_TAG = HomeFragment.class.getSimpleName();
  private static final int COLUMN_CATEGORY_ID_INDEX = 0;
  private static final int COLUMN_CATEGORY_TITLE_INDEX = 1;
  private static final int LOADER_ID = 0;
  private ViewPager viewPager;

  public static HomeFragment getInstance() {
    return new HomeFragment();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    viewPager = (ViewPager) root.findViewById(R.id.viewpager);

    TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
    tabLayout.setVisibility(View.VISIBLE);
    tabLayout.setupWithViewPager(viewPager);
    return root;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), DealContract.CategoryEntry.CONTENT_URI, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (!data.moveToFirst()) {
      return;
    }

    int countFragment = 0;

    if (viewPager.getAdapter() != null) {
      countFragment = viewPager.getAdapter().getCount();
    }

    PagerFragmentAdapter adapter = new PagerFragmentAdapter(getActivity().getSupportFragmentManager());

    do {
      adapter.addFragment(
          DealFragment.newInstance(String.valueOf(data.getInt(COLUMN_CATEGORY_ID_INDEX))),
          data.getString(COLUMN_CATEGORY_TITLE_INDEX));
    } while (data.moveToNext());

    if (adapter.getCount() != countFragment) {
      viewPager.setAdapter(adapter);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }

  public boolean backHanlder() {
    if (viewPager.getCurrentItem() == 0) {
      return false;
    } else {
      viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
      return true;
    }
  }

  @Override
  public void onDetach() {
    getActivity().getSupportLoaderManager().destroyLoader(LOADER_ID);
    super.onDetach();
  }
}
