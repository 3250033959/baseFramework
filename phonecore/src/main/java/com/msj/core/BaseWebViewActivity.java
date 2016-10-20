package com.msj.core;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 基本WebView，需要初始化contentView和webview
 */
public abstract class BaseWebViewActivity extends BaseActivity {

    public View contentView;
    public WebView webview;
    public String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        //实例化WebView对象
        initContentView();
        initWebView();
        setWebView();
       
        setContentView(contentView);
    }

    /**
     * 初始化contentView
     */
    protected abstract void initContentView();

    /**
     * 初始化webview
     */
    public abstract void initWebView();
    
    private void setWebView(){
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);//允许dom存储,部分不设置的网页会打不开

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            //支持处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            //页面加载开始加载的时候调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoadingDialog();
                super.onPageStarted(view, url, favicon);
            }

            //页面加载结束的时候调用
            @Override
            public void onPageFinished(WebView view, String url) {
                cancelLoadingDialog();
                super.onPageFinished(view, url);
            }



        });

        try {
            //设置打开的页面地址
            webview.loadUrl(url);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 返回键(重写返回方法)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
