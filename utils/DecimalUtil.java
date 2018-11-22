package com.zb.qjs.order.common.utils;

import org.apache.commons.lang3.StringUtils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * BigDecimal工具类
 */
public class DecimalUtil {

	/**
	 * 数值为0的BigDecimal
	 */
	public static final BigDecimal ZERO = new BigDecimal(0);

	// 加减烦运算精度
	private static final Integer DEF_ADD_SCALE = 6;

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param values
	 *            加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(Number... values) {
		Number number0 = values[0] == null ? 0d : values[0];
		BigDecimal val = new BigDecimal(Double.toString(number0.doubleValue()));
		for (int i = 1; i < values.length; i++) {
			Number numberi = values[i] == null ? 0d : values[i];
			BigDecimal b1 = new BigDecimal(Double.toString(numberi.doubleValue()));
			val = val.add(b1);
		}
		return val.setScale(DEF_ADD_SCALE, BigDecimal.ROUND_DOWN);
	}

	public static Double doubleFloorValue(BigDecimal bd) {
		if (bd == null)
			return 0d;
		DecimalFormat formater = new DecimalFormat("0.00");
		formater.setGroupingSize(0);
		formater.setMaximumFractionDigits(2);
		formater.setRoundingMode(RoundingMode.FLOOR);
		return Double.valueOf(formater.format(bd.doubleValue()));
	}

	public static String doubleFloorString(BigDecimal bd) {
		if (bd == null)
			return "0.00";
		DecimalFormat formater = new DecimalFormat("0.00");
		formater.setGroupingSize(0);
		formater.setMaximumFractionDigits(2);
		formater.setRoundingMode(RoundingMode.FLOOR);
		return formater.format(bd.doubleValue());
	}

	/**
	 * 向上取整, 保留两位
	 * 
	 * @param bd
	 *            金额
	 * @return 取整后的金额
	 */
	public static String decimalUpScale(BigDecimal bd) {
		DecimalFormat formatter = new DecimalFormat("0.00");
		if (bd == null) {
			return null;
		}
		formatter.setGroupingSize(0);
		formatter.setMaximumFractionDigits(2);
		formatter.setRoundingMode(RoundingMode.UP);
		return formatter.format(bd.doubleValue());
	}

	/**
	 * 向下取整, 保留两位
	 * 
	 * @param bd
	 *            金额
	 * @return 取整后的金额
	 */
	public static String decimalDownScale(BigDecimal bd) {
		DecimalFormat formatter = new DecimalFormat("0.00");
		if (bd == null) {
			return null;
		}
		formatter.setGroupingSize(0);
		formatter.setMaximumFractionDigits(2);
		formatter.setRoundingMode(RoundingMode.DOWN);
		return formatter.format(bd.doubleValue());
	}

	public static BigDecimal doubleFloorBigDecimal(BigDecimal bd) {
		if (bd == null)
			return BigDecimal.ZERO;
		DecimalFormat formater = new DecimalFormat("0.00");
		formater.setGroupingSize(0);
		formater.setMaximumFractionDigits(2);
		formater.setRoundingMode(RoundingMode.FLOOR);
		return new BigDecimal(formater.format(bd.doubleValue()));
	}

	public static BigDecimal doubleFloorBigDecimalByString(String str) {
		if (StringUtils.isBlank(str))
			return BigDecimal.ZERO;
		return doubleFloorBigDecimal(new BigDecimal(str));
	}

	public static BigDecimal mutiply(BigDecimal bd1, BigDecimal bd2) {
		if (bd1 == null)
			bd1 = ZERO;
		if (bd2 == null)
			bd2 = ZERO;
		return bd1.multiply(bd2);
	}

	public static BigDecimal subtract(BigDecimal bd1, BigDecimal bd2) {
		if (bd1 == null)
			bd1 = ZERO;
		if (bd2 == null)
			bd2 = ZERO;
		return bd1.subtract(bd2);
	}

	public static BigDecimal doubleToBigDecimal(Double a) {
		if (a == null)
			return ZERO;
		return new BigDecimal(a);
	}

	/**
	 * 比较大小
	 * 
	 * @param bd1
	 * @param bd2
	 * @return -1:bd1<bd2;0:bd1=bd2;a=1bd1>bd2
	 */
	public static int compare(BigDecimal bd1, BigDecimal bd2) {
		return bd1.compareTo(bd2);
	}

	/**
	 * 向下取小数,只保留两位小数 多余小数位直接去掉
	 * 
	 * @param b
	 * @return
	 */
	public static BigDecimal roundDown(BigDecimal b) {
		return b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
	}

	public static BigDecimal roundDownByDouble(Double b) {
		BigDecimal c = new BigDecimal(b);
		return c.setScale(DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
	}

	public static BigDecimal roundDownByString(String b) {
		return new BigDecimal(b).setScale(DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
	}

	public static String roundDownFormatString(BigDecimal b) {
		BigDecimal c = b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
		DecimalFormat formater = new DecimalFormat("0.00");
		return formater.format(c);
	}

	public static Double roundDownFormatDouble(BigDecimal b) {
		BigDecimal c = b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
		return c.doubleValue();
	}

	/**
	 * 向上取小数,只保留两位小数 只要有第三位 则第二位自动加1
	 * 
	 * @param b
	 * @return
	 */
	public static BigDecimal roundUp(BigDecimal b) {
		return b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_UP);
	}

	public static BigDecimal roundUpByDouble(Double b) {
		BigDecimal c = new BigDecimal(b);
		return c.setScale(DEFAULT_SCALE, BigDecimal.ROUND_UP);
	}

	public static BigDecimal roundUpByString(String b) {
		return new BigDecimal(b).setScale(DEFAULT_SCALE, BigDecimal.ROUND_UP);
	}

	public static String roundUpFormatString(BigDecimal b) {
		BigDecimal c = b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_UP);
		DecimalFormat formater = new DecimalFormat("0.00");
		return formater.format(c);
	}

	public static Double roundUpFormatDouble(BigDecimal b) {
		BigDecimal c = b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_UP);
		return c.doubleValue();
	}

	/**
	 * 四舍五入
	 * 
	 * @param b
	 * @return
	 */
	public static BigDecimal roundHalf(BigDecimal b) {
		return b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal roundHalfByDouble(Double b) {
		BigDecimal c = new BigDecimal(b);
		return c.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal roundHalfByString(String b) {
		return new BigDecimal(b).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	public static String roundHalfFormatString(BigDecimal b) {
		BigDecimal c = b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
		DecimalFormat formater = new DecimalFormat("0.00");
		return formater.format(c);
	}
	
	/**
	 * 用于利率
	 * @param v1
	 * @return
	 */
	public static String fromatRate(Number v1) {
		BigDecimal b = new BigDecimal(Double.toString(v1.doubleValue()));
		BigDecimal c = b.setScale(4, BigDecimal.ROUND_HALF_UP);
		DecimalFormat formater = new DecimalFormat("0.0000");
		return formater.format(c);
	}

	public static Double roundHalfFormatDouble(BigDecimal b) {
		BigDecimal c = b.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
		return c.doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
		return b1.multiply(b2);
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal div(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
		return b1.divide(b2,6,BigDecimal.ROUND_DOWN);
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal add(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
		return b1.add(b2);
	}

	/**
	 * 提供精确的建发运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal sub(Number v1, Number v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
		return b1.subtract(b2);
	}

	public static final int  DEFAULT_SCALE = 2;


    /**
     * 金额取整方式
     * @param bd 金额
     * @param roundingMode 取整方式
     * @return 取整后的金额
     */
    public static String roundingMode(BigDecimal bd, RoundingMode roundingMode) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (bd == null ){
            return null;
        }
        formatter.setGroupingSize(0);
        formatter.setMaximumFractionDigits(2);

        switch (roundingMode) {
            case HALF_UP: //四舍五入, 收益率
                formatter.setRoundingMode(RoundingMode.HALF_UP);
                break;
            case UP: //向上取整, 手续费
                formatter.setRoundingMode(RoundingMode.UP);
                break;
            case DOWN: //向下取整
                formatter.setRoundingMode(RoundingMode.DOWN);
                break;
            case FLOOR:
                formatter.setRoundingMode(RoundingMode.FLOOR);
                break;
            default:
                formatter.setRoundingMode(RoundingMode.UP);
        }

        return formatter.format(bd.doubleValue());
    }
    
    public static BigDecimal roundHalf(String b){
    	return new BigDecimal(b).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }
}
