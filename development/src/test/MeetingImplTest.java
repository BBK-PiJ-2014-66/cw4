package test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cw4.Contact;
import cw4.ContactImpl;
import cw4.Meeting;
import cw4.MeetingImpl;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
/**
 * JUnit tests for MeetingImpl. A pretty simple class single constructor, 3
 * getters.
 * 
 * Passes all tests.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 *
 */
public class MeetingImplTest {

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
		Meeting testMeeting = new MeetingImpl(testContacts, testDate);
		assertThat("Get back the same date as provided to constructor?",
				testMeeting.getDate(), is(testDate));
		assertThat("Get back the same contacts as provided to constructor?",
				testMeeting.getContacts(), is(testContacts));
	}

	/**
	 * Create two Meeting objects and check that their ID's are distinct.
	 */
	@Test
	public void testTwoMeetingsHaveDifferentIDs() {
		Contact testContact = new ContactImpl("Test User");
		Set<Contact> testContacts = new HashSet<>();
		testContacts.add(testContact);
		Calendar testDate = new GregorianCalendar(2015, 0, 01);
		Meeting testMeetA = new MeetingImpl(testContacts, testDate);
		Meeting testMeetB = new MeetingImpl(testContacts, testDate);
		assertThat("Two meetings have distinct ID's", testMeetA.getId(),
				not(testMeetB.getId()));
	}

	/**
	 * test method orderByDate
	 */
	@Test
	public void testOrderByDate() {
		Contact testContact = new ContactImpl("Test User");
		Set<Contact> testContacts = new HashSet<>();
		testContacts.add(testContact);
		Calendar cal01Jan2015 = new GregorianCalendar(2015, 0, 01);
		Calendar cal01Feb2015 = new GregorianCalendar(2015, 1, 01);
		Meeting testMeetA = new MeetingImpl(testContacts, cal01Jan2015);
		Meeting testMeetB = new MeetingImpl(testContacts, cal01Feb2015);
		assertThat(
				"orderByDate(meetA,meetA) should give zero",
				MeetingImpl.orderByDate(testMeetA, testMeetA), is(0));
		assertThat(
				"orderByDate(meetA,meetB) should give +ve as meetA is before meetB",
				(MeetingImpl.orderByDate(testMeetA,testMeetB)>0), is(true));
	
		assertThat(
				"orderByDate(meetB,meetA) should give -ve as meetA is before meetB",
				(MeetingImpl.orderByDate(testMeetB,testMeetA)>0), is(true));
	}

	/**
	 * error testing: supply null contacts and calendar must get a
	 * NullPointerException to pass test
	 */
	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void SupplyNullArgumentsToConstructor() {
		Meeting testCM = new MeetingImpl(null, null);
	}

	/**
	 * error testing: supply empty set of contacts must get a
	 * NullPointerException to pass test
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void SupplyEmptyContactsToConstructor() {
		Set<Contact> testContacts = new HashSet<>();
		Calendar testDate = new GregorianCalendar(2015, 0, 01); // 1st January
																// 2015
		Meeting testMeeting = new MeetingImpl(testContacts, testDate);
	}

}
