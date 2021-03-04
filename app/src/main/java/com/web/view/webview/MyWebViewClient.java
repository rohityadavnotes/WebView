package com.web.view.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.web.view.utilities.LogcatUtils;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = MyWebViewClient.class.getSimpleName();

    private Activity activity;
    private WebViewClientCallback webViewClientCallback;
    private WebViewOpenPDFCallback webViewOpenPDFCallback;

    public MyWebViewClient(Activity activity, WebViewClientCallback webViewClientCallback, WebViewOpenPDFCallback webViewOpenPDFCallback) {
        super();
        this.activity              = activity;
        this.webViewClientCallback = webViewClientCallback;
        this.webViewOpenPDFCallback= webViewOpenPDFCallback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("tel:")) {
            try {
                Intent telIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                activity.startActivity(telIntent);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else if (url.startsWith("mailto:")) {
            try {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{url.substring(7)});
                if (emailIntent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivity(emailIntent);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            if (webViewClientCallback != null) {
                return webViewClientCallback.shouldOverrideUrlLoading(webView, url);
            } else {
                return super.shouldOverrideUrlLoading(webView, url);
            }
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String url = webResourceRequest.getUrl().toString();
            if (url.startsWith("tel:")) {
                try {
                    Intent telIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    activity.startActivity(telIntent);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else if (url.startsWith("mailto:")) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{url.substring(7)});
                    if (emailIntent.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(emailIntent);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else if (url.endsWith("pdf")) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                if (webViewOpenPDFCallback != null) webViewOpenPDFCallback.onOpenPDF();
                return true;
            } else {
                if (webViewClientCallback != null) {
                    return webViewClientCallback.shouldOverrideUrlLoading(webView, url);
                } else {
                    return super.shouldOverrideUrlLoading(webView, url);
                }
            }
        }
        return super.shouldOverrideUrlLoading(webView, webResourceRequest);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        super.onPageStarted(webView, url, favicon);
        if (webViewClientCallback != null) webViewClientCallback.onPageStarted(webView, url, favicon);
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        if (webViewClientCallback != null) webViewClientCallback.onPageFinished(webView, url);
    }

    @Override
    public void onLoadResource(WebView webView, String url) {
        super.onLoadResource(webView, url);
    }

    @Override
    public void onPageCommitVisible(WebView webView, String url) {
        super.onPageCommitVisible(webView, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        return super.shouldInterceptRequest(webView, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override
    public void onTooManyRedirects(WebView webView, Message cancelMessage, Message continueMessage) {
        super.onTooManyRedirects(webView, cancelMessage, continueMessage);
    }

    /*
     * api < 23
     */
    @Override
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
        LogcatUtils.informationMessage(TAG, "Error Code api < 23 : " + errorCode);

        try {
            webView.stopLoading();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (webView.canGoBack()) {
            webView.goBack();
        }

        super.onReceivedError(webView, errorCode, description, failingUrl);
    }

    /*
     * api > 23
     */
    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedError(WebView webView, WebResourceRequest request, WebResourceError error) {
        LogcatUtils.informationMessage(TAG, "Error Code api > 23: " + error.getErrorCode());

        try {
            webView.stopLoading();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (webView.canGoBack()) {
            webView.goBack();
        }
        super.onReceivedError(webView, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(webView, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView webView, Message dontResend, Message resend) {
        super.onFormResubmission(webView, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView webView, String url, boolean isReload) {
        super.doUpdateVisitedHistory(webView, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(webView, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest request) {
        super.onReceivedClientCertRequest(webView, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(webView, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent event) {
        return super.shouldOverrideKeyEvent(webView, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView webView, KeyEvent event) {
        super.onUnhandledKeyEvent(webView, event);
    }

    @Override
    public void onScaleChanged(WebView webView, float oldScale, float newScale) {
        super.onScaleChanged(webView, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView webView, String realm, @Nullable String account, String args) {
        super.onReceivedLoginRequest(webView, realm, account, args);
    }

    @Override
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail detail) {
        return super.onRenderProcessGone(webView, detail);
    }

    @Override
    public void onSafeBrowsingHit(WebView webView, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        super.onSafeBrowsingHit(webView, request, threatType, callback);
    }
}
