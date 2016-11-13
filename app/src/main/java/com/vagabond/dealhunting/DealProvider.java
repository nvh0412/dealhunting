package com.vagabond.dealhunting;/*
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

import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.data.DealDBHelper;

public class DealProvider extends ContentProvider {

  private static final int CATEGORY = 200;
  private DealDBHelper mOpenHelper;
  private static final UriMatcher sUriMatcher = buildUriMatcher();


  private static final SQLiteQueryBuilder sDealQueryBuild;

  static {
    sDealQueryBuild = new SQLiteQueryBuilder();

    sDealQueryBuild.setTables(DealContract.CategoryEntry.TABLE_NAME);
  }

  private static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(DealContract.CONTENT_AUTHORITY, DealContract.CategoryEntry.PATH_CATEGORY, CATEGORY);
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
      case CATEGORY:
        retCursor = sDealQueryBuild.query(mOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknow uri: " + uri);
    }

    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    switch (sUriMatcher.match(uri)) {
      case CATEGORY:
        return DealContract.CategoryEntry.CONTENT_TYPE;
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

    switch (match) {
      case CATEGORY:
        long rowId = db.insert(DealContract.CategoryEntry.TABLE_NAME, null, values);
        if (rowId != -1) {
          retUri = DealContract.CategoryEntry.buildMovieUri(rowId);
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

    switch (match) {
      case CATEGORY:
        db.beginTransaction();
        int returnCount = 0;
        try {
          for (ContentValues value : values) {
            long _id = db.insert(DealContract.CategoryEntry.TABLE_NAME, null, value);
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
}
