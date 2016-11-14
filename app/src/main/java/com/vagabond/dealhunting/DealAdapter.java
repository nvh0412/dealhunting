package com.vagabond.dealhunting;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealAdapterViewHolder> {

  private Context context;
  private Cursor mCursor;

  public DealAdapter(Context context) {
    this.context = context;
  }


  @Override
  public DealAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    if ( viewGroup instanceof RecyclerView ) {
      int layoutId = R.layout.list_item_deal;
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
      view.setFocusable(true);
      return new DealAdapterViewHolder(view);
    } else {
      throw new RuntimeException("Not bound to RecyclerView");
    }
  }

  @Override
  public void onBindViewHolder(DealAdapterViewHolder holder, int position) {
    mCursor.moveToPosition(position);
    holder.titleView.setText(mCursor.getString(1));
    Picasso.with(context).load(mCursor.getString(4)).into(holder.dynamicHeightImageView);
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

  public Cursor getCursor() {
    return mCursor;
  }


  public class DealAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView titleView;
    public ImageView dynamicHeightImageView;

    public DealAdapterViewHolder(View itemView) {
      super(itemView);
      titleView = (TextView) itemView.findViewById(R.id.deal_title_textview);
      dynamicHeightImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
    }

    @Override
    public void onClick(View view) {

    }
  }
}