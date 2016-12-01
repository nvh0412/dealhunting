package com.vagabond.dealhunting.ui;
/*
 * Copyright (C) 2016 SkyUnity (HoaNV)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.Constant;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;

public class StoreFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, Toolbar.OnMenuItemClickListener {

  private static final int LOADER_ID = 5;
  private static final String LOG_TAG = StoreFragment.class.getSimpleName();
  private RecyclerView storeRecyclerView;
  private StoreAdapter storeAdapter;
  private Toolbar toolbar;

  public static StoreFragment getInstance() {
    return new StoreFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(LOADER_ID, null, this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_store, container, false);

    toolbar = (Toolbar) root.findViewById(R.id.toolbar);
    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

    ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
    actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);

    storeAdapter = new StoreAdapter(getActivity(), new StoreAdapter.StoreAdapterClickHandler() {
      @Override
      public void onClick(int storeId) {
        Intent intent = new Intent(getActivity(), StoreDetail.class);
        intent.putExtra(Constant.BUNDLE_STORE_KEY, storeId);
        startActivity(intent);
      }
    });

    storeRecyclerView = (RecyclerView) root.findViewById(R.id.store_rv);
    storeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    storeRecyclerView.setHasFixedSize(true);
    storeRecyclerView.setAdapter(storeAdapter);
    return root;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);

    toolbar.inflateMenu(R.menu.main);
    toolbar.setOnMenuItemClickListener(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (null != storeRecyclerView) {
      storeRecyclerView.clearOnScrollListeners();
    }
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(),
        DealContract.StoreEntry.CONTENT_URI, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (!data.moveToFirst()) {
      data.close();
      return;
    }

    storeAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    storeRecyclerView.setAdapter(null);
  }

  @Override
  public void onDetach() {
    getActivity().getSupportLoaderManager().destroyLoader(LOADER_ID);
    super.onDetach();
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_search:
        startActivity(new Intent(getActivity(), SearchActivity.class));
        return true;
    }
    return false;
  }
}
