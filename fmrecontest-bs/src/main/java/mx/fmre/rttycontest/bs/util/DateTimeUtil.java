package mx.fmre.rttycontest.bs.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtil {
	private final static TimeZone timeZone = TimeZone.getTimeZone("UTC");
	
	public static Date getUtcTimeDate() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.getTime();
	}
	
	public static String timeRemaining(Date startDate, int current, int total) {
		if (current <= 0)
			return "can't calculate time left";
		long diff = (new Date()).getTime() - startDate.getTime();
		long mult = (total - 1) * diff;
		double leftMiliseconds = (mult / current);
		if(leftMiliseconds >= 0 && leftMiliseconds <= 1000)
			return String.format("%s miliseconds left", BigDecimal.valueOf(leftMiliseconds).setScale(2, BigDecimal.ROUND_FLOOR).toString());
		double leftSeconds = leftMiliseconds / 1000;
		if(leftSeconds > 1 && leftSeconds < 60)
			return String.format("%s seconds left", BigDecimal.valueOf(leftSeconds).setScale(2, BigDecimal.ROUND_FLOOR).toString());
		double leftMinutes = leftSeconds / 60;
		if(leftMinutes > 1 && leftMinutes < 60)
			return String.format("%s minutes left", BigDecimal.valueOf(leftMinutes).setScale(2, BigDecimal.ROUND_FLOOR).toString());
		double leftHours = leftMinutes / 60;
		if(leftHours > 1 && leftHours < 24)
			return String.format("%s hours left", BigDecimal.valueOf(leftHours).setScale(2, BigDecimal.ROUND_FLOOR).toString());

		return String.format("%s miliseconds left", BigDecimal.valueOf(leftMiliseconds).setScale(2, BigDecimal.ROUND_FLOOR).toString());
	}
	
	public static void sayTime(Calendar calendar) {
		DateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS z", Locale.ENGLISH);
		log.info(f.format(calendar.getTime()));
	}
	
	public static void sayTime(Date date) {
		DateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS z", Locale.ENGLISH);
		log.info(f.format(date.getTime()));
	}
}
