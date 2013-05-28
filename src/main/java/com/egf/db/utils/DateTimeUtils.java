/*		
 * Copyright 2013-5-28 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DateTimeUtils.java,fangj Exp$
 * created at:下午02:12:25
 */
package com.egf.db.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间处理类
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public final class DateTimeUtils {

	private final static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
	public static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
	public static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT_ENGLISH = "MM/dd/yyyy HH:mm:ss";
	public static final String TIME_FORMAT_CHINA = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String TIME_FORMAT_MILLISECOND = "yyyyMMddhhmmssSSS"; // 毫秒级别

	public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
	public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
	public static final String DATE_FORMAT_ENGLISH = "MM/dd/yyyy";
	public static final String DATE_FORMAT_CHINA = "yyyy年MM月dd日";
	public static final String MONTH_FORMAT = "yyyyMM";
	public static final String DATE_FORMAT_MINUTE = "yyyyMMddHHmm";


	/**
	 * 得到日期字符串,格式为 yyyy-MM-dd
	 * 
	 * @param date
	 *            待格式化日期
	 * @return 返回格式化后的日期字符串
	 * @since 0.1
	 */
	public static String getDateNormalString(Date date) {
		DateFormat fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
		return fmt.format(date);
	}

	/**
	 * 得到日期字符串,格式为 yyyyMMdd
	 * 
	 * @param date
	 *            待格式化日期
	 * @return 返回格式化后的日期字符串
	 * @since 0.1
	 */
	public static String getDateShortString(Date date) {
		DateFormat fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
		return fmt.format(date);
	}

	
	/**
	 * 得到日期字符串,格式为 yyyy-MM-dd
	 * 
	 * @return 返回当前日期的字符串
	 */
	public static String getNowDateNormalString() {
		return getDateNormalString(new Date());
	}

	/**
	 * 得到日期字符串,格式为 yyyyMMdd
	 * 
	 * @return 返回当前日期的字符串
	 */
	public static String getNowDateShortString() {
		return getDateShortString(new Date());
	}

	/**
	 * 得到时间字符串,格式为 yyyy年MM月dd日 HH时mm分ss秒
	 * 
	 * @return 返回当前时间的字符串
	 */
	public static String getNowTimeChinaString() {
		return getTimeChinaString(new Date());
	}

	/**
	 * 得到时间字符串,格式为 MM/dd/yyyy HH:mm:ss
	 * 
	 * @return 返回当前时间的字符串
	 */
	public static String getNowTimeEnglishString() {
		return getTimeEnglishString(new Date());
	}

	/**
	 * 得到时间字符串,格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回当前时间的字符串
	 */
	public static String getNowTimeNormalString() {
		return getTimeNormalString(new Date());
	}

	/**
	 * 得到时间字符串,格式为 yyyyMMddHHmmss
	 * 
	 * @return 返回当前时间的字符串
	 */
	public static String getNowTimeShortString() {
		return getTimeShortString(new Date());
	}

	/**
	 * 返回当前时间的字符串，精确到毫秒级。
	 * 
	 * @return
	 * @since 0.1
	 */
	public static String getNowTimeMilliString() {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_MILLISECOND);
		return sdf.format(new Date());
	}

	/**
	 * 得到时间字符串,格式为 yyyy年MM月dd日 HH:mm:ss
	 * 
	 * @param date
	 *            待格式化日期
	 * @return 返回格式化后的时间字符串
	 * @since 0.1
	 */
	public static String getTimeChinaString(Date date) {
		DateFormat fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
		return fmt.format(date);
	}

	/**
	 * 得到时间字符串,格式为 MM/dd/yyyy HH:mm:ss
	 * 
	 * @param date
	 *            待格式化日期
	 * @return 返回格式化后的时间字符串
	 * @since 0.1
	 */
	public static String getTimeEnglishString(Date date) {
		DateFormat fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
		return fmt.format(date);
	}

	/**
	 * 得到时间字符串,格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            待格式化日期
	 * @return 返回格式化后的时间字符串
	 * @since 0.1
	 */
	public static String getTimeNormalString(Date date) {
		DateFormat fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
		return fmt.format(date);
	}

	/**
	 * 得到时间字符串,格式为 yyyyMMddHHmmss
	 * 
	 * @param date
	 *            待格式化日期
	 * @return 返回格式化后的时间字符串
	 * @since 0.1
	 */
	public static String getTimeShortString(Date date) {
		DateFormat fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
		return fmt.format(date);
	}
	
}