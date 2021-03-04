package com.web.view.webview;

import android.webkit.WebView;

public interface WebChromeClientCallback {
    void onProgress(WebView webView, int newProgress);
}