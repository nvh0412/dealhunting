package com.vagabond.dealhunting.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
public class DealHuntingSyncService extends Service {
  private static final Object syncAdapterLock = new Object();
  private static final String LOG_TAG = DealHuntingSyncService.class.getSimpleName();
  private static DealHuntingSyncAdapter dealHutingSyncAdapter;

  @Override
  public void onCreate() {
    Log.d(LOG_TAG, "onCreate");
    synchronized (syncAdapterLock) {
      if (dealHutingSyncAdapter == null) {
        dealHutingSyncAdapter = new DealHuntingSyncAdapter(getApplicationContext(), true);
      }
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return dealHutingSyncAdapter.getSyncAdapterBinder();
  }
}
