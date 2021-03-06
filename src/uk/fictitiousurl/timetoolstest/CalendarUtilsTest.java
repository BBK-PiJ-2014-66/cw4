package uk.fictitiousurl.timetoolstest;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import uk.fictitiousurl.timetools.CalendarUtils;

/**
 * Unit tests of CalendarUtils
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @version 3.0.01
 * @since 3.0
 */
public class CalendarUtilsTest {


	/**
	 * test sameDate(Calendar,Calendar) - passes
	 */
	@Test
	public void testSameDate() {
		// dates to test
		Calendar d_2014_mar_15 = new GregorianCalendar(2014, 2, 15);
		Calendar d_2014_mar_15_10am = new GregorianCalendar(2014, 2, 15);
		d_2014_mar_15_10am.set(Calendar.HOUR_OF_DAY, 10);
		Calendar d_2015_mar_15 = new GregorianCalendar(2015, 2, 15);
		Calendar d_2014_feb_15 = new GregorianCalendar(2014, 1, 15);
		Calendar d_2014_mar_16 = new GregorianCalendar(2014, 2, 16);
		assertThat("same day/time twice, sameDate() ", CalendarUtils.sameDate(d_2014_mar_15,d_2014_mar_15), is(true));
		assertThat("same day different time, sameDate() ", CalendarUtils.sameDate(d_2014_mar_15,d_2014_mar_15_10am), is(true));
		assertThat("different years, sameDate() ", CalendarUtils.sameDate(d_2014_mar_15,d_2015_mar_15), is(false));
		assertThat("different months, sameDate() ", CalendarUtils.sameDate(d_2014_mar_15,d_2014_feb_15), is(false));
		assertThat("different day-of-month, sameDate() ", CalendarUtils.sameDate(d_2014_mar_15,d_2014_mar_16), is(false));
	}

}



