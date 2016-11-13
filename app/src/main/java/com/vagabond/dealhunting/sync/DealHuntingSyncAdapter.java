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
  private static final int SYNC_INTERVAL = 60;
  private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

  public DealHuntingSyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
  }

  @Override
  public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
    Log.d(LOG_TAG, "onPerformSync");
    DealHuntingService dealHuntingService = WebService.getDealHuntingervice();

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

  private void categoryListHandler(List<Category> categoryList) {
    Vector<ContentValues> cvVector = new Vector<>(categoryList.size());

    for (Category category : categoryList) {
      ContentValues cv = new ContentValues();
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

  /**
   * Helper method to schedule the sync adapter periodic execution
   */
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
