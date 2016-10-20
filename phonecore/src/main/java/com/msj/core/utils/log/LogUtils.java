package com.msj.core.utils.log;

import android.util.Log;

import com.msj.core.BuildConfig;
import com.msj.core.utils.constant.LogConstant;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;


/**
 * @author mengxiangcheng
 * @date 2016/10/11 上午11:03
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc 日志打印
 */
public class LogUtils {
    private static final boolean LOG_MODE = BuildConfig.LOG_DEBUG;


    public static void e(String tag,String message){
        if(LOG_MODE){
            Log.e(tag, message);
        }
    }

    public static void w(String tag,String message){
        if(LOG_MODE){
            Log.w(tag, message);
        }
    }

    public static void d(String tag,String message){
        if(LOG_MODE){
            Log.d(tag, message);
        }
    }

    public static void i(String tag,String message){
        if(LOG_MODE){
            Log.i(tag,message);
        }
    }


    public static void outputLog(String directorypath, String msg) {
        File directory = new File(directorypath);
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        Date date = new Date();
        String fileName = String.valueOf(date.getYear()) + "年"
                + String.valueOf(date.getMonth()) + "月"
                + String.valueOf(date.getDate()) + "日"
                + String.valueOf(date.getHours()) + "点"
                + String.valueOf(date.getMinutes()) + "分"
                + String.valueOf(date.getSeconds()) + "秒";
        File f = new File(directorypath + "/" + fileName + "log.txt");
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(f, true));
            writer.append(msg + "\r\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            Log.e(LogConstant.DATA_OUTPUT,"日志输出错误");
        }
    }


}
