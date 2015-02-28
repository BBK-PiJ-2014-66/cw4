import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class PastMeetingImplTest {

	/**
	 * Use constructor to produce a simple Meeting object and check we can
	 * get back the supplied data correctly.
	 */
	@Test
	public void testConstructAndGetters() {
		Contact testContact = new ContactImpl("Test User");
		Set<Contact> testContacts = new HashSet<>();
		testContacts.add(testContact);
		Calendar testDate = new GregorianCalendar(2015, 0, 01); // 1st January 2015
		String testNotes = "Test notes";
		PastMeeting testMeeting = new PastMeetingImpl(testContacts, testDate, testNotes);
		assertThat("Get back the same date as provided to constructor?",
				testMeeting.getDate(), is(testDate));
		assertThat("Get back the same contacts as provided to constructor?",
				testMeeting.getContacts(), is(testContacts));
		assertThat("Get back the same notes as provided to constructor?",
				testMeeting.getNotes(), is(testNotes));
	}
	
	/**
	 * error testing: supply null parameters
	 * must get a NullPointerException to pass test
	 */
	@SuppressWarnings("unused")
	@Test(expected = NullPointerException.class)
	public void SupplyNullArgumentsToConstructor() {
		PastMeeting test = new PastMeetingImpl( null, null, null);
	}
	
	

}
