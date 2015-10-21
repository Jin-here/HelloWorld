package com.vgaw.helloworld.util;


public class TextEditor {
	/**
	 * judge whether the input text is valid(need to persist).
	 * @param context
	 * @return
	 */
	public static boolean isAll32(String context){
		char[] raw = context.toCharArray();
		for (int i = 0;i < raw.length;i++){
			if (raw[i] == 32 || raw[i] == 13 || raw[i] == 10){
				continue;
			}else{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * return the former 13 char of the given string.
	 * @param context
	 * @return
	 */
	public static String getBrief(String context){
		if ("".equals(context) || context == null){
			return "";
		}
		char[] raw = context.toCharArray();
		int i = 0;
		while (raw[i] == 32){i++;}		
		return new String(raw, i, (raw.length-i) <= 13?(raw.length-i):13);
	}
}
