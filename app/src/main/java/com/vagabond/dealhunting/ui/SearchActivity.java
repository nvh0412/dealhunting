package com.vagabond.dealhunting.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.adapter.SearchAdapter;
import com.vagabond.dealhunting.data.DealContract;

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
public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
  private static final String ARG_QUERY = "query";
  private static final String SCREEN_LABEL = "Search";
  private static final int COLUMN_PROMOTION_ID_INDEX = 0;
  private SearchView mSearchView;
  private String mQuery = "";
  private ListView mSearchResults;
  private SearchAdapter mResultsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search);

    mSearchView = (SearchView) findViewById(R.id.search_view);
    setupSearchView();
    mSearchResults = (ListView) findViewById(R.id.search_results);
    mResultsAdapter = new SearchAdapter(this, null, 0);
    mSearchResults.setAdapter(mResultsAdapter);
    mSearchResults.setOnItemClickListener(this);

    String query = getIntent().getStringExtra(SearchManager.QUERY);
    query = query == null ? "" : query;
    mQuery = query;

    if (mSearchView != null) {
      mSearchView.setQuery(query, false);
    }
  }

  private void setupSearchView() {
    SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
    mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    mSearchView.setIconified(false);
    // Set the query hint.
    mSearchView.setQueryHint(getString(R.string.search_hint));
    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        mSearchView.clearFocus();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        searchFor(s);
        return true;
      }
    });

    if (!TextUtils.isEmpty(mQuery)) {
      mSearchView.setQuery(mQuery, false);
    }
  }

  public void dismiss(View view) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

    } else {
      ActivityCompat.finishAfterTransition(this);
    }
  }

  private void searchFor(String query) {
    Bundle args = new Bundle(1);
    if (query == null) {
      query = "";
    }
    args.putString(ARG_QUERY, query);

    if (TextUtils.equals(query, mQuery)) {
      getSupportLoaderManager().initLoader(SearchPromotionQuery.TOKEN, args, this);
    } else {
      getSupportLoaderManager().restartLoader(SearchPromotionQuery.TOKEN, args, this);
    }
    mQuery = query;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this,
        DealContract.PromotionEntry.CONTENT_URI,
        SearchPromotionQuery.PROJECTION,
        null, new String[] {args.getString(ARG_QUERY)}, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mResultsAdapter.swapCursor(data);
    mSearchResults.setVisibility(data.getCount() > 0 ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    mResultsAdapter.swapCursor(null);
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    Cursor cursor = mResultsAdapter.getCursor();
    cursor.moveToPosition(position);

    Uri dealUri = DealContract.PromotionEntry.buildPromotionUri(cursor.getInt(COLUMN_PROMOTION_ID_INDEX));

    Intent intent = new Intent(this, DetailActivity.class);
    intent.setData(dealUri);
    startActivity(intent);
  }

  private interface SearchPromotionQuery {
    int TOKEN = 0x4;
    String[] PROJECTION = DealContract.PromotionEntry.DEFAULT_PROJECTION;
  }
}
