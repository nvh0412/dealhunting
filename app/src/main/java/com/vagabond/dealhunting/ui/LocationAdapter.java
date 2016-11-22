package com.vagabond.dealhunting.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    locations = new Location[]{
        new Location("Store Address 1", "Store Address 1"),
        new Location("Store Address 1", "Store Address 1"),
        new Location("Store Address 1", "Store Address 1"),
        new Location("Store Address 1", "Store Address 1")
    };
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
      int layoutId = viewType;
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
    Location location = locations[position];
    holder.titleTV.setText(location.getTitle());
    holder.addressTV.setText(location.getAddress());
  }

  @Override
  public int getItemViewType(int position) {
    int viewId = R.layout.list_item_location;
    if (position == 0) {
      viewId = R.layout.list_item_location_active;
    }
    return viewId;
  }

  @Override
  public int getItemCount() {
    return locations.length;
  }

  public class LocationAdapterViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTV;
    private TextView addressTV;
    private TextView distanceTV;

    public LocationAdapterViewHolder(View itemView) {
      super(itemView);
      titleTV = (TextView) itemView.findViewById(R.id.location_title);
      addressTV = (TextView) itemView.findViewById(R.id.location_address);
      distanceTV = (TextView) itemView.findViewById(R.id.distance_textview);
    }
  }
}
