package cw4;

import java.util.Calendar;

/**
 * Class to Calendar related utilities
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 02 March 2015
 * 
 */
public class CalendarUtils {

	private CalendarUtils() {
		throw new UnsupportedOperationException("Uninstantiable class");
	}

	/**
	 * Compare two Calendar times and returns true if they are on the same date
	 * (day of month, month and year) ignoring time of day. Does not take time
	 * zones into account.
	 * 
	 * @param calendarA the first calendar time
	 * @param calendarB the second calendar time
	 * @return true if the dates match, false otherwise
	 */
	public static boolean sameDate(Calendar calendarA, Calendar calendarB) {
		return false; // TODO stub implementation
	}
}