package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	/*
	* 以yyyy-MM-dd HH:mm:ss格式返回当前系统时间
	* */
	public static String getSysTime(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		String dateStr = sdf.format(date);
		
		return dateStr;
		
	}
	
}
