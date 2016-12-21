package com.vagabond.dealhunting.ui.widget;/*
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

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.ui.MainActivity;

import java.io.IOException;

public class TodayWidgetIntentService extends IntentService {
  private static final String[] DEAL_COLUMNS = {
      DealContract.PromotionEntry.TABLE_NAME + "." + DealContract.PromotionEntry.COLUMN_TITLE,
      DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry.COLUMN_THUMBNAIL_URL
  };

  public TodayWidgetIntentService() {
    super("TodayWidget");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
        TodayWidgetProvider.class));

    Uri dealUri = DealContract.PromotionEntry.CONTENT_URI;
    Cursor data = getContentResolver().query(dealUri, DEAL_COLUMNS, null,
        null, DealContract.PromotionEntry.COLUMN_START_DATE + " DESC LIMIT 1");
    if (data == null) {
      return;
    }
    if (!data.moveToFirst()) {
      data.close();
      return;
    }

    String dealTitle = data.getString(0);
    String storeImageURL = data.getString(1);

    Bitmap dealCompanyThumbnail = null;
    try {
      dealCompanyThumbnail = Picasso.with(this).load(storeImageURL).get();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int appWidgetId : appWidgetIds) {
      RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_today_small);

      views.setImageViewBitmap(R.id.deal_widget_thumbnail, dealCompanyThumbnail);
      views.setTextViewText(R.id.deal_widget_detail, dealTitle);

      // Create an Intent to launch MainActivity
      Intent launchIntent = new Intent(this, MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
      views.setOnClickPendingIntent(R.id.widget, pendingIntent);

      // Tell the AppWidgetManager to perform an update on the current app widget
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}
