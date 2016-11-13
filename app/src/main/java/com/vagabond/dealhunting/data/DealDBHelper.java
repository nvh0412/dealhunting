package com.vagabond.dealhunting.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

public class DealDBHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "dealhunting.db";
  private static final String LOG_TAG = DealDBHelper.class.getSimpleName();

  public DealDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    // Create store table
    final StringBuilder SQL_CREATE_STORE_TABLE = new StringBuilder("CREATE TABLE ");
    SQL_CREATE_STORE_TABLE.append(DealContract.StoreEntry.TABLE_NAME).append(" (");
    SQL_CREATE_STORE_TABLE.append(DealContract.StoreEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
    SQL_CREATE_STORE_TABLE.append(DealContract.StoreEntry.COLUMN_TITLE).append(" TEXT NOT NULL, ");
    SQL_CREATE_STORE_TABLE.append(DealContract.StoreEntry.COLUMN_THUMBNAIL_URL).append(" TEXT NOT NULL, ");
    SQL_CREATE_STORE_TABLE.append("UNIQUE (").append(DealContract.StoreEntry.COLUMN_TITLE).append(") ON CONFLICT REPLACE);");

    // Create category table
    final StringBuilder SQL_CREATE_CATEGORY_TABLE = new StringBuilder("CREATE TABLE ");
    SQL_CREATE_CATEGORY_TABLE.append(DealContract.CategoryEntry.TABLE_NAME).append(" (");
    SQL_CREATE_CATEGORY_TABLE.append(DealContract.CategoryEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
    SQL_CREATE_CATEGORY_TABLE.append(DealContract.CategoryEntry.COLUMN_TITLE).append(" TEXT NOT NULL, ");
    SQL_CREATE_CATEGORY_TABLE.append("UNIQUE (").append(DealContract.CategoryEntry.COLUMN_TITLE).append(") ON CONFLICT REPLACE);");

    // Create promotion table
    final StringBuilder SQL_CREATE_PROMOTION_TABLE = new StringBuilder("CREATE TABLE ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.TABLE_NAME).append(" (");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_TITLE).append(" TEXT NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_TITLE_DETAIL).append(" TEXT NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_SUMMARY).append(" TEXT NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_THUMBNAIL_URL).append(" TEXT NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_IMAGE_URL).append(" TEXT NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_START_DATE).append(" INTEGER NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_END_DATE).append(" INTEGER NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_STORE_KEY).append(" INTEGER NOT NULL, ");
    SQL_CREATE_PROMOTION_TABLE.append(DealContract.PromotionEntry.COLUMN_CATEGORY_KEY).append(" INTEGER NOT NULL, ");

    // Setup promotion column as a foreign key to store table
    SQL_CREATE_PROMOTION_TABLE
        .append("FOREIGN KEY (" + DealContract.PromotionEntry.COLUMN_STORE_KEY + ") REFERENCES ")
        .append(DealContract.StoreEntry.TABLE_NAME + " ( " + DealContract.StoreEntry._ID + " ), ");

    SQL_CREATE_PROMOTION_TABLE
        .append("FOREIGN KEY (" + DealContract.PromotionEntry.COLUMN_CATEGORY_KEY + ") REFERENCES ")
        .append(DealContract.CategoryEntry.TABLE_NAME + " ( " + DealContract.CategoryEntry._ID + " ));");

    sqLiteDatabase.execSQL(SQL_CREATE_STORE_TABLE.toString());
    sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_TABLE.toString());
    sqLiteDatabase.execSQL(SQL_CREATE_PROMOTION_TABLE.toString());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS " + DealContract.StoreEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + DealContract.CategoryEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + DealContract.PromotionEntry.TABLE_NAME);
    onCreate(db);
  }
}
