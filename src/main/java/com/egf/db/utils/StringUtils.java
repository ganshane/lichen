/*		
 * Copyright 2013-5-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,StringUtils.java,fangj Exp$
 * created at:下午05:12:25
 */
package com.egf.db.utils;

/**
 * @author fangj
 * @version $Revision: $
 * @since 1.0
 */
public class StringUtils {

	/**
	 * <p>
	 * Checks if a CharSequence is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace
	 * @since 2.0
	 * @since 3.0 Changed signature from isBlank(String) to
	 *        isBlank(CharSequence)
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取数组以特定字符合并成字符串
	 * @param array 数组
	 * @param split 分隔符
	 * @return
	 */
	public static String getUnionStringArray(String[] array,String split) {
		StringBuffer sb = new StringBuffer();
		for (String s : array) {
			sb.append(s);
			sb.append(split);
		}
		sb=sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
}
