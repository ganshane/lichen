/*		
 * Copyright 2010 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: StringUtils.java,v 1.11 2010/03/02 04:47:03 yub Exp $
 * created at:2010-2-1
 */
package lichen.migration.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 1.11 $
 * @since 0.1
 */
public final class StringUtils extends org.apache.commons.lang.StringUtils {
	private static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
	private static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME_FORMAT_ENGLISH = "MM/dd/yyyy HH:mm:ss";
	private static final String TIME_FORMAT_CHINA = "yyyy年MM月dd日 HH时mm分ss秒";

	private static final String DATE_FORMAT_SHORT = "yyyyMMdd";
	private static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
	private static final String DATE_FORMAT_ENGLISH = "MM/dd/yyyy";
	private static final String DATE_FORMAT_CHINA = "yyyy年MM月dd日";

	/**
	 * 判断给定的字符串是否为空,以及空字符串
	 * 
	 * @param input
	 *            输入字符串
	 * @return 是否为空,空则返回true，反之亦反
	 * @since 0.1
	 */
	public static boolean blank(String input) {
		return input == null || "".equals(input) || input.length() == 0 || input.trim().length() == 0;
	}

	/**
	 * 判断给定的字符串不为空
	 * 
	 * @param input
	 *            输入字符串
	 * @return 是否不为空，不为空返回true，反之亦反.
	 * @since 0.1
	 */
	public static boolean notBlank(String input) {
		return !blank(input);
	}

	/**
	 * 求字符串中某个子串的位置（自字符串orig的i位开始将orig按组长度len分隔为若干段，求第一个indexOf(段,sub)=0
	 * 的段首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @param len
	 *            每组长度
	 * @param i
	 *            开始查找位置
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub, int len, int i) {
		if (orig == null)
			return -1;
		int idx = orig.indexOf(sub, i);
		if (idx == -1)
			return idx;
		if (idx % len == 0)
			return idx;
		i = (idx / len + 1) * len;
		int tmp = -1;
		if ((tmp = indexOf(orig, sub, len, i)) > -1) {
			return tmp;
		} else {
			return -1;
		}
	}

	/**
	 * 求字符串中某个子串的位置（将字符串按组长度len分隔为若干段，求第一个indexOf(段,sub)=0的段首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @param len
	 *            每组长度
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub, int len) {
		return indexOf(orig, sub, len, 0);
	}

	/**
	 * 求字符串中某个子串的位置（将字符串按子串长度分隔为若干段，求第一个同子串相同的段 首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub) {
		return indexOf(orig, sub, sub.length(), 0);
	}

	/**
	 * 返回代码层级
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @return
	 * @since 0.1
	 */
	public static int level(String orig, String sub) {
		String prefix = orig.substring(0,6);
		String suffix = orig.substring(6);
		int index = indexOf(prefix, sub);
		if(index==-1){
			if("000000".equals(suffix)){
				return 3;
			}else{
				return 4;
			}
		}
		return index / sub.length();
	}

	/**
	 * 截取字符串
	 * 
	 * @param orig
	 *            源字符串
	 * @param length
	 *            字符串长度
	 * @return
	 * @since 0.1
	 */
	public static String substr(String orig, int length) {
		if (orig == null)
			return "";
		if (orig.length() <= length)
			return orig;
		return orig.substring(0, length - 1) + "...";
	}

	/**
	 * 首字母大写
	 * 
	 * @param input
	 *            输入字符串
	 * @return
	 * @since 0.1
	 */
	public static String toFirstUpperCase(String input) {
		return StringUtils.blank(input) ? input : input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	/**
	 * 根据单位编码及单位所属级别【部=M,省=P,市=C】 获取平台代码
	 * 
	 * @param unitCode
	 *            单位编码
	 * @param level
	 *            部=M,省=P,市=C
	 * @return
	 */
	@Deprecated
	public static String getAsPlatformCode(String unitCode, String level) {
		String codeStr = unitCode;
		if ("M".equalsIgnoreCase(level)) {
			codeStr = "010000000000";
		}
		if ("P".equalsIgnoreCase(level)) {
			codeStr = unitCode.subSequence(0, 2) + "0000000000";
		}
		if ("C".equalsIgnoreCase(level)) {
			codeStr = unitCode.subSequence(0, 4) + "00000000";
		}
		return codeStr;
	}

	/**
	 * 获取上级平台代码
	 * 
	 * @param code
	 *            当前平台编码
	 * @return
	 */
	@Deprecated
	public static String getParentPlatformCode(String code) {
		String codeStr = getPrfixCode(code);
		codeStr = codeStr.substring(0, codeStr.length() - 2);
		for (int i = codeStr.length(); i < 12; i++) {
			codeStr = codeStr + "0";
		}
		return codeStr;
	}

	/**
	 * 获取单位代码中不为0的前缀 如:code=510001000000,返回:510001
	 * 
	 * @param code
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static String getPrfixCode(String code) {
		String codeStr = code + "#";
		codeStr = codeStr.replace("00#", "#").replace("00#", "#").replace("00#", "#").replace("00#", "#").replace("00#", "#").replace("#", "");
		return codeStr;
	}

	/**
	 * 字符转换方法
	 * 
	 * @param source
	 *            原字符串
	 * @param clazz
	 *            转换类型
	 * @return
	 * @throws ParseException
	 */
	public static Object convert(String orig, Class<?> clazz) {
		if (StringUtils.isBlank(orig)) {
			if (clazz == boolean.class) {
				return false;
			}
			if (clazz == Boolean.class) {
				return Boolean.FALSE;
			}
			return null;
		}
		if (clazz == String.class) {
			return orig;
		}
		if (StringUtils.isBlank(orig)) {
			return null;
		}
		if (clazz == short.class) {
			return Short.parseShort(orig);
		}
		if (clazz == Short.class) {
			return Short.valueOf(orig);
		}
		if (clazz == int.class) {
			return Integer.parseInt(orig);
		}
		if (clazz == Integer.class) {
			return Integer.valueOf(orig);
		}
		if (clazz == long.class) {
			return Long.parseLong(orig);
		}
		if (clazz == Long.class) {
			return Long.valueOf(orig);
		}
		if (clazz == float.class) {
			return Float.parseFloat(orig);
		}
		if (clazz == Float.class) {
			return Float.valueOf(orig);
		}
		if (clazz == double.class) {
			return Double.parseDouble(orig);
		}
		if (clazz == Double.class) {
			return Double.valueOf(orig);
		}

		if (orig.equalsIgnoreCase("t") || orig.equalsIgnoreCase("true") || orig.equalsIgnoreCase("y") || orig.equalsIgnoreCase("yes")) {
			if (clazz == boolean.class) {
				return true;
			}
			if (clazz == Boolean.class) {
				return Boolean.valueOf(true);
			}
		} else {
			if (clazz == boolean.class) {
				return false;
			}
			if (clazz == Boolean.class) {
				return Boolean.valueOf(false);
			}
		}

		try {
			if (clazz == java.util.Date.class) {
				DateFormat fmt = null;
				if (orig.matches("\\d{14}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
				} else if (orig.matches("\\d{8}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_CHINA);
				}
				return fmt.parse(orig);
			}
			if (clazz == java.util.Calendar.class) {
				Calendar cal = Calendar.getInstance();
				DateFormat fmt = null;
				if (orig.matches("\\d{14}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
				} else if (orig.matches("\\d{8}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_CHINA);
				}
				cal.setTime(fmt.parse(orig));
				return cal;
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("字符串不能转换为" + clazz.getName() + "类型.");
		}
		throw new IllegalArgumentException("字符串不能转换为" + clazz.getName() + "类型.");
	}

	/**
	 * 字符转换方法
	 * 
	 * @param source
	 *            原字符串
	 * @param clazz
	 *            转换类型
	 * @return
	 * @throws ParseException
	 */
	public static String convert(Object orig) {
		if (orig == null) {
			return null;
		}
		if (orig instanceof String) {
			return (String) orig;
		}
		if (orig instanceof Short) {
			return Short.toString((Short) orig);
		}
		if (orig instanceof Integer) {
			return Integer.toString((Integer) orig);
		}
		if (orig instanceof Long) {
			return Long.toString((Long) orig);
		}
		if (orig instanceof Float) {
			return Float.toString((Float) orig);
		}
		if (orig instanceof Double) {
			return Double.toString((Double) orig);
		}
		if (orig instanceof Boolean) {
			return Boolean.toString((Boolean) orig);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		if (orig instanceof java.util.Date) {
			return format.format((java.util.Date) orig);
		}
		if (orig instanceof java.sql.Date) {
			return format.format((java.sql.Date) orig);
		}
		if (orig instanceof java.util.Calendar) {
			return format.format(((java.util.Calendar) orig).getTime());
		}
		throw new java.lang.IllegalArgumentException("参数类型不支持.");
	}

	/**
	 * 中文参数转码
	 * 
	 * @param param
	 * @return
	 * @since 0.1
	 */
//	public static String encoding(String param) {
//		if (param == null)
//			return null;
//		String flag = SysConfigPropertyUtil.getInstance().getPropertyValue("EncodingConvert");
//		if ("Y".equals(flag)) {
//			try {
//				return new String(param.getBytes("ISO-8859-1"), "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				return param;
//			}
//		}
//		return param;
//	}

	/**
	 * 转码字符
	 * 
	 * @param param
	 *            待转换字符串
	 * @param origEncode
	 *            原编码
	 * @param destEncode
	 *            转换编码
	 * @return 转码后字符串
	 * @since 0.1
	 */
	public static String encoding(String param, String origEncode, String destEncode) {
		if (param == null)
			return null;
		try {
			return new String(param.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return param;
		}
	}

	/**
	 * 转换数据库列名，分隔符(_)后的首字母大写其他字母小写，删除分隔符
	 * 
	 * @param column
	 *            列名
	 * @return 属性名
	 * @since 0.1
	 */
	public static String convertColumn2Field(String column) {
		String[] fields = column.toLowerCase().split("_");
		StringBuffer sfield = new StringBuffer();
		int i = 0;
		for (String field : fields) {
			if (i > 0) {
				sfield.append(StringUtils.toFirstUpperCase(field));
			} else {
				sfield.append(field);
			}
			i++;
		}
		return sfield.toString();
	}

	/**
	 * 转换数据库表名，首字母及分隔符(_)后的首字母大写其他字母小写，删除分隔符
	 * 
	 * @param column
	 *            列名
	 * @return 属性名
	 * @since 0.1
	 */
	public static String convertTable2Class(String column) {
		String[] fields = column.toLowerCase().split("_");
		StringBuffer sfield = new StringBuffer();
		for (String field : fields) {
			sfield.append(StringUtils.toFirstUpperCase(field));
		}
		return sfield.toString();
	}

	/**
	 * 获取平台代码
	 * 
	 * @param unitCode
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static String getPlatformCode(String unitCode) {
		return (unitCode == null || unitCode.equals("")) ? unitCode : unitCode.substring(0, 4) + "00000000";
	}

	/**
	 * 获取子平台代码
	 * 
	 * @param departCode
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static String getChildPlatFormCode(String localCode, String departCode) {
		if (StringUtils.blank(departCode) || StringUtils.blank(localCode)) {
			throw new java.lang.IllegalArgumentException("参数不能为空.");
		}
		if ("0100".equals(localCode)) {
			return departCode.substring(0, 2) + "00";
		} else {
			return departCode.substring(0, 4);
		}
	}

	/**
	 * 获取上级平台代码
	 * 
	 * @param localCode
	 * @param departCode
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static String getParentPlatFormCode(String localCode, String departCode) {
		if (StringUtils.blank(departCode) || StringUtils.blank(localCode)) {
			throw new java.lang.IllegalArgumentException("参数不能为空.");
		}
		if ("00".equals(localCode.substring(localCode.length() - 2, localCode.length()))) {
			return "0100";
		} else {
			return departCode.substring(0, 2) + "00";
		}
	}

	/**
	 * 判断单位2是否属于单位1的管辖
	 * 
	 * @param unitCode1
	 * @param unitCode2
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static boolean isInclude(String unitCode1, String unitCode2) {
		for (int i = 0; i < unitCode1.toCharArray().length; i++) {
			if (unitCode1.toCharArray()[i] != unitCode2.toCharArray()[i]) {
				if (unitCode1.toCharArray()[i] == '0') {
					return true;
				} else {
					return false;
				}
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 判断是否属于同一个平台
	 * 
	 * @param localCode
	 *            本地平台代码
	 * @param ssgajgjgdm
	 *            所属公安机关机构代码
	 * @return 返回 是否
	 * @since 0.1
	 */
	@Deprecated
	public static boolean isSamePlatFormCode(String localCode, String ssgajgjgdm) {

		if (localCode.equals(ssgajgjgdm.substring(0, 4) + "00000000")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断临控指令编号的前6位后面加0(总共12位)是否和平台代码相同
	 * 
	 * @param localCode
	 *            本地平台代码
	 * @param lkzlbh
	 *            临控指令编号
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static boolean isSamePlatFormLkzlbh(String localCode, String lkzlbh) {
		return localCode.equals(lkzlbh.substring(0, 6) + "000000") ? true : false;
	}

	/**
	 * 弹出框提示
	 * 
	 * @param message
	 * @param response
	 * @since 0.1
	 */
//	@Deprecated
//	public static void alert(String message, HttpServletResponse response) {
//		PrintWriter out = null;
//		response.setContentType("text/html;charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		try {
//			out = response.getWriter();
//			out.print("<script>alert('" + message + "');history.go(-1);</script>");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			out.flush();
//			out.close();
//		}
//	}

	/**
	 * 签收操作提示的信息
	 * 
	 * @param state
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static String signAlertMessage(String state) {
		if (!StringUtils.blank(state)) {
			if ("1".equals(state))
				return "正常签收";
			if ("2".equals(state))
				return "迟签收";
			if ("4".equals(state))
				return "该信息已经签收";
			if ("5".equals(state))
				return "只有待签收状态的预警才可以签收";
			if ("6".equals(state))
				return "此预警信息为下级上报的预警信息不需要签收";
		}
		return null;
	}

	/**
	 * 判断当前平台是否是省平台
	 * 
	 * @param localCode
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static boolean isProvincePlatForm(String localCode) {
		return localCode.substring(2).equals("0000000000") ? true : false;
	}

	/**
	 * 判断单位编码1是否是单位编码2的上级
	 * 
	 * @param unitcode1
	 *            单位编码1
	 * @param unitcode2
	 *            单位编码2
	 * @return
	 * @since 0.1
	 */
	public static boolean isParentCode(String unitcode1, String unitcode2) {
		if (StringUtils.isBlank(unitcode1) || StringUtils.isBlank(unitcode2)) {
			return false;
		} else {
			int len=unitcode1.toCharArray().length/2;
			for (int i = 0; i < len; i++) {
				if (!unitcode1.substring(2*i, 2*i+2) .equals( unitcode2.substring(2*i, 2*i+2))) {
					if ("00".equals(unitcode1.substring(2*i, 2*i+2))) {
						return true;
					} else {
						return false;
					}
				} else {
					continue;
				}
			}
			return false;
		}
	}

	/**
	 * 判断字符串是否上下级关系
	 * 
	 * @param upper
	 * @param lower
	 * @param subcode
	 * @return 0:相等 1:上级 -1:下级 9:不属于上下级
	 * @since 0.1
	 */
	public static int lowerOrUpper(String upper, String lower, String subcode) {
		if (lower == upper) {
			return 0;
		}
		if (lower == null || upper == null) {
			return 9;
		}
		String upperSubCode = StringUtils.subCode(upper, subcode);
		String lowerSubCode = StringUtils.subCode(lower, subcode);
		if (upperSubCode.equals(lowerSubCode)) {
			return 0;
		}
		if (lowerSubCode.indexOf(upperSubCode) > -1) {
			return 1;
		}
		if (upperSubCode.indexOf(lowerSubCode) > -1) {
			return -1;
		}
		return 9;
	}

	/**
	 * 从map字符串获取value值
	 * 
	 * @param orig
	 *            字符串 例:a=1&b=2&c=3
	 * @param param
	 *            key 例:a
	 * @param separator
	 *            例:&
	 * @return 例:1
	 * @since 0.1
	 */
	public static String[] getValueFromString(String orig, String param, String separator) {
		String[] result = new String[] {};
		if (StringUtils.isBlank(orig)) {
			return result;
		}
		List<String> list = new ArrayList<String>();
		String[] values = orig.split(separator);
		for (String tmp : values) {
			String key = StringUtils.substringBefore(tmp, "=");
			String value = StringUtils.substringAfter(tmp, "=");
			if (param.equals(key)) {
				list.add(value);
			}
		}
		return list.toArray(result);
	}

	public static Map<String, String[]> getMapFromString(String orig, String separator) {
		if (StringUtils.isBlank(orig)) {
			return null;
		}
		String[] values = orig.split(separator);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String tmp : values) {
			String key = StringUtils.substringBefore(tmp, "=");
			String value = StringUtils.substringAfter(tmp, "=");
			if (map.containsKey(key)) {
				List<String> valueList = map.get(key);
				valueList.add(value);
			} else {
				List<String> valueList = new ArrayList<String>();
				valueList.add(value);
				map.put(key, valueList);
			}
		}
		Map<String, String[]> mapReturn = new HashMap<String, String[]>();
		for (String key : map.keySet()) {
			mapReturn.put(key, map.get(key).toArray(new String[] {}));
		}
		return mapReturn;
	}

	/**
	 * 根据用户单位编码和数据的所属公安机关判断用户是否有修改的权限
	 * 
	 * @param userunit
	 * @param dwcode
	 * @return
	 * @since 0.1
	 */
	@Deprecated
	public static boolean havePurview(String userunit, String dwcode) {
		if (userunit.substring(0, 4).equals("5100")) { // 省厅用户都有权限
			return true;
		}
		String result = userunit.substring(0, StringUtils.indexOf(userunit, "00"));
		if (result.equals(dwcode.substring(0, result.length()))) {
			return true; // 有权限
		} else {
			return false;
		}
	}

	/**
	 * 求代码的子代码 例subcode('510100010000','00')='51010001'
	 * 
	 * @param orig
	 *            代码
	 * @param substr
	 *            子字符串
	 * @return
	 * @since 0.1
	 */
	public static String subCode(String orig, String substr) {
		if (StringUtils.isBlank(orig)) {
			return "";
		}
		if("notNull".equals(orig)){
			return "%0%";
		}
		if (StringUtils.isBlank(substr)) {
			return orig;
//			throw new IllegalArgumentException("子字符串不能为空.");
		}
		
		if (orig.length()% substr.length() != 0) {
			throw new IllegalArgumentException("原字符串长度必须是子字符串长度整数倍.");
		}
		for (int i = -substr.length();;) {
			if (-i == substr.length()) {
				if (!StringUtils.substring(orig, i).equals(substr)) {
					return orig.substring(0, orig.length() + i + substr.length());
				}
			} else {
				if (!StringUtils.substring(orig, i, i + substr.length()).equals(substr)) {
					return orig.substring(0, orig.length() + i + substr.length());
				}
			}

			if ((-i) >= orig.length()) {
				break;
			}
			i -= substr.length();
		}
		return orig;
	}

	/**
	 * 求上级子代码
	 * 
	 * @param orig
	 * @param substr
	 * @return
	 * @since 0.1
	 */
	public static String parentSubCode(String orig, String substr) {
		String subcode = StringUtils.subCode(orig, substr);
		return subcode.substring(0, subcode.length() - substr.length());
	}

	/**
	 * 求上级代码
	 * 
	 * @param orig
	 * @param substr
	 * @return
	 * @since 0.1
	 */
	public static String parentCode(String orig, String substr) {
		return StringUtils.rightPad(StringUtils.parentSubCode(orig, substr), orig.length(), substr);
	}

	/**
	 * 返回包含下级单位的单位编码
	 * 
	 * @param unitCode
	 *            单位编码
	 * @return
	 * @since 0.1
	 */
//	@Deprecated
//	public static String containChildrenUnit(String unitCode) {
//		String code = unitCode;
//		String[] codes = new String[] {};
//		while (StringUtils.isNotBlank(code)) {
//			if (code.length() >= 2) {
//				codes = (String[]) ArrayUtils.add(codes, code.substring(0, 2));
//				code = code.substring(2, code.length());
//			} else {
//				break;
//			}
//		}
//		int index = ArrayUtils.indexOf(codes, "00");
//		if (index >= 1) {
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < index; i++) {
//				sb.append(codes[i]);
//			}
//			return sb.toString() + "%";
//		}
//		return unitCode;
//	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param str
	 *            要过滤的字符串
	 * @param regEx
	 *            正则表达式(只允许字母和数字:[^a-zA-Z0-9],清除掉所有特殊字符:[`~!@#$%^&*()+=|{}
	 *            ':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？])
	 * @return 返回过滤后的字符串
	 * @since 0.1
	 */
	public static String StringFilter(String str, String regEx) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(regEx)) {
			return str;
		} else {
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim();
		}
	}

	/**
	 * 求上级代码对应的下n级代码
	 * 
	 * @param higher
	 *            上级代码
	 * @param lower
	 *            下级代码
	 * @param step
	 *            步进
	 * @param subCode
	 *            分级代码
	 * @return
	 * @since 0.1
	 */
	public static String lowerCode(String higher, String lower, int step, String subCode) {
		return StringUtils.rightPad(StringUtils.substring(lower, 0, StringUtils.subCode(higher, subCode).length() + subCode.length() * step), lower.length(), subCode);
	}

	public static void main(String[] args) {
		System.out.println(subCode("0051000011", "00"));
	}
}