package com.msj.core.utils.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.msj.core.utils.constant.SharePreConstant;
import com.msj.core.utils.java.JsonUtils;
import com.msj.core.utils.security.AesUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * @author Vincent.M
 * @date 16/10/09 下午6:50
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc SharePre类，所有key存储到SharePreConstant
 */
public class SharePreferenceStorageService {

    private static SharePreferenceStorageService preferenceStorageService;
    private Context context;

    public SharePreferenceStorageService(Context context) {
        this.context = context;
    }

    SharedPreferences preferences;

    public static SharePreferenceStorageService newInstance(Context context) {

        if (preferenceStorageService == null) {
            preferenceStorageService = new SharePreferenceStorageService(
                    context);
        }

        return preferenceStorageService;
    }

    public SharedPreferences getPreference(Context context) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return preferences;
    }

    public String getInfo(String key) {
        SharedPreferences preferences = getPreference(context);
        return AesUtil.decrypt(preferences.getString(key, ""));
    }

    public void setInfo(String key, String value) {

        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString(key, AesUtil.encrypt(value));
        editor.commit();
    }


    /**
     * @return
     */
    public boolean isFirst() {

        SharedPreferences preferences = getPreference(context);
        return preferences.getBoolean(SharePreConstant.FIRST, true);
    }

    public void setFirst() {

        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putBoolean(SharePreConstant.FIRST, false);
        editor.commit();
    }


    public void setMap(String key, String json) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.commit();
    }

    public HashMap<String, String> getMap(String key) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String json = preferences.getString(key, "");
        if (!TextUtils.isEmpty(json)) {
            return JsonUtils.getMapForJson(json);
        } else {
            return new HashMap<String, String>();
        }
    }


    public void setSplashImage(String url, String file) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(SharePreConstant.SPLASH_IMAGE, url);
        editor.putString(SharePreConstant.SPLASH_IMAGE_FILE, file);
        editor.commit();
    }

    public String getSplashImage() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(SharePreConstant.SPLASH_IMAGE, "");
    }

    public String getSplashImageFile() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(SharePreConstant.SPLASH_IMAGE_FILE, "");
    }

    public void setSplashImageShow(boolean isShow) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(SharePreConstant.SPLASH_IMAGE_SHOW, isShow);
        editor.commit();
    }

    /**
     * 通过字符串获取数组
     *
     * @param key
     * @return
     */
    public String[] getArrayData(String key) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String data_str = preferences.getString(key, "");
        if (!TextUtils.isEmpty(data_str)) {
            String[] dataArray = data_str.split(",---,");
            return dataArray;
        }
        return null;
    }

    /**
     * 存储数组转化成字符串
     *
     * @param key
     * @param data
     */
    public void setArrayData(String key, String[] data) {
        String data_str = "";
        for (int i = 0; i < data.length; i++) {
            if (i != data.length - 1) {
                data_str += data[i] + ",---,";
            } else {
                data_str += data[i];
            }
        }
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(key, data_str);
        editor.commit();
    }

    /**
     * 是否使用保存下来的图片
     */
    public boolean getSplashImageShow() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getBoolean(SharePreConstant.SPLASH_IMAGE_SHOW, false);
    }


    /**
     * @param filename
     * @param data
     * @param <T>
     */
    public <T> void writeToFile(String filename, T... data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(context.getFilesDir().toString() + "/"
                            + filename));

            for (T list : data) {
                out.writeObject(list);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     * @param <T>
     * @return
     */
    public <T> Object getFromFile(String filename) {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(context.getFilesDir().toString() + "/"
                            + filename));
            Object data = inputStream.readObject();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

}
