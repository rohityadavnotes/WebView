package com.web.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.web.view.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import com.web.view.webview.MyWebChromeClient;
import com.web.view.webview.MyWebViewClient;
import com.web.view.webview.WebChromeClientCallback;
import com.web.view.webview.WebViewClientCallback;
import com.web.view.webview.WebViewOpenPDFCallback;
import java.net.URL;

public class WebViewActivity extends AppCompatActivity implements WebViewClientCallback, WebViewOpenPDFCallback, WebChromeClientCallback {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;
    private RoundedHorizontalProgressBar progressBar;
    private TextView titleTextView;
    private ImageButton closeButton, refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initializeView();
        initializeObject();
        initializeToolBar();
        onTextChangedListener();
        initializeEvent();
        //initializingWebView("https://backend24.000webhostapp.com/Json/profile.jpg");
       //initializingWebView("https://najdisheep.com/");
        //initializingWebView("https://sample-videos.com/");
        //initializingWebView("file:///android_asset/index.html");
        initializingWebView("https://google.com/");
    }

    protected void initializeView() {
        refreshButton   = findViewById(R.id.refreshImageButton);
        titleTextView   = findViewById(R.id.titleTextView);
        closeButton     = findViewById(R.id.closeImageButton);
        webView         = findViewById(R.id.webView);
        progressBar     = findViewById(R.id.progressBar);
    }

    protected void initializeObject() {
    }

    protected void initializeToolBar() {
    }

    protected void onTextChangedListener() {
    }

    protected void initializeEvent() {
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
    }

    private void initializingWebView(String url) {
        MyWebViewClient myWebViewClient     = new MyWebViewClient(this,this, this);
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient(this);

        webView.setWebViewClient(myWebViewClient);
        webView.setWebChromeClient(myWebChromeClient);

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.setDescription("Downloading file...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                assert downloadManager != null;
                downloadManager.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(getApplicationContext(), "Downloading Complete", Toast.LENGTH_SHORT).show();
                }
            };
        });

        webView.canGoBack();
        webView.canGoForward();
        webView.setLongClickable(false);

        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        WebSettings webSettings = webView.getSettings();

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        /**
         * LOAD_CACHE_ONLY: Do not use the network, only read the local cache data
         * LOAD_DEFAULT: (default) Decide whether to fetch data from the network according to cache-control.
         * LOAD_NO_CACHE: Do not use cache, only get data from the network.
         * LOAD_CACHE_ELSE_NETWORK, as long as it is locally available, regardless of whether it is expired or no-cache, the data in the cache is used.
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        /* webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36"); */

        webView.loadUrl(url);
        titleTextView.setText(getTitleFromUrl(url));
    }

    public void ConfirmExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                WebViewActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if(webView != null && webView.canGoBack()){
            webView.goBack();
        }
        else
        {
            ConfirmExit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView=null;
    }

    /*
     ***********************************************************************************************
     ***************************************** Implemented methods *********************************
     ***********************************************************************************************
     */
    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        titleTextView.setText(getTitleFromUrl(url));
        webView.loadUrl(url);
        return true;
    }

    @Override
    public void onProgress(WebView webView, int newProgress) {
        reportProgress(newProgress);
    }

    @Override
    public void onOpenPDF() {
    }

    /*
     ***********************************************************************************************
     ******************************************* Helper methods ************************************
     ***********************************************************************************************
     */
    private void reportProgress(final int newProgress) {
        if (newProgress > 0 && newProgress < 100) {
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setProgress(newProgress);
        } else {
            if (progressBar.getVisibility() != View.INVISIBLE) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void refresh () {
        if (webView != null) webView.reload();
    }

    private String getTitleFromUrl(String url) {
        String title = url;
        try {
            URL urlObj = new URL(url);
            String host = urlObj.getHost();
            if (host != null && !host.isEmpty()) {
                return urlObj.getProtocol() + "://" + host;
            }
            if (url.startsWith("file:")) {
                String fileName = urlObj.getFile();
                if (fileName != null && !fileName.isEmpty()) {
                    return fileName;
                }
            }
        } catch (Exception e) {
        }
        return title;
    }
}