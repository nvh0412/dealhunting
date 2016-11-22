package com.vagabond.dealhunting.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.model.Location;

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
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationAdapterViewHolder> {
  private Context context;
  private Cursor mCursor;
  private static Location[] locations;

  static {
    locations = new Location[]{new Location(), new Location(), new Location(), new Location()};
  }

  public LocationAdapter(Context context) {
    this.context = context;
  }

  public void swapCursor(Cursor newCursor) {
    mCursor = newCursor;
    notifyDataSetChanged();
  }

  public Cursor getCursor() {
    return mCursor;
  }

  @Override
  public LocationAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if ( parent instanceof RecyclerView ) {
      int layoutId = R.layout.list_item_location;
      View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
      view.setFocusable(true);
      return new LocationAdapterViewHolder(view);
    } else {
      throw new RuntimeException("Not bound to RecyclerView");
    }
  }

  @Override
  public void onBindViewHolder(LocationAdapter.LocationAdapterViewHolder holder, int position) {
//    mCursor.moveToPosition(position);
  }

  @Override
  public int getItemCount() {
    return locations.length;
  }

  public class LocationAdapterViewHolder extends RecyclerView.ViewHolder {
    LocationAdapterViewHolder(View itemView) {
      super(itemView);
    }
  }
}
