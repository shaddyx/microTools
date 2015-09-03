package ua.org.shaddy.microtools;

import java.util.regex.Pattern;

public class StringTools {
	/**
	 * replaces <b>pattern</b> in <b>str<b> to <b>replace</b>
	 * 
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replace(String str, String pattern, String replace) {
		int start = 0;
		int end = 0;
		StringBuffer result = new StringBuffer();
		while ((end = str.indexOf(pattern, start)) >= 0) {
			result.append(str.substring(start, end));
			result.append(replace);
			start = end + pattern.length();
		}
		result.append(str.substring(start));
		str = result.toString();
		return str;
	}
	/**
	 * splits the string
	 * @param str - string to split
	 * @param delimiter - delimiter 
	 * @return
	 */
	public static String[] split(String str, String delimiter){
		Pattern p = Pattern.compile(delimiter, Pattern.LITERAL);
		return p.split(str);
	}
}
