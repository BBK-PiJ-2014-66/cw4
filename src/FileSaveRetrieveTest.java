

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uk.fictitiousurl.contactmanager.Contact;
import uk.fictitiousurl.contactmanager.ContactManagerPlus;
import uk.fictitiousurl.contactmanager.FileSaveRetrieve;
import uk.fictitiousurl.contactmanager.FileSaveRetrieveImpl;

/**
 * 
 * Unit tests for {@link FileSaveRetrieveImpl}
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 25 February 2015
 * 
 */
public class FileSaveRetrieveTest {


	private FileSaveRetrieve fileSaveRetrieve;
	private String fileName;
	private ContactManagerPlus testCMP;

	@Before
	public void init() {
		fileSaveRetrieve = new FileSaveRetrieveImpl();
		fileName = "testfiles/fileSaveRetrieveTest.txt";
		fileSaveRetrieve.setFileName(fileName);

		// provide a decent contact manager to save/retrieve
		ContactManagerImplTest cmpit = new ContactManagerImplTest();
		// one contact 4 meetings - N.B. also overrides date to "nowCal"
		testCMP = cmpit.standardFilledCMP(); 
		// want to check how new lines and <> are encoded in the XML
		testCMP.addNewContact("John Smith",
				"email: <j.smith@somecompany.com>\n" + "tel: 0123 456789.");
		testCMP.addNewContact("Jane Doe", "email: <jane@someorganization.org>");
		// add a new past meeting involving everyone
		Set<Contact> everyone = testCMP.getContacts(" ");
		testCMP.addNewPastMeeting(everyone, new GregorianCalendar(1805,
				Calendar.OCTOBER, 21), "Battle of Trafalgar");
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
		checkRestoreCMP(testCMP, restoreCMP); // run checks

	}

	/**
	 * runs a series of checks that the restored version of a contactManger is
	 * the same as the original. Used by by many tests!
	 * 
	 * @param origCMP
	 *            the original
	 * @param restoreCMP
	 *            restored version to check.
	 */
	static void checkRestoreCMP(ContactManagerPlus origCMP,
			ContactManagerPlus restoreCMP) {

		assertNotNull("\nRestoration of ContactManagerPlus failed:\n"
				+ "The restored ContactManagerPlus is null!", restoreCMP);

		assertThat("\nRestored ContactManagerPlus has same contacts?",
				restoreCMP.getAllContacts(), is(origCMP.getAllContacts()));

		assertThat("\nRestored ContactManagerPlus has same future meetings?",
				restoreCMP.getAllFutureMeetings(),
				is(origCMP.getAllFutureMeetings()));

		assertThat("\nRestored ContactManagerPlus has same past meetings?",
				restoreCMP.getAllPastMeetings(),
				is(origCMP.getAllPastMeetings()));


		/*
		 * Check for denormalisation:
		 * 
		 * being paranoid it is possible that the contact objects in the
		 * MeetingsImpl.contacts are clones rather than references to the
		 * contacts. Test by altering all user's notes in both objects in turn
		 */
		for (Contact itCon : origCMP.getAllContacts()) {
			itCon.addNotes("altered user notes");
		}
		// so meeting lists should not now match
		// (This test shows that origCMP is normalised).
		if (origCMP.getAllPastMeetings().size() > 0)
			assertThat("\nAfter altering all users notes in the original\n"
					+ "pastMeetings should no longer match.",
					restoreCMP.getAllPastMeetings(),
					not(origCMP.getAllPastMeetings()));
		if (origCMP.getAllFutureMeetings().size() > 0)
			assertThat("\nAfter altering all users notes in the original\n"
					+ "pastMeetings should no longer match.",
					restoreCMP.getAllFutureMeetings(),
					not(origCMP.getAllFutureMeetings()));
		// make same change on restored
		for (Contact itCon : restoreCMP.getAllContacts()) {
			itCon.addNotes("altered user notes");
		}

		// the two objects should be back in sync
		if (origCMP.getAllPastMeetings().size() > 0) 
			assertThat("\nAfter altering all users notes in both\n"
					+ "pastMeetings should match again.",
					restoreCMP.getAllPastMeetings(),
					is(origCMP.getAllPastMeetings()));

		// the two objects should be back in sync
		if (origCMP.getAllFutureMeetings().size() > 0)
			assertThat("\nAfter altering all users notes in both\n"
					+ "future Meetings should match again.",
					restoreCMP.getAllFutureMeetings(),
					is(origCMP.getAllFutureMeetings()));

	}

}
