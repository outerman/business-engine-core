package com.github.outerman.be.engine.util;

public class StringUtil {

	/**
	 * 判断字符串是否为空或者空串
	 *
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "null".equals(str) || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

}
