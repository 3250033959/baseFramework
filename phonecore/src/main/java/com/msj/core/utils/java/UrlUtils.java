package com.msj.core.utils.java;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengxiangcheng
 * @date 2016/10/10 上午11:00
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc Url相关的工具类
 */
public class UrlUtils {

    /**
     * 判断是否http或者https开头
     *
     * @param str
     * @return
     */
    public static boolean isStartWithHttp(String str) {
        return str.startsWith("http://") || str.startsWith("https://");
    }

    /**
     * 给路径添加http
     *
     * @param str
     * @return
     */
    public static String addHttp(String str) {
        return String.format("http://%1$s", str);
    }



    /**
     * url Encode
     *
     * @param content
     * @return
     */
    public static String getUrlEncodeString(String content) {
        String content_ = content;
        try {
            content_ = URLEncoder.encode(content, "utf-8").replace("+", "%20");
        } catch (Exception e) {
            content_ = content;
        }
        return content_;
    }

    /**
     * url Decode
     *
     * @param content
     * @return
     */
    public static String getUrlDecodeString(String content) {
        String content_ = content;
        try {
            content_ = URLDecoder.decode(content, "utf-8");
        } catch (Exception e) {
            content_ = content;
        }
        return content_;
    }

    public static String[] getIpAndPort(String ip) {
        if (ip == null || ip.trim().length() < 4) {
            return null;
        }
        String[] strs = null;

        int index = ip.indexOf(":");
        if (index != -1) {
            strs = ip.split(":");
        } else {
            strs = new String[]{ip, "80"};
        }
        return strs;
    }

    /**
     * 获取url路径
     *
     * @param url
     * @param params
     * @return
     */
    public static String urlWithParameters(String url,
                                           Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (builder.length() == 0) {
                    if (url.contains("?")) {
                        builder.append("&");
                    } else {
                        builder.append("?");
                    }
                } else {
                    builder.append("&");
                }
                builder.append(entry.getKey()).append("=")
                        .append(entry.getValue());
            }
        }

        return url + builder.toString();
    }


    /**
     * 转化成一个参数，有多个值情况
     *
     * @param params
     * @param valuePair
     */
    public static void mergeParam(HashMap<String, Object> params,
                                  List<NameValuePair> valuePair) {
        if (params != null) {
            for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                valuePair.add(new BasicNameValuePair(key, value));
            }
        }
    }

}
