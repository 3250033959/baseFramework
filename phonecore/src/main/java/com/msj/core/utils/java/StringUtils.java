/*
 * Copyright (c) 2014.
 * Jackrex
 */

package com.msj.core.utils.java;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vincent.M
 * @date 16/10/09 下午6:50
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc 字符串工具类
 */
public class StringUtils {


    public static final String[] UNCIVIL_TEXTS = "母,妹,狗血,垃圾,我去,SB,高能,山寨,中二,吹箫,艹,撸,装X,装13,坑爹,变态,装逼,中枪,v5,V5,MLGB,mlgb,肤浅,脑残,我X,了个,滚,无聊,没趣,没劲,白痴,小白,老公,老婆,尼玛,2B,2b,天朝,吊丝,贱,宾馆,广告,政府,人权,共产党,民主,台独,喷精,包夜,动乱,学生妹,护法,强奸,考试答案,qq,QQ".split(",");

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "null".equals(input) || TextUtils.isEmpty(input))
            return true;
        return false;
    }


    /**
     * 字符串敏感截取，超出部分用...结束，处理后的长度=num区分字母和汉字
     *
     * @param str
     * @param num
     * @return String
     */
    public static String subStrSensitive(String str, int num) {
        if (str == null || num <= 3) {
            return str;
        }
        StringBuffer sbStr = new StringBuffer();
        String returnStr = "";
        int length = 0;
        char temp;
        for (int i = 0; i < str.length(); i++) {
            temp = str.charAt(i);
            if (isChinese(temp)) {
                length = length + 2;
            } else {
                length++;
            }
            if (length == num && i == (str.length() - 1)) {
                sbStr.append(temp);
                returnStr = sbStr.toString();
                break;
            } else if (length >= num) {
                sbStr.append("...");
                returnStr = sbStr.toString();
                try {
                    int lastL = returnStr.getBytes("GBK").length;

                    while (lastL > num) {
                        returnStr = returnStr.substring(0,
                                returnStr.length() - 4) + "...";

                        lastL = returnStr.getBytes("GBK").length;
                    }
                    break;
                } catch (Exception e) {
                    break;
                }
            }
            sbStr.append(temp);
            if (i == (str.length() - 1)) {

                returnStr = sbStr.toString();
            }
        }

        return returnStr;
    }

    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                return true;
        }
        return false;
    }

    /**
     * 是中文或者英文,.和空格排除
     *
     * @param s
     * @return
     */
    public static boolean isName(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (!isChinese(s.charAt(i)) && !isEnglish(s.charAt(i)) && s.charAt(i) != ' ' && !".".equals(s.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isEnglish(int word) {
        if (!(word >= 'A' && word <= 'Z')
                && !(word >= 'a' && word <= 'z')) {
            return false;
        }
        return true;
    }


    public static boolean containsNumber(String s) {
        if (null == s || "".equals(s.trim())) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) { //循环遍历字符串
            if (Character.isDigit(s.charAt(i))) { //用char包装类中的判断数字的方法判断每一个字符 isNumber=true;
                return true;
            }
        }
        return false;

    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 字符串为空则转换成“”
     *
     * @param value
     * @return
     */
    public static String defaultString(String value) {
        return defaultString(value, "");
    }

    /**
     * 字符串为空则转换成默认字符串
     *
     * @param value
     * @param defaultValue 默认字符串
     * @return
     */
    public static String defaultString(String value, String defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    public static String encodeHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        encodeHexString(bytes, buffer);
        return buffer.toString();
    }

    public static void encodeHexString(byte[] bytes, StringBuilder buffer) {
        for (byte b : bytes) {
            int hi = (b >>> 4) & 0x0f;
            int lo = (b >>> 0) & 0x0f;
            buffer.append(Character.forDigit(hi, 16));
            buffer.append(Character.forDigit(lo, 16));
        }
    }

    /**
     * 字符串数组转换成字符串加入相应的间隔符
     *
     * @param array
     * @param delimiter
     * @return
     */
    public static String join(String[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (i < (array.length - 1)) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }


    public static String decodeMessage(String str, String jsonNodeName) {
        String result = str;
        try {
            JSONObject json = new JSONObject(str);
            result = (String) json.get(jsonNodeName);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static String getMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.substring(8, 24).toString();
    }

    public static String convertStreamToString(InputStream is)
            throws IOException {
        if (is != null) {

            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    @SuppressLint("NewApi")
    public String insertWithOnConflict(String table, ContentValues initialValues) {
        StringBuilder col = new StringBuilder();
        col.append("INSERT");
        col.append(" INTO ");
        col.append(table);
        col.append('(');

        StringBuilder val = new StringBuilder();
        val.append(" VALUES (");

        try {
            int size = (initialValues != null && initialValues.size() > 0) ? initialValues
                    .size() : 0;
            if (size > 0) {
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    col.append((i > 0) ? "," : "");
                    col.append("[" + colName + "]");

                    Object o = initialValues.get(colName);
                    val.append((i > 0) ? "," : "");
                    val.append("\"");
                    val.append(o);
                    val.append("\"");
                    i++;
                }
                col.append(')');
                val.append(')');

                col.append(val);
                col.append(";");
                return col.toString();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean isNumber(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 转换成ascii码
     *
     * @param value
     * @return
     */
    public static String getASCII(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * 按自己规则字符串转数字
     *
     * @param value
     * @return
     */
    public static String getStringToNumber(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            {
                sbu.append(msg.get("" + chars[i]));
            }
        }
        return sbu.toString();
    }

    private static Map<String, String> msg = new HashMap<String, String>();

    static {
        msg.put("0", "0");
        msg.put("1", "1");
        msg.put("2", "2");
        msg.put("3", "3");
        msg.put("4", "4");
        msg.put("5", "5");
        msg.put("6", "6");
        msg.put("7", "7");
        msg.put("8", "8");
        msg.put("9", "9");
        msg.put("a", "10");
        msg.put("b", "11");
        msg.put("c", "12");
        msg.put("d", "13");
        msg.put("e", "14");
        msg.put("f", "15");
        msg.put("g", "16");
        msg.put("h", "17");
        msg.put("i", "18");
        msg.put("j", "19");
        msg.put("k", "20");
        msg.put("l", "21");
        msg.put("m", "22");
        msg.put("n", "23");
        msg.put("o", "24");
        msg.put("p", "25");
        msg.put("q", "26");
        msg.put("r", "27");
        msg.put("s", "28");
        msg.put("t", "29");
        msg.put("u", "30");
        msg.put("v", "31");
        msg.put("w", "32");
        msg.put("x", "33");
        msg.put("y", "34");
        msg.put("z", "35");
        msg.put("A", "10");
        msg.put("B", "11");
        msg.put("C", "12");
        msg.put("D", "13");
        msg.put("E", "14");
        msg.put("F", "15");
        msg.put("G", "16");
        msg.put("H", "17");
        msg.put("I", "18");
        msg.put("J", "19");
        msg.put("K", "20");
        msg.put("L", "21");
        msg.put("M", "22");
        msg.put("N", "23");
        msg.put("O", "24");
        msg.put("P", "25");
        msg.put("Q", "26");
        msg.put("R", "27");
        msg.put("S", "28");
        msg.put("T", "29");
        msg.put("U", "30");
        msg.put("V", "31");
        msg.put("W", "32");
        msg.put("X", "33");
        msg.put("Y", "34");
        msg.put("Z", "35");
        msg.put(".", "36");
        msg.put("@", "37");
        msg.put("_", "38");
    }


    public static String inputStreamToString(InputStream in_temp) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(in_temp));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static InputStream stringToInputStream(String str, String code) {
        if (TextUtils.isEmpty(code)) {
            return new ByteArrayInputStream(str.getBytes());
        } else {
            try {
                return new ByteArrayInputStream(str.getBytes(code));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new ByteArrayInputStream(str.getBytes());
            }
        }
    }



    /**
     * 去除html标记
     */
    public static String removeHtml(String htmlStr) {
        if (TextUtils.isEmpty(htmlStr)) {
            return "";
        }
        String html = htmlStr;
        html = html.replaceAll("<(.*?)\\>", " ");//Removes all items in brackets
        html = html.replaceAll("<(.*?)\\\n", " ");//Must be undeneath
        html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
        html = html.replaceAll("&nbsp;", " ");
        html = html.replaceAll("&amp;", "&");
        html = html.replaceAll("&lt;", "<");
        html = html.replaceAll("&gt;", ">");
        html = html.replaceAll("&nbsp;", " ");
        return html;
    }


    /**
     * 屏蔽脏话
     *
     * @param text
     * @return
     */
    public static String filterUncivilText(String text) {
        for (String str : UNCIVIL_TEXTS) {
            text = text.replace(str, "***");
        }
        return text;
    }

    /**
     * 判断是否有脏话
     *
     * @param text
     * @return
     */
    public static boolean isHasUncivilText(String text) {
        for (String str : UNCIVIL_TEXTS) {
            if (text.contains(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  获取内容解码后的html
     * @param htmlStr
     * @return
     */
    public static String getDecodeBodyHtml(String htmlStr){
        if(containsChinese(htmlStr)){
            return htmlStr;
        }

        ArrayList<String> strs = new ArrayList<>();

        byte[] bytes = htmlStr.getBytes();

        int lastindex = 0;
        for(int index = 1 ; index < bytes.length ; index ++){
            byte by = bytes[index];
            if(by == '>'){
                if(index < bytes.length -1 && bytes[index+1] != '<'){
                    strs.add(htmlStr.substring(lastindex, index+1));
                    lastindex = index+1;
                }else if(index == bytes.length -1){
                    strs.add(htmlStr.substring(lastindex, bytes.length));
                }
            }else if( by == '<'){
                if(index-1 < bytes.length && index-1>0 && bytes[index-1]!='>'){
                    strs.add(UrlUtils.getUrlDecodeString(htmlStr.substring(lastindex, index)));
                    lastindex = index;
                }
            }
        }

        StringBuffer buffer = new StringBuffer();
        for(String str : strs){
            buffer.append(str);
        }

        return buffer.toString();
    }
}
