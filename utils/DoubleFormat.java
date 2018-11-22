package com.zb.qjs.order.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleFormat {

    public static Double doubleFloorFormat(Double dvalue,int count){
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(count);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return Double.valueOf(formater.format(dvalue));
    }

    public static Double doubleFloorFormat(Double dvalue){
        if(dvalue == null) return 0d;
        DecimalFormat formater = new DecimalFormat("0.00");
        formater.setGroupingSize(0);
        formater.setMaximumFractionDigits(2);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return Double.valueOf(formater.format(dvalue));
    }

    public static String doubleFloorFormatString(Double dvalue,int count){
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(count);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(dvalue);
    }

    public static String doubleFloorFormatString(Double dvalue){
        if(dvalue == null) return "0.00";
        DecimalFormat formater = new DecimalFormat("0.00");
        formater.setGroupingSize(0);
        formater.setMaximumFractionDigits(2);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(dvalue);
    }

    public static Double doubleUpFormat(Double dvalue,int count){
    	if(dvalue == null) return 0d;
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(count);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.UP);
        return Double.valueOf(formater.format(dvalue));
    }
    
    public static String doubleUpFormatString(Double dvalue){
        if(dvalue == null) return "0.00";
        DecimalFormat formater = new DecimalFormat("0.00");
        formater.setGroupingSize(0);
        formater.setMaximumFractionDigits(2);
        formater.setRoundingMode(RoundingMode.UP);
        return formater.format(dvalue);
    }
    
    public static Double doubleHalfUpFormat(Double dvalue,int count){
    	if(dvalue == null) return 0d;
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(count);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(formater.format(dvalue));
    }
    
    public static String doubleHalfUpFormatString(Double dvalue,int count){
    	if(dvalue == null) return "0.00";
    	DecimalFormat formater = new DecimalFormat("0.00");
        formater.setMaximumFractionDigits(count);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.HALF_UP);
        return formater.format(dvalue);
    }
    
    public static void main(String[] args) {
    	System.out.println(doubleHalfUpFormatString(1010.3566788,2));
    	System.out.println(doubleFloorFormatString(1010.3566788,2));
	}

}
