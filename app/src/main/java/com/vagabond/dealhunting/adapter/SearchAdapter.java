package com.vagabond.dealhunting.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.ui.widget.SearchSnippet;

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
public class SearchAdapter extends CursorAdapter {

  private static final int INDEX_STORE_ICON = 3;
  private static final int INDEX_PROMOTION_TITLE = 1;
  private SearchItemHolder mHolder;

  public SearchAdapter(Context context, Cursor c, int flags) {
    super(context, c, flags);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
    return LayoutInflater.from(context).inflate(R.layout.list_item_search_result, viewGroup, false);
  }

  @Override
  public void bindView(View view, final Context context, Cursor cursor) {
    mHolder = (SearchItemHolder) view.getTag();
    if (mHolder == null) {
      mHolder = new SearchItemHolder(view);
    }

    Picasso.with(context).load(cursor.getString(INDEX_STORE_ICON)).into(new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        RoundedBitmapDrawable iconStoreDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        iconStoreDrawable.setCircular(true);
        mHolder.storeIcon.setImageDrawable(iconStoreDrawable);
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {

      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {

      }
    });
    mHolder.titlePromotion.setText(cursor.getString(INDEX_PROMOTION_TITLE));
    view.setTag(mHolder);
  }

  private class SearchItemHolder {
    private ImageView storeIcon;
    private TextView titlePromotion;

    public SearchItemHolder(View view) {
      this.storeIcon = (ImageView) view.findViewById(R.id.search_store_item_logo);
      this.titlePromotion = (SearchSnippet) view.findViewById(R.id.search_result);
    }
  }
}
