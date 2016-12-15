package com.msj.core;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.msj.core.utils.log.LogUtils;
import com.msj.networkcore.https.HttpsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * 基本WebView，需要初始化contentView和webview
 */
public abstract class AbstractBaseWebViewActivity extends AbstractBaseActivity {

    public WebView webview;
    public String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initPrivateKeyAndX509Certificate();
        } catch (Exception e) {
            LogUtils.e("msj", e.getMessage());
            e.printStackTrace();
        }
        //支持4.0以下调用使用
//        setWebViewSSLCert();
        //实例化WebView对象
        initContentView();
        initUri();
        //实例化WebView对象
        initWebView();
        initView();
        setWebView();
    }


    /**
     * 初始化contentView
     * 使用姿势:  @Override
     protected void initContentView() {
     setContentView(R.layout.ac_h5);
     }
     */
    protected abstract void initContentView();

    /**
     * 初始化webview
     * 使用姿势:
     * *@Override
     public void initWebView() {
     webview = (WebView) findViewById(R.id.h5_webview);
     }
     */
    public abstract void initWebView();

    /**
     * 初始化路径
     * 使用姿势:
     *  * @Override
     public void initUri(){
     url = "https://wallet.95598pay.com/gift/rest/activity/index.htm";
     }
     */
    public abstract void initUri();

    /**
     * 有其他view需要初始化,就在此方法里
     */
    public void initView(){

    }

    private void setWebView() {
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setDomStorageEnabled(true);//允许dom存储,部分不设置的网页会打不开


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            //支持处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //为了发生不可预知的错误处理,是自己公司的签名则信任通过,否则取消,即显示空白页
//                if("ComanyName自己签名的公司名".equals(error.getCertificate().getIssuedBy().getOName())){
//                    LogUtils.e("msj", "procceed"+error.getCertificate().getIssuedBy().getCName());
                    handler.proceed();
//                }else{
//                    LogUtils.e("msj", "cancel"+error.getCertificate().getIssuedBy().getCName());
//                    handler.cancel();
//                }

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

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                //为webview添加证书
//                LogUtils.e("msj", "onReceivedClientCertRequest");
//                if(((mX509Certificates != null) && (mX509Certificates.length !=0))){
//                    request.proceed(mPrivateKey, mX509Certificates);
//                }else{
//                    request.cancel();
//                }

            }

//            public void onReceivedClientCertRequest(WebView view,
//                                                    ClientCertRequestHandler handler, String host_and_port) {
//                //注意该方法是调用的隐藏函数接口。这儿是整个验证的技术难点：就是如何调用隐藏类的接口。
//                if(((mX509Certificates != null) && (mX509Certificates.length !=0))){
//                    handler.proceed(mPrivateKey, mX509Certificates);
//                }else{
//                    handler.cancel();
//                }
//            }



        });

        try {
            //设置打开的页面地址
            webview.loadUrl(url);
        } catch (Exception ex) {
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


    private final static String CERTFILE_PASSWORD = "pw123456";


    /**
     * 设置webview的ssl双向认证
     * 注意：该方法只支持android4.0（不包含）以下
     * 该方法调用一次即可
     * <P>Author : Vincent.M </P>
     */
    public boolean setWebViewSSLCert() {
        boolean issuc = false;// true 代表验证和设置成功
        if (Build.VERSION.SDK_INT >= 14) {
            return issuc;
        }

        InputStream[] inputStreams = new InputStream[1];
        try {
            inputStreams[0] = this.getAssets().open("key.cer");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(inputStreams, null, null);
        try {
            Field[] arrayOfField = Class.forName(
                    "android.net.http.HttpsConnection").getDeclaredFields();
            for (Field localField : arrayOfField) {
                if (localField.getName().equals("mSslSocketFactory")) {//采用反射的方式修改mSslSocketFactory变量
                    localField.setAccessible(true);
                    localField.set(null, sslParams.sSLSocketFactory);
                    issuc = true;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return issuc;
    }

    private X509Certificate[] mX509Certificates;
    private PrivateKey mPrivateKey;

    private void initPrivateKeyAndX509Certificate()
            throws Exception {
        // 创建一个证书库，并将证书导入证书库
        try {
            InputStream input = this.getContext().getResources().openRawResource(
//                    R.raw.keystore
                    0
            );
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(input, CERTFILE_PASSWORD.toCharArray());
            Enumeration<?> localEnumeration;
            localEnumeration = keyStore.aliases();
            while (localEnumeration.hasMoreElements()) {
                String str3 = (String) localEnumeration.nextElement();
                mPrivateKey = (PrivateKey) keyStore.getKey(str3,
                        CERTFILE_PASSWORD.toCharArray());
                if (mPrivateKey == null) {
                    continue;
                } else {
                    Certificate[] arrayOfCertificate = keyStore
                            .getCertificateChain(str3);
                    mX509Certificates = new X509Certificate[arrayOfCertificate.length];
                    for (int j = 0; j < mX509Certificates.length; j++) {
                        mX509Certificates[j] = ((X509Certificate) arrayOfCertificate[j]);
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("msj", "initPrivateKey" + mPrivateKey);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
