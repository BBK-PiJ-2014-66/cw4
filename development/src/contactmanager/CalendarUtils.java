package contactmanager;

import java.util.Calendar;

/**
 * Class to Calendar related utilities
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 02 March 2015
 * 
 */
class CalendarUtils {

	private CalendarUtils() {
		throw new UnsupportedOperationException("Uninstantiable class");
	}

	/**
	 * Compare two Calendar times and returns true if they are on the same date
	 * (day of month, month and year) ignoring time of day. Does not take time
	 * zones into account.
	 * 
	 * @param calA
	 *            the first calendar time
	 * @param calB
	 *            the second calendar time
	 * @return true if the dates match, false otherwise
	 */
	static boolean sameDate(Calendar calA, Calendar calB) {
		boolean match = true;
		if (calA.get(Calendar.YEAR) != calB.get(Calendar.YEAR)) {
			match = false;
		} else if (calA.get(Calendar.MONTH) != calB.get(Calendar.MONTH)) {
			match = false;
		} else if (calA.get(Calendar.DATE) != calB.get(Calendar.DATE)) {
			// (DATE is the day in the month)
			match = false;
		}
		return match;
	}
}