package com.vagabond.dealhunting.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vagabond.dealhunting.R;

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

  private static final int INDEX_COLUMN_ID = 0;
  private static final int INDEX_COLUMN_TITLE = 1;
  private static final int INDEX_COLUMN_IMAGE = 4;
  private static final int INDEX_COLUMN_END_DATE = 7;
  private static final int INDEX_COLUMN_STORE_TITLE = 11;
  private static final int INDEX_COLUMN_STORE_IMAGE = 12;
  private static final int SECOND_IN_HOUR = 3600 * 1000;

  private Context context;
  private Cursor mCursor;
  private DealAdapterOnClickHandler mClickHandler;

  public DealAdapter(Context context, DealAdapterOnClickHandler dh, View emptyView) {
    this.context = context;
    this.mClickHandler = dh;
  }

  @Override
  public DealAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    if ( viewGroup instanceof RecyclerView ) {
      int layoutId = R.layout.list_item_deal;
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
      view.setFocusable(true);
      final DealAdapterViewHolder vh = new DealAdapterViewHolder(view);
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          mClickHandler.onClick(mCursor, vh);
        }
      });
      return vh;
    } else {
      throw new RuntimeException("Not bound to RecyclerView");
    }
  }

  @Override
  public void onBindViewHolder(final DealAdapterViewHolder holder, int position) {
    mCursor.moveToPosition(position);

    holder.titleView.setText(mCursor.getString(INDEX_COLUMN_TITLE));
    setTimeDuration(mCursor.getLong(INDEX_COLUMN_END_DATE), holder);

    Picasso.with(context).load(mCursor.getString(INDEX_COLUMN_STORE_IMAGE)).into(new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        holder.storeBrandImageView.setImageBitmap(bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
          @Override
          public void onGenerated(Palette palette) {
            Palette.Swatch brandSwatch = palette.getDominantSwatch();
            if (brandSwatch != null) {
              holder.brandBar.setBackgroundColor(brandSwatch.getRgb());
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
    Picasso.with(context).load(mCursor.getString(INDEX_COLUMN_IMAGE)).into(holder.dynamicHeightImageView);

  }

  private void setTimeDuration(long time, DealAdapterViewHolder holder) {
    String durationStr;
    long duration = time - System.currentTimeMillis();
    boolean overtime = true;
    if (duration > 0) {
      long numdays = duration / (SECOND_IN_HOUR * 24);
      if (numdays > 0) {
        durationStr = duration / SECOND_IN_HOUR * 24 + " " + context.getString(R.string.day_text);
      } else {
        durationStr = duration / SECOND_IN_HOUR + " " + context.getString(R.string.hour_text);
      }
      overtime = false;
    } else {
      durationStr = context.getString(R.string.overtime);
    }
    holder.durationView.setText(durationStr);
    holder.durationView.setTextColor(ContextCompat.getColor(context, overtime ? R.color.colorPrimary : R.color.navview_icon));
  }

  @Override
  public long getItemId(int position) {
    mCursor.moveToPosition(position);
    return mCursor.getLong(INDEX_COLUMN_ID);
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

  class DealAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView titleView;
    private TextView durationView;
    private ImageView storeBrandImageView;
    private DynamicHeightImageView dynamicHeightImageView;
    private View brandBar;

    DealAdapterViewHolder(View itemView) {
      super(itemView);
      titleView = (TextView) itemView.findViewById(R.id.deal_title_textview);
      dynamicHeightImageView = (DynamicHeightImageView) itemView.findViewById(R.id.thumbnail);
      storeBrandImageView = (ImageView) itemView.findViewById(R.id.brand_logo);
      brandBar = itemView.findViewById(R.id.brand_bar);
      durationView = (TextView) itemView.findViewById(R.id.timer_tv);
    }

    @Override
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      mCursor.moveToPosition(adapterPosition);
    }
  }

  interface DealAdapterOnClickHandler {
    void onClick(Cursor cursor, DealAdapterViewHolder vh);
  }
}