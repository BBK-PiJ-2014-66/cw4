package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

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
public class FileSaveRetrieveTest {

	private FileSaveRetrieve fileSaveRetrieve;
	private ContactManagerPlus testCMP;

	@Before
	public void init() {
		// for now set mode to XML
		fileSaveRetrieve = new FileSaveRetrieve();
		fileSaveRetrieve.setMode(FileSaveRetrieveMethod.XML);
		// provide a decent contact manager to save/retrieve
		ContactManagerImplTest cmpit = new ContactManagerImplTest();
		testCMP = cmpit.standardFilledCMP(); // one contact 4 meetings
		// want to check how new lines and <> are encoded in the XML
		testCMP.addNewContact("John Smith",
				"email: <j.smith@somecompany.com>\n" + "tel: 0123 456789.");
		testCMP.addNewContact("Jane Doe",
				"email: <jane@someorganization.org>\n");
		// add a new past meeting involving everyone
		Set<Contact> everyone = testCMP.getContacts(" ");
		testCMP.addNewPastMeeting(everyone, new GregorianCalendar(1805,
				Calendar.OCTOBER, 21), "Battle of Trafalgar");
		//System.out.println("debug testCMP= " + testCMP);
	}


	/**
	 * Test saving state to a string and creating a new ContactManagerPlus from
	 * that string compare data returned with the original
	 */
	@Test
	public void saveToStringAndRestore() {
		String str = fileSaveRetrieve.saveToString(testCMP);
		//System.out.println("debug testCMP saveToString=\n" + str + "\n");
		
		ContactManagerPlus restoreCMP = fileSaveRetrieve
				.retrieveFromString(str);
		
		// N.B. the comparison will use the .equals method of ContactImpl
		assertThat("\nRestored ContactManagerPlus has same contacts?",
				restoreCMP.getAllContacts(), is(testCMP.getAllContacts()));

		assertThat("\nRestored ContactManagerPlus has same future meetings?",
				restoreCMP.getAllFutureMeetings(), is(testCMP.getAllFutureMeetings()));

		assertThat("\nRestored ContactManagerPlus has same past meetings?",
				restoreCMP.getAllPastMeetings(), is(testCMP.getAllPastMeetings()));

	}

}
