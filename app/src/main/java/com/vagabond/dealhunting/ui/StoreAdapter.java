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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vagabond.dealhunting.R;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreAdpaterViewHolder> {
  private static final int INDEX_COLUMN_ID = 0;
  private static final int INDEX_COLUMN_TITLE = 1;
  private static final int INDEX_COLUMN_THUMBNAIL = 2;
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
  public void onBindViewHolder(final StoreAdpaterViewHolder holder, int position) {
    if (!mCursor.moveToPosition(position)) {
      return;
    }
    holder.storeTitle.setText(mCursor.getString(INDEX_COLUMN_TITLE));
    holder.storeTitleDetail.setText(String.format("%s %s", context.getString(R.string.promotion_text), mCursor.getString(INDEX_COLUMN_TITLE)));
    Picasso.with(context).load(mCursor.getString(INDEX_COLUMN_THUMBNAIL)).into(new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        RoundedBitmapDrawable iconStoreDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        iconStoreDrawable.setCircular(true);
        holder.storeIV.setImageDrawable(iconStoreDrawable);
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
    private TextView storeTitle;
    private ImageView storeIV;
    private TextView storeTitleDetail;

    public StoreAdpaterViewHolder(View itemView) {
      super(itemView);

      storeTitle = (TextView) itemView.findViewById(R.id.store_item_title);
      storeIV = (ImageView) itemView.findViewById(R.id.store_item_logo);
      storeTitleDetail = (TextView) itemView.findViewById(R.id.store_item_title_detail);
    }
  }
}
