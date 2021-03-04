package com.web.view.webview;

import android.graphics.Bitmap;
import android.webkit.WebView;

public interface WebViewClientCallback {
    void onPageStarted(WebView webView, String url, Bitmap favicon);
    void onPageFinished(WebView webView, String url);
    boolean shouldOverrideUrlLoading(WebView webView, String url);
}