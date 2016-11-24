package com.vagabond.dealhunting.ui.widget;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

import android.content.Context;
import android.util.AttributeSet;

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
public class SquareMap extends MapView {
  public SquareMap(Context context) {
    super(context);
  }

  public SquareMap(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  public SquareMap(Context context, AttributeSet attributeSet, int i) {
    super(context, attributeSet, i);
  }

  public SquareMap(Context context, GoogleMapOptions googleMapOptions) {
    super(context, googleMapOptions);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int squareHeight = MeasureSpec.getSize(widthMeasureSpec) * 2/3;
    int squareHeightSpec = MeasureSpec.makeMeasureSpec(squareHeight, MeasureSpec.EXACTLY);
    super.onMeasure(widthMeasureSpec, squareHeightSpec);
  }
}
