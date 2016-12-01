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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.data.DealDBHelper;

public class DealProvider extends ContentProvider {

  private static final String LOG_TAG = DealProvider.class.getSimpleName();

  private static final int CATEGORY = 200;
  private static final int PROMOTION = 201;
  private static final int PROMOTION_CATEGORY = 202;
  private static final int STORE = 203;
  private static final int STORE_WITH_ID = 204;
  private static final int PROMOTION_WITH_ID = 205;
  private static final int STORE_PROMOTION = 206;
  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private static final SQLiteQueryBuilder sCategoryQueryBuild;
  private static final SQLiteQueryBuilder sPromotionQueryBuild;
  private static final SQLiteQueryBuilder sStoreQueryBuild;
  private static final String promotionCategorySelection = DealContract.PromotionEntry.TABLE_NAME +
      "." + DealContract.PromotionEntry.COLUMN_CATEGORY_KEY + " = ? ";
  private static final String promotionStoreSelection = DealContract.PromotionEntry.TABLE_NAME +
      "." + DealContract.PromotionEntry.COLUMN_STORE_KEY + " = ? ";
  private static final String sPromotionIDSelection = DealContract.PromotionEntry.TABLE_NAME +
      "." + DealContract.PromotionEntry._ID + " = ? ";
  private static final String sStoreIDSelection = DealContract.PromotionEntry._ID + " = ? ";

  static {
    sCategoryQueryBuild = new SQLiteQueryBuilder();
    sPromotionQueryBuild = new SQLiteQueryBuilder();
    sStoreQueryBuild = new SQLiteQueryBuilder();

    sCategoryQueryBuild.setTables(DealContract.CategoryEntry.TABLE_NAME);
    sPromotionQueryBuild.setTables(DealContract.PromotionEntry.TABLE_NAME + " INNER JOIN " +
        DealContract.StoreEntry.TABLE_NAME + " ON " + DealContract.PromotionEntry.COLUMN_STORE_KEY +
        " = " + DealContract.StoreEntry.TABLE_NAME + "." + DealContract.StoreEntry._ID);
    sStoreQueryBuild.setTables(DealContract.StoreEntry.TABLE_NAME);
  }

  private DealDBHelper mOpenHelper;

  private static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.StoreEntry.PATH_STORE, STORE);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.StoreEntry.PATH_STORE + "/#", STORE_WITH_ID);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.StoreEntry.PATH_STORE + "/*", STORE_PROMOTION);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.CategoryEntry.PATH_CATEGORY, CATEGORY);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.PromotionEntry.PATH_PROMOTION, PROMOTION);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.PromotionEntry.PATH_PROMOTION + "/#", PROMOTION_WITH_ID);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.PromotionEntry.PATH_PROMOTION + "/*", PROMOTION_CATEGORY);
    return uriMatcher;
  }

  @Override
  public boolean onCreate() {
    mOpenHelper = new DealDBHelper(getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      case STORE:
        retCursor = sStoreQueryBuild.query(mOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        break;
      case STORE_WITH_ID:
        retCursor = getStoreById(uri, projection);
        break;
      case STORE_PROMOTION:
        retCursor = getPromotionByStore(uri, projection);
        break;
      case CATEGORY:
        retCursor = sCategoryQueryBuild.query(mOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        break;
      case PROMOTION:
        if (!selectionArgs[0].equals("")) {
          selectionArgs[0] = "%" + selectionArgs[0] + "%";
          retCursor = sPromotionQueryBuild.query(mOpenHelper.getReadableDatabase(), projection, "promotion.title LIKE ?", selectionArgs, null, null, sortOrder);
        } else if (selectionArgs[0].equals("")) {
          retCursor = sPromotionQueryBuild.query(mOpenHelper.getReadableDatabase(), projection, selection, null, null, null, sortOrder, "10");
        } else {
          retCursor = sPromotionQueryBuild.query(mOpenHelper.getReadableDatabase(), projection, selection, null, null, null, sortOrder);
        }
        break;
      case PROMOTION_WITH_ID:
        retCursor = getPromotionByPromotionID(uri, projection, sortOrder);
        break;
      case PROMOTION_CATEGORY:
        retCursor = getPromotionByCategory(uri, projection, sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknow uri: " + uri);
    }

    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }

  private Cursor getPromotionByStore(Uri uri, String[] projection) {
    String selection = promotionStoreSelection;
    String[] selectionArgs = new String[]{DealContract.PromotionEntry.getStoreId(uri)};
    return sPromotionQueryBuild.query(mOpenHelper.getReadableDatabase(),
        projection,
        selection,
        selectionArgs,
        null,
        null,
        null
    );
  }

  private Cursor getPromotionByCategory(Uri uri, String[] projection, String sortOrder) {
    String selection = promotionCategorySelection;
    String[] selectionArgs = new String[]{DealContract.PromotionEntry.getCategoryId(uri) };
    Log.d(LOG_TAG, "getPromotionByCategory:" + selectionArgs[0]);
    return sPromotionQueryBuild.query(mOpenHelper.getReadableDatabase(),
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    );
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    switch (sUriMatcher.match(uri)) {
      case CATEGORY:
        return DealContract.CategoryEntry.CONTENT_TYPE;
      case PROMOTION_CATEGORY:
        return DealContract.PromotionEntry.CONTENT_TYPE;
      case PROMOTION_WITH_ID:
        return DealContract.PromotionEntry.CONTENT_ITEM_TYPE;
      case PROMOTION:
        return DealContract.PromotionEntry.CONTENT_TYPE;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    Uri retUri;
    long rowId;

    switch (match) {
      case CATEGORY:
        rowId = db.insert(DealContract.CategoryEntry.TABLE_NAME, null, values);
        if (rowId != -1) {
          retUri = DealContract.CategoryEntry.buildCategoryUri(rowId);
        } else {
          throw new SQLException("Fail to insert row into " + uri);
        }
        break;
      case PROMOTION:
        rowId = db.insert(DealContract.PromotionEntry.TABLE_NAME, null, values);
        if (rowId != -1) {
          retUri = DealContract.PromotionEntry.buildPromotionUri(rowId);
        } else {
          throw new SQLException("Fail to insert row into " + uri);
        }
        break;
      case STORE:
        rowId = db.insert(DealContract.StoreEntry.TABLE_NAME, null, values);
        if (rowId != -1) {
          retUri = DealContract.StoreEntry.buildStoreUri(rowId);
        } else {
          throw new SQLException("Fail to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return retUri;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);

    int rowDeleteds;
    switch (match) {
      case CATEGORY:
        rowDeleteds = db.delete(DealContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
        break;
      case PROMOTION:
        rowDeleteds = db.delete(DealContract.PromotionEntry.TABLE_NAME, selection, selectionArgs);
        break;
      case STORE:
        rowDeleteds = db.delete(DealContract.StoreEntry.TABLE_NAME, selection, selectionArgs);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowDeleteds;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);

    int rowUpdated;
    switch (match) {
      case CATEGORY:
        rowUpdated = db.update(DealContract.CategoryEntry.TABLE_NAME, values, selection, selectionArgs);
        break;
      case PROMOTION:
        rowUpdated = db.update(DealContract.PromotionEntry.TABLE_NAME, values, selection, selectionArgs);
        break;
      case STORE:
        rowUpdated = db.update(DealContract.StoreEntry.TABLE_NAME, values, selection, selectionArgs);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowUpdated;
  }

  @Override
  public int bulkInsert(Uri uri, ContentValues[] values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    int returnCount = 0;

    switch (match) {
      case CATEGORY:
        db.beginTransaction();
        try {
          for (ContentValues value : values) {
            long _id = db.insertWithOnConflict(DealContract.CategoryEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
            if (_id != -1) {
              returnCount++;
            }
          }
          db.setTransactionSuccessful();
        } finally {
          db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
      case PROMOTION:
        db.beginTransaction();
        try {
          for (ContentValues value : values) {
            long _id = db.insertWithOnConflict(DealContract.PromotionEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
            if (_id != -1) {
              returnCount++;
            }
          }
          db.setTransactionSuccessful();
        } finally {
          db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
      case STORE:
        db.beginTransaction();
        try {
          for (ContentValues value : values) {
            long _id = db.insertWithOnConflict(DealContract.StoreEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
            if (_id != -1) {
              returnCount++;
            }
          }
          db.setTransactionSuccessful();
        } finally {
          db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
      default:
        return super.bulkInsert(uri, values);
    }
  }

  public Cursor getPromotionByPromotionID(Uri uri, String[] projection, String sortOrder) {
    return sPromotionQueryBuild.query(
        mOpenHelper.getReadableDatabase(),
        projection, sPromotionIDSelection,
        new String[]{ DealContract.PromotionEntry.getPromotionIdFromUri(uri) },
        null,
        null,
        sortOrder);
  }

  private Cursor getStoreById(Uri uri, String[] projection) {
    return sStoreQueryBuild.query(
        mOpenHelper.getReadableDatabase(),
        projection, sStoreIDSelection,
        new String[]{DealContract.StoreEntry.getStoreIdFromUri(uri)},
        null,
        null,
        null);
  }
}
