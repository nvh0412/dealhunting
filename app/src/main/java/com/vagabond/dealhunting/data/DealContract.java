package com.vagabond.dealhunting.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

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

public class DealContract {
  public static final String CONTENT_AUTHORITY = "com.vagabond.dealhunting.app";
  private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

  public static class StoreEntry implements BaseColumns {
    public static final String TABLE_NAME = "store";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
    public static final String PATH_STORE = "store";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STORE).build();

    public static Uri buildStoreUri(long rowId) {
      return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

  }

  public static class PromotionEntry implements BaseColumns {
    public static final String PATH_PROMOTION = "promotion";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROMOTION).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROMOTION;

    public static final String TABLE_NAME = "promotion";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TITLE_DETAIL = "title_detail";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_STORE_KEY = "store_id";
    public static final String COLUMN_CATEGORY_KEY = "category_id";

    public static Uri buildPromotionUri(long rowId) {
      return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    public static Uri buildPromotionUriByCategory(String categoryId) {
      return CONTENT_URI.buildUpon().appendPath(categoryId).build();
    }

    public static String getCategoryId(Uri uri) {
      return uri.getPathSegments().get(1);
    }
  }

  public static class CategoryEntry implements BaseColumns {
    public static final String PATH_CATEGORY = "category";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;

    public static final String TABLE_NAME = "category";
    public static final String COLUMN_TITLE = "title";

    public static Uri buildCategoryUri(long rowId) {
      return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }
  }
}
