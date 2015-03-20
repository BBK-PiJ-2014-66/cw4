

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.fictitiousurl.contactmanager.ContactManagerImpl;
import uk.fictitiousurl.contactmanager.ContactManagerPlus;

/**
 * Single test run in isolation:
 * 
 * idea test constructor load from "testfiles/contacts.txt" this is written as
 * file "fileSaveRetrieveTest.txt" by the test
 * {@link uk.fictitiousurl.contactmanagertest.FileSaveRetrieveTest#saveStateToFileName()}
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 *
 */
public class RetrieveContactsDotTxtTest {

	@Test
	public void testContactManagerFrom_Test_ContactsDotTxt() {
		String testFile = "testfiles/contacts.txt";
		ContactManagerPlus retrieveCMP = new ContactManagerImpl(testFile);
		// just check number of contacts, # meetings and override date
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has three contacts.", retrieveCMP.getAllContacts()
				.size(), is(3));
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has two past meetings.", retrieveCMP
				.getAllPastMeetings().size(), is(2));
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has three future meetings.", retrieveCMP
				.getAllFutureMeetings().size(), is(3));
		// check for problems in the id generation by adding 1000 contacts
		for (int cc = 0; cc < 1000; cc++)
			retrieveCMP.addNewContact("new contact #" + (cc+1), "notes");

	}

}