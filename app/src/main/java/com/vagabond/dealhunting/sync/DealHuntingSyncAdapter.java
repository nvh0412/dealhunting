package com.vagabond.dealhunting.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.vagabond.dealhunting.R;
import com.vagabond.dealhunting.data.DealContract;
import com.vagabond.dealhunting.model.Category;
import com.vagabond.dealhunting.model.Promotion;
import com.vagabond.dealhunting.model.Store;
import com.vagabond.dealhunting.services.DealHuntingService;
import com.vagabond.dealhunting.services.WebService;

import java.util.List;
import java.util.Vector;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
public class DealHuntingSyncAdapter extends AbstractThreadedSyncAdapter {

  private static final String LOG_TAG = "DealSyncAdapter";
  private static final int SYNC_INTERVAL = 30;
  private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
  DealHuntingService dealHuntingService = WebService.getDealHuntingervice();

  public DealHuntingSyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
  }

  @Override
  public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
    Log.d(LOG_TAG, "onPerformSync");

    dealHuntingService.getStoreData()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Action1<List<Store>>() {
              @Override
              public void call(List<Store> storeList) {
                Log.d(LOG_TAG, "Sync list of all stores");
                storeListHandler(storeList);
              }
            },
            new Action1<Throwable>() {
              @Override
              public void call(Throwable e) {
                Log.e(LOG_TAG, "Error: Can't sync data from API", e);
              }
            }
        );

    dealHuntingService.getCategoryData()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Action1<List<Category>>() {
              @Override
              public void call(List<Category> categoryList) {
                Log.d(LOG_TAG, "Sync list of all categories");
                categoryListHandler(categoryList);
              }
            },
            new Action1<Throwable>() {
              @Override
              public void call(Throwable e) {
                Log.e(LOG_TAG, "Error: Can't sync data from API", e);
              }
            }
        );
  }

  private void storeListHandler(List<Store> storeList) {
    Vector<ContentValues> cvVector = new Vector<>(storeList.size());

    for (Store store : storeList) {
      ContentValues cv = new ContentValues();
      cv.put(DealContract.StoreEntry._ID, store.getId());
      cv.put(DealContract.StoreEntry.COLUMN_TITLE, store.getTitle());
      cv.put(DealContract.StoreEntry.COLUMN_THUMBNAIL_URL, store.getThumbnailUrl());

      cvVector.add(cv);
    }

    int inserted = 0;
    if (storeList.size() > 0) {
      ContentValues[] cvArray = new ContentValues[cvVector.size()];
      cvVector.toArray(cvArray);
      inserted = getContext().getContentResolver().bulkInsert(DealContract.StoreEntry.CONTENT_URI, cvArray);
    }

    Log.d(LOG_TAG, "Sync Category completed. " + inserted + " inserted.");
  }

  private void categoryListHandler(List<Category> categoryList) {
    Vector<ContentValues> cvVector = new Vector<>(categoryList.size());

    for (Category category : categoryList) {
      ContentValues cv = new ContentValues();
      cv.put(DealContract.CategoryEntry._ID, category.getId());
      cv.put(DealContract.CategoryEntry.COLUMN_TITLE, category.getTitle());
      cvVector.add(cv);
    }

    int inserted = 0;
    if (categoryList.size() > 0) {
      ContentValues[] cvArray = new ContentValues[cvVector.size()];
      cvVector.toArray(cvArray);
      inserted = getContext().getContentResolver().bulkInsert(DealContract.CategoryEntry.CONTENT_URI, cvArray);
    }

    Log.d(LOG_TAG, "Sync Category completed. " + inserted + " inserted.");

    for (final Category category: categoryList) {
      dealHuntingService.getPromotionDataByCategory(String.valueOf(category.getId()))
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              new Action1<List<Promotion>>() {
                @Override
                public void call(List<Promotion> promotionList) {
                  Log.d(LOG_TAG, "Sync list of all promotions by category: " + category.getTitle());
                  promotionListHanlder(promotionList);
                }
              },
              new Action1<Throwable>() {
                @Override
                public void call(Throwable e) {
                  Log.e(LOG_TAG, "Error: Can't sync data from API", e);
                }
              }
          );
    }
  }

  private void promotionListHanlder(List<Promotion> promotionList) {
    Vector<ContentValues> cvVector = new Vector<>(promotionList.size());

    for (Promotion promotion : promotionList) {
      ContentValues cv = new ContentValues();
      cv.put(DealContract.PromotionEntry._ID, promotion.getId());
      cv.put(DealContract.PromotionEntry.COLUMN_TITLE, promotion.getTitle());
      cv.put(DealContract.PromotionEntry.COLUMN_TITLE_DETAIL, promotion.getTitleDetail());
      cv.put(DealContract.PromotionEntry.COLUMN_IMAGE_URL, promotion.getImageUrl());
      cv.put(DealContract.PromotionEntry.COLUMN_THUMBNAIL_URL, promotion.getThumbnailUrl());
      cv.put(DealContract.PromotionEntry.COLUMN_SUMMARY, promotion.getSummary());
      cv.put(DealContract.PromotionEntry.COLUMN_CATEGORY_KEY, promotion.getCategoryId());
      cv.put(DealContract.PromotionEntry.COLUMN_STORE_KEY, promotion.getStoreId());

      if (promotion.getStartDate() != null) {
        cv.put(DealContract.PromotionEntry.COLUMN_START_DATE, promotion.getStartDate().getTime());
      }

      if (promotion.getEndDate() != null) {
        cv.put(DealContract.PromotionEntry.COLUMN_END_DATE, promotion.getEndDate().getTime());
      }

      cvVector.add(cv);
    }

    int inserted = 0;
    if (promotionList.size() > 0) {
      ContentValues[] cvArray = new ContentValues[cvVector.size()];
      cvVector.toArray(cvArray);
      inserted = getContext().getContentResolver().bulkInsert(DealContract.PromotionEntry.CONTENT_URI, cvArray);
    }

    Log.d(LOG_TAG, "Sync Promotions completed. " + inserted + " inserted");
  }


  private static Account getSyncAccount(Context context) {
    Log.d(LOG_TAG, "getSyncAccount");
    AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
    Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

    if ( null == accountManager.getPassword(newAccount) ) {
      if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
        return null;
      }
      onAccountCreated(newAccount, context);
    }
    return newAccount;
  }

  private static void onAccountCreated(Account newAccount, Context context) {
    DealHuntingSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
    
    ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

    syncImmediately(context);
  }

  public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
    Account account = getSyncAccount(context);
    String authority = context.getString(R.string.content_authority);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // we can enable inexact timers in our periodic sync
      SyncRequest request = new SyncRequest.Builder().
          syncPeriodic(syncInterval, flexTime).
          setSyncAdapter(account, authority).
          setExtras(new Bundle()).build();
      ContentResolver.requestSync(request);
    } else {
      ContentResolver.addPeriodicSync(account,
          authority, new Bundle(), syncInterval);
    }
  }

  private static void syncImmediately(Context context) {
    Bundle bundle = new Bundle();
    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

    ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
  }

  public static void initializeSyncAdapter(Context context) {
    getSyncAccount(context);
  }
}
