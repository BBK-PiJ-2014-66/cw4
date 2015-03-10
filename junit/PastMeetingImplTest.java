package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cw4.Contact;
import cw4.ContactImpl;
import cw4.PastMeeting;
import cw4.PastMeetingImpl;

/**
 * JUnit tests for PastMeetingImpl.
 * 
 * Need to be able to convert a future meeting to a past one.
 * 
 * Passes all tests.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 *
 */
public class PastMeetingImplTest {

	/**
	 * Use constructor to produce a simple Meeting object and check we can get
	 * back the supplied data correctly.
	 */
	@Test
	public void testConstructAndGetters() {
		Contact testContact = new ContactImpl("Test User");
		Set<Contact> testContacts = new HashSet<>();
		testContacts.add(testContact);
		Calendar testDate = new GregorianCalendar(2015, 0, 01); // 1st January
																// 2015
		String testNotes = "Test notes";
		PastMeeting testMeeting = new PastMeetingImpl(testContacts, testDate,
				testNotes);
		assertThat("Get back the same date as provided to constructor?",
				testMeeting.getDate(), is(testDate));
		assertThat("Get back the same contacts as provided to constructor?",
				testMeeting.getContacts(), is(testContacts));
		assertThat("Get back the same notes as provided to constructor?",
				testMeeting.getNotes(), is(testNotes));
	}

	@Test
	public void testIDConstruct() {
		Contact testContact = new ContactImpl("Test User");
		Set<Contact> testContacts = new HashSet<>();
		testContacts.add(testContact);
		Calendar testDate = new GregorianCalendar(2015, 0, 01); // 1st January
																// 2015
		String testNotes = "Test notes";
		int testId = 57;
		// use constructor with 4 arguments
		PastMeeting testMeeting = new PastMeetingImpl(testId, testContacts,
				testDate, testNotes);
		assertThat("Get back the same ID as provided to constructor?",
				testMeeting.getId(), is(testId));
	}

	/**
	 * error testing: supply null parameters must get a NullPointerException to
	 * pass test
	 */
	@Test(expected = NullPointerException.class)
	public void supplyNullNotesToConstructor() {
		Contact testContact = new ContactImpl("Test User");
		Set<Contact> testContacts = new HashSet<>();
		testContacts.add(testContact);
		Calendar testDate = new GregorianCalendar(2015, Calendar.JANUARY, 01);
		new PastMeetingImpl(testContacts, testDate, null);
	}

}
