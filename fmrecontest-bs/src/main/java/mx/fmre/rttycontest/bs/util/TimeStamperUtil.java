package mx.fmre.rttycontest.bs.util;

import java.math.BigDecimal;
import java.util.Date;

public abstract class TimeStamperUtil {
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
}
