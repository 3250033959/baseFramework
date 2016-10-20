package com.msj.core.utils.android;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * @author mengxiangcheng
 * @date 2016/10/9 下午6:58
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc 屏幕相关
 */
public class DisplayUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.e("desitiny", scale + "");
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机屏幕宽
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getWindowHeigh(Context context) {
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenHeigh = dm.heightPixels;
        return mScreenHeigh;
    }

    /**
     * 是否是竖屏
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return isPortrait(context.getResources().getConfiguration());
    }

    public static boolean isPortrait(Configuration newConfig) {
        if (newConfig != null && newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }
        return true;
    }

    /**
     * 获取DisplayMetrics
     * @param context
     * @return
     */
    private static DisplayMetrics getDisplayMetrics(final Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
