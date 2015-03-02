package test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import static cw4.CalendarUtils.sameDate;

public class CalendarUtilsTest {


	@Test
	public void testSameDate() {
		// dates to test
		Calendar d_2014_mar_15 = new GregorianCalendar(2014, 2, 15);
		Calendar d_2014_mar_15_10am = new GregorianCalendar(2014, 2, 15);
		d_2014_mar_15_10am.set(Calendar.HOUR_OF_DAY, 10);
		Calendar d_2015_mar_15 = new GregorianCalendar(2015, 2, 15);
		Calendar d_2014_feb_15 = new GregorianCalendar(2014, 1, 15);
		Calendar d_2014_mar_16 = new GregorianCalendar(2014, 2, 16);
		assertThat("same day/time twice, sameDate() ", sameDate(d_2014_mar_15,d_2014_mar_15), is(true));
		assertThat("same day different time, sameDate() ", sameDate(d_2014_mar_15,d_2014_mar_15_10am), is(true));
		assertThat("different years, sameDate() ", sameDate(d_2014_mar_15,d_2015_mar_15), is(false));
		assertThat("different months, sameDate() ", sameDate(d_2014_mar_15,d_2014_feb_15), is(false));
		assertThat("different day-of-month, sameDate() ", sameDate(d_2014_mar_15,d_2014_mar_16), is(false));
	}

}
