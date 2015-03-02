package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

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
		System.out.println("debug d_2014_mar_15      " + d_2014_mar_15.getTime());
		System.out.println("debug d_2014_mar_15_10am " + d_2014_mar_15_10am.getTime());
		System.out.println("debug d_2015_mar_15      " + d_2015_mar_15.getTime());
		System.out.println("debug d_2014_feb_15      " + d_2014_feb_15.getTime());
		System.out.println("debug d_2014_mar_16      " + d_2014_mar_16.getTime());
		fail("Not yet implemented");
	}

}
