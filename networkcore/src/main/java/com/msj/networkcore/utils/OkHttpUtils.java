package com.msj.networkcore.utils;

import android.content.Context;
import android.util.Log;

import com.msj.networkcore.BuildConfig;
import com.msj.networkcore.constant.CacheConstant;
import com.msj.networkcore.constant.TimeConstant;
import com.msj.networkcore.cookie.CookieJarImpl;
import com.msj.networkcore.cookie.store.PersistentCookieStore;
import com.msj.networkcore.https.HttpsUtils;
import com.msj.networkcore.interceptor.CacheInterceptor;
import com.msj.networkcore.interceptor.HeaderInterceptor;
import com.msj.networkcore.interceptor.LoggerInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午4:30
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc 作为参考
 */
public class OkHttpUtils
{
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    public OkHttpUtils(OkHttpClient okHttpClient, Context context, Map<String,String> headers) throws IOException {
        if (okHttpClient == null)
        {
            Cache cache = null ;
            try {
                cache = new Cache(CacheConstant.getDiskCacheDir(context,CacheConstant.CACHE_FOLDER),CacheConstant.CACHE_SIZE);
            } catch (Exception e) {
                Log.e("msj", "Could not create http cache", e);
            }

            InputStream[] inputStreams = new InputStream[1];
            inputStreams[0] = context.getAssets().open("key.cer");
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(inputStreams, null, null);
            //信任所有https写法
//            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//            .sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)                   .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
            //信任所有https写法完毕
            mOkHttpClient = new OkHttpClient().newBuilder()
                    //添加log拦截器用来打印log
                    .addInterceptor(new LoggerInterceptor(LoggerInterceptor.TAG, BuildConfig.DEBUG))
                    .sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)
                    .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                    //stetho,可以在chrome中查看请求
//                    .addNetworkInterceptor(new StethoInterceptor())

                    //添加header
                    .addInterceptor(new HeaderInterceptor(headers))
                    //添加UA
//                    .addInterceptor(new UserAgentInterceptor(HttpHelper.getUserAgent()))

                    //必须是设置Cache目录
                    .cache(cache)

                    //走缓存，两个都要设置
                    .addInterceptor(new CacheInterceptor(context))
                    .addNetworkInterceptor(new CacheInterceptor(context))

                    //失败重连
                    .retryOnConnectionFailure(true)
                    //time out
                    .readTimeout(TimeConstant.TIMEOUT_READ, TimeUnit.SECONDS)
                    .connectTimeout(TimeConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    .writeTimeout(TimeConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                    .build();
        } else
        {
            mOkHttpClient = okHttpClient;
        }

    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient,Context context,Map<String,String> headers)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    try {
                        mInstance = new OkHttpUtils(okHttpClient,context,headers);
                    } catch (IOException e) {
                        Log.e("msj","证书读取异常");
                        e.printStackTrace();
                    }
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance(Context context,Map<String,String> headers)
    {
        return initClient(null,context,headers);
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

}

