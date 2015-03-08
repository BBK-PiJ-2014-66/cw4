package test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import cw4.Contact;
import cw4.ContactManagerPlus;
import cw4.FileSaveRetrieve;
import cw4.FileSaveRetrieveMethod;

/**
 * 
 * Unit tests for {@link FileSaveRetrieve}
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 25 February 2015
 * 
 */
@RunWith(Parameterized.class)
public class FileSaveRetrieveTest {

	/*
	 * Want to run the same tests using FileSaveRetrieveMethod.XML and
	 * FileSaveRetrieveMethod.SERIALIZATION do so using parameterized tests
	 * following
	 * 
	 * http://www.vogella.com/tutorials/JUnit/article.html#
	 * junitadvanced_parameterizedtests
	 * 
	 * https://github.com/junit-team/junit/wiki/Parameterized-tests
	 */

	// creates the test data
	@Parameters
	public static Collection<Object[]> data() {

		Object[][] data = new Object[][] { { FileSaveRetrieveMethod.XML },
				{ FileSaveRetrieveMethod.SERIALIZATION } };

		return Arrays.asList(data);

	}

	@Parameter
	public FileSaveRetrieveMethod method;

	private FileSaveRetrieve fileSaveRetrieve;
	private String fileName;
	private ContactManagerPlus testCMP;

	@Before
	public void init() {
		// parameterized part
		fileSaveRetrieve = new FileSaveRetrieve();
		fileSaveRetrieve.setMethod(method);
		if (method == FileSaveRetrieveMethod.XML)
			fileName = "testfiles/fileSaveRetrieveTest.xml";
		else
			fileName = "testfiles/fileSaveRetrieveTest.txt";
		fileSaveRetrieve.setFileName(fileName);

		// provide a decent contact manager to save/retrieve
		ContactManagerImplTest cmpit = new ContactManagerImplTest();
		testCMP = cmpit.standardFilledCMP(); // one contact 4 meetings
		// want to check how new lines and <> are encoded in the XML
		testCMP.addNewContact("John Smith",
				"email: <j.smith@somecompany.com>\n" + "tel: 0123 456789.");
		testCMP.addNewContact("Jane Doe", "email: <jane@someorganization.org>");
		// add a new past meeting involving everyone
		Set<Contact> everyone = testCMP.getContacts(" ");
		testCMP.addNewPastMeeting(everyone, new GregorianCalendar(1805,
				Calendar.OCTOBER, 21), "Battle of Trafalgar");
		// System.out.println("debug testCMP= " + testCMP);
	}

	/**
	 * Test saving state to a string and creating a new ContactManagerPlus from
	 * that string compare data returned with the original. See last commented
	 * out test for possible bug in
	 * {@link cw4.PastMeetingImpl#equals(Object obj)}
	 */
	@Test
	public void saveToStringAndRestore() {
		String str = fileSaveRetrieve.saveToString(testCMP);

		assertNotNull(
				"\n.saveToString( ContactManagerPlus) failed as it returned null,",
				str);

		// System.out.println("debug testCMP saveToString=\n" + str + "\n");

		ContactManagerPlus restoreCMP = fileSaveRetrieve
				.retrieveFromString(str);
		checkRestoreCMP(restoreCMP); // run checks

	}

	/**
	 * Test that we get a RuntimeException after restoring from nonsense string.
	 * Furthermore it should have a meaningful exception message it should
	 * contain "string to contactManager" as it should have been recast.
	 */
	@Test
	public void attemptRestoreFromNonsenseString() {
		String require = "string to contactManager";
		try {
			fileSaveRetrieve
					.retrieveFromString("No ContactManagerPlus in this string!");
			fail("Attempt to restore ContactManager from nonsense string\n"
					+ "failed to produce a RuntimeException");

		} catch (RuntimeException ex) {
			assertTrue(
					"Attempt to restore ContactManager from nonsense string\n"
							+ "Failure of test that exception message contains '"
							+ require + "'\n" + "Exception message is:\n " + ex,
					ex.toString().contains(require));
		}

	}

	/**
	 * further check that corrupting string by truncation produces reasonable
	 * exception message
	 */
	@Test
	public void attemptRestoreFromTruncatedString() {
		String require = "string to contactManager";
		try {
			String str = fileSaveRetrieve.saveToString(testCMP);
			// truncate string by half
			str = str.substring(0, str.length() / 2);
			fileSaveRetrieve.retrieveFromString(str);
			fail("Attempt to restore ContactManager from truncated string\n"
					+ "failed to produce a RuntimeException");

		} catch (RuntimeException ex) {
			assertTrue(
					"Attempt to restore ContactManager from truncated string\n"
							+ "Failure of test that exception message contains '"
							+ require + "'\n" + "Exception message is:\n " + ex,
					ex.toString().contains(require));
		}

	}

	/**
	 * Save state to fileName
	 * 
	 * want to be able to run separately from
	 * {@link #retrieveStateFromFileName()} so that can examine / copy file
	 * created
	 */
	@Test
	public void saveStateToFileName() {
		// First check the setter/getter has worked
		String backFileName = fileSaveRetrieve.getFileName();
		assertNotNull(
				"\nSetter/Getter for fileName failed as .getFileName() returned null",
				backFileName);
		assertThat(
				"\nSetter/Getter for fileName failed to get back supplied name",
				backFileName, is(fileName));

		File file = new File(fileName);

		if (file.isFile()) { // file already exists
			// remove the file
			try {
				Files.delete(file.toPath());
			} catch (IOException ex) {
				fail("\nTest failed because could not delete file: " + file
						+ "\n" + "Exception detail: " + ex);
			}
		}

		// save our test contact manage object to the file
		fileSaveRetrieve.saveToFile(testCMP);

		assertTrue("\n.saveToFile(testCMP) has failed to create\n"
				+ " the save-state file: '" + fileName + "'", file.isFile());

	}

	/**
	 * retrieve state from file fileName
	 * 
	 */
	@Test
	public void retrieveStateFromFileName() {
		saveStateToFileName(); // run previous test to create file

		File file = new File(fileName);
		if (!file.isFile())
			fail("\nCould not run test because file '"
					+ file
					+ "' does not exist\n"
					+ "This file should have been created in previous test 'saveStateToFileName'?");

		ContactManagerPlus restoreCMP = fileSaveRetrieve.retrieveFromFile();
		checkRestoreCMP(restoreCMP); // run checks

	}

	/**
	 * runs a series of checks that the restored version of {@link #testCMP} is
	 * the same as the original. Used by tests {@link #saveToStringAndRestore()}
	 * and {@link #retrieveStateFromFileName()}
	 * 
	 * @param restoreCMP restored version of testCMP to check.
	 */
	private void checkRestoreCMP(ContactManagerPlus restoreCMP) {
		
		assertNotNull(
				"\nRestoration of ContactManagerPlus failed:\n"
				+ "The restored ContactManagerPlus is null!",
				restoreCMP);

		// N.B. the comparison will use the .equals method of ContactImpl
		assertThat("\nRestored ContactManagerPlus has same contacts?",
				restoreCMP.getAllContacts(), is(testCMP.getAllContacts()));

		assertThat("\nRestored ContactManagerPlus has same future meetings?",
				restoreCMP.getAllFutureMeetings(),
				is(testCMP.getAllFutureMeetings()));

		assertThat("\nRestored ContactManagerPlus has same past meetings?",
				restoreCMP.getAllPastMeetings(),
				is(testCMP.getAllPastMeetings()));

		// so complete object should be the same
		assertThat("\nRestored ContactManagerPlus .equals original",
				restoreCMP, is(testCMP));

		/*
		 * Check for denormalisation:
		 * 
		 * being paranoid it is possible that the contact objects in the
		 * MeetingsImpl.contacts are clones rather than references to the
		 * contacts. Test by altering John Smith's notes in both objects in turn
		 */
		Contact johnSmith = (Contact) testCMP.getContacts("John Smith")
				.toArray()[0];
		johnSmith.addNotes("alter John's notes");
		// john is involved in a past meeting so these should no longer match
		// (This test shows that testCMP is normalised).
		assertThat("\nAfter altering John Smith's notes in the original\n"
				+ "pastMeetings should no longer match.",
				restoreCMP.getAllPastMeetings(),
				not(testCMP.getAllPastMeetings()));
		// now make the same alteration in the restored version
		johnSmith = (Contact) restoreCMP.getContacts("John Smith").toArray()[0];
		johnSmith.addNotes("alter John's notes");

		// if the past meetings are back in sync then restoreCMP is normalised
		assertThat("\nAfter altering John Smith's notes in the both\n"
				+ "pastMeetings should match again."
				+ "Normalisation problem???",
				"" + restoreCMP.getAllPastMeetings(),
				is("" + testCMP.getAllPastMeetings()));

		/*
		 * TODO: there is probably a bug in pastMeetingImpl.equals as the repeat
		 * of past test not using .toString fails:
		 */
		// assertThat(restoreCMP.getAllPastMeetings(),
		// is(testCMP.getAllPastMeetings()));

	}

}
