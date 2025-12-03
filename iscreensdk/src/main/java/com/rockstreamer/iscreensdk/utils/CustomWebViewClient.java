package com.rockstreamer.iscreensdk.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by anshul on 26/04/17.
 */

public  class CustomWebViewClient extends WebViewClient {

  private String TAG = "CustomWebViewClient";

  private Context context;

  public CustomWebViewClient(Context context){
    this.context = context;
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    super.onPageStarted(view, url, favicon);
    Log.d(TAG, "The webView with the following url " + url + " has started loading");

  }

  @Override
  public final void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    Log.d(TAG, "The webView with the following url " + url + " has finished loading");
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onReceivedError(WebView view, int errorCode,
                              String description, String failingUrl) {
    super.onReceivedError(view, errorCode, description, failingUrl);
    Log.d(TAG, "The webView with the following url " + failingUrl +
        " failed with the following errorCode " +
        "" + errorCode);

    Toast.makeText(context, "The webView with the following url " + failingUrl +
            " failed with the following errorCode " +
            "" + errorCode, Toast.LENGTH_SHORT).show();



  }

  @TargetApi(Build.VERSION_CODES.M)
  @Override
  public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
    super.onReceivedError(view, request, error);
    Log.d(TAG, "The webView with the following url " + request.getUrl().toString() +
        " failed with the following errorCode " +
        "" + error.getDescription());
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  public void onReceivedHttpError(
      WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
    super.onReceivedHttpError(view, request, errorResponse);
    Log.d(TAG, "The webView with the following url " + request.getUrl().toString() +
        " failed with the following errorCode " +
        "" + errorResponse.getStatusCode());
  }
}
