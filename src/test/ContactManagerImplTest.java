import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * JUnit tests for ContactManagerImpl implementation of ContactManager. N.B.,
 * often use ContactManagerPlus interface to use simple getters.
 * 
 * All tests DO NOT currently pass.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 *
 */
public class ContactManagerImplTest {

	/**
	 * Test adding a future meeting
	 */
	@Test
	public void testaddFutureMeeting() {
		ContactManager testCM = new ContactManagerImpl();
		Calendar future = new GregorianCalendar(3000, 0, 01); // 1st January
																// 3000
		String testName = "Test Name";
		testCM.addNewContact(testName, "Test Notes");
		Set<Contact> testContacts = testCM.getContacts(testName);
		int meetingID = testCM.addFutureMeeting(testContacts, future);
		// development - print the ContactManager
		System.out.println("debug contact manager with one future meeting: " + testCM);
		// to check this has worked now need to get the meeting back.
		// (much simpler than I had made it at first and can be done with basic ContactManager)
		FutureMeeting futureMeeting = testCM.getFutureMeeting(meetingID);
		assertNotNull(
				"Added a future meeting: .getFutureMeetings(ID) should not return null.",
				futureMeeting);
		assertThat(
				"Added a future meeting: date of the meeting should be same as supplied.",
				futureMeeting.getDate(), is(future));

	}

	/**
	 * test for a contact when contacts are empty
	 */
	@Test
	public void testGetContactOnEmpty() {
		ContactManager testCM = new ContactManagerImpl();
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
		ContactManager testCM = new ContactManagerImpl();
		testCM.addNewContact("Jane Decoy", "a decoy"); // add a decoy to make
														// things harder
		String testName = "Joe Bloggs";
		String testNotes = "test notes";
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
		ContactManager testCM = new ContactManagerImpl();
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
		ContactManager testCM = new ContactManagerImpl();
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
		ContactManager testCM = new ContactManagerImpl();
		testCM.addNewContact(null, "notes");
	}

	/**
	 * test adding an invalid contact with a null name produces NullPointer
	 * exception as specified in Interface
	 */
	@Test(expected = NullPointerException.class)
	public void testAddewContactNullNotes() {
		ContactManager testCM = new ContactManagerImpl();
		testCM.addNewContact("name", null);
	}

	/**
	 * test searching with an invalid id produces IllegalArgumentException as
	 * specified in Interface {@link ContactManager#getContacts(int...)
	 * getContacts}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetContactsInvalidID() {
		ContactManager testCM = new ContactManagerImpl();
		testCM.getContacts(-10000);
	}

	/**
	 * test searching with null name produces NullPointerException as specified
	 * in interface
	 */
	@Test(expected = NullPointerException.class)
	public void testGetContactsNullName() {
		ContactManager testCM = new ContactManagerImpl();
		String strNull = null;
		testCM.getContacts(strNull);
	}
}
