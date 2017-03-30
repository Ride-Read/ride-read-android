package com.rideread.rideread.common.base;

import android.support.annotation.Nullable;

public interface IMVPView {
//  void display(CharSequence stuff);

  void showToast(String message);


  void showLoadProgress(@Nullable String content);

  void hideLoadProgress();
}