package com.zb.qjs.order.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 */
public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);//log4j记录日志

    public final static String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public final static String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String COMPACT_DATE_FORMAT = "yyyyMMddHHmmss";
    public final static String COMPACT_SHORT_FORMAT = "yyMMddHHmmss";
    public final static String CHINESE_SHORT_FORMAT = "yyyy年MM月dd日";
    public final static String COMPACT_SHORT_MILLISECOND_FORMAT = "yyMMddHHmmssSSS";

    /**
     * 当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 增加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days) {
        if (days == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static String formatShortDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        return sdf.format(date);
    }

    public static Date getShortDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            logger.error("日期转换出错", e);
        }
        return null;
    }

    public static String formatLongDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        return sdf.format(date);
    }

    public static Date getLongDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            logger.error("日期转换出错", e);
        }
        return null;
    }

    public static String formatCompactDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(COMPACT_DATE_FORMAT);
        return sdf.format(date);
    }

    public static String compactShortDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(COMPACT_SHORT_FORMAT);
        return sdf.format(date);
    }
    
    public static String compactShortMillisecondDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(COMPACT_SHORT_MILLISECOND_FORMAT);
        return sdf.format(date);
    }

    public static String formatDateOnPattern(Date date, String pattern) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date addYear(Date date, int years) {
        if (years == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     */
    public static Integer daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        Integer days = 0;
        if (smdate == null || bdate == null) return 0;

        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            days = Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            logger.error("获取两个日期之间相差的天数出错", e);
            return 0;
        }
        return days;
    }
    
    /**
     * 计算两个日期之间相差的天数（重要，常用）
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysCountBetween(Date smdate,Date bdate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time1 = cal.getTimeInMillis();
        
        cal.setTime(bdate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取当前时间(删除时分秒)
     *
     * @return
     */
    public static Date getCurrDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date currDay = new Date();
        try {
            currDay = format.parse(format.format(currDay));
        } catch (Exception e) {
            logger.error("getCurrDay {}", e);
        }
        return currDay;
    }

    /**
     * 获取当前时间(删除时分秒)
     *
     * @return
     */
    public static Date getCurrDay(String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date currDay = new Date();
        try {
            currDay = f.parse(f.format(currDay));
        } catch (Exception e) {
            logger.error("getCurrDay {}", e);
        }
        return currDay;
    }

    /**
     * 将给定格式的文本形式日期转换为Date类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取较大日期
     *
     * @param date1 时间1 不可为空
     * @param date2 时间2 不可为空
     */
    public static Date getBiggerDate(Date date1, Date date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        int compare = DateUtils.biggerOrSmall(date1, date2);
        if (compare > 1) {
            return date1;
        }
        return date2;
    }

    /**
     * 比较时间1是否大于时间2 大于返回2 等于返回1 小于返回0
     *
     * @param date1 时间1 不可为空
     * @param date2 时间2 不可为空
     */
    public static Integer biggerOrSmall(Date date1, Date date2) {
        int result = 1;
        if (date1 == null || date2 == null) return 1;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            if (time1 > time2) {
                result = 2;
            } else if (time1 < time2) {
                result = 0;
            } else {
                result = 1;
            }
        } catch (Exception e) {
            logger.error("获取两个日期之间相差的天数出错", e);
            return 1;
        }
        return result;
    }

    /**
     * 比较某一时间是否大于当天某一时间
     *
     * @param date
     * @param hour
     * @param minite
     * @param second
     * @param millisecond
     * @return
     */
    public static boolean biggerThanSomeTime(Date date, int hour, int minite, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minite);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        long calendar2 = calendar.getTimeInMillis();
        calendar.setTime(date);
        long calendar1 = calendar.getTimeInMillis();
        if (calendar1 > calendar2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较指定日期和年月日
     *
     * @return
     */
    public static boolean compareHMS(Date d, Date d1) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        long calendarMillis = calendar.getTimeInMillis();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(d1);
        long calendarMillis1 = calendar1.getTimeInMillis();
        if (calendarMillis > calendarMillis1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串yyyyMMddHHmmss转为Date类型
     *
     * @param dateStr
     */
    public static Date getCompactDateByStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMPACT_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("字符串yyyyMMddHHmmss转为Date类型出错", e);
        }
        return date;
    }

    /**
     * 字符串yyyy-MM-dd转为Date类型
     *
     * @param dateStr
     */
    public static Date getDateByStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.info("字符串yyyy-MM-dd转为Date类型出错");
        }
        return date;
    }

    /**
     * 查询指定时间是星期几,分别用数字1~7表示
     *
     * @param date
     */
    public static int dayForWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 获取预计提现到账时间
     *
     * @param d
     * @return
     */
    public static String getTXEndDate(Date d) {
        if (d == null) return "";
        int weeks = DateUtils.dayForWeek(d);
        //判断日期是否是15点前
        boolean isFifteen = compareHMS(d, parse(formatShortDate(d) + " 15:00:00", LONG_DATE_FORMAT));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        if (month == 10 && day > 0 && day < 9) {//国庆节
            return "2017/10/09 24:00前";
        } else if (month == 9 && day == 30) {//国庆节补班
            if (isFifteen) return "2017/10/09 24:00前";
            else return "2017/09/30 24:00前";
        } else {
            if (isFifteen) { //大于15:00 D+1
                if (weeks == 5) { //周五
                    return formatDateToString(addDay(d, 3), "yyyy/MM/dd") + " 24:00前";
                } else if (weeks == 6) {//周六
                    return formatDateToString(addDay(d, 2), "yyyy/MM/dd") + " 24:00前";
                } else {
                    return formatDateToString(addDay(d, 1), "yyyy/MM/dd") + " 24:00前";
                }
            } else {
                if (weeks == 6) { //周六
                    return formatDateToString(addDay(d, 2), "yyyy/MM/dd") + " 24:00前";
                } else if (weeks == 7) {//周日
                    return formatDateToString(addDay(d, 1), "yyyy/MM/dd") + " 24:00前";
                } else {//工作日
                    return formatDateToString(d, "yyyy/MM/dd") + " 24:00前";
                }
            }
        }
    }

    public static String formatDateToString(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date stampToDate(Long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = simpleDateFormat.format(new Date(s));
        try {
            return simpleDateFormat.parse(res);
        } catch (ParseException e) {
            return null;
        }
    }
    
	public static Date getYstDate() {
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return getSpecifiedDayBefore(nowDate);
	}

	public static Date getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}
	
	public static String getAfterDaysByChineseShortFormat(Date date,Integer num,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(null==date) date = new Date();
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        c.add(Calendar.DAY_OF_MONTH, num);//当天+1天  
        return sdf.format(c.getTime()); 
	}

    public static void main(String[] args) {
        System.out.println(stampToDate(1499998249547l));
    }
}

