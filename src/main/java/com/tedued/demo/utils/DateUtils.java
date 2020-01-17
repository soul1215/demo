package com.tedued.demo.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;

import lombok.extern.slf4j.Slf4j;



@Slf4j
public class DateUtils {
	
	//日期格式常量
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	//时间格式常量yyyy-MM-dd
	public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIME_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";

	public static final String TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * 将Date日期转换为指定格式字符串
	 * @param date 日期对象
	 * @return 返回“yyyy-MM-dd”字符串
	 */
	public static String getDateToString(Date date) {
		if (date == null)
		{
			date = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sdf.format(date);
	}
	
	/**
	 * 将Date时间转换为指定格式字符串
	 * @param date 日期对象
	 * @return 返回“yyyy-MM-dd hh:mm:ss”字符串
	 */
	public static String getDateTimeToString(Date date) {
		if (date == null)
		{
			date = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		return sdf.format(date);
	}
	
	/**
	 * 将Date日期转换为指定格式字符串
	 * @param date 日期对象
	 * @param pattern 格式模型（yyyy-MM-dd HH:mm:ss、yyyy年MM月dd日 HH时mm分ss秒 ...）
	 * @return 返回对应格式字符串
	 */
	public static String getDateToString(Date date, String pattern) {
		if (date == null || "".equals(date))
		{
			date = new Date();
		}
		if (pattern == null || "".equals(pattern)){
			pattern = DEFAULT_TIME_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 将字符串类型的时间转换为指定格式的Date类型
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date getStringToDate(String strDate, String pattern) {
		if(strDate==null||strDate.isEmpty()){
			return null;
		}

		if(pattern==null||pattern.isEmpty()){
			pattern = DEFAULT_TIME_FORMAT;
		}
		if(strDate.length() == 28 ){
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",Locale.US);
			//Fri Apr 27 20:36:42 SGT 2018
			if(strDate.indexOf("SGT") > 0){
				strDate = strDate.replace("SGT", "CST");
			}
			//Fri Apr 27 20:36:42 GMT+08:00 2018
			else if(strDate.indexOf("GMT") > 0){
				int gmtIndex = strDate.indexOf("GMT");
				strDate = (strDate.substring(0, gmtIndex)) + "CST" + (strDate.substring((gmtIndex + 9), strDate.length()));
			}
			Date date = null;
			try {
				date = sdf.parse(strDate);
			} catch (ParseException e1) {
				log.error("", e1);
			}
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strDate = sdf.format(date);
		}else if(strDate.indexOf("T") > 0){
			strDate = strDate.replace("T", " ");
		}else if(strDate.endsWith(".0")){
			strDate = strDate.replace(".0", "");
		}

		pattern = DEFAULT_TIME_FORMAT.substring(0, strDate.trim().length());
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}

	}
	
	/**
	 * 将字符串类型的时间转换为Date类型
	 * @param strDate 时间字符串
	 * @return
	 */
	public static Date getStringToDate(String strDate){
		DateFormat format = DateFormat.getDateInstance();
		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		// 需要捕获ParseException异常，如不要捕获，可以直接抛出异常，或者抛出到上层
		try{
			if(strDate!=null&&!strDate.isEmpty()){
				strDate = GetTimeValueFromCST(strDate).trim();
				if(strDate.indexOf(" ") == -1){
					try {
						time = format.parse(strDate);
					} catch (Exception e) {
						time = formatDate.parse(strDate);
					}
				}else{
					time = formatTime.parse(strDate);
				}
			}
		}catch (ParseException e){
			log.error("can not parse date : " + strDate, e);
		}
		return time;
	}
	
	public static String GetTimeValueFromCST(String strDate){
		if(strDate.indexOf("CST")==-1)return strDate;
		String result = "";
		String[] arr = strDate.split(" ");
		if(arr.length<6)return strDate;
		result = arr[5];
		switch(arr[1])
		{
			case "Jan":
				result += "-01";
				break;
			case "Feb":
				result += "-02";
				break;
			case "Mar":
				result += "-03";
				break;
			case "Apr":
				result += "-04";
				break;
			case "May":
				result += "-05";
				break;
			case "Jun":
				result += "-06";
				break;
			case "Jul":
				result += "-07";
				break;
			case "Aug":
				result += "-08";
				break;
			case "Sep":
				result += "-09";
				break;
			case "Oct":
				result += "-10";
				break;
			case "Nov":
				result += "-11";
				break;
			default:result += "-12";break;
		}
		result += "-"+arr[2];
		result += " "+arr[3];
		return result;
		// Thu May 04 17:10:04 CST 2017
	}
	
	/**
	 * 日期加一天
	 * @param dateStr 日期字符串类型参数
	 * @return
	 */
	public static Date getDateStrPlusOne(String dateStr){
		Date date = getStringToDate(dateStr);
		return getDatePlusOne(date);
	}
	
	/**
	 * 日期加一天
	 * @param date
	 * @return
	 */
	public static Date getDatePlusOne(Date date){
		if(null != date){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date;
	}
	
	/**
	 * 给指定的日期加上几天
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date getDatePlus(Date date, int count){
		if(null != date){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, count);
			date = calendar.getTime();
		}
		return date;
	}
	
	/**
	 * 获取当前系统年份
	 * @return
	 */
	public static int getYear(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获取指定日期的年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(date);
		return Integer.parseInt(year);
	}
	
	/**
	 * 获取当前系统月份
	 * @return
	 */
	public static int getMonth(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String month = sdf.format(date);
		return Integer.parseInt(month);
	}
	
	/**
	 * 获取指定日期的月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String month = sdf.format(date);
		return Integer.parseInt(month);
	}
	
	/**
	 * 获取当前系统天
	 * @return
	 */
	public static int getDay(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String day = sdf.format(date);
		return Integer.parseInt(day);
	}
	
	/**
	 * 获取指定日期的天
	 * @param date
	 * @return
	 */
	public static int getDay(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String day = sdf.format(date);
		return Integer.parseInt(day);
	}
	
	/**
	 * 获取当前系统小时
	 * @return
	 */
	public static int getHour(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh");
		String hour = sdf.format(date);
		return Integer.parseInt(hour);
	}
	
	/**
	 * 获取指定日期的小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("hh");
		String hour = sdf.format(date);
		return Integer.parseInt(hour);
	}
	
	/**
	 * 获取当前系统分钟数
	 * @return
	 */
	public static int getMinute(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		String minute = sdf.format(date);
		return Integer.parseInt(minute);
	}
	
	/**
	 * 获取指定日期的分钟数
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		String minute = sdf.format(date);
		return Integer.parseInt(minute);
	}
	
	/**
	 * 获取当前系统秒钟数
	 * @return
	 */
	public static int getSecond(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		String second = sdf.format(date);
		return Integer.parseInt(second);
	}
	
	/**
	 * 获取指定日期的秒钟数
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		String second = sdf.format(date);
		return Integer.parseInt(second);
	}
	
	/**
	 * 获取指定日期追加年份数后的日期
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYears(Date date, int year){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.add(Calendar.YEAR, year);

		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期追加月份数后的日期
	 * @param date
	 * @param Month
	 * @return
	 */
	public static Date addMonths(Date date, int month){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.add(Calendar.MONTH, month);

		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期追加天数后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, int day){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.add(Calendar.DATE, day);

		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期追加小时数后的日期
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHours(Date date, int hour){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.add(Calendar.HOUR, hour);

		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期追加分钟数后的日期
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinutes(Date date, int minute){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.add(Calendar.MINUTE, minute);

		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期追加秒钟数后的日期
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSeconds(Date date, int second){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.add(Calendar.SECOND, second);

		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期所属月份的最后一天的日期
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDayToDate(Date date){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.set(Calendar.DAY_OF_MONTH, clenr.getActualMaximum(Calendar.DAY_OF_MONTH));
		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期所属月份的第一天的日期
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDayToDate(Date date){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.set(Calendar.DAY_OF_MONTH, clenr.getActualMinimum(Calendar.DAY_OF_MONTH));
		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期下个月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfNextMonth(Date date){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		clenr.set(Calendar.DAY_OF_MONTH, clenr.getActualMinimum(Calendar.DAY_OF_MONTH));
		clenr.add(Calendar.MONTH, 1);
		return clenr.getTime();
	}
	
	/**
	 * 获取指定日期所属月份的最大天数
	 * @param date
	 * @return
	 */
	public static int getMaxDayToDate(Date date){
		Calendar clenr = Calendar.getInstance();
		clenr.setTime(date);
		int max = clenr.getActualMaximum(Calendar.DAY_OF_MONTH);
		return max;
	}
	
	/**
	 * 获取指定日期所在年的最后一天的日期
	 * @param date
	 * @return
	 */
	public static Date getYearLastDayToDate(Date date){
		Calendar clenr = Calendar.getInstance();
		clenr.clear();
		clenr.set(Calendar.YEAR, getYear(date));
		clenr.roll(Calendar.DAY_OF_YEAR, -1);
		return clenr.getTime();
	}
	
	/**
	 * 比较2个日期是否是同年同月
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareYearMonth(Date date1, Date date2) {
	    boolean flag = true;
	    if(date1==null || date2==null) {
	        flag = false;
	    }
	    if(getYear(date1) != getYear(date2)) {
	        flag = false;
	    }
	    if(getMonth(date1) != getMonth(date2)) {
	        flag = false;
	    }
	    return flag;
	}
	
	/**
	 * 获取指定日期是星期几
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINESE);
		String week = sdf.format(date);
		int result = 1;
		for (int i = 0; i < weeks.length; i++) {
			if(week.equals(weeks[i])){
				break;
			}
			result++;
		}
		return result;
	}
	
	/**
	 * 计算两个时间相差多少天
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer diffDay(Date startDate, Date endDate) {  
	    if (startDate == null || endDate == null) {  
	        return null;  
	    }  
	    DateTime dt1 = new DateTime(startDate);  
	    DateTime dt2 = new DateTime(endDate);  
	    int day = Days.daysBetween(dt1, dt2).getDays();  
	    return Math.abs(day);  
	}  
	
	public static void main(String[] args) {
		System.out.println(addDays(getStringToDate("2019-09-20"),-1));
		System.out.println(diffDay(getStringToDate("2019-10-20"), getMonthFirstDayToDate(getStringToDate("2019-10-20"))));
		System.out.println(getStringToDate("2019-10-20").after(getStringToDate("2019-10-20")));
	}
}
