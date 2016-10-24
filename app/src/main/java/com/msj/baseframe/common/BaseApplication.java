package com.msj.baseframe.common;

import android.content.Context;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.msj.core.AbstractBaseApplication;
import com.msj.core.utils.android.ImageUtils;

/**
 * Created by liang on 2016/10/8.
 */
public class BaseApplication  extends AbstractBaseApplication {

        @SuppressWarnings("unused")
        private final String TAB = "BaseApplication";
        protected static BaseApplication instance;


        @Override
        public void onCreate() {
            super.onCreate();
            instance = this;
            // 内存泄漏检测
            //LeakCanary.install(this);
            Fresco.initialize(this, ImageUtils.getImagePipelineConfig(this));
        }


    public static Context getContext() {
        return instance.getApplicationContext();
    }


    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }


    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };
    public void restartApp(){
//        Intent intent = new Intent(this,StartPageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        instance.startActivity(intent);
//        ActivityList.removeAllActivity();

        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
