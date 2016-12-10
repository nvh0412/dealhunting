package com.vagabond.dealhunting.sync;/*
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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vagabond.dealhunting.R;

import java.util.Set;

public class Utility {
  public static Set<String> getPreferredNotifiableType(Context context) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    return prefs.getStringSet(context.getString(R.string.pref_notifiable_type_key), null);
  }
}
