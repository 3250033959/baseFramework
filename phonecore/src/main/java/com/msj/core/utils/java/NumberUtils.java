package com.msj.core.utils.java;

import java.text.DecimalFormat;

/**
 * @author CentMeng csdn@vip.163.com on 15/12/1.
 */
public class NumberUtils {

    /**
     * 返回100，000.00元有千位符和后两位小数
     *
     * @param amount
     * @return
     */
    public static String getSeperateWithFloatAmount(double amount) {
        // String format = "%1$s 元";
        String double_format = "%1$.2f";
        String total = String.format(double_format, amount);
        String point_total = "." + total.substring(total.length() - 2);
        // return
        return addSeparateSign((int) amount) + point_total;
    }

    /**
     * 返回100，000元有千位符整数
     *
     * @param amount
     * @return
     */
    public static String getSeperateAmount(double amount) {
        String double_format = "%1$.2f";
        String total = String.format(double_format, amount);
        // return String.format(format,
        // "" +
        return addSeparateSign((int) Float.parseFloat(total));
    }

    /**
     * 返回***万
     *
     * @param amount
     * @return
     */
    public static String getWanAmount(double amount) {
        int total = (int) (amount / 10000);
        return "" + total + "万";
    }


    /**
     * 将整型添加“,”隔位符
     * “123456789”-》“123，456，789”
     *
     * @param number
     * @return
     */
    public static String addSeparateSign(int number) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(number);
    }


    public static String doubleTrans(int num){
//        BigDecimal account = new BigDecimal(String.valueOf(num));
//        DecimalFormat df=new java.text.DecimalFormat("0.##");
//        return df.format(account.movePointLeft(2).floatValue());

        return getSeperateWithFloatAmount(num/100.00
        );
    }

    /**
     * 转换成ChineseNumber
     * @param number
     * @return
     */
    public static String formatChineseNumber(int number) {
        if (number < 10) {
            return getChineseNumber(number);
        } else {
            int shi = (int) Math.floor(number / 10);
            if (shi > 1) {
                return getChineseNumber(shi) + "十" + getChineseNumber(number);
            } else {
                return "十" + getChineseNumber(number);
            }
        }
    }

    public static String getChineseNumber(int number) {
        switch (number) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
//            case 10:
//                return "十";
//            case 100:
//                return "一百";
//            case 1000:
//                return "一千";
//            case 10000:
//                return "一万";
//            case 100000:
//                return "十万";
//            case 1000000:
//                return "一百万";
//            case 10000000:
//                return "一千万";
//            case 100000000:
//                return "一亿";
            default:
                return "";
        }
    }

}
