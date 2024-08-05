package com.pcwk.ehr.cmn;

import java.util.Calendar;

public class DateUtil {
	
	/*
	 * Cal util => 2024년04월03일
	 * @param cal
	 * @return 년월일 ex) 2024 8월 29일
	 * */
	public static String toDateFormatString(Calendar cal) {

		String result = "";
		
		result = result.format("%d년 %02d월 %02d일", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
		
		return result;
	} // toDateFormatString
} // class
