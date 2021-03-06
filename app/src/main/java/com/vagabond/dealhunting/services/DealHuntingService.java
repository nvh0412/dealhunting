package com.vagabond.dealhunting.services;

import com.vagabond.dealhunting.model.Category;
import com.vagabond.dealhunting.model.Location;
import com.vagabond.dealhunting.model.Promotion;
import com.vagabond.dealhunting.model.Store;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

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

public interface DealHuntingService {
  @GET("stores")
  Observable<List<Store>> getStoreData();

  @GET("stores/{id}/locations")
  Observable<List<Location>> getLocationData(@Path("id") int storeId);

  @GET("stores/{id}/promotions")
  Observable<List<Promotion>> getPromotionDataByStore(@Path("id") String storeId);

  @GET("categories")
  Observable<List<Category>> getCategoryData();

  @GET("categories/{id}/promotions")
  Observable<List<Promotion>> getPromotionDataByCategory(@Path("id") String categoryId);
}
