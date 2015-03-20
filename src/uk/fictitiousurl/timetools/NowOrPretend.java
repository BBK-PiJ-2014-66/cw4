package uk.fictitiousurl.timetools;

import java.util.Calendar;

/**
 * singleton class to provide either the real system time
 * for now or a "pretend" time.
 *
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @version 3.0.01
 * @since 3.0
 * 
 */
public enum NowOrPretend {
	TIME; 

	/**
	 * Normally the real system date time will be used to check whether a
	 * meeting is in the future or past. However if pretendNow is not null then
	 * this date time will be used instead. Intended for testing.
	 */
	private Calendar pretendNow = null;

	/**
	 * Used to set a false current date in place of the real actual date/time
	 * "now". Useful for testing and may be for the program.
	 * 
	 * @param pretendNow
	 *            the date/time to treat as "now".
	 */
	public void setPretendNow(Calendar pretendNow) {
		this.pretendNow = pretendNow;
	}

	/**
	 * Getter for pretendNow - the overriding date for "now".
	 * 
	 * @return the date/time to treat as "now" or null if no pretend is set (so
	 *         real system time will be used).
	 */
	public Calendar getPretendNow() {
		return pretendNow;
	}

	/**
	 * check whether a date is in the future N.B. checks against current
	 * date/time unless {@link #overrideDateNow(Calendar) overrideDateNow} has
	 * been used to set a "pretendNow"
	 * 
	 * @param date
	 *            the date to check
	 * @return whether the date is in the future.
	 */
	public boolean checkDateIsFuture(Calendar date) {
		Calendar now = (pretendNow != null) ? pretendNow : Calendar
				.getInstance();
		return date.after(now);
	}

}
