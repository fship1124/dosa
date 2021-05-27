package com.funfun.dosa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    final String url = "https://lemondoc.co.kr/";

    @SuppressLint("JavascriptInterface")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 웹뷰 셋팅
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());
        webView.getSettings().setAppCacheEnabled(true);//자바스크립트 허용
        webView.getSettings().setDatabaseEnabled(true);//자바스크립트 허용
        webView.getSettings().setDomStorageEnabled(true);//자바스크립트 허용
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        webView.loadUrl(url);

        webView.addJavascriptInterface(MainActivity.this, "Interface");
    }


    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 뒤로가기 종료
     */
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }


    private String TAG = "AndroidBridge";

    @JavascriptInterface
    public String share_sns(final String message) {
        Log.d(TAG, message);

        Intent msg = new Intent(Intent.ACTION_SEND);

        msg.addCategory(Intent.CATEGORY_DEFAULT);
        msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.funfun.dosa");
        msg.putExtra(Intent.EXTRA_TITLE, message);
        msg.setType("text/plain");
        startActivity(Intent.createChooser(msg, "공유하기"));

        return "test";
    }
}
