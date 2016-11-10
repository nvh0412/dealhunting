package com.vagabond.dealhunting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.vagabond.dealhunting.model.Store;
import com.vagabond.dealhunting.services.DealHuntingService;
import com.vagabond.dealhunting.services.WebService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView mTextView = (TextView) findViewById(R.id.test_textview);

    DealHuntingService dealHuntingService = WebService.getDealHuntingervice();
    dealHuntingService.getStoreData()
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<List<Store>>() {
          @Override
          public void call(List<Store> storeList) {
            Log.d(LOG_TAG, "Sync popular movie");
            mTextView.setText(storeList.toString());
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
