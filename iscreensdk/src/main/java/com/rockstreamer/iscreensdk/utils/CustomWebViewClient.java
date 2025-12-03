package com.rockstreamer.iscreensdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rockstreamer.iscreensdk.IScreenActivity;

public class CustomWebViewClient extends WebViewClient {

  private static final String TAG = "CustomWebViewClient";
  private final Context context;

  public CustomWebViewClient(Context context) {
    this.context = context;
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    super.onPageStarted(view, url, favicon);
    Log.d(TAG, "Loading: " + url);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    Log.d(TAG, "Loaded: " + url);

    // Update current URL inside activity
    if (context instanceof IScreenActivity) {
      ((IScreenActivity) context).setCurrentUrl(url);
    }
  }
}