package com.web.view.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import androidx.annotation.Nullable;

public class MyWebChromeClient extends WebChromeClient {

    private static final String TAG = MyWebChromeClient.class.getSimpleName();

    private WebChromeClientCallback webChromeClientCallback;

    public MyWebChromeClient(WebChromeClientCallback webChromeClientCallback) {
        super();
        this.webChromeClientCallback = webChromeClientCallback;
    }

    @Override
    public void onProgressChanged(WebView webView, int newProgress) {
        super.onProgressChanged(webView, newProgress);
        webChromeClientCallback.onProgress(webView, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView webView, String title) {
        super.onReceivedTitle(webView, title);
    }

    @Override
    public void onReceivedIcon(WebView webView, Bitmap icon) {
        super.onReceivedIcon(webView, icon);
    }

    @Override
    public void onReceivedTouchIconUrl(WebView webView, String url, boolean preComposed) {
        super.onReceivedTouchIconUrl(webView, url, preComposed);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
    }

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        super.onShowCustomView(view, requestedOrientation, callback);
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
    }

    @Override
    public boolean onCreateWindow(WebView webView, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return super.onCreateWindow(webView, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView webView) {
        super.onRequestFocus(webView);
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        return super.onJsAlert(webView, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        return super.onJsConfirm(webView, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(webView, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(webView, url, message, result);
    }

    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
        super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
    }

    @Override
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
        super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        super.onPermissionRequest(request);
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
    }

    @Override
    public boolean onJsTimeout() {
        return super.onJsTimeout();
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        super.onConsoleMessage(message, lineNumber, sourceID);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Nullable
    @Override
    public Bitmap getDefaultVideoPoster() {
        return super.getDefaultVideoPoster();
    }

    @Nullable
    @Override
    public View getVideoLoadingProgressView() {
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        super.getVisitedHistory(callback);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}
