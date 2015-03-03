package test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cw4.Contact;
import cw4.ContactImpl;
import cw4.ContactManager;
import cw4.ContactManagerImpl;
import cw4.ContactManagerPlus;
import cw4.FutureMeeting;
import cw4.Meeting;

/**
 * JUnit tests for ContactManagerImpl implementation of ContactManager. N.B.,
 * often use ContactManagerPlus interface to use simple getters.
 * 
 * All tests now pass....
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 *
 */
public class ContactManagerImplTest {

	/**
	 * empty ContactManager for the tests that can use
	 */
	private ContactManager testCM;
	/**
	 * empty ContactManagerPlus for the tests that need the extra methods
	 */
	private ContactManagerPlus testCMP;

	/**
	 * a ContactManagerPlus initialised with contact, future meetings ...
	 */
	private ContactManagerPlus standardFilledCMP;

	/**
	 * for most tests override the current date time "now" to 10am 13th March
	 * 2014 (only works for ContactManagerPlus)
	 */
	private Calendar nowCal = new GregorianCalendar(2014, Calendar.MARCH, 13,
			10, 00);
	/**
	 * future date 10am 15th March 2014
	 */
	private Calendar futureCal = new GregorianCalendar(2014, Calendar.MARCH,
			15, 10, 00);
	/**
	 * past date 10am 11th March 2014
	 */
	private Calendar pastCal = new GregorianCalendar(2014, Calendar.MARCH, 11,
			10, 00);
	private String testName = "Test Name";
	String testNotes = "Test Contact Notes";
	private Contact unknown = new ContactImpl("Mr X");

	/**
	 * initialise the common test ContactManager*
	 */
	@Before
	public void init() {
		testCM = new ContactManagerImpl();
		testCMP = new ContactManagerImpl();
		testCMP.overrideDateNow(nowCal);
		standardFilledCMP = standardFilledCMP();
	}

	/**
	 * Test adding a future meeting
	 */
	@Test
	public void testaddFutureMeeting() {
		testCMP.addNewContact(testName, testNotes);
		Set<Contact> testContacts = testCMP.getContacts(testName);
		int meetingID = testCMP.addFutureMeeting(testContacts, futureCal);
		// to check this has worked now need to get the meeting back.
		FutureMeeting futureMeeting = testCMP.getFutureMeeting(meetingID);
		assertNotNull(
				"Added a future meeting: .getFutureMeeting(ID) should not return null.",
				futureMeeting);
		assertThat(
				"Added a future meeting: date of the meeting should be same as supplied.",
				futureMeeting.getDate(), is(futureCal));
		// additional test that getMeetings(id) returns the same meeting
		Meeting baseMeeting = testCMP.getMeeting(meetingID);
		assertNotNull(
				"Added a future meeting: .getMeeting(ID) should not return null.",
				baseMeeting);
		assertThat(
				".getFutureMeetings(ID) and .getMeeting(ID) should return same meeting ",
				baseMeeting.toString(), is(futureMeeting.toString()));
	}

	/**
	 * Test addFutureMeeting() with past date produces the required
	 * IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testaddFutureMeetingWithPastDate() {
		testCMP.addNewContact(testName, testNotes);
		Set<Contact> testContacts = testCM.getContacts(testName);
		testCMP.addFutureMeeting(testContacts, pastCal);
	}

	/**
	 * Test addFutureMeeting() with a contact that has not been added with
	 * addNewContact so is "unknown/non-existent". Interface requires this to
	 * produce an IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testaddFutureMeetingUnknownContact() {
		Set<Contact> testContacts = new HashSet<>(Arrays.asList(unknown));
		testCMP.addFutureMeeting(testContacts, futureCal);
	}

	/**
	 * Test getFutureMeetingList(Contact) normal behaviour
	 */
	@Test
	public void testGetFutureMeetingList_Contact() {
		testCMP.addNewContact(testName, testNotes);
		// need to get the contact created by this back
		// for search: use getAllContacts() for this
		assertThat(testCMP.getAllContacts().size(), is(1));
		Contact testContact = testCMP.getAllContacts().get(0);
		// get futureMeetings with the contact
		List<Meeting> futureMeets = testCMP.getFutureMeetingList(testContact);
		// should have returned an empty list (not null).
		assertNotNull(
				".getFutureMeetingList(Contact) should never return null!",
				futureMeets);
		assertThat(
				".getFutureMeetingList(Contact) should return empty list if there are no meetings",
				futureMeets.size(), is(0));

		// add meetings on 20th March, 17th March, 18th March
		Calendar futureA = new GregorianCalendar(2014, 2, 20);
		Calendar futureB = new GregorianCalendar(2014, 2, 17);
		Calendar futureC = new GregorianCalendar(2014, 2, 18);
		// add the meetings
		Set<Contact> testContacts = testCMP.getContacts(testName);
		testCMP.addFutureMeeting(testContacts, futureA);
		testCMP.addFutureMeeting(testContacts, futureB);
		testCMP.addFutureMeeting(testContacts, futureC);
		futureMeets = testCMP.getFutureMeetingList(testContact);
		assertThat(
				"after adding 3 meetings involving the contact."
						+ " .getFutureMeetingList(Contact) should return list of three meetings",
				futureMeets.size(), is(3));
		// meeting should have been sorted chronologically
		Date futureMeetsDates[] = new Date[] {
				futureMeets.get(0).getDate().getTime(),
				futureMeets.get(1).getDate().getTime(),
				futureMeets.get(2).getDate().getTime() };
		Date expected[] = new Date[] { futureB.getTime(), futureC.getTime(),
				futureA.getTime() };
		assertThat(
				".getFutureMeetingList(Contact) should return meetings sorted chronologically",
				futureMeetsDates, is(expected));
	}

	/**
	 * check that we get interface required IllegalArgumentException if
	 * getFutureMeetingList is called with an unknown contact
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFutureMeetingList_UnknownContact() {
		testCMP.getFutureMeetingList(unknown);
	}

	// /**
	// * Test getFutureMeetingList(Contact) normal behaviour
	// */
	// @Test
	// public void testGetFutureMeetingList_Calendar() {
	// // TODO write the test
	// }

	/**
	 * test for a contact when contacts are empty
	 */
	@Test
	public void testGetContactOnEmpty() {
		Set<Contact> retrieveContacts = testCM.getContacts(" ");
		// should not be null
		assertNotNull("retrieving contacts set should not return null",
				retrieveContacts);
		assertTrue(
				"Looking for contacts when none added. Should get an empty set back",
				retrieveContacts.isEmpty());
	}

	/**
	 * basic test for contacts, add new contact and search for that name
	 */
	@Test
	public void testAddNewContactGetContactCheckName() {
		// first add a decoy to make things a bit harder
		testCM.addNewContact("Jane Decoy", "a decoy");
		testCM.addNewContact(testName, testNotes);
		// to retrieve contact search of name
		Set<Contact> retrieveContacts = testCM.getContacts(testName);
		// should not be null
		assertNotNull("retrieving contacts set should never return null",
				retrieveContacts);
		// should have one contact
		assertThat(
				"added one contact and searched for that name. Should get one name back",
				retrieveContacts.size(), is(1));
		// furthermore should be able to get back the name
		Contact back = retrieveContacts.stream().findFirst().get(); // java 8
		// old-style:
		// Contact back = retrieveContacts.toArray(new Contact[0])[0];
		assertThat("added one contact and searched for that name. Check name",
				back.getName(), is(testName));
	}

	/**
	 * test getContacts(String) "contains"
	 */
	@Test
	public void testGetContactContains() {
		List<String> names = Arrays.asList("John Smith", "Jane Doe",
				"Adam Ant", "Jason Hippo");
		for (String name : names) {
			testCM.addNewContact(name, "");
		}
		int numBack;
		numBack = testCM.getContacts("J").size();
		assertThat(
				"added contacts " + names + ".\n getContacts(\"J\").size() ",
				numBack, is(3));
		numBack = testCM.getContacts("son").size();
		assertThat("added contacts " + names
				+ ".\n getContacts(\"son\").size() ", numBack, is(1));
		numBack = testCM.getContacts("z").size();
		assertThat("added contacts " + names
				+ ".\n getContacts(\"son\").size() ", numBack, is(0));

	}

	/**
	 * test searching for new contacts by id.
	 * 
	 * This is a bit involved as it is not that easy to get the id of a contact.
	 * Have to first search by name.
	 */
	@Test
	public void testGetContactsById() {
		String testNames[] = { "Joe Bloggs", "Jane Doe", "Joseph Something",
				"Fred Flint" };
		int nNames = testNames.length;
		int[] testIDs = new int[nNames];
		for (int tc = 0; tc < nNames; tc++) {
			String itName = testNames[tc];
			testCM.addNewContact(itName, "");
			Set<Contact> retrieveContacts = testCM.getContacts(itName);
			assertNotNull("retrieving contacts set should not return null",
					retrieveContacts);
			assertThat(
					"added distinct searched for that name. Should get one name back",
					retrieveContacts.size(), is(1));
			Contact back = retrieveContacts.stream().findFirst().get();
			testIDs[tc] = back.getId(); // get the ID back
		}
		// now search for the id's that we know are there
		Set<Contact> retrieveContacts = testCM.getContacts(testIDs);
		assertNotNull("retrieving contacts set should never return null",
				retrieveContacts);
		assertThat("searching by id should return correct number of names.",
				retrieveContacts.size(), is(nNames));
	}

	/**
	 * test adding an invalid contact with a null name produces NullPointer
	 * exception as specified in Interface
	 * 
	 * For method see
	 * http://stackoverflow.com/questions/156503/how-do-you-assert
	 * -that-a-certain -exception-is-thrown-in-junit-4-tests
	 */
	@Test(expected = NullPointerException.class)
	public void testAddewContactNullName() {
		testCM.addNewContact(null, "notes");
	}

	/**
	 * test adding an invalid contact with a null name produces NullPointer
	 * exception as specified in Interface
	 */
	@Test(expected = NullPointerException.class)
	public void testAddewContactNullNotes() {
		testCM.addNewContact("name", null);
	}

	/**
	 * test searching with an invalid id produces IllegalArgumentException as
	 * specified in Interface {@link ContactManager#getContacts(int...)
	 * getContacts}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetContactsInvalidID() {
		testCM.getContacts(-10000);
	}

	/**
	 * test searching with null name produces NullPointerException as specified
	 * in interface
	 */
	@Test(expected = NullPointerException.class)
	public void testGetContactsNullName() {
		String strNull = null;
		testCM.getContacts(strNull);
	}

	/**
	 * Provides a standard test filled CMP for unit tests.
	 * 
	 * @return a Contact manager with contact, future Meetings
	 */
	public ContactManagerPlus standardFilledCMP() {
		ContactManagerPlus filledCMP = new ContactManagerImpl();
		filledCMP.overrideDateNow(nowCal);
		filledCMP.addNewContact(testName, testNotes);
		// to add contacts have to provide set
		Set<Contact> testContacts = filledCMP.getContacts(testName);
		assertThat(
				"standardFilledCMP(): check that testContacts set has 1 contact.",
				testContacts.size(), is(1));
		// make 3 meetings with this user on futureCal: 10 am 9am 7am
		filledCMP.addFutureMeeting(testContacts, futureCal);
		Calendar cal9am = (Calendar) futureCal.clone();
		cal9am.set(Calendar.HOUR_OF_DAY, 9);
		filledCMP.addFutureMeeting(testContacts, cal9am);
		Calendar cal7am = (Calendar) futureCal.clone();
		cal7am.set(Calendar.HOUR_OF_DAY, 7);
		filledCMP.addFutureMeeting(testContacts, cal7am);

		// to keep track of everything print to console
		System.out.println("standardFilledCMP:" + filledCMP);
		return filledCMP;
	}
}
