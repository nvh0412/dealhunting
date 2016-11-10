package com.vagabond.dealhunting.services;

import com.vagabond.dealhunting.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HoaNV on 11/10/16.
 */

public class WebService {
  public static DealHuntingService getDealHuntingervice() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(Constant.DEALHUNTING_PATH)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    return retrofit.create(DealHuntingService.class);
  }
}
