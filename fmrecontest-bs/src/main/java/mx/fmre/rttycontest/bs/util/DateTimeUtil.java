package mx.fmre.rttycontest.bs.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
	private final static TimeZone timeZone = TimeZone.getTimeZone("UTC");
	
	public static Date getUtcTimeDate() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.getTime();
	}
}
