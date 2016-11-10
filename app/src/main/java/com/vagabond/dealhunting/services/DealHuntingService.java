package com.vagabond.dealhunting.services;

import com.vagabond.dealhunting.model.Category;
import com.vagabond.dealhunting.model.Promotion;
import com.vagabond.dealhunting.model.Store;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by HoaNV on 11/10/16.
 */

public interface DealHuntingService {
  @GET("stores")
  Observable<List<Store>> getStoreData();

  @GET("categories/${id}/promotions")
  Observable<Promotion> getPromotionDataByStore(@Path("id") String storeId);

  @GET("categories")
  Observable<Category> getCategoryData();

  @GET("categories/${id}/promotions")
  Observable<Promotion> getPromotionDataByCategory(@Path("id") String categoryId);
}
