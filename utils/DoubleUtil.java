package com.zb.qjs.order.common.utils;

import java.math.BigDecimal;

/**
 * double的计算不精确，会有类似0.0000000000000002的误差，正确的方法是使用BigDecimal或者用整型
 * 整型地方法适合于货币精度已知的情况，比如12.11+1.10转成1211+110计算，最后再/100即可 以下是摘抄的BigDecimal方法:
 */
public class DoubleUtil {
	// 默认除法运算精度
	private static final Integer DEF_DIV_SCALE = 2;
	// 加减烦运算精度
	private static final Integer DEF_ADD_SCALE = 6;

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param values
	 *            加数
	 * @return 两个参数的和
	 */
	public static Double add(Number... values) {
		Number number0 = values[0] == null ? 0d : values[0];
		BigDecimal val = new BigDecimal(Double.toString(number0.doubleValue()));
		for (int i = 1; i < values.length; i++) {
			Number numberi = values[i] == null ? 0d : values[i];
			BigDecimal b1 = new BigDecimal(Double.toString(numberi.doubleValue()));
			val = val.add(b1);
		}
		return val.setScale(DEF_ADD_SCALE, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param values
	 *            加数
	 * @return 两个参数的和
	 */
	public static Double add(Integer scale, Number... values) {
		Number number0 = values[0] == null ? 0d : values[0];
		BigDecimal val = new BigDecimal(Double.toString(number0.doubleValue()));
		for (int i = 1; i < values.length; i++) {
			Number numberi = values[i] == null ? 0d : values[i];
			BigDecimal b1 = new BigDecimal(Double.toString(numberi.doubleValue()));
			val = val.add(b1);
		}
		if (scale == null) {
			scale = DEF_ADD_SCALE;
		}
		return val.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @return 两个参数的差
	 */
	public static double sub(Number... values) {
		Number number0 = values[0] == null ? 0d : values[0];
		BigDecimal val = new BigDecimal(Double.toString(number0.doubleValue()));
		for (int i = 1; i < values.length; i++) {
			Number numberi = values[i] == null ? 0d : values[i];
			BigDecimal b1 = new BigDecimal(Double.toString(numberi.doubleValue()));
			val = val.subtract(b1);
		}
		return val.setScale(DEF_ADD_SCALE, BigDecimal.ROUND_DOWN).doubleValue();
	}

	public static double sub(Integer scale, Number... values) {
		Number number0 = values[0] == null ? 0d : values[0];
		BigDecimal val = new BigDecimal(Double.toString(number0.doubleValue()));
		for (int i = 1; i < values.length; i++) {
			Number numberi = values[i] == null ? 0d : values[i];
			BigDecimal b1 = new BigDecimal(Double.toString(numberi.doubleValue()));
			val = val.subtract(b1);
		}
		if (scale == null) {
			scale = DEF_ADD_SCALE;
		}
		return val.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static Double mul(Number value1, Number value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
	 *
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return 两个参数的商
	 */
	public static Double div(Number dividend, Number divisor) {
		return div(dividend, divisor, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 *
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static Double div(Number dividend, Number divisor, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
		return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 *
	 * @param value
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static Double round(Double value, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	public static void main(String[] args) {
		Double aDouble=20.256;
		Double bDouble=10.9711;
		System.out.println(round(aDouble,2));
		System.out.println(round(bDouble,2));
		System.out.println(div(aDouble,bDouble,2));
		System.out.println(div(aDouble,bDouble,5));
//		Double aa=0.00;
//		aa=sub(2,aDouble,bDouble);
//		System.out.println(aa);
//		Double bb=sub(aDouble,bDouble);
//		Double cc=sub(2,aDouble,bDouble);
//		System.out.println(bb);
//		System.out.println(cc);
//		System.out.println(sub(2,aDouble,bDouble));
//        DecimalFormat df = new DecimalFormat("0.00");
//        //不进行四舍五入
//        df.setRoundingMode(RoundingMode.FLOOR);
//        System.out.println(df.format(cc));
//        System.out.println(df.format(bb));
		
	}
}
