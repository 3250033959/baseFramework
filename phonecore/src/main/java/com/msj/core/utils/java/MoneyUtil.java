package com.msj.core.utils.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

public class MoneyUtil {
	public static final BigDecimal MAX_VALUE = BigDecimal
			.valueOf(Long.MAX_VALUE);
	public static final BigDecimal MIN_VALUE = BigDecimal
			.valueOf(Long.MIN_VALUE);
	public static final long NumEmpty = Long.MIN_VALUE + 988;

	/**
	 * 带千分位的字符串 分 转 元<p>
	 * 例子:12,300,014.55<p>
	 * 
	 * @param bal
	 * @param floatSize
	 * @return
	 */
	public static String getBalanceDisplay(String bal, int floatSize) {
		if ("".equals(bal)) {
			return "";
		} else {
			long amount = Long.parseLong(bal);
			NumberFormat format = NumberFormat.getInstance();
			if (NumEmpty == amount)
				return "";
			if (floatSize < 0) { // error input
				return Long.toString(amount);
			}
			if (floatSize == 2) {
				boolean zs = true;
				if (amount < 0) {
					zs = false;
					amount = -amount;
				}
				long m = amount / 100;
				long f = amount - m * 100;
				return (zs ? "" : "-") + format.format(m) + "."
						+ (f > 9 ? Long.toString(f) : "0" + f);
			} else if (floatSize == 0) {
				return format.format(amount);
			} else { // error input
				return format.format(amount);
			}
		}
	}

	/**
	 * 带千分位的字符串 分 转 元<br/>
	 * 例子:12,300,014.55<br/>
	 * 
	 * @param amount
	 * @param floatSize
	 * @return
	 */
	public static String getBalanceDisplay(long amount, int floatSize) {
		NumberFormat format = NumberFormat.getInstance();
		if (NumEmpty == amount)
			return "";
		if (floatSize < 0) { // error input
			return Long.toString(amount);
		}
		if (floatSize == 2) {
			boolean zs = true;
			if (amount < 0) {
				zs = false;
				amount = -amount;
			}
			long m = amount / 100;
			long f = amount - m * 100;
			return (zs ? "" : "-") + format.format(m) + "."
					+ (f > 9 ? Long.toString(f) : "0" + f);
		} else if (floatSize == 0) {
			return format.format(amount);
		} else { // error input
			return format.format(amount);
		}
	}

	/**
	 * 字符串金额乘以100转换为BigInteger类型 金额：可为负数，允许2位小数
	 * 
	 * @param input
	 * @return
	 */
	public static BigInteger str2BigInteger(String input) {
		// 金额：可为负数，允许2位小数
		String regex = "(-)?(([1-9]\\d*)|0)(\\.\\d{1,2})?";
		if (!Pattern.matches(regex, input)) {
			throw new RuntimeException("金额格式错误");
		}
		BigDecimal amount = new BigDecimal(input).multiply(BigDecimal
				.valueOf(100L));
		if (MAX_VALUE.compareTo(amount) < 0) {
			throw new RuntimeException("金额值太大");
		}
		if (MIN_VALUE.compareTo(amount) > 0) {
			throw new RuntimeException("金额值太小");
		}
		return amount.toBigInteger();
	}

	/**
	 * 字符串金额乘以100转换为long类型 金额：可为负数，允许2位小数
	 * 
	 * @param input
	 * @return
	 */
	public static long str2long(String input) {
		// 金额：可为负数，允许2位小数
		String regex = "(-)?(([1-9]\\d*)|0)(\\.\\d{1,2})?";
		if (!Pattern.matches(regex, input)) {
			throw new RuntimeException("金额格式错误");
		}
		BigDecimal amount = new BigDecimal(input).multiply(BigDecimal
				.valueOf(100L));
		if (MAX_VALUE.compareTo(amount) < 0) {
			throw new RuntimeException("金额值太大");
		}
		if (MIN_VALUE.compareTo(amount) > 0) {
			throw new RuntimeException("金额值太小");
		}
		return amount.longValue();
	}

	/**
	 * 将BigInteger类型金额除以100转换为字符串
	 * 
	 * @param amount
	 * @return
	 */
	public static String bigInteger2str(BigInteger amount) {

		return (amount.divide((BigInteger.valueOf(100l)))).toString();
	}

	/**
	 * 将long类型金额除以100转换为字符串
	 * 
	 * @param amount
	 * @return
	 */
	public static String long2str(String amount) {

		return BigDecimal.valueOf(Long.parseLong(amount))
				.divide(BigDecimal.valueOf(100L)).toString();
	}

	/**
	 * 将long类型金额除以100转换为字符串
	 * 
	 * @param amount
	 * @return
	 */
	public static String long2str(long amount) {
		return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100L))
				.toString();
	}

	public static String getMoneyDisplay(BigDecimal amount) {
		if (amount == null) {
			return "0.00";
		} else {
			DecimalFormat df = new DecimalFormat("##.00");
			return df.format(amount.setScale(2, BigDecimal.ROUND_DOWN));
		}
	}

	/**
	 * 将long类型金额除以100转换为字符串，并保留2位小数位
	 * 
	 * @param amount
	 * @return
	 */
	public static String long2strWith2Dec(long amount) {
		return MoneyUtil.getBalanceDisplay(amount, 2);
		// String str = MoneyUtil.long2str(amount);
		// int index = str.indexOf(".");
		// if(index == -1){
		// return str + ".00";
		// }else if(index == str.length()-2){
		// return str + "0";
		// }else{
		// return str;
		// }
	}

	public static void main(String[] args) {
		try {
			System.out.println(MoneyUtil.str2long("122222123.35"));
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(MoneyUtil.long2strWith2Dec(1234000));
	}

	/**
	 *
	 * 功能描述：金额字符串转换：单位分转成单元

	 * @param str 传入需要转换的金额字符串
	 * @return 转换后的金额字符串
	 */
	public static String fenToYuan(String str) {
		if(str == null)
			return "0.00";
		String s = str.toString();
		int len = -1;
		StringBuilder sb = new StringBuilder();
		if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
			s = removeZero(s);
			if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
				len = s.length();
				int tmp = s.indexOf("-");
				if(tmp>=0){
					if(len==2){
						sb.append("-0.0").append(s.substring(1));
					}else if(len==3){
						sb.append("-0.").append(s.substring(1));
					}else{
						sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));
					}
				}else{
					if(len==1){
						sb.append("0.0").append(s);
					}else if(len==2){
						sb.append("0.").append(s);
					}else{
						sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));
					}
				}
			}else{
				sb.append("0.00");
			}
		}else{
			sb.append("0.00");
		}
		return sb.toString();
	}

	/**
	 *
	 * 功能描述：去除字符串首部为"0"字符

	 * @param str 传入需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String removeZero(String str){
		char  ch;
		String result = "";
		if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
			try{
				for(int i=0;i<str.length();i++){
					ch = str.charAt(i);
					if(ch != '0'){
						result = str.substring(i);
						break;
					}
				}
			}catch(Exception e){
				result = "";
			}
		}else{
			result = "";
		}
		return result;

	}
}
