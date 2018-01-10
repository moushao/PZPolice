package com.pvirtech.pzpolice.utils;

public class TextFilter {

/*
	private static boolean isEmoji(char c) {
		return (c == 0x0) || (c == 0x9) || (c == 0xA) || (c == 0xD)
				|| ((c >= 0x20) && (c <= 0xD7FF))
				|| ((c >= 0xE000) && (c <= 0xFFFD))
				|| ((c >= 0x10000) && (c <= 0x10FFFF));
	}
*/

	private static boolean isWhitespace(char c) {
		return (!(c >= 0x21 && c <= 0x7e));
	}

	public static String rmWhitespace(String str) {
		String value = "";
		if (str == null || (str.length() == 0)) {
			return value;
		}

		int m = str.length();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < m; i++) {
			char c = str.charAt(i);
			String s = new String(new char[] { c });
			byte[] b = s.getBytes();
			if (b.length == 1) {
				if (!isWhitespace(c)) {
					buf.append(c);
				}
			} else {
				buf.append(c);
			}
		}

		value = buf.toString();
		return value;
	}
}
