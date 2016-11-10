package com.vagabond.dealhunting.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

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
@RunWith(AndroidJUnit4.class)
public class TestDB {
  public static final String LOG_TAG = TestDB.class.getSimpleName();

  public void deleteDB() {
    InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase(DealDBHelper.DATABASE_NAME);
  }

  @Before
  public void setUp() {
    deleteDB();
  }

  @Test
  public void testCreateDb() {
    final HashSet<String> tableNameHashSet = new HashSet<>();
    tableNameHashSet.add(DealContract.PromotionEntry.TABLE_NAME);
    tableNameHashSet.add(DealContract.CategoryEntry.TABLE_NAME);
    tableNameHashSet.add(DealContract.StoreEnty.TABLE_NAME);

    SQLiteDatabase db = new DealDBHelper(InstrumentationRegistry.getInstrumentation().getTargetContext()).getWritableDatabase();
    assertEquals(true, db.isOpen());

    Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

    assertTrue("Error: This means that the database has not been created correctly",
        c.moveToFirst());

    do {
      tableNameHashSet.remove(c.getString(0));
    } while( c.moveToNext() );

    assertTrue("Error: Your database was created without store entry, category entry and promotion entry tables",
        tableNameHashSet.isEmpty());

    db.close();
  }

  @Test
  public void testStoreColumn() {
    SQLiteDatabase db = new DealDBHelper(InstrumentationRegistry.getInstrumentation().getTargetContext()).getWritableDatabase();
    assertEquals(true, db.isOpen());

    Cursor c = db.rawQuery("PRAGMA table_info(" + DealContract.StoreEnty.TABLE_NAME + ")",
        null);

    assertTrue("Error: This means that we were unable to query the database for table information.",
        c.moveToFirst());

    // Build a HashSet of all of the column names we want to look for
    final HashSet<String> storeColumnHashSet = new HashSet<>();
    storeColumnHashSet.add(DealContract.StoreEnty._ID);
    storeColumnHashSet.add(DealContract.StoreEnty.COLUMN_TITLE);
    storeColumnHashSet.add(DealContract.StoreEnty.COLUMN_THUMBNAIL_URL);

    int columnNameIndex = c.getColumnIndex("name");
    do {
      String columnName = c.getString(columnNameIndex);
      storeColumnHashSet.remove(columnName);
    } while(c.moveToNext());

    assertTrue("Error: The database doesn't contain all of the required store entry columns", storeColumnHashSet.isEmpty());

    db.close();
  }

  @Test
  public void testCategoryColumn() {
    SQLiteDatabase db = new DealDBHelper(InstrumentationRegistry.getInstrumentation().getTargetContext()).getWritableDatabase();
    assertEquals(true, db.isOpen());

    Cursor c = db.rawQuery("PRAGMA table_info(" + DealContract.StoreEnty.TABLE_NAME + ")",
        null);

    assertTrue("Error: This means that we were unable to query the database for table information.",
        c.moveToFirst());

    // Build a HashSet of all of the column names we want to look for
    final HashSet<String> categoryColumnHashSet = new HashSet<>();
    categoryColumnHashSet.add(DealContract.CategoryEntry._ID);
    categoryColumnHashSet.add(DealContract.CategoryEntry.COLUMN_TITLE);

    int columnNameIndex = c.getColumnIndex("name");
    do {
      String columnName = c.getString(columnNameIndex);
      categoryColumnHashSet.remove(columnName);
    } while(c.moveToNext());

    assertTrue("Error: The database doesn't contain all of the required category entry columns", categoryColumnHashSet.isEmpty());

    db.close();
  }

  @Test
  public void testPromotionColumn() {
    SQLiteDatabase db = new DealDBHelper(InstrumentationRegistry.getInstrumentation().getTargetContext()).getWritableDatabase();
    assertEquals(true, db.isOpen());

    Cursor c = db.rawQuery("PRAGMA table_info(" + DealContract.PromotionEntry.TABLE_NAME + ")",
        null);

    assertTrue("Error: This means that we were unable to query the database for table information.",
        c.moveToFirst());

    // Build a HashSet of all of the column names we want to look for
    final HashSet<String> promotionColumnHashSet = new HashSet<>();
    promotionColumnHashSet.add(DealContract.PromotionEntry._ID);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_TITLE);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_TITLE_DETAIL);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_SUMMARY);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_END_DATE);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_START_DATE);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_IMAGE_URL);
    promotionColumnHashSet.add(DealContract.PromotionEntry.COLUMN_THUMBNAIL_URL);

    int columnNameIndex = c.getColumnIndex("name");
    do {
      String columnName = c.getString(columnNameIndex);
      promotionColumnHashSet.remove(columnName);
    } while(c.moveToNext());

    assertTrue("Error: The database doesn't contain all of the required promotion entry columns", promotionColumnHashSet.isEmpty());

    db.close();
  }
}
