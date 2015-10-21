package com.vgaw.helloworld.util;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DateTools {
	//yyyy-MM-dd HH:mm:ss.SSSZ.
	/**
	 * return string time forms like
	 * yyyyMMddHHmmss.
	 * @return
	 */
	public static String getTimeS(){
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmE",Locale.CHINA);
		return format.format(new Date());
	}

	/**
	 * return long time forms like
	 * yyyyMMddHHmmss.
	 * @return
	 */
	public static long getTimeL(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
		return Long.parseLong(format.format(new Date()));
	}
	
	/**
	 * return time string forms like
	 * 2015��,07��04��,17ʱ03��39��,������ ]
	 * @return
	 */
	public static ArrayList<String> getChilds(){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        String raw = dateformat.format(new Date());
        ArrayList<String> childs = new ArrayList<String>();
        childs.add(raw.substring(0, 5));
        childs.add(raw.substring(5, 11));
        childs.add(raw.substring(12, 21));
        childs.add(raw.substring(22, raw.length()));
        return childs;
	}

    public static String getDate(String raw){
        return raw.substring(0,2) + "/" + raw.substring(2,4);
    }

	public static String getDateMore(String raw){
		return raw.substring(8, raw.length()) + " " + raw.substring(4,6) + ":" + raw.substring(6,8);
	}

	/*public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
	}*/
}
