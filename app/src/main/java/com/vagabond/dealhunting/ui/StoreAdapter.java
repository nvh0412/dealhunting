package com.vagabond.dealhunting.ui;/*
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

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreAdpaterViewHolder> {
  private static final int INDEX_COLUMN_ID = 0;
  private Context context;
  private Cursor mCursor;

  public StoreAdapter(Context context) {
    this.context = context;
  }

  @Override
  public StoreAdapter.StoreAdpaterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    if ( viewGroup instanceof RecyclerView ) {
      int layoutId = R.layout.list_item_store;
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
      view.setFocusable(true);
      return new StoreAdapter.StoreAdpaterViewHolder(view);
    } else {
      throw new RuntimeException("Not bound to RecyclerView");
    }
  }

  @Override
  public void onBindViewHolder(StoreAdpaterViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    if ( null == mCursor ) return 0;
    return mCursor.getCount();
  }

  public void swapCursor(Cursor newCursor) {
    mCursor = newCursor;
    notifyDataSetChanged();
  }

  @Override
  public long getItemId(int position) {
    mCursor.moveToPosition(position);
    return mCursor.getLong(INDEX_COLUMN_ID);
  }

  public class StoreAdpaterViewHolder extends RecyclerView.ViewHolder {

    public StoreAdpaterViewHolder(View itemView) {
      super(itemView);
    }
  }
}
